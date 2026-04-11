package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGCamoNetBlock extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    private static final VoxelShape CENTER = box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape ARM_NORTH = box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 7.0D);
    private static final VoxelShape ARM_EAST = box(9.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    private static final VoxelShape ARM_SOUTH = box(7.0D, 0.0D, 9.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape ARM_WEST = box(0.0D, 0.0D, 7.0D, 7.0D, 16.0D, 9.0D);

    public TGCamoNetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
        return this.updateConnections(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, net.minecraft.world.level.LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return this.updateConnections(state, level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    protected boolean connectsTo(BlockState state) {
        return state.getBlock() instanceof TGCamoNetBlock;
    }

    protected BlockState updateConnections(BlockState state, LevelReader level, BlockPos pos) {
        return state
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(EAST, connectsTo(level.getBlockState(pos.east())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(WEST, connectsTo(level.getBlockState(pos.west())));
    }

    private VoxelShape shapeFor(BlockState state) {
        VoxelShape shape = CENTER;
        if (state.getValue(NORTH)) {
            shape = Shapes.join(shape, ARM_NORTH, BooleanOp.OR);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.join(shape, ARM_EAST, BooleanOp.OR);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.join(shape, ARM_SOUTH, BooleanOp.OR);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.join(shape, ARM_WEST, BooleanOp.OR);
        }
        return shape;
    }
}
