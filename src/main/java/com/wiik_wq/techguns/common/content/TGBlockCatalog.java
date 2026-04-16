package com.wiik_wq.techguns.common.content;

import java.util.List;
import java.util.Map;

public final class TGBlockCatalog {

    public static final String BIOBLOB = "bioblob";
    public static final String SLIMY_LADDER = "slimyladder";

    public static final Map<String, String> CUBE_MODEL_BLOCKS = Map.ofEntries(
            Map.entry("ore_copper", "ore_copper"),
            Map.entry("ore_tin", "ore_tin"),
            Map.entry("ore_lead", "ore_lead"),
            Map.entry("ore_titanium", "ore_titanium"),
            Map.entry("ore_uranium", "ore_uranium"),
            Map.entry("concrete_brown", "concrete_brown"),
            Map.entry("concrete_brown_light", "concrete_brown_light"),
            Map.entry("concrete_grey", "concrete_grey"),
            Map.entry("concrete_grey_dark", "concrete_grey_dark"),
            Map.entry("concrete_brown_pipes", "concrete_brown_pipes"),
            Map.entry("concrete_brown_light_scaff", "concrete_brown_light_scaff"),
            Map.entry("container_red", "container_red"),
            Map.entry("container_green", "container_green"),
            Map.entry("container_blue", "container_blue"),
            Map.entry("container_orange", "container_orange"),
            Map.entry("panel_large_border", "panel_large_border"),
            Map.entry("steelframe_blue", "steelframe_blue"),
            Map.entry("steelframe_dark", "steelframe_dark"),
            Map.entry("steelframe_scaffold", "steelframe_scaffold"),
            Map.entry("nethermetal_panel", "nethermetal_panel"),
            Map.entry("nethermetal_grate1", "nethermetal_grate1"),
            Map.entry("nethermetal_grate2", "nethermetal_grate2"),
            Map.entry("nethermetal_grey_dark", "nethermetal_grey_dark"),
            Map.entry("nethermetal_grey", "nethermetal_grey"),
            Map.entry("nethermetal_grey_tiles", "nethermetal_grey_tiles"),
            Map.entry("nethermetal_border_red", "nethermetal_border_red"),
            Map.entry("nethermetal_plate_black", "nethermetal_plate_black"),
            Map.entry("nethermetal_plate_red", "nethermetal_plate_red"),
            Map.entry("nethermetal_border_lava", "nethermetal_border_lava"),
            Map.entry("ore_cluster_coal", "ore_cluster_coal"),
            Map.entry("ore_cluster_common_metal", "ore_cluster_common_metal"),
            Map.entry("ore_cluster_rare_metal", "ore_cluster_rare_metal"),
            Map.entry("ore_cluster_shiny_metal", "ore_cluster_shiny_metal"),
            Map.entry("ore_cluster_uranium", "ore_cluster_uranium"),
            Map.entry("ore_cluster_common_gem", "ore_cluster_common_gem"),
            Map.entry("ore_cluster_shiny_gem", "ore_cluster_shiny_gem"),
            Map.entry("ore_cluster_nether_crystal", "ore_cluster_nether_crystal"),
            Map.entry("ore_cluster_oil", "ore_cluster_oil"),
            Map.entry("neontubes4_rotated", "neontubes4_rotated"),
            Map.entry("neontubes2_rotated", "neontubes2_rotated"),
            Map.entry("neontubes4", "neontubes4"),
            Map.entry("neonsquare_white", "neonsquare_white"),
            Map.entry("neontubes2", "neontubes2"),
            Map.entry("dungeon_scanner", "dungeon_scanner"),
            Map.entry("dungeon_generator", "dungeon_generator"),
            Map.entry("camo_bench", "camo_bench"),
            Map.entry("repair_bench", "repair_bench"),
            Map.entry("blast_furnace", "blast_furnace"),
            Map.entry("grinder", "grinder"),
            Map.entry("fabricator_housing", "fabricator_housing"),
            Map.entry("fabricator_glass", "fabricator_glass"),
            Map.entry("fabricator_controller", "fabricator_controller"),
            Map.entry("reactionchamber_housing", "reactionchamber_housing"),
            Map.entry("reactionchamber_glass", "reactionchamber_glass"),
            Map.entry("reactionchamber_controller", "reactionchamber_controller"),
            Map.entry("oredrill_frame", "oredrill_frame"),
            Map.entry("oredrill_scaffold", "oredrill_scaffold"),
            Map.entry("oredrill_rod", "oredrill_rod"),
            Map.entry("oredrill_engine", "oredrill_engine"),
            Map.entry("oredrill_controller", "oredrill_controller"),
            Map.entry("bugnest_sand", "bugnest_sand"),
            Map.entry("bugnest_eggs", "bugnest_eggs"),
            Map.entry("airmarker", "airmarker"),
            Map.entry("antiairmarker", "antiairmarker"),
            Map.entry("hole", "hole"),
            Map.entry("soldier_spawn", "soldier_spawn"),
            Map.entry("sandbags", "sandbags_center")
    );

