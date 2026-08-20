package com.jayemuaxd.dark.core.block;

import com.jayemuaxd.dark.core.alchemy.ElementAmount;
import com.jayemuaxd.dark.core.alchemy.ElementData;
import com.jayemuaxd.dark.core.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DarkCalderoBlockEntity extends BlockEntity {
    private boolean processing = false;
    private int progress = 0;
    private int totalTime = 200; // ticks (10 seconds)
    private List<ElementAmount> result = null; // result produced after processing

    public DarkCalderoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CALDERO.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DarkCalderoBlockEntity be) {
        if (level.isClientSide) return;
        if (!be.processing) return;

        be.progress++;
        if (be.progress >= be.totalTime) {
            be.finishProcessing(level, pos, state);
        }
    }

    public boolean isProcessing() {
        return processing;
    }

    public boolean hasResult() {
        return result != null && !result.isEmpty();
    }

    /** Start processing a single item (the itemstack passed should represent the consumed item) */
    public void startProcessingForItem(net.minecraft.world.item.ItemStack sample) {
        // resolve elements immediately and store them as the transient result
        List<ElementAmount> resolved = ElementData.resolve(sample);
        this.result = new ArrayList<>(resolved);
        this.processing = true;
        this.progress = 0;
        setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    private void finishProcessing(Level level, BlockPos pos, BlockState state) {
        this.processing = false;
        this.progress = 0; // keep result available until player extracts
        setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
    }

    /** Consume and return the result; afterwards the BE has no result */
    public List<ElementAmount> consumeResult() {
        List<ElementAmount> r = result;
        result = null;
        setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        return r == null ? List.of() : r;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("processing", processing);
        tag.putInt("progress", progress);
        tag.putInt("totalTime", totalTime);
        if (result != null && !result.isEmpty()) {
            ListTag list = new ListTag();
            for (ElementAmount ea : result) {
                CompoundTag e = new CompoundTag();
                e.putString("element", ea.element().name());
                e.putInt("amount", ea.amount());
                list.add(e);
            }
            tag.put("result", list);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.processing = tag.getBoolean("processing");
        this.progress = tag.getInt("progress");
        if (tag.contains("totalTime")) this.totalTime = tag.getInt("totalTime");

        if (tag.contains("result", Tag.TAG_LIST)) {
            ListTag list = tag.getList("result", Tag.TAG_COMPOUND);
            List<ElementAmount> res = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                CompoundTag e = list.getCompound(i);
                String name = e.getString("element");
                int amount = e.getInt("amount");
                try {
                    var el = com.jayemuaxd.dark.core.alchemy.Element.valueOf(name);
                    res.add(ElementAmount.of(el, amount));
                } catch (IllegalArgumentException ignored) {
                }
            }
            this.result = res;
        } else {
            this.result = null;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}
