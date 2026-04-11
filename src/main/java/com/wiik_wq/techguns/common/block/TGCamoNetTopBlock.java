package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGCamoNetTopBlock extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    private static final double H = 1.0D;
    private static final VoxelShape[] SHAPES = {
            box(4.0D, 0.0D, 4.0D, 12.0D, H, 12.0D), // none
            box(0.0D, 0.0D, 4.0D, 9.0D, H, 12.0D),  // w
            box(4.0D, 0.0D, 7.0D, 12.0D, H, 16.0D), // s
            box(0.0D, 0.0D, 7.0D, 9.0D, H, 16.0D),  // sw
            box(7.0D, 0.0D, 4.0D, 16.0D, H, 12.0D), // e
            box(0.0D, 0.0D, 4.0D, 16.0D, H, 12.0D), // ew
            box(7.0D, 0.0D, 7.0D, 16.0D, H, 16.0D), // es
            box(0.0D, 0.0D, 7.0D, 16.0D, H, 16.0D), // esw
            box(4.0D, 0.0D, 0.0D, 12.0D, H, 9.0D),  // n
            box(0.0D, 0.0D, 0.0D, 9.0D, H, 9.0D),   // nw
            box(4.0D, 0.0D, 0.0D, 12.0D, H, 16.0D), // ns
            box(0.0D, 0.0D, 0.0D, 9.0D, H, 16.0D),  // nsw
            box(7.0D, 0.0D, 0.0D, 16.0D, H, 9.0D),  // ne
            box(0.0D, 0.0D, 0.0D, 16.0D, H, 9.0D),  // new
            box(7.0D, 0.0D, 0.0D, 16.0D, H, 16.0D), // nes
            box(0.0D, 0.0D, 0.0D, 16.0D, H, 16.0D)  // nesw
    };

    public TGCamoNetTopBlock(BlockBehaviour.Properties properties) {
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
        return updateConnections(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, net.minecraft.world.level.LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return updateConnections(state, level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
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
        return state.getBlock() instanceof TGCamoNetTopBlock;
    }

    protected BlockState updateConnections(BlockState state, BlockGetter level, BlockPos pos) {
        return state
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(EAST, connectsTo(level.getBlockState(pos.east())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(WEST, connectsTo(level.getBlockState(pos.west())));
    }

    private VoxelShape shapeFor(BlockState state) {
        return SHAPES[connectionIndex(state)];
    }

    private int connectionIndex(BlockState state) {
        int index = 0;
        if (state.getValue(NORTH)) {
            index += 8;
        }
        if (state.getValue(EAST)) {
            index += 4;
        }
        if (state.getValue(SOUTH)) {
            index += 2;
        }
        if (state.getValue(WEST)) {
            index += 1;
        }
        return index;
    }
}
