package com.jayemuaxd.dark.core.block;

import com.jayemuaxd.dark.core.alchemy.Element;
import com.jayemuaxd.dark.core.alchemy.ElementAmount;
import com.jayemuaxd.dark.core.alchemy.ElementData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class DarkCalderoBlock extends Block {
    // 0 = vacío, 1 = agua, 2 = veneno
    public static final IntegerProperty TIPO = IntegerProperty.create("tipo", 0, 2);

    public DarkCalderoBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TIPO, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIPO);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(TIPO, 0);
    }

    // Uso: mano vacía cicla el tipo; mano con item intenta extraer esencias y llenar botellas
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);

        // Mano vacía: comportamiento previo (ciclar tipo)
        if (held.isEmpty()) {
            int current = state.getValue(TIPO);
            int next = (current + 1) % 3; // ciclo 0->1->2->0

            level.setBlock(pos, state.setValue(TIPO, next), 3);
            return InteractionResult.CONSUME;
        }

        // Si el jugador sostiene una botella ya llena de esencia, no hacemos nada aquí
        // (el depósito/mesa se encargará más adelante). Solo procesamos ítems fuente.
        if (isEssenceBottle(held)) {
            // No almacenamos ni procesamos botellas en el Caldero en esta etapa.
            return InteractionResult.PASS;
        }

        // Para extraer/esencia: el Caldero debe estar en estado AGUA (TIPO==1) y "hirviendo"
        if (state.getValue(TIPO) != 1 || !isBoiling(level, pos)) {
            // No está listo para extraer
            return InteractionResult.PASS;
        }

        // Intentar resolver elementos del item sostenido
        List<ElementAmount> elements = ElementData.resolve(held);
        if (elements == null || elements.isEmpty()) {
            // Nada que extraer
            return InteractionResult.PASS;
        }

        // Consumir 1 del item de entrada (si no está en creative)
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }

        boolean gaveAnything = false;

        // Por cada elemento resultante, creamos UNA botella de cristal con NBT describiendo el elemento
        for (ElementAmount ea : elements) {
            Element elem = ea.element();
            int amount = ea.amount();

            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            CompoundTag tag = bottle.getOrCreateTag();
            CompoundTag dc = new CompoundTag();
            dc.putString("element", elem.name());
            dc.putInt("amount", amount);
            tag.put("DarkCore", dc);

            // Intentamos añadir al inventario del jugador; si no cabe, soltamos la entidad en el mundo
            boolean added = player.addItem(bottle);
            if (!added) {
                ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, bottle);
                level.addFreshEntity(entity);
            }
            gaveAnything = true;
        }

        if (gaveAnything) {
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    private boolean isEssenceBottle(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        if (stack.getItem() != Items.GLASS_BOTTLE) return false;
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("DarkCore");
    }

    private boolean isBoiling(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        if (below.is(Blocks.LAVA) || below.is(Blocks.FIRE) || below.is(Blocks.CAMPFIRE) || below.is(Blocks.SOUL_FIRE) || below.is(Blocks.SOUL_CAMPFIRE)) {
            return true;
        }
        return false;
    }
}