    public static final List<String> HORIZONTAL_MODEL_BLOCKS = List.of(
            "bunkerdoor",
            "door3x3_metal",
            "door3x3_nether",
            "hangar_door_up",
            "hangar_door_down",
            "armor_bench",
            "charging_station"
    );

    public static final Map<String, String> HORIZONTAL_MODEL_NAMES = Map.ofEntries(
            Map.entry("bunkerdoor", "bunker_door_bottom"),
            Map.entry("door3x3_metal", "techdoor3x3_full"),
            Map.entry("door3x3_nether", "techdoor3x3_full_nether"),
            Map.entry("hangar_door_up", "hangar_door_closed"),
            Map.entry("hangar_door_down", "hangar_door_down_closed"),
            Map.entry("armor_bench", "upgrade_bench"),
            Map.entry("charging_station", "charging_station_centered")
    );

    public static final List<String> DIRECTIONAL_MODEL_BLOCKS = List.of(
            "tnt_charge",
            "c4_charge"
    );

    public static final Map<String, String> DIRECTIONAL_MODEL_NAMES = Map.ofEntries(
            Map.entry("tnt_charge", "tnt_charge"),
            Map.entry("c4_charge", "c4_charge")
    );

    public static final Map<String, Integer> ROTATED_MODEL_BLOCKS = Map.ofEntries(
            Map.entry("interiormarker_north", 0),
            Map.entry("interiormarker_east", 90),
            Map.entry("interiormarker_south", 180),
            Map.entry("interiormarker_west", 270)
    );

    public static final Map<String, String> GENERATED_CUBE_ALL_BLOCKS = Map.ofEntries(
            Map.entry("ammo_press", "ammopress"),
            Map.entry("chem_lab", "chemlab"),
            Map.entry("turret_base", "turret_base")
    );

    public static final Map<String, List<String>> MILITARY_CRATE_TEXTURES = Map.ofEntries(
            Map.entry("military_crate_ammo", List.of("crate_top_dark", "military_crate_side", "military_crate_bottom")),
            Map.entry("military_crate_gun", List.of("crate_top_dark", "military_crate_gun", "military_crate_bottom")),
            Map.entry("military_crate_armor", List.of("crate_top", "armor_crate_side", "crate_top")),
            Map.entry("military_crate_medical", List.of("crate_top_red", "medical_crate_side", "crate_top_blue")),
            Map.entry("military_crate_explosive", List.of("crate_top_red", "explosive_crate", "crate_top_dark")),
            Map.entry("crate_oak", List.of("crate_top", "crate_top", "crate_top")),
            Map.entry("crate_jungle", List.of("crate_top_dark", "crate_top_dark", "crate_top_dark")),
            Map.entry("crate_birch", List.of("crate_top_blue", "crate_top_blue", "crate_top_blue")),
            Map.entry("crate_spruce", List.of("crate_top_red", "crate_top_red", "crate_top_red"))
    );

