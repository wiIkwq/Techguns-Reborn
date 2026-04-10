package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGLanternBlock extends Block {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    private static final VoxelShape SHAPE = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 14.0D, 12.0D);

    public TGLanternBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DOWN, false)
                .setValue(UP, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DOWN, UP, NORTH, SOUTH, WEST, EAST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = computeConnections(context.getLevel(), context.getClickedPos());
        return hasAnySupport(state) ? state : null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = computeConnections(level, pos);
        return hasAnySupport(updated) ? updated : net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return hasAnySupport(computeConnections(level, pos));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    private BlockState computeConnections(LevelReader level, BlockPos pos) {
        return this.defaultBlockState()
                .setValue(UP, hasSupport(level, pos, Direction.UP))
                .setValue(DOWN, hasSupport(level, pos, Direction.DOWN))
                .setValue(NORTH, hasSupport(level, pos, Direction.NORTH))
                .setValue(SOUTH, hasSupport(level, pos, Direction.SOUTH))
                .setValue(WEST, hasSupport(level, pos, Direction.WEST))
                .setValue(EAST, hasSupport(level, pos, Direction.EAST));
    }

    private boolean hasAnySupport(BlockState state) {
        return state.getValue(UP)
                || state.getValue(DOWN)
                || state.getValue(NORTH)
                || state.getValue(SOUTH)
                || state.getValue(WEST)
                || state.getValue(EAST);
    }

    private boolean hasSupport(LevelReader level, BlockPos pos, Direction direction) {
        BlockPos supportPos = pos.relative(direction);
        BlockState supportState = level.getBlockState(supportPos);
        return supportState.isFaceSturdy(level, supportPos, direction.getOpposite());
    }
}
