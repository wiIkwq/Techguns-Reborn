package com.wiik_wq.techguns.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TGSandbagsBlock extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty CORNER_NE = BooleanProperty.create("corner_ne");
    public static final BooleanProperty CORNER_ES = BooleanProperty.create("corner_es");
    public static final BooleanProperty CORNER_SW = BooleanProperty.create("corner_sw");
    public static final BooleanProperty CORNER_WN = BooleanProperty.create("corner_wn");

    private static final VoxelShape CENTER = box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape NORTH_ARM = box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D);
    private static final VoxelShape EAST_ARM = box(12.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
    private static final VoxelShape SOUTH_ARM = box(4.0D, 0.0D, 12.0D, 12.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_ARM = box(0.0D, 0.0D, 4.0D, 4.0D, 16.0D, 12.0D);
    private static final VoxelShape[] SHAPES = makeShapes();

    public TGSandbagsBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(CORNER_NE, false)
                .setValue(CORNER_ES, false)
                .setValue(CORNER_SW, false)
                .setValue(CORNER_WN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, CORNER_NE, CORNER_ES, CORNER_SW, CORNER_WN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateConnections(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return updateConnections(state, level, pos);
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

    private BlockState updateConnections(BlockState state, LevelReader level, BlockPos pos) {
        boolean north = connectsTo(level, pos, Direction.NORTH);
        boolean east = connectsTo(level, pos, Direction.EAST);
        boolean south = connectsTo(level, pos, Direction.SOUTH);
        boolean west = connectsTo(level, pos, Direction.WEST);

        return state
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(CORNER_NE, north && east && connectsCorner(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(CORNER_ES, east && south && connectsCorner(level, pos, Direction.EAST, Direction.SOUTH))
                .setValue(CORNER_SW, south && west && connectsCorner(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(CORNER_WN, west && north && connectsCorner(level, pos, Direction.WEST, Direction.NORTH));
    }

    private boolean connectsCorner(LevelReader level, BlockPos pos, Direction first, Direction second) {
        return connectsTo(level, pos.relative(first), second) || connectsTo(level, pos.relative(second), first);
    }

    private boolean connectsTo(LevelReader level, BlockPos pos, Direction direction) {
        BlockPos otherPos = pos.relative(direction);
        BlockState otherState = level.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();

        if (otherBlock instanceof TGSandbagsBlock || otherBlock instanceof FenceBlock || otherBlock instanceof FenceGateBlock) {
            return true;
        }

        return otherState.isFaceSturdy(level, otherPos, direction.getOpposite());
    }

    private VoxelShape shapeFor(BlockState state) {
        return SHAPES[shapeIndex(state)];
    }

    private static VoxelShape[] makeShapes() {
        VoxelShape[] shapes = new VoxelShape[16];
        for (int index = 0; index < shapes.length; index++) {
            VoxelShape shape = CENTER;
            if ((index & 1) != 0) {
                shape = Shapes.join(shape, NORTH_ARM, BooleanOp.OR);
            }
            if ((index & 2) != 0) {
                shape = Shapes.join(shape, EAST_ARM, BooleanOp.OR);
            }
            if ((index & 4) != 0) {
                shape = Shapes.join(shape, SOUTH_ARM, BooleanOp.OR);
            }
            if ((index & 8) != 0) {
                shape = Shapes.join(shape, WEST_ARM, BooleanOp.OR);
            }
            shapes[index] = shape;
        }
        return shapes;
    }

    private static int shapeIndex(BlockState state) {
        int index = 0;
        if (state.getValue(NORTH)) {
            index |= 1;
        }
        if (state.getValue(EAST)) {
            index |= 2;
        }
        if (state.getValue(SOUTH)) {
            index |= 4;
        }
        if (state.getValue(WEST)) {
            index |= 8;
        }
        return index;
    }
}