    public static final Map<String, String> CAMONET_BLOCKS = Map.ofEntries(
            Map.entry("camonet_wood", "camonet"),
            Map.entry("camonet_desert", "camonet_desert"),
            Map.entry("camonet_snow", "camonet_snow")
    );

    public static final Map<String, String> CAMONET_TOP_BLOCKS = Map.ofEntries(
            Map.entry("camonet_top_wood", "camonet"),
            Map.entry("camonet_top_desert", "camonet_desert"),
            Map.entry("camonet_top_snow", "camonet_snow")
    );

    public static final Map<String, String> LADDER_BLOCKS = Map.ofEntries(
            Map.entry("metal_ladder", "ladder_metal"),
            Map.entry("shiny_metal_ladder", "ladder_shiny"),
            Map.entry("rusty_metal_ladder", "ladder_rusty"),
            Map.entry("carbon_ladder", "ladder_carbon"),
            Map.entry(SLIMY_LADDER, SLIMY_LADDER)
    );

    public static final Map<String, String> STATIC_MODEL_BLOCKS = Map.ofEntries(
            Map.entry("lamp_yellow", "lamp_yellow"),
            Map.entry("lamp_white", "lamp_white")
    );

    public static final Map<String, String> LANTERN_BLOCKS = Map.ofEntries(
            Map.entry("lantern_yellow", "lantern_yellow"),
            Map.entry("lantern_white", "lantern_white")
    );

    public static final Map<String, String> BLOCK_ITEM_DISPLAY_MODEL_TEMPLATES = Map.ofEntries(
            Map.entry("ammo_press", "basicmachine"),
            Map.entry("metal_press", "basicmachine"),
            Map.entry("chem_lab", "basicmachine"),
            Map.entry("charging_station", "basicmachine"),
            Map.entry("armor_bench", "basicmachine"),
            Map.entry("turret_base", "basicmachine")
    );

    public static final Map<String, String> STAIRS = Map.ofEntries(
            Map.entry("panel_large_border_stairs", "panel_large_border"),
            Map.entry("steelframe_dark_stairs", "steelframe_dark"),
            Map.entry("concrete_grey_dark_stairs", "concrete_grey_dark"),
            Map.entry("concrete_brown_light_stairs", "concrete_brown_light")
    );

    public static final Map<String, Integer> LIGHT_BLOCKS = Map.ofEntries(
            Map.entry("ore_uranium", 7),
            Map.entry("nethermetal_border_lava", 15),
            Map.entry("neontubes4_rotated", 15),
            Map.entry("neontubes2_rotated", 15),
            Map.entry("neontubes4", 15),
            Map.entry("neonsquare_white", 15),
            Map.entry("neontubes2", 15),
            Map.entry("lamp_yellow", 15),
            Map.entry("lamp_white", 15),
            Map.entry("lantern_yellow", 15),
            Map.entry("lantern_white", 15),
            Map.entry(BIOBLOB, 7)
    );

    public static final List<String> CUTOUT_BLOCKS = List.of(
            "hole",
            "soldier_spawn",
            "camonet_wood",
            "camonet_desert",
            "camonet_snow",
            "camonet_top_wood",
            "camonet_top_desert",
            "camonet_top_snow",
            "metal_ladder",
            "shiny_metal_ladder",
            "rusty_metal_ladder",
            "carbon_ladder",
            SLIMY_LADDER,
            "sandbags",
            BIOBLOB,
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
            "door3x3_metal",
            "door3x3_nether",
            "hangar_door_up",
            "hangar_door_down",
            "bunkerdoor",
            "fabricator_glass",
            "reactionchamber_glass",
            "oredrill_scaffold"
    );

    public static final Map<String, String> BLOCK_ITEM_TEXTURE_OVERRIDES = Map.ofEntries(
            Map.entry("bunkerdoor", "item_bunkerdoor"),
            Map.entry("door3x3_metal", "item_door3x3"),
            Map.entry("door3x3_nether", "item_door3x3_nether"),
            Map.entry("hangar_door_up", "hangar_door_up"),
            Map.entry("hangar_door_down", "hangar_door_down")
    );

    private TGBlockCatalog() {
    }
}
