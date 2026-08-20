package com.jayemuaxd.dark.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;

public class DarkCalderoBlock extends Block implements EntityBlock {
    public DarkCalderoBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new net.minecraft.world.level.block.entity.BlockEntity(pos, state) {
            // placeholder anonymous BlockEntity to satisfy compilation if actual BE not yet present
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof com.jayemuaxd.dark.core.block.entity.DarkCalderoBlockEntity caldero) {
            String current = caldero.getTipoDeLiquido();
            String next;
            if ("vacío".equals(current)) next = "agua";
            else if ("agua".equals(current)) next = "veneno";
            else next = "vacío";

            caldero.setTipoDeLiquido(next);
            caldero.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
}
