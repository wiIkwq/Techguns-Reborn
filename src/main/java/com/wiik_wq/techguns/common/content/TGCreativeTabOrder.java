package com.wiik_wq.techguns.common.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            "stielgranate",
            "fraggrenade"
    );

    private static final List<String> LEGACY_DOOR_ITEM_BLOCK_ORDER = List.of(
            "door3x3_metal",
            "hangar_door_up",
            "hangar_door_down",
            "door3x3_nether",
            "bunkerdoor"
    );

    private static final List<String> LEGACY_STANDALONE_ITEM_ORDER = List.of(
            "radaway",
            "radpills",
            "gasmask",
            "glider",
            "jumppack",
            "scubatanks",
            "nightvisiongoggles",
            "jetpack",
            "tacticalmask",
            "antigravpack"
    );

    private static final Set<String> LEGACY_DEBUG_ITEM_EXCLUSIONS = Set.of(
            "worldgentesttool",
            "buildingscantool"
    );

    private static final Set<String> LEGACY_CREATIVE_ITEM_EXCLUSIONS = Set.of(
            "steelbarrel"
    );

    private static final List<String> LEGACY_SHARED_ITEM_ORDER = createLegacySharedItemOrder();

    private static final List<String> LEGACY_BLOCK_ORDER = List.of(
            "ammo_press",
            "metal_press",
            "chem_lab",
            "turret_base",
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
            "hole",
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
            "oredrill_controller",
            "block_creeper_acid",
            "block_milk"
    );

    private TGCreativeTabOrder() {
    }

    public static List<String> legacyItemOrder() {
        return LEGACY_SHARED_ITEM_ORDER;
    }

    public static List<String> legacyBlockOrder() {
        return LEGACY_BLOCK_ORDER;
    }

    public static List<String> legacyStandaloneItemOrder() {
        return LEGACY_STANDALONE_ITEM_ORDER;
    }

    public static List<String> legacyToolOrder() {
        return TGItemCatalog.HANDHELD_ITEMS;
    }

    public static List<String> legacyArmorOrder() {
        return TGItemCatalog.ARMOR_ITEMS;
    }

    public static List<String> legacyShieldOrder() {
        return TGItemCatalog.SHIELD_ITEMS;
    }

    public static List<String> legacyGunOrder() {
        return LEGACY_GUN_ORDER;
    }

    public static List<String> legacyItemBlockOrder() {
        return LEGACY_DOOR_ITEM_BLOCK_ORDER;
    }

    private static List<String> createLegacySharedItemOrder() {
        List<String> order = new ArrayList<>();
        TGItemCatalog.SIMPLE_ITEMS.stream()
                .filter(id -> !LEGACY_STANDALONE_ITEM_ORDER.contains(id))
                .filter(id -> !LEGACY_DEBUG_ITEM_EXCLUSIONS.contains(id))
                .filter(id -> !LEGACY_CREATIVE_ITEM_EXCLUSIONS.contains(id))
                .forEach(order::add);
        return List.copyOf(order);
    }
}
