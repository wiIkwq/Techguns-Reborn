package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.block.TGBioblobBlock;
import com.wiik_wq.techguns.common.block.TGCamoNetBlock;
import com.wiik_wq.techguns.common.block.TGCamoNetTopBlock;
import com.wiik_wq.techguns.common.block.TGLanternBlock;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TGBlockStateProvider extends BlockStateProvider {

    public TGBlockStateProvider(net.minecraft.data.PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TechgunsReborn.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        TGBlockCatalog.CUBE_MODEL_BLOCKS.forEach(this::simpleExistingModel);
        bioblobModel();
        TGBlockCatalog.GENERATED_CUBE_ALL_BLOCKS.forEach(this::simpleGeneratedCube);
        TGBlockCatalog.HORIZONTAL_MODEL_BLOCKS.forEach(id -> horizontalExistingModel(id, TGBlockCatalog.HORIZONTAL_MODEL_NAMES.get(id)));
        TGBlockCatalog.DIRECTIONAL_MODEL_BLOCKS.forEach(id -> directionalExistingModel(id, TGBlockCatalog.DIRECTIONAL_MODEL_NAMES.get(id)));
        TGBlockCatalog.ROTATED_MODEL_BLOCKS.forEach((id, yRot) -> fixedRotationModel(id, "interiormarker", yRot));
        TGBlockCatalog.MILITARY_CRATE_TEXTURES.forEach(this::militaryCrateModel);
        TGBlockCatalog.CAMONET_BLOCKS.forEach(this::camonetModel);
        TGBlockCatalog.CAMONET_TOP_BLOCKS.forEach(this::camonetTopModel);
        TGBlockCatalog.LADDER_BLOCKS.forEach(this::ladderModel);
        TGBlockCatalog.STATIC_MODEL_BLOCKS.forEach(this::simpleExistingModel);
        TGBlockCatalog.LANTERN_BLOCKS.forEach(this::lanternModel);
        TGBlockCatalog.STAIRS.forEach(this::stairsModel);
    }

    private void simpleExistingModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        simpleBlock(block.get(), models().getExistingFile(modLoc("block/" + modelName)));
    }

    private void simpleGeneratedCube(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().cubeAll(id, modLoc("blocks/" + textureName));
        simpleBlock(block.get(), model);
    }

    private void horizontalExistingModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(model)
                .rotationY(rotationForHorizontal(state.getValue(HorizontalDirectionalBlock.FACING)))
                .build());
    }

    private void directionalExistingModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block.get()).forAllStates(state -> {
            Direction direction = state.getValue(com.wiik_wq.techguns.common.block.TGAttachedDirectionalBlock.FACING);
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(rotationX(direction))
                    .rotationY(rotationY(direction))
                    .build();
        });
    }

    private void bioblobModel() {
        RegistryObject<Block> block = entry(TGBlockCatalog.BIOBLOB);
        getVariantBuilder(block.get()).forAllStates(state -> {
            Direction direction = state.getValue(TGBioblobBlock.FACING);
            int size = state.getValue(TGBioblobBlock.SIZE);
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/bioblob" + size)))
                    .rotationX(bioblobRotationX(direction))
                    .rotationY(bioblobRotationY(direction))
                    .build();
        });
    }

    private void fixedRotationModel(String id, String modelName, int yRot) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block.get()).partialState().setModels(ConfiguredModel.builder().modelFile(model).rotationY(yRot).build());
    }

    private void militaryCrateModel(String id, java.util.List<String> textures) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().withExistingParent(id, modLoc("block/military_crate"))
                .texture("top", modLoc("blocks/" + textures.get(0)))
                .texture("side", modLoc("blocks/" + textures.get(1)))
                .texture("bottom", modLoc("blocks/" + textures.get(2)))
                .texture("particle", modLoc("blocks/" + textures.get(1)));
        simpleBlock(block.get(), model);
    }

    private void camonetModel(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ResourceLocation supportTexture = camonetSupportTexture(id);
        ResourceLocation netTexture = modLoc("blocks/" + textureName);
        ModelFile stick = camonetSubmodel(id + "_stick", "camonet_stick", supportTexture, netTexture);
        ModelFile half = camonetSubmodel(id + "_half", "camonet_half", supportTexture, netTexture);
        ModelFile full = camonetSubmodel(id + "_full", "camonet_full", supportTexture, netTexture);

        getVariantBuilder(block.get()).forAllStates(state -> camonetModels(state, stick, half, full));
    }

    private void camonetTopModel(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ResourceLocation netTexture = modLoc("blocks/" + textureName);
        ModelFile center = camonetTopSubmodel(id + "_center", "camonet_top_center", netTexture);
        ModelFile side = camonetTopSubmodel(id + "_side", "camonet_top_side", netTexture);
        ModelFile corner = camonetTopSubmodel(id + "_corner", "camonet_top_corner", netTexture);
        ModelFile side2 = camonetTopSubmodel(id + "_side2", "camonet_top_side2", netTexture);
        ModelFile full = camonetTopSubmodel(id + "_full", "camonet_top_full", netTexture);

        getVariantBuilder(block.get()).forAllStates(state -> camonetTopModels(state, center, side, corner, side2, full));
    }

    private void ladderModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block.get()).forAllStates(state -> {
            Direction direction = state.getValue(net.minecraft.world.level.block.LadderBlock.FACING);
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(rotationForLegacyLadder(direction));
            if ("slimyladder".equals(id)) {
                builder.rotationX(90);
            }
            return builder.build();
        });
    }

    private void stairsModel(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ModelFile stairs = models().stairs(id, modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        ModelFile stairsInner = models().stairsInner(id + "_inner", modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        ModelFile stairsOuter = models().stairsOuter(id + "_outer", modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        stairsBlock((StairBlock) block.get(), stairs, stairsInner, stairsOuter);
    }

    private void lanternModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        ModelFile base = models().getExistingFile(modLoc("block/" + modelName));
        ModelFile side = models().getExistingFile(modLoc("block/" + modelName.replace("lantern_", "lantern_side_")));
        ModelFile top = models().getExistingFile(modLoc("block/" + modelName.replace("lantern_", "lantern_top_")));

        var builder = getMultipartBuilder(block.get());
        builder.part().modelFile(base).addModel();
        builder.part().modelFile(side).rotationY(270).addModel().condition(TGLanternBlock.NORTH, true);
        builder.part().modelFile(side).rotationY(0).addModel().condition(TGLanternBlock.EAST, true);
        builder.part().modelFile(side).rotationY(90).addModel().condition(TGLanternBlock.SOUTH, true);
        builder.part().modelFile(side).rotationY(180).addModel().condition(TGLanternBlock.WEST, true);
        builder.part().modelFile(top).addModel().condition(TGLanternBlock.UP, true);
        builder.part().modelFile(top).rotationX(180).addModel().condition(TGLanternBlock.DOWN, true);
    }

    private ModelFile camonetSubmodel(String name, String parent, ResourceLocation supportTexture, ResourceLocation netTexture) {
        return models().withExistingParent(name, modLoc("block/" + parent))
                .texture("0", supportTexture)
                .texture("1", netTexture)
                .texture("particle", netTexture);
    }

    private ModelFile camonetTopSubmodel(String name, String parent, ResourceLocation netTexture) {
        return models().withExistingParent(name, modLoc("block/" + parent))
                .texture("0", netTexture)
                .texture("particle", netTexture);
    }

    private ConfiguredModel[] camonetModels(net.minecraft.world.level.block.state.BlockState state, ModelFile stick, ModelFile half, ModelFile full) {
        boolean north = state.getValue(TGCamoNetBlock.NORTH);
        boolean east = state.getValue(TGCamoNetBlock.EAST);
        boolean south = state.getValue(TGCamoNetBlock.SOUTH);
        boolean west = state.getValue(TGCamoNetBlock.WEST);

        List<ConfiguredModel> models = new ArrayList<>();

        if (!north && !east && !south && !west) {
            models.add(new ConfiguredModel(stick));
            return models.toArray(ConfiguredModel[]::new);
        }

        if (!north && east && !south && west) {
            models.add(new ConfiguredModel(full));
            return models.toArray(ConfiguredModel[]::new);
        }

        if (north && !east && south && !west) {
            models.add(new ConfiguredModel(full, 0, 270, true));
            return models.toArray(ConfiguredModel[]::new);
        }

        models.add(new ConfiguredModel(stick));
        if (north) {
            models.add(new ConfiguredModel(half, 0, 270, true));
        }
        if (east) {
            models.add(new ConfiguredModel(half));
        }
        if (south) {
            models.add(new ConfiguredModel(half, 0, 90, true));
        }
        if (west) {
            models.add(new ConfiguredModel(half, 0, 180, true));
        }
        return models.toArray(ConfiguredModel[]::new);
    }

    private ConfiguredModel[] camonetTopModels(net.minecraft.world.level.block.state.BlockState state, ModelFile center, ModelFile side, ModelFile corner, ModelFile side2, ModelFile full) {
        boolean north = state.getValue(TGCamoNetTopBlock.NORTH);
        boolean east = state.getValue(TGCamoNetTopBlock.EAST);
        boolean south = state.getValue(TGCamoNetTopBlock.SOUTH);
        boolean west = state.getValue(TGCamoNetTopBlock.WEST);

        int connection = connectionIndex(north, east, south, west);
        return switch (connection) {
            case 0 -> models(center);
            case 1 -> models(side, 180);
            case 2 -> models(side, 90);
            case 3 -> models(corner, 180);
            case 4 -> models(side, 0);
            case 5 -> new ConfiguredModel[]{new ConfiguredModel(side), new ConfiguredModel(side, 0, 180, true)};
            case 6 -> models(corner, 90);
            case 7 -> models(side2, 90);
            case 8 -> models(side, 270);
            case 9 -> models(corner, 270);
            case 10 -> new ConfiguredModel[]{new ConfiguredModel(side, 0, 270, true), new ConfiguredModel(side, 0, 90, true)};
            case 11 -> models(side2, 180);
            case 12 -> models(corner, 0);
            case 13 -> models(side2, 270);
            case 14 -> models(side2, 0);
            case 15 -> models(full);
            default -> models(center);
        };
    }

    private ConfiguredModel[] models(ModelFile model) {
        return new ConfiguredModel[]{new ConfiguredModel(model)};
    }

    private ConfiguredModel[] models(ModelFile model, int rotationY) {
        return new ConfiguredModel[]{new ConfiguredModel(model, 0, rotationY, true)};
    }

    private int connectionIndex(boolean north, boolean east, boolean south, boolean west) {
        int index = 0;
        if (north) {
            index += 8;
        }
        if (east) {
            index += 4;
        }
        if (south) {
            index += 2;
        }
        if (west) {
            index += 1;
        }
        return index;
    }

    private ResourceLocation camonetSupportTexture(String id) {
        if (id.contains("desert")) {
            return new ResourceLocation("minecraft", "block/acacia_planks");
        }
        if (id.contains("snow")) {
            return new ResourceLocation("minecraft", "block/spruce_planks");
        }
        return new ResourceLocation("minecraft", "block/oak_planks");
    }

    private RegistryObject<Block> entry(String id) {
        return TGBlocks.ENTRIES.get(id).block();
    }

    private int rotationForHorizontal(Direction direction) {
        return ((int) direction.toYRot() + 180) % 360;
    }

    private int rotationX(Direction direction) {
        return switch (direction) {
            case UP -> 90;
            case DOWN -> 270;
            default -> 0;
        };
    }

    private int rotationY(Direction direction) {
        return switch (direction) {
            case NORTH -> 180;
            case SOUTH -> 0;
            case WEST -> 90;
            case EAST -> 270;
            case UP, DOWN -> 0;
        };
    }

    private int rotationForLegacyLadder(Direction direction) {
        return switch (direction) {
            case NORTH -> 180;
            case EAST -> 270;
            case SOUTH -> 0;
            case WEST -> 90;
            case UP, DOWN -> 0;
        };
    }

    private int bioblobRotationX(Direction direction) {
        return switch (direction) {
            case UP -> 180;
            case NORTH, SOUTH, WEST, EAST -> 90;
            case DOWN -> 0;
        };
    }

    private int bioblobRotationY(Direction direction) {
        return switch (direction) {
            case NORTH -> 180;
            case EAST -> 270;
            case SOUTH -> 0;
            case WEST -> 90;
            case UP, DOWN -> 0;
        };
    }
}
