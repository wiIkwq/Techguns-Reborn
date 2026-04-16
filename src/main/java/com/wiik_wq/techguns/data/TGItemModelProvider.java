package com.wiik_wq.techguns.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.content.TGItemCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGFluids;
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
import java.util.Map;
import java.util.Set;

public class TGItemModelProvider extends ItemModelProvider {

    private static final Path LEGACY_ITEM_MODELS = TGDataPaths.resolveLegacy("src", "main", "resources", "assets", "techguns", "models", "item");
    private static final Path MAIN_ASSETS = TGDataPaths.resolve("src", "main", "resources", "assets");
    private static final TGDisplayTransform LADDER_GUI_TRANSFORM =
            new TGDisplayTransform(30.0F, 225.0F, 0.0F, -1.0F, 1.0F, 0.0F, 0.625F, 0.625F, 0.625F);
    private static final Map<String, String> BLOCK_ITEM_PARENT_OVERRIDES = Map.ofEntries(
            Map.entry("camonet_wood", "camonet_inventory"),
            Map.entry("camonet_desert", "camonet_inventory"),
            Map.entry("camonet_snow", "camonet_inventory"),
            Map.entry("camonet_top_wood", "camonet_top_inventory"),
            Map.entry("camonet_top_desert", "camonet_top_inventory"),
            Map.entry("camonet_top_snow", "camonet_top_inventory"),
            Map.entry("armor_bench", "upgrade_bench"),
            Map.entry("charging_station", "charging_station"),
            Map.entry("sandbags", "sandbags_inventory"),
            Map.entry("lamp_yellow", "lamp_inventory_yellow"),
            Map.entry("lamp_white", "lamp_inventory_white"),
            Map.entry("lantern_yellow", "lantern_inventory_yellow"),
            Map.entry("lantern_white", "lantern_inventory_white"),
            Map.entry(TGBlockCatalog.BIOBLOB, "bioblob0")
    );
    private static final Map<String, TGDisplayTransform> BLOCK_ITEM_GUI_OVERRIDES = Map.of(
            "metal_ladder", LADDER_GUI_TRANSFORM,
            "shiny_metal_ladder", LADDER_GUI_TRANSFORM,
            "rusty_metal_ladder", LADDER_GUI_TRANSFORM,
            "carbon_ladder", LADDER_GUI_TRANSFORM,
            TGBlockCatalog.SLIMY_LADDER, new TGDisplayTransform(90F, 0F, 0F, 0.0F, 0.0F, 0.0F, 0.9F, 0.9F, 0.9F),
            "charging_station", new TGDisplayTransform(30.0F, 45.0F, 0.0F, 6.9F, 12.5F, 0.0F, 0.6F, 0.6F, 0.6F),
            "sandbags", new TGDisplayTransform(28.0F, 225.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.72F, 0.72F, 0.72F)
    );
    private static final Set<String> SPECIAL_RENDER_BLOCK_ITEMS = Set.of(
            "ammo_press",
            "metal_press",
            "chem_lab",
            "turret_base"
    );

    public TGItemModelProvider(net.minecraft.data.PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechgunsReborn.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        TGItemCatalog.OBJ_MODEL_ITEMS.forEach(this::objBackingItemModel);
        TGBlocks.all().forEach(entry -> blockItemModel(entry.id()));
        TGFluids.allBlocks().forEach(entry -> fluidBlockItemModel(entry.id(), entry.itemTexture()));
        TGItems.all().forEach(entry -> itemModel(entry.id(), entry.style()));
    }

    private void fluidBlockItemModel(String id, String stillTexture) {
        withExistingParent(id, mcLoc("item/generated"))
                .texture("layer0", modLoc("blocks/" + stillTexture));
    }

    private void blockItemModel(String id) {
        ItemModelBuilder builder;
        if (SPECIAL_RENDER_BLOCK_ITEMS.contains(id)) {
            builder = getBuilder(id).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
            applyLegacyBlockItemDisplayTransforms(builder, id);
            applyBlockItemGuiOverride(builder, id);
            return;
        }

        String itemTexture = TGBlockCatalog.BLOCK_ITEM_TEXTURE_OVERRIDES.get(id);
        if (itemTexture != null) {
            builder = withExistingParent(id, mcLoc("item/generated")).texture("layer0", modLoc("items/" + itemTexture));
            applyLegacyBlockItemDisplayTransforms(builder, id);
            applyBlockItemGuiOverride(builder, id);
            return;
        }

        String parent = blockItemParent(id);
        builder = withExistingParent(id, modLoc("block/" + parent));
        applyBlockItemTextures(builder, id, parent);
        applyLegacyBlockItemDisplayTransforms(builder, id);
        applyBlockItemGuiOverride(builder, id);
    }

