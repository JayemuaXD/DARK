package com.jayemuaxd.dark.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

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

    // No implementamos EntityBlock ni BlockEntity — usamos propiedad de bloque para el estado
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        int current = state.getValue(TIPO);
        int next = (current + 1) % 3; // ciclo 0->1->2->0

        level.setBlock(pos, state.setValue(TIPO, next), 3);
        return InteractionResult.CONSUME;
    }
}
