package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGItems;
import net.minecraftforge.common.data.LanguageProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TGLanguageProvider extends LanguageProvider {

    private static final Map<String, List<String>> BLOCK_TRANSLATION_ALIASES = Map.ofEntries(
            Map.entry("ammo_press", List.of("block.techguns.basicmachine.0")),
            Map.entry("metal_press", List.of("block.techguns.basicmachine.1")),
            Map.entry("chem_lab", List.of("block.techguns.basicmachine.2")),
            Map.entry("turret_base", List.of("block.techguns.basicmachine.3")),
            Map.entry("dungeon_scanner", List.of("block.techguns.basicmachine.4")),
            Map.entry("dungeon_generator", List.of("block.techguns.basicmachine.5")),
            Map.entry("camo_bench", List.of("block.techguns.simplemachine.8")),
            Map.entry("repair_bench", List.of("block.techguns.simplemachine.9")),
            Map.entry("charging_station", List.of("block.techguns.simplemachine.10")),
            Map.entry("blast_furnace", List.of("block.techguns.simplemachine.11")),
            Map.entry("grinder", List.of("block.techguns.simplemachine2.8")),
            Map.entry("ore_copper", List.of("block.techguns.basicore.0")),
            Map.entry("ore_tin", List.of("block.techguns.basicore.1")),
            Map.entry("ore_lead", List.of("block.techguns.basicore.2")),
            Map.entry("ore_titanium", List.of("block.techguns.basicore.3")),
            Map.entry("ore_uranium", List.of("block.techguns.basicore.4")),
            Map.entry("fabricator_housing", List.of("block.techguns.multiblockmachine.0")),
            Map.entry("fabricator_glass", List.of("block.techguns.multiblockmachine.1")),
            Map.entry("fabricator_controller", List.of("block.techguns.multiblockmachine.2")),
            Map.entry("reactionchamber_housing", List.of("block.techguns.multiblockmachine.3")),
            Map.entry("reactionchamber_glass", List.of("block.techguns.multiblockmachine.4")),
            Map.entry("reactionchamber_controller", List.of("block.techguns.multiblockmachine.5")),
            Map.entry("lamp_yellow", List.of("block.techguns.lamp0.0")),
            Map.entry("lamp_white", List.of("block.techguns.lamp0.6")),
            Map.entry("lantern_yellow", List.of("block.techguns.lamp0.12")),
            Map.entry("lantern_white", List.of("block.techguns.lamp0.13")),
            Map.entry("sandbags", List.of("block.techguns.sandbags")),
            Map.entry("metal_ladder", List.of("block.techguns.ladder0.8")),
            Map.entry("shiny_metal_ladder", List.of("block.techguns.ladder0.9")),
            Map.entry("rusty_metal_ladder", List.of("block.techguns.ladder0.10")),
            Map.entry("carbon_ladder", List.of("block.techguns.ladder0.11")),
            Map.entry("camonet_wood", List.of("block.techguns.camonet.0")),
            Map.entry("camonet_desert", List.of("block.techguns.camonet.1")),
            Map.entry("camonet_snow", List.of("block.techguns.camonet.2")),
            Map.entry("camonet_top_wood", List.of("block.techguns.camonet_top.0")),
            Map.entry("camonet_top_desert", List.of("block.techguns.camonet_top.1")),
            Map.entry("camonet_top_snow", List.of("block.techguns.camonet_top.2")),
            Map.entry("bunkerdoor", List.of("item.techguns.item_bunkerdoor")),
            Map.entry("door3x3_metal", List.of("item.techguns.item_door3x3")),
            Map.entry("hangar_door_up", List.of("item.techguns.item_door3x3_1")),
            Map.entry("hangar_door_down", List.of("item.techguns.item_door3x3_2")),
            Map.entry("door3x3_nether", List.of("item.techguns.item_door3x3_3")),
            Map.entry("hole", List.of("block.techguns.tg_spawner.0")),
            Map.entry("soldier_spawn", List.of("block.techguns.tg_spawner.1")),
            Map.entry("bioblob", List.of("block.techguns.bioblob.0")),
            Map.entry("military_crate_ammo", List.of("block.techguns.military_crate.0")),
            Map.entry("military_crate_gun", List.of("block.techguns.military_crate.1")),
            Map.entry("military_crate_armor", List.of("block.techguns.military_crate.2")),
            Map.entry("military_crate_medical", List.of("block.techguns.military_crate.3")),
            Map.entry("military_crate_explosive", List.of("block.techguns.military_crate.4")),
            Map.entry("crate_oak", List.of("block.techguns.military_crate.5")),
            Map.entry("crate_jungle", List.of("block.techguns.military_crate.6")),
            Map.entry("crate_birch", List.of("block.techguns.military_crate.7")),
            Map.entry("crate_spruce", List.of("block.techguns.military_crate.8")),
            Map.entry("concrete_brown", List.of("block.techguns.concrete.0")),
            Map.entry("concrete_brown_light", List.of("block.techguns.concrete.1")),
            Map.entry("concrete_grey", List.of("block.techguns.concrete.2")),
            Map.entry("concrete_grey_dark", List.of("block.techguns.concrete.3")),
            Map.entry("concrete_brown_pipes", List.of("block.techguns.concrete.4")),
            Map.entry("concrete_brown_light_scaff", List.of("block.techguns.concrete.5")),
            Map.entry("neontubes2", List.of("block.techguns.neonlights.0")),
            Map.entry("neontubes2_rotated", List.of("block.techguns.neonlights.1")),
            Map.entry("neontubes4", List.of("block.techguns.neonlights.2")),
            Map.entry("neontubes4_rotated", List.of("block.techguns.neonlights.3")),
            Map.entry("neonsquare_white", List.of("block.techguns.neonlights.4")),
            Map.entry("container_red", List.of("block.techguns.metalpanel.0")),
            Map.entry("container_green", List.of("block.techguns.metalpanel.1")),
            Map.entry("container_blue", List.of("block.techguns.metalpanel.2")),
            Map.entry("container_orange", List.of("block.techguns.metalpanel.3")),
            Map.entry("panel_large_border", List.of("block.techguns.metalpanel.4")),
            Map.entry("steelframe_blue", List.of("block.techguns.metalpanel.5")),
            Map.entry("steelframe_dark", List.of("block.techguns.metalpanel.6")),
            Map.entry("steelframe_scaffold", List.of("block.techguns.metalpanel.7")),
            Map.entry("panel_large_border_stairs", List.of("block.techguns.stairs_metal.7")),
            Map.entry("steelframe_dark_stairs", List.of("block.techguns.stairs_metal.15")),
            Map.entry("tnt_charge", List.of("block.techguns.explosive_charge.0")),
            Map.entry("c4_charge", List.of("block.techguns.explosive_charge.1")),
            Map.entry("airmarker", List.of("block.techguns.debugblock.0")),
            Map.entry("antiairmarker", List.of("block.techguns.debugblock.1")),
            Map.entry("interiormarker_north", List.of("block.techguns.debugblock.2")),
            Map.entry("interiormarker_east", List.of("block.techguns.debugblock.2")),
            Map.entry("interiormarker_south", List.of("block.techguns.debugblock.2")),
            Map.entry("interiormarker_west", List.of("block.techguns.debugblock.2")),
            Map.entry("slimyladder", List.of("block.techguns.slimyladder.2")),
            Map.entry("oredrill_frame", List.of("block.techguns.oredrill.0")),
            Map.entry("oredrill_scaffold", List.of("block.techguns.oredrill.1")),
            Map.entry("oredrill_rod", List.of("block.techguns.oredrill.2")),
            Map.entry("oredrill_engine", List.of("block.techguns.oredrill.3")),
            Map.entry("oredrill_controller", List.of("block.techguns.oredrill.4", "block.techguns.oredrill")),
            Map.entry("ore_cluster_coal", List.of("block.techguns.orecluster.0")),
            Map.entry("ore_cluster_common_metal", List.of("block.techguns.orecluster.1")),
            Map.entry("ore_cluster_rare_metal", List.of("block.techguns.orecluster.2")),
            Map.entry("ore_cluster_shiny_metal", List.of("block.techguns.orecluster.3")),
            Map.entry("ore_cluster_uranium", List.of("block.techguns.orecluster.4")),
            Map.entry("ore_cluster_common_gem", List.of("block.techguns.orecluster.5")),
            Map.entry("ore_cluster_shiny_gem", List.of("block.techguns.orecluster.6")),
            Map.entry("ore_cluster_nether_crystal", List.of("block.techguns.orecluster.7")),
            Map.entry("ore_cluster_oil", List.of("block.techguns.orecluster.8")),
            Map.entry("nethermetal_grate1", List.of("block.techguns.nethermetal.1")),
            Map.entry("nethermetal_grate2", List.of("block.techguns.nethermetal.2")),
            Map.entry("nethermetal_grey_dark", List.of("block.techguns.nethermetal.3")),
            Map.entry("nethermetal_grey", List.of("block.techguns.nethermetal.4")),
            Map.entry("nethermetal_grey_tiles", List.of("block.techguns.nethermetal.5")),
            Map.entry("nethermetal_border_lava", List.of("block.techguns.nethermetal.9")),
            Map.entry("concrete_brown_light_stairs", List.of("block.techguns.stairs_concrete.15")),
            Map.entry("concrete_grey_dark_stairs", List.of("block.techguns.stairs_concrete.7"))
    );

    private final String locale;

    public TGLanguageProvider(net.minecraft.data.PackOutput output, String locale) {
        super(output, TechgunsReborn.MODID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        Map<String, String> translations = loadLegacyTranslations();

        add("itemGroup.techguns", translations.getOrDefault("itemGroup.techguns", "Techguns"));

        TGBlocks.all().forEach(entry -> add(blockKey(entry.id()), resolveBlockTranslation(translations, entry.id())));
        TGItems.all().forEach(entry -> add(itemKey(entry.id()), translations.getOrDefault(itemKey(entry.id()), humanize(entry.id()))));

        translations.forEach((key, value) -> {
            if (!key.startsWith("item." + TechgunsReborn.MODID + ".") && !key.startsWith("block." + TechgunsReborn.MODID + ".") && !"itemGroup.techguns".equals(key)) {
                add(key, value);
            }
        });
    }

    private Map<String, String> loadLegacyTranslations() {
        Map<String, String> result = new HashMap<>();
        Path file = TGDataPaths.resolve("techguns_old", "src", "main", "resources", "assets", "techguns", "lang", locale + ".lang");
        if (!Files.exists(file)) {
            return result;
        }

        try {
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank() || line.startsWith("#") || !line.contains("=")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                result.put(remapKey(parts[0].trim()), parts[1].trim());
            }
        } catch (IOException ignored) {
        }

        return result;
    }

    private String remapKey(String key) {
        if ("techguns.itemGroup.techguns".equals(key)) {
            return "itemGroup.techguns";
        }
        if (key.startsWith("item." + TechgunsReborn.MODID + ".") && key.endsWith(".name")) {
            return key.substring(0, key.length() - 5);
        }
        if (key.startsWith("tile." + TechgunsReborn.MODID + ".") && key.endsWith(".name")) {
            return "block." + TechgunsReborn.MODID + "." + key.substring(("tile." + TechgunsReborn.MODID + ".").length(), key.length() - 5);
        }
        if (key.startsWith("techguns.item.") && key.endsWith(".name")) {
            return "item." + TechgunsReborn.MODID + "." + key.substring("techguns.item.".length(), key.length() - 5);
        }
        if (key.startsWith("techguns.block.") && key.endsWith(".name")) {
            return "block." + TechgunsReborn.MODID + "." + key.substring("techguns.block.".length(), key.length() - 5);
        }
        return key;
    }

    private String resolveBlockTranslation(Map<String, String> translations, String id) {
        String direct = translations.get(blockKey(id));
        if (direct != null) {
            return direct;
        }

        List<String> aliases = BLOCK_TRANSLATION_ALIASES.get(id);
        if (aliases != null) {
            for (String alias : aliases) {
                String value = translations.get(alias);
                if (value != null) {
                    return value;
                }
            }
        }

        return humanize(id);
    }

    private String itemKey(String id) {
        return "item." + TechgunsReborn.MODID + "." + id;
    }

    private String blockKey(String id) {
        return "block." + TechgunsReborn.MODID + "." + id;
    }

    private String humanize(String id) {
        StringBuilder builder = new StringBuilder();
        for (String part : id.split("_")) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            if (part.isEmpty()) {
                continue;
            }
            builder.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                builder.append(part.substring(1));
            }
        }
        return builder.toString();
    }
}