    private void itemModel(String id, TGItems.ItemStyle style) {
        if (TGItemCatalog.usesObjItemModel(id)) {
            ItemModelBuilder builder = getBuilder(id).parent(new ModelFile.UncheckedModelFile(modLoc("item/" + id + "_legacy")));
            applyTransforms(builder, TGGunDisplayProfiles.objTransforms(id));
            return;
        }

        if (TGItemCatalog.usesSpecialItemRenderer(id)) {
            ItemModelBuilder builder = getBuilder(id).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
            applyGeneratedDisplayTransforms(builder, id);
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

    private void applyGeneratedDisplayTransforms(ItemModelBuilder builder, String id) {
        if (TGItemCatalog.SHIELD_ITEMS.contains(id)) {
            applyShieldDisplayTransforms(builder);
            return;
        }

        applyTransforms(builder, TGGunDisplayProfiles.standardTransforms(id));
    }

    private void applyShieldDisplayTransforms(ItemModelBuilder builder) {
        builder.transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0.0F, 90.0F, 0.0F)
                .translation(10.0F, 6.0F, -4.0F)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(0.0F, 90.0F, 0.0F)
                .translation(10.0F, 6.0F, 12.0F)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0.0F, 180.0F, 5.0F)
                .translation(-10.0F, 2.0F, -10.0F)
                .scale(1.25F, 1.25F, 1.25F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0.0F, 180.0F, 5.0F)
                .translation(10.0F, 0.0F, -10.0F)
                .scale(1.25F, 1.25F, 1.25F)
                .end()
                .transform(ItemDisplayContext.GUI)
                .rotation(15.0F, -25.0F, -5.0F)
                .translation(0.8F, 0.65F, 0.0F)
                .scale(0.6F, 0.6F, 0.6F)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .rotation(0.0F, 180.0F, 0.0F)
                .translation(-4.5F, 4.5F, -5.0F)
                .scale(0.55F, 0.55F, 0.55F)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .rotation(0.0F, 0.0F, 0.0F)
                .translation(2.0F, 4.0F, 2.0F)
                .scale(0.25F, 0.25F, 0.25F)
                .end();
    }

    private void applyLegacyBlockItemDisplayTransforms(ItemModelBuilder builder, String id) {
        Path modelPath = legacyBlockItemDisplayModel(id);
        if (modelPath == null) {
            return;
        }
        applyDisplayTransforms(builder, modelPath);
    }

    private void applyBlockItemGuiOverride(ItemModelBuilder builder, String id) {
        TGDisplayTransform transform = BLOCK_ITEM_GUI_OVERRIDES.get(id);
        if (transform == null) {
            return;
        }
        applyTransform(builder, ItemDisplayContext.GUI, transform);
    }

    private void applyBlockItemTextures(ItemModelBuilder builder, String id, String parent) {
        if ("camonet_inventory".equals(parent)) {
            builder.texture("0", camonetSupportTexture(id))
                    .texture("1", camonetNetTexture(id))
                    .texture("particle", camonetNetTexture(id));
            return;
        }

        if ("camonet_top_inventory".equals(parent)) {
            builder.texture("0", camonetNetTexture(id))
                    .texture("particle", camonetNetTexture(id));
        }
    }

    private ResourceLocation camonetSupportTexture(String id) {
        if (id.contains("desert")) {
            return mcLoc("block/acacia_planks");
        }
        if (id.contains("snow")) {
            return mcLoc("block/spruce_planks");
        }
        return mcLoc("block/oak_planks");
    }

    private ResourceLocation camonetNetTexture(String id) {
        if (id.contains("desert")) {
            return modLoc("blocks/camonet_desert");
        }
        if (id.contains("snow")) {
            return modLoc("blocks/camonet_snow");
        }
        return modLoc("blocks/camonet");
    }

    private Path legacyBlockItemDisplayModel(String id) {
        if (!TGDataPaths.hasLegacySource()) {
            return null;
        }

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

                applyTransform(builder, context, new TGDisplayTransform(
                        rotation[0], rotation[1], rotation[2],
                        translation[0], translation[1], translation[2],
                        scale[0], scale[1], scale[2]
                ));
            }
        } catch (IOException ignored) {
        }
    }

    private void applyTransforms(ItemModelBuilder builder, Map<ItemDisplayContext, TGDisplayTransform> transforms) {
        transforms.forEach((context, transform) -> applyTransform(builder, context, transform));
    }

    private void applyTransform(ItemModelBuilder builder, ItemDisplayContext context, TGDisplayTransform transform) {
        builder.transforms()
                .transform(context)
                .rotation(transform.rotationX(), transform.rotationY(), transform.rotationZ())
                .translation(transform.translationX(), transform.translationY(), transform.translationZ())
                .scale(transform.scaleX(), transform.scaleY(), transform.scaleZ())
                .end();
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
        if (!TGDataPaths.hasLegacySource()) {
            return null;
        }

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
        if (BLOCK_ITEM_PARENT_OVERRIDES.containsKey(id)) {
            return BLOCK_ITEM_PARENT_OVERRIDES.get(id);
        }
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
