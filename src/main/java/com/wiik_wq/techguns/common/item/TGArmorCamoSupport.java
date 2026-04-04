package com.wiik_wq.techguns.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public final class TGArmorCamoSupport {

    private static final String CAMO_TAG = "camo";

    private TGArmorCamoSupport() {
    }

    public static boolean supports(ItemStack stack) {
        return family(stack) != null;
    }

    public static int getCamoCount(ItemStack stack) {
        ArmorCamoFamily family = family(stack);
        return family == null ? 0 : family.textureNames.length;
    }

    public static int getCurrentCamoIndex(ItemStack stack) {
        ArmorCamoFamily family = family(stack);
        if (family == null) {
            return 0;
        }

        CompoundTag tag = stack.getTag();
        int camo = tag != null ? tag.getByte(CAMO_TAG) : 0;
        if (camo < 0 || camo >= family.textureNames.length) {
            return 0;
        }
        return camo;
    }

    public static int cycleForward(ItemStack stack) {
        ArmorCamoFamily family = family(stack);
        if (family == null) {
            return 0;
        }

        int next = (getCurrentCamoIndex(stack) + 1) % family.textureNames.length;
        stack.getOrCreateTag().putByte(CAMO_TAG, (byte) next);
        return next;
    }

    public static @Nullable ResourceLocation texture(ItemStack stack) {
        ArmorCamoFamily family = family(stack);
        if (family == null) {
            return null;
        }
        return texture(family, getCurrentCamoIndex(stack));
    }

    public static Component currentCamoComponent(ItemStack stack) {
        ArmorCamoFamily family = family(stack);
        if (family == null) {
            return Component.translatable("techguns.item.invalidcamo");
        }

        int index = getCurrentCamoIndex(stack);
        if (family.translationKeyPrefix != null) {
            return Component.translatable(family.translationKeyPrefix + index);
        }

        return Component.literal((index + 1) + "/" + family.textureNames.length);
    }

    private static @Nullable ArmorCamoFamily family(ItemStack stack) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null) {
            return null;
        }
        String itemId = key.getPath();
        if (itemId.startsWith("steam_")) {
            return ArmorCamoFamily.STEAM;
        }
        if (itemId.startsWith("t3_power_")) {
            return ArmorCamoFamily.T3_POWER;
        }
        if (itemId.startsWith("t4_power_")) {
            return ArmorCamoFamily.T4_POWER;
        }
        return null;
    }

    private static ResourceLocation texture(ArmorCamoFamily family, int index) {
        int clamped = Math.max(0, Math.min(index, family.textureNames.length - 1));
        return ResourceLocation.parse("techguns:textures/models/armor/" + family.textureNames[clamped] + ".png");
    }

    private enum ArmorCamoFamily {
        STEAM(
                new String[]{"steam_armor", "steam_armor_rusty", "steam_armor_iron", "steam_armor_hazard"},
                "techguns.item.steam_armor.camoname."
        ),
        T3_POWER(
                new String[]{"powerarmor", "powerarmor_dark", "powerarmor_dark2"},
                "techguns.item.powerarmor.camoname."
        ),
        T4_POWER(
                new String[]{"powerarmor_mk2_darkgrey", "powerarmor_mk2", "powerarmor_mk2_black", "powerarmor_mk2_white"},
                null
        );

        private final String[] textureNames;
        private final @Nullable String translationKeyPrefix;

        ArmorCamoFamily(String[] textureNames, @Nullable String translationKeyPrefix) {
            this.textureNames = textureNames;
            this.translationKeyPrefix = translationKeyPrefix;
        }
    }
}
