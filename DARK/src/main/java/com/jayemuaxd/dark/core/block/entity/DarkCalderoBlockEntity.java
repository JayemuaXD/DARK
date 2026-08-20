package com.jayemuaxd.dark.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DarkCalderoBlockEntity extends BlockEntity {
    private String tipoDeLiquido = "vacío";

    public DarkCalderoBlockEntity(BlockPos pos, BlockState state, BlockEntityType<?> type) {
        super(type, pos, state);
    }

    public String getTipoDeLiquido() {
        return tipoDeLiquido == null ? "vacío" : tipoDeLiquido;
    }

    public void setTipoDeLiquido(String tipo) {
        this.tipoDeLiquido = tipo;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("tipoDeLiquido")) {
            this.tipoDeLiquido = tag.getString("tipoDeLiquido");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("tipoDeLiquido", getTipoDeLiquido());
    }
}
