package com.wiik_wq.techguns.common.content;

import java.util.ArrayList;
import java.util.List;

public final class TGCreativeTabOrder {

    private static final List<String> LEGACY_GUN_ORDER = List.of(
            "handcannon",
            "sawedoff",
            "revolver",
            "goldenrevolver",
            "thompson",
            "ak47",
            "boltaction",
            "m4",
            "m4_infiltrator",
            "pistol",
            "combatshotgun",
            "mac10",
            "flamethrower",
            "rocketlauncher",
            "grimreaper",
            "grenadelauncher",
            "aug",
            "netherblaster",
            "biogun",
            "teslagun",
            "lmg",
            "minigun",
            "as50",
            "vector",
            "scar",
            "lasergun",
            "blasterrifle",
            "scatterbeamrifle",
            "sonicshotgun",
            "pdw",
            "pulserifle",
            "mibgun",
            "alienblaster",
            "powerhammer",
            "chainsaw",
            "nucleardeathray",
            "gaussrifle",
            "guidedmissilelauncher",
            "miningdrill",
            "tfg",
            "laserpistol",
            "shishkebap",
            "stielgranate",
            "fraggrenade"
    );

    private static final List<String> LEGACY_ITEM_BLOCK_ORDER = List.of(
            "door3x3_metal",
            "hangar_door_up",
            "hangar_door_down",
            "door3x3_nether",
            "bunkerdoor"
    );

    private static final List<String> LEGACY_ITEM_ORDER = createLegacyItemOrder();

    private static final List<String> LEGACY_BLOCK_ORDER = List.of(
            "ammo_press",
            "metal_press",
            "chem_lab",
            "turret_base",
            "dungeon_scanner",
            "dungeon_generator",
            "camo_bench",
            "repair_bench",
            "charging_station",
            "blast_furnace",
            "grinder",
            "armor_bench",
            "fabricator_housing",
            "fabricator_glass",
            "fabricator_controller",
            "reactionchamber_housing",
            "reactionchamber_glass",
            "reactionchamber_controller",
            "ore_copper",
            "ore_tin",
            "ore_lead",
            "ore_titanium",
            "ore_uranium",
            "bioblob",
            "sandbags",
            "lamp_yellow",
            "lamp_white",
            "lantern_yellow",
            "lantern_white",
            "container_red",
            "container_green",
            "container_blue",
            "container_orange",
            "panel_large_border",
            "steelframe_blue",
            "steelframe_dark",
            "steelframe_scaffold",
            "nethermetal_panel",
            "nethermetal_grate1",
            "nethermetal_grate2",
            "nethermetal_grey_dark",
            "nethermetal_grey",
            "nethermetal_grey_tiles",
            "nethermetal_border_red",
            "nethermetal_plate_black",
            "nethermetal_plate_red",
            "nethermetal_border_lava",
            "concrete_brown",
            "concrete_brown_light",
            "concrete_grey",
            "concrete_grey_dark",
            "concrete_brown_pipes",
            "concrete_brown_light_scaff",
            "metal_ladder",
            "shiny_metal_ladder",
            "rusty_metal_ladder",
            "carbon_ladder",
            "camonet_wood",
            "camonet_desert",
            "camonet_snow",
            "camonet_top_wood",
            "camonet_top_desert",
            "camonet_top_snow",
            "panel_large_border_stairs",
            "steelframe_dark_stairs",
            "concrete_grey_dark_stairs",
            "concrete_brown_light_stairs",
            "soldier_spawn",
            "neontubes2",
            "neontubes2_rotated",
            "neontubes4",
            "neontubes4_rotated",
            "neonsquare_white",
            "military_crate_ammo",
            "military_crate_gun",
            "military_crate_armor",
            "military_crate_medical",
            "military_crate_explosive",
            "crate_oak",
            "crate_jungle",
            "crate_birch",
            "crate_spruce",
            "tnt_charge",
            "c4_charge",
            "bugnest_sand",
            "bugnest_eggs",
            "slimyladder",
            "ore_cluster_coal",
            "ore_cluster_common_metal",
            "ore_cluster_rare_metal",
            "ore_cluster_shiny_metal",
            "ore_cluster_uranium",
            "ore_cluster_common_gem",
            "ore_cluster_shiny_gem",
            "ore_cluster_nether_crystal",
            "ore_cluster_oil",
            "oredrill_frame",
            "oredrill_scaffold",
            "oredrill_rod",
            "oredrill_engine",
            "oredrill_controller"
    );

    private TGCreativeTabOrder() {
    }

    public static List<String> legacyItemOrder() {
        return LEGACY_ITEM_ORDER;
    }

    public static List<String> legacyBlockOrder() {
        return LEGACY_BLOCK_ORDER;
    }

    public static List<String> legacyItemBlockOrder() {
        return LEGACY_ITEM_BLOCK_ORDER;
    }

    private static List<String> createLegacyItemOrder() {
        List<String> order = new ArrayList<>();
        order.addAll(TGItemCatalog.SIMPLE_ITEMS);
        order.addAll(LEGACY_ITEM_BLOCK_ORDER);
        order.addAll(TGItemCatalog.HANDHELD_ITEMS);
        order.addAll(TGItemCatalog.ARMOR_ITEMS);
        order.addAll(TGItemCatalog.SHIELD_ITEMS);
        order.addAll(LEGACY_GUN_ORDER);
        return List.copyOf(order);
    }
}
