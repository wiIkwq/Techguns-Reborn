package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
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

public class TGBlockStateProvider extends BlockStateProvider {

    public TGBlockStateProvider(net.minecraft.data.PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TechgunsReborn.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        TGBlockCatalog.CUBE_MODEL_BLOCKS.forEach(this::simpleExistingModel);
        TGBlockCatalog.GENERATED_CUBE_ALL_BLOCKS.forEach(this::simpleGeneratedCube);
        TGBlockCatalog.HORIZONTAL_MODEL_BLOCKS.forEach(id -> horizontalExistingModel(id, TGBlockCatalog.HORIZONTAL_MODEL_NAMES.get(id)));
        TGBlockCatalog.DIRECTIONAL_MODEL_BLOCKS.forEach(id -> directionalExistingModel(id, TGBlockCatalog.DIRECTIONAL_MODEL_NAMES.get(id)));
        TGBlockCatalog.ROTATED_MODEL_BLOCKS.forEach((id, yRot) -> fixedRotationModel(id, "interiormarker", yRot));
        TGBlockCatalog.MILITARY_CRATE_TEXTURES.forEach(this::militaryCrateModel);
        TGBlockCatalog.CAMONET_BLOCKS.forEach(this::camonetModel);
        TGBlockCatalog.CAMONET_TOP_BLOCKS.forEach(this::camonetTopModel);
        TGBlockCatalog.LADDER_BLOCKS.forEach(this::ladderModel);
        TGBlockCatalog.STATIC_MODEL_BLOCKS.forEach(this::simpleExistingModel);
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
            Direction direction = state.getValue(com.wiik_wq.techguns.common.block.TGDirectionalBlock.FACING);
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(rotationX(direction))
                    .rotationY(rotationY(direction))
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
        ModelFile model = models().withExistingParent(id, modLoc("block/camonet_full"))
                .texture("1", modLoc("blocks/" + textureName))
                .texture("particle", modLoc("blocks/" + textureName));
        simpleBlock(block.get(), model);
    }

    private void camonetTopModel(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().withExistingParent(id, modLoc("block/camonet_top_full"))
                .texture("0", modLoc("blocks/" + textureName))
                .texture("particle", modLoc("blocks/" + textureName));
        simpleBlock(block.get(), model);
    }

    private void ladderModel(String id, String modelName) {
        RegistryObject<Block> block = entry(id);
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(model)
                .rotationY(rotationForHorizontal(state.getValue(net.minecraft.world.level.block.LadderBlock.FACING)))
                .build());
    }

    private void stairsModel(String id, String textureName) {
        RegistryObject<Block> block = entry(id);
        ModelFile stairs = models().stairs(id, modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        ModelFile stairsInner = models().stairsInner(id + "_inner", modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        ModelFile stairsOuter = models().stairsOuter(id + "_outer", modLoc("blocks/" + textureName), modLoc("blocks/" + textureName), modLoc("blocks/" + textureName));
        stairsBlock((StairBlock) block.get(), stairs, stairsInner, stairsOuter);
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
}
