package com.wiik_wq.techguns.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.content.TGItemCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TGItemModelProvider extends ItemModelProvider {

    private static final Path LEGACY_ITEM_MODELS = TGDataPaths.resolve("techguns_old", "src", "main", "resources", "assets", "techguns", "models", "item");
    private static final Path MAIN_ASSETS = TGDataPaths.resolve("src", "main", "resources", "assets");

    public TGItemModelProvider(net.minecraft.data.PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechgunsReborn.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        TGBlocks.all().forEach(entry -> blockItemModel(entry.id()));
        TGItems.all().forEach(entry -> itemModel(entry.id(), entry.style()));
        TGItemCatalog.OBJ_MODEL_ITEMS.forEach(this::objBackingItemModel);
    }

    private void blockItemModel(String id) {
        ItemModelBuilder builder;
        String itemTexture = TGBlockCatalog.BLOCK_ITEM_TEXTURE_OVERRIDES.get(id);
        if (itemTexture != null) {
            builder = withExistingParent(id, mcLoc("item/generated")).texture("layer0", modLoc("items/" + itemTexture));
            applyLegacyBlockItemDisplayTransforms(builder, id);
            return;
        }

        String parent = blockItemParent(id);
        builder = withExistingParent(id, modLoc("block/" + parent));
        applyLegacyBlockItemDisplayTransforms(builder, id);
    }

    private void itemModel(String id, TGItems.ItemStyle style) {
        if (TGItemCatalog.usesSpecialItemRenderer(id)) {
            ItemModelBuilder builder = getBuilder(id).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
            applyGeneratedDisplayTransforms(builder);
            return;
        }

        if (TGItemCatalog.usesObjItemModel(id)) {
            objBackingItemModel(id);
            return;
        }

        ResourceLocation texture = textureForItem(id);
        String parent = style == TGItems.ItemStyle.HANDHELD ? "item/handheld" : "item/generated";
        withExistingParent(id, mcLoc(parent)).texture("layer0", texture);
    }

    private void objBackingItemModel(String id) {
        String modelId = id + "_legacy";
        if ("gaussrifle".equals(id)) {
            ItemModelBuilder builder = getBuilder(modelId).texture("particle", modLoc("guns/gaussrifle"));
            builder.customLoader(ObjModelBuilder::begin)
                    .modelLocation(modLoc("models/item/gaussrifle.obj"))
                    .flipV(true)
                    .end();
            return;
        }

        if ("grenadelauncher".equals(id)) {
            ItemModelBuilder builder = getBuilder(modelId).texture("particle", modLoc("guns/grenadelauncher"));
            CompositeModelBuilder<ItemModelBuilder> composite = builder.customLoader(CompositeModelBuilder::begin);
            composite.child("body", objChild(modelId + "_body", "models/item/grenadelauncher.obj"));
            composite.child("drum", objChild(modelId + "_drum", "models/item/grenadelauncher_1.obj"));
            composite.itemRenderOrder("body", "drum").end();
        }
    }

    private ItemModelBuilder objChild(String id, String modelPath) {
        ItemModelBuilder builder = new ItemModelBuilder(modLoc("generated/" + id), existingFileHelper)
                .texture("particle", modLoc("guns/grenadelauncher"));
        builder.customLoader(ObjModelBuilder::begin)
                .modelLocation(modLoc(modelPath))
                .flipV(true)
                .end();
        return builder;
    }

    private void applyGeneratedDisplayTransforms(ItemModelBuilder builder) {
        builder.transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0.0F, -90.0F, 25.0F)
                .translation(1.13F, 3.2F, 1.13F)
                .scale(0.68F, 0.68F, 0.68F)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0.0F, 0.0F, 0.0F)
                .translation(0.0F, 3.0F, 1.0F)
                .scale(0.55F, 0.55F, 0.55F)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .rotation(0.0F, 0.0F, 0.0F)
                .translation(0.0F, 2.0F, 0.0F)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .rotation(0.0F, 180.0F, 0.0F)
                .translation(0.0F, 0.0F, 0.0F)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemDisplayContext.HEAD)
                .rotation(0.0F, 180.0F, 0.0F)
                .translation(0.0F, 13.0F, 7.0F)
                .scale(1.0F, 1.0F, 1.0F)
                .end();
    }

    private void applyLegacyBlockItemDisplayTransforms(ItemModelBuilder builder, String id) {
        Path modelPath = legacyBlockItemDisplayModel(id);
        if (modelPath == null) {
            return;
        }
        applyDisplayTransforms(builder, modelPath);
    }

    private Path legacyBlockItemDisplayModel(String id) {
        Path directPath = LEGACY_ITEM_MODELS.resolve(id + ".json");
        if (Files.exists(directPath)) {
            return directPath;
        }

        String template = TGBlockCatalog.BLOCK_ITEM_DISPLAY_MODEL_TEMPLATES.get(id);
        if (template == null) {
            return null;
        }

        Path templatePath = LEGACY_ITEM_MODELS.resolve(template + ".json");
        return Files.exists(templatePath) ? templatePath : null;
    }

    private void applyDisplayTransforms(ItemModelBuilder builder, Path modelPath) {
        try (var reader = Files.newBufferedReader(modelPath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (!json.has("display")) {
                return;
            }

            JsonObject display = json.getAsJsonObject("display");
            for (var entry : display.entrySet()) {
                ItemDisplayContext context = displayContext(entry.getKey());
                if (context == null || !entry.getValue().isJsonObject()) {
                    continue;
                }

                JsonObject transformJson = entry.getValue().getAsJsonObject();
                float[] rotation = readVec3(transformJson, "rotation", 0.0F, 0.0F, 0.0F);
                float[] translation = readVec3(transformJson, "translation", 0.0F, 0.0F, 0.0F);
                float[] scale = readScale(transformJson);

                builder.transforms()
                        .transform(context)
                        .rotation(rotation[0], rotation[1], rotation[2])
                        .translation(translation[0], translation[1], translation[2])
                        .scale(scale[0], scale[1], scale[2])
                        .end();
            }
        } catch (IOException ignored) {
        }
    }

    private ItemDisplayContext displayContext(String name) {
        return switch (name) {
            case "thirdperson_righthand" -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            case "thirdperson_lefthand" -> ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
            case "firstperson_righthand" -> ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
            case "firstperson_lefthand" -> ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
            case "gui" -> ItemDisplayContext.GUI;
            case "ground" -> ItemDisplayContext.GROUND;
            case "fixed" -> ItemDisplayContext.FIXED;
            case "head" -> ItemDisplayContext.HEAD;
            default -> null;
        };
    }

    private float[] readVec3(JsonObject json, String key, float defaultX, float defaultY, float defaultZ) {
        if (!json.has(key)) {
            return new float[]{defaultX, defaultY, defaultZ};
        }

        var value = json.get(key);
        if (!value.isJsonArray()) {
            float scalar = value.getAsFloat();
            return new float[]{scalar, scalar, scalar};
        }

        var array = value.getAsJsonArray();
        if (array.size() < 3) {
            return new float[]{defaultX, defaultY, defaultZ};
        }

        return new float[]{
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat(),
                array.get(2).getAsFloat()
        };
    }

    private float[] readScale(JsonObject json) {
        return readVec3(json, "scale", 1.0F, 1.0F, 1.0F);
    }

    private ResourceLocation textureForItem(String id) {
        ResourceLocation legacyTexture = readLegacyTexture(id);
        if (legacyTexture != null && textureExists(legacyTexture)) {
            return legacyTexture;
        }

        if (TGItemCatalog.GUN_TEXTURES.containsKey(id)) {
            ResourceLocation gunTexture = modLoc("guns/" + TGItemCatalog.GUN_TEXTURES.get(id));
            if (textureExists(gunTexture)) {
                return gunTexture;
            }
        }

        if (TGItemCatalog.SHIELD_ITEMS.contains(id)) {
            ResourceLocation shieldTexture = modLoc("armors/" + id);
            if (textureExists(shieldTexture)) {
                return shieldTexture;
            }
        }

        if (TGItemCatalog.TEXTURE_FALLBACKS.containsKey(id)) {
            ResourceLocation fallbackTexture = modLoc(TGItemCatalog.TEXTURE_FALLBACKS.get(id));
            if (textureExists(fallbackTexture)) {
                return fallbackTexture;
            }
        }

        ResourceLocation itemTexture = modLoc("items/" + id);
        if (textureExists(itemTexture)) {
            return itemTexture;
        }

        ResourceLocation gunFallback = modLoc("guns/" + id);
        if (textureExists(gunFallback)) {
            return gunFallback;
        }

        return mcLoc("item/barrier");
    }

    private ResourceLocation readLegacyTexture(String id) {
        Path modelPath = LEGACY_ITEM_MODELS.resolve(id + ".json");
        if (!Files.exists(modelPath)) {
            return null;
        }

        try (var reader = Files.newBufferedReader(modelPath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (!json.has("textures")) {
                return null;
            }

            JsonObject textures = json.getAsJsonObject("textures");
            if (!textures.has("layer0")) {
                return null;
            }

            String value = textures.get("layer0").getAsString();
            if ("techguns:items/dummy".equals(value)) {
                return null;
            }

            return resolveTexture(value);
        } catch (IOException ignored) {
            return null;
        }
    }

    private ResourceLocation resolveTexture(String value) {
        if (!value.contains(":")) {
            return modLoc(value);
        }
        return ResourceLocation.parse(value);
    }

    private boolean textureExists(ResourceLocation location) {
        Path texturePath = MAIN_ASSETS
                .resolve(location.getNamespace())
                .resolve("textures")
                .resolve(location.getPath() + ".png");
        return Files.exists(texturePath);
    }

    private String blockItemParent(String id) {
        if (TGBlockCatalog.CUBE_MODEL_BLOCKS.containsKey(id)) {
            return TGBlockCatalog.CUBE_MODEL_BLOCKS.get(id);
        }
        if (TGBlockCatalog.HORIZONTAL_MODEL_NAMES.containsKey(id)) {
            return TGBlockCatalog.HORIZONTAL_MODEL_NAMES.get(id);
        }
        if (TGBlockCatalog.DIRECTIONAL_MODEL_NAMES.containsKey(id)) {
            return TGBlockCatalog.DIRECTIONAL_MODEL_NAMES.get(id);
        }
        if (TGBlockCatalog.ROTATED_MODEL_BLOCKS.containsKey(id)) {
            return "interiormarker";
        }
        if (TGBlockCatalog.LADDER_BLOCKS.containsKey(id)) {
            return TGBlockCatalog.LADDER_BLOCKS.get(id);
        }
        if (TGBlockCatalog.STATIC_MODEL_BLOCKS.containsKey(id)) {
            return TGBlockCatalog.STATIC_MODEL_BLOCKS.get(id);
        }
        return id;
    }
}
