package com.wiik_wq.techguns.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.content.TGItemCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
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
    }

    private void blockItemModel(String id) {
        String itemTexture = TGBlockCatalog.BLOCK_ITEM_TEXTURE_OVERRIDES.get(id);
        if (itemTexture != null) {
            withExistingParent(id, mcLoc("item/generated")).texture("layer0", modLoc("items/" + itemTexture));
            return;
        }

        String parent = blockItemParent(id);
        withExistingParent(id, modLoc("block/" + parent));
    }

    private void itemModel(String id, TGItems.ItemStyle style) {
        ResourceLocation texture = textureForItem(id);
        String parent = style == TGItems.ItemStyle.HANDHELD ? "item/handheld" : "item/generated";
        withExistingParent(id, mcLoc(parent)).texture("layer0", texture);
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
