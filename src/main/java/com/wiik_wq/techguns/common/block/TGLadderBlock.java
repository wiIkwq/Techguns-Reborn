package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGLadderBlock extends LadderBlock {

    private static final double LADDER_SIZE = 2.0D;
    private static final VoxelShape LADDER_EAST_AABB = box(0.0D, 0.0D, 0.0D, LADDER_SIZE, 16.0D, 16.0D);
    private static final VoxelShape LADDER_WEST_AABB = box(16.0D - LADDER_SIZE, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape LADDER_SOUTH_AABB = box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, LADDER_SIZE);
    private static final VoxelShape LADDER_NORTH_AABB = box(0.0D, 0.0D, 16.0D - LADDER_SIZE, 16.0D, 16.0D, 16.0D);

    public TGLadderBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState base = this.defaultBlockState();
        Direction clickedFace = context.getClickedFace();

        if (clickedFace.getAxis().isHorizontal()) {
            return base.setValue(FACING, clickedFace);
        }

        BlockPos pos = context.getClickedPos();
        BlockState below = context.getLevel().getBlockState(pos.below());
        if (below.is(this)) {
            return base.setValue(FACING, below.getValue(FACING));
        }

        BlockState above = context.getLevel().getBlockState(pos.above());
        if (above.is(this)) {
            return base.setValue(FACING, above.getValue(FACING));
        }

        return base.setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> LADDER_NORTH_AABB;
            case SOUTH -> LADDER_SOUTH_AABB;
            case WEST -> LADDER_WEST_AABB;
            case EAST -> LADDER_EAST_AABB;
            default -> LADDER_NORTH_AABB;
        };
    }
}
