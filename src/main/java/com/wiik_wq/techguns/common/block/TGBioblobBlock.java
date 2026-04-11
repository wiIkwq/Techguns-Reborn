package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGBioblobBlock extends DirectionalBlock {

    public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 2);

    private static final int SMALL_WIDTH = 5;
    private static final int SMALL_HEIGHT = 3;
    private static final int MEDIUM_WIDTH = 3;
    private static final int MEDIUM_HEIGHT = 4;
    private static final int LARGE_WIDTH = 1;
    private static final int LARGE_HEIGHT = 5;

    public TGBioblobBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.DOWN)
                .setValue(SIZE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIZE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(SIZE), state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(SIZE), state.getValue(FACING));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    private static VoxelShape shapeFor(int size, Direction facing) {
        return switch (size) {
            case 1 -> shapeFor(MEDIUM_WIDTH, MEDIUM_HEIGHT, facing);
            case 2 -> shapeFor(LARGE_WIDTH, LARGE_HEIGHT, facing);
            default -> shapeFor(SMALL_WIDTH, SMALL_HEIGHT, facing);
        };
    }

    private static VoxelShape shapeFor(int width, int height, Direction facing) {
        return switch (facing) {
            case DOWN -> box(width, 0.0D, width, 16.0D - width, height, 16.0D - width);
            case UP -> box(width, 16.0D - height, width, 16.0D - width, 16.0D, 16.0D - width);
            case NORTH -> box(width, width, 0.0D, 16.0D - width, 16.0D - width, height);
            case SOUTH -> box(width, width, 16.0D - height, 16.0D - width, 16.0D - width, 16.0D);
            case WEST -> box(0.0D, width, width, height, 16.0D - width, 16.0D - width);
            case EAST -> box(16.0D - height, width, width, 16.0D, 16.0D - width, 16.0D - width);
        };
    }
}
