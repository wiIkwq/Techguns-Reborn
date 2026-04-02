package com.wiik_wq.techguns.client.gui;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.item.TGGunItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

final class TGGunCrosshairProfiles {

    private static final CrosshairProfile VANILLA = new CrosshairProfile(TGCrosshairStyle.VANILLA, 1);

    private static final Map<String, CrosshairProfile> PROFILES = Map.ofEntries(
            Map.entry("handcannon", profile(TGCrosshairStyle.QUAD_NO_CORNERS, 4)),
            Map.entry("sawedoff", profile(TGCrosshairStyle.FOUR_PARTS, 1)),
            Map.entry("revolver", profile(TGCrosshairStyle.GUN_DYNAMIC, 3)),
            Map.entry("ak47", profile(TGCrosshairStyle.GUN_DYNAMIC, 3)),
            Map.entry("m4", profile(TGCrosshairStyle.GUN_DYNAMIC, 2)),
            Map.entry("thompson", profile(TGCrosshairStyle.GUN_DYNAMIC, 5)),
            Map.entry("pistol", profile(TGCrosshairStyle.GUN_DYNAMIC, 3)),
            Map.entry("lmg", profile(TGCrosshairStyle.GUN_DYNAMIC, 2)),
            Map.entry("boltaction", profile(TGCrosshairStyle.GUN_DYNAMIC, 5)),
            Map.entry("flamethrower", profile(TGCrosshairStyle.QUAD_NO_CORNERS, 5)),
            Map.entry("biogun", profile(TGCrosshairStyle.QUAD_NO_CORNERS, 2)),
            Map.entry("rocketlauncher", profile(TGCrosshairStyle.QUAD_CORNERS_DOT, 5)),
            Map.entry("minigun", profile(TGCrosshairStyle.GUN_DYNAMIC, 3)),
            Map.entry("combatshotgun", profile(TGCrosshairStyle.FOUR_PARTS, 1)),
            Map.entry("grimreaper", VANILLA),
            Map.entry("pdw", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 3)),
            Map.entry("as50", profile(TGCrosshairStyle.GUN_DYNAMIC, 6)),
            Map.entry("teslagun", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART_E, 1)),
            Map.entry("m4_infiltrator", profile(TGCrosshairStyle.GUN_DYNAMIC, 1)),
            Map.entry("goldenrevolver", profile(TGCrosshairStyle.GUN_DYNAMIC, 2)),
            Map.entry("pulserifle", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 2)),
            Map.entry("lasergun", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 1)),
            Map.entry("alienblaster", profile(TGCrosshairStyle.FOUR_PARTS_SPIKED, 1)),
            Map.entry("netherblaster", profile(TGCrosshairStyle.FOUR_PARTS_SPIKED, 1)),
            Map.entry("blasterrifle", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 3)),
            Map.entry("powerhammer", VANILLA),
            Map.entry("grenadelauncher", profile(TGCrosshairStyle.QUAD_NO_CORNERS, 2)),
            Map.entry("aug", profile(TGCrosshairStyle.GUN_DYNAMIC, 1)),
            Map.entry("sonicshotgun", profile(TGCrosshairStyle.QUAD_NO_CORNERS, 1)),
            Map.entry("chainsaw", VANILLA),
            Map.entry("scatterbeamrifle", profile(TGCrosshairStyle.FOUR_PARTS, 10)),
            Map.entry("nucleardeathray", profile(TGCrosshairStyle.TRI, 1)),
            Map.entry("mac10", profile(TGCrosshairStyle.GUN_DYNAMIC, 5)),
            Map.entry("mibgun", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 4)),
            Map.entry("scar", profile(TGCrosshairStyle.GUN_DYNAMIC, 2)),
            Map.entry("vector", profile(TGCrosshairStyle.GUN_DYNAMIC, 5)),
            Map.entry("gaussrifle", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART_LARGE, 3)),
            Map.entry("guidedmissilelauncher", profile(TGCrosshairStyle.QUAD_CORNERS_DOT, 5)),
            Map.entry("miningdrill", VANILLA),
            Map.entry("tfg", profile(TGCrosshairStyle.TRI_INV, 2)),
            Map.entry("laserpistol", profile(TGCrosshairStyle.HORIZONTAL_TWO_PART, 3)),
            Map.entry("shishkebap", VANILLA),
            Map.entry("stielgranate", VANILLA),
            Map.entry("fraggrenade", VANILLA)
    );

    private TGGunCrosshairProfiles() {
    }

    static CrosshairProfile profileFor(ItemStack stack) {
        if (!(stack.getItem() instanceof TGGunItem)) {
            return VANILLA;
        }

        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null || !TechgunsReborn.MODID.equals(key.getNamespace())) {
            return VANILLA;
        }

        return PROFILES.getOrDefault(key.getPath(), profile(TGCrosshairStyle.GUN_DYNAMIC, 3));
    }

    private static CrosshairProfile profile(TGCrosshairStyle style, int spacing) {
        return new CrosshairProfile(style, spacing);
    }

    record CrosshairProfile(TGCrosshairStyle style, int spacing) {
    }
}
