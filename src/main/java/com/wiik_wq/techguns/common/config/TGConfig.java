package com.wiik_wq.techguns.common.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.mojang.logging.LogUtils;
import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TGConfig {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<String, String> REGISTRY_ITEM_OPTIONS = Map.of(
            "ingotcopper", "addCopperIngot",
            "nuggetcopper", "addCopperNugget",
            "ingottin", "addTinIngot",
            "ingotbronze", "addBronzeIngot",
            "ingotlead", "addLeadIngot",
            "nuggetlead", "addLeadNugget",
            "ingotsteel", "addSteelIngot",
            "nuggetsteel", "addSteelNugget"
    );

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final Common COMMON;
    public static final Server SERVER;
    public static final Client CLIENT;

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        COMMON = new Common(commonBuilder);
        COMMON_SPEC = commonBuilder.build();

        ForgeConfigSpec.Builder serverBuilder = new ForgeConfigSpec.Builder();
        SERVER = new Server(serverBuilder);
        SERVER_SPEC = serverBuilder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        CLIENT = new Client(clientBuilder);
        CLIENT_SPEC = clientBuilder.build();
    }

    private TGConfig() {
    }

    public static void register() {
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC, TechgunsReborn.MODID + "-common.toml");
        context.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC, TechgunsReborn.MODID + "-server.toml");
        context.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC, TechgunsReborn.MODID + "-client.toml");
    }

    public static boolean shouldRegisterItem(String id) {
        String option = REGISTRY_ITEM_OPTIONS.get(id);
        return option == null || EarlyRegistryOptions.ITEMS.getOrDefault(option, true);
    }

    private static final class EarlyRegistryOptions {
        private static final Map<String, Boolean> ITEMS = load();

        private EarlyRegistryOptions() {
        }

        private static Map<String, Boolean> load() {
            Path configPath = FMLPaths.CONFIGDIR.get().resolve(TechgunsReborn.MODID + "-common.toml");
            if (!Files.isRegularFile(configPath)) {
                return Map.of();
            }

            Map<String, Boolean> values = new HashMap<>();
            try (CommentedFileConfig config = CommentedFileConfig.builder(configPath).build()) {
                config.load();
                for (String option : REGISTRY_ITEM_OPTIONS.values()) {
                    Boolean value = config.get(List.of("items", option));
                    if (value != null) {
                        values.put(option, value);
                    }
                }
            } catch (RuntimeException exception) {
                LOGGER.warn("Could not read early Techguns registry config from {}. Registry item defaults will be used.",
                        configPath, exception);
            }
            return Map.copyOf(values);
        }
    }

    public static final class Common {
        public final ForgeConfigSpec.BooleanValue addCopperIngot;
        public final ForgeConfigSpec.BooleanValue addCopperNugget;
        public final ForgeConfigSpec.BooleanValue addTinIngot;
        public final ForgeConfigSpec.BooleanValue addBronzeIngot;
        public final ForgeConfigSpec.BooleanValue addLeadIngot;
        public final ForgeConfigSpec.BooleanValue addLeadNugget;
        public final ForgeConfigSpec.BooleanValue addSteelIngot;
        public final ForgeConfigSpec.BooleanValue addSteelNugget;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Registry-affecting item toggles. Changing these requires a full game restart.")
                    .push("items");

            addCopperIngot = builder.comment("Add Techguns copper ingots.")
                    .define("addCopperIngot", true);
            addCopperNugget = builder.comment("Add Techguns copper nuggets.")
                    .define("addCopperNugget", true);
            addTinIngot = builder.comment("Add Techguns tin ingots.")
                    .define("addTinIngot", true);
            addBronzeIngot = builder.comment("Add Techguns bronze ingots.")
                    .define("addBronzeIngot", true);
            addLeadIngot = builder.comment("Add Techguns lead ingots.")
                    .define("addLeadIngot", true);
            addLeadNugget = builder.comment("Add Techguns lead nuggets.")
                    .define("addLeadNugget", true);
            addSteelIngot = builder.comment("Add Techguns steel ingots.")
                    .define("addSteelIngot", true);
            addSteelNugget = builder.comment("Add Techguns steel nuggets.")
                    .define("addSteelNugget", true);

            builder.pop();
        }
    }

    public static final class Server {
        public final ForgeConfigSpec.BooleanValue debug;
        public final ForgeConfigSpec.IntValue upgradeXpCost;
        public final ForgeConfigSpec.BooleanValue restrictUnsafeModeToOp;
        public final ForgeConfigSpec.BooleanValue disableAutofeeder;
        public final ForgeConfigSpec.BooleanValue machinesNeedNoPower;
        public final ForgeConfigSpec.BooleanValue keepLavaRecipesWhenFuelIsPresent;
        public final ForgeConfigSpec.BooleanValue disableRadiationSystem;
        public final ForgeConfigSpec.DoubleValue explosiveChargeMaxHardness;
        public final ForgeConfigSpec.DoubleValue explosiveChargeAdvancedMaxHardness;

        public final ForgeConfigSpec.IntValue distanceSpawnLevel0;
        public final ForgeConfigSpec.IntValue distanceSpawnLevel1;
        public final ForgeConfigSpec.IntValue distanceSpawnLevel2;
        public final ForgeConfigSpec.IntValue spawnWeightTechgunsOverworld;
        public final ForgeConfigSpec.IntValue spawnWeightTechgunsNether;
        public final ForgeConfigSpec.IntValue spawnWeightZombieSoldier;
        public final ForgeConfigSpec.IntValue spawnWeightZombieFarmer;
        public final ForgeConfigSpec.IntValue spawnWeightZombieMiner;
        public final ForgeConfigSpec.IntValue spawnWeightZombiePigmanSoldier;
        public final ForgeConfigSpec.IntValue spawnWeightCyberDemon;
        public final ForgeConfigSpec.IntValue spawnWeightBandit;
        public final ForgeConfigSpec.IntValue spawnWeightSkeletonSoldier;
        public final ForgeConfigSpec.IntValue spawnWeightPsychoSteve;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> biomeBlacklist;

        public final ForgeConfigSpec.DoubleValue damagePvP;
        public final ForgeConfigSpec.DoubleValue damageTurretToPlayer;
        public final ForgeConfigSpec.DoubleValue damageFactorNPC;

        public final ForgeConfigSpec.BooleanValue doOreGenCopper;
        public final ForgeConfigSpec.BooleanValue doOreGenTin;
        public final ForgeConfigSpec.BooleanValue doOreGenLead;
        public final ForgeConfigSpec.BooleanValue doOreGenUranium;
        public final ForgeConfigSpec.BooleanValue doOreGenTitanium;
        public final ForgeConfigSpec.BooleanValue spawnStructures;
        public final ForgeConfigSpec.IntValue structureSpawnWeightSmall;
        public final ForgeConfigSpec.IntValue structureSpawnWeightMedium;
        public final ForgeConfigSpec.IntValue structureSpawnWeightBig;
        public final ForgeConfigSpec.BooleanValue spawnOreClusterStructures;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> fluidListFuel;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> fluidListOil;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> fluidListOilWorldspawn;

        public final ForgeConfigSpec.DoubleValue oreDrillMultiplierOre;
        public final ForgeConfigSpec.DoubleValue oreDrillMultiplierPower;
        public final ForgeConfigSpec.DoubleValue oreDrillFuelMultiplier;
        public final ForgeConfigSpec.DoubleValue oreDrillFuelValueFuel;

        public final OreClusterConfig coalCluster;
        public final OreClusterConfig commonMetalCluster;
        public final OreClusterConfig rareMetalCluster;
        public final OreClusterConfig shinyMetalCluster;
        public final OreClusterConfig uraniumCluster;
        public final OreClusterConfig commonGemCluster;
        public final OreClusterConfig shinyGemCluster;
        public final OreClusterConfig netherCrystalCluster;
        public final OreClusterConfig oilCluster;

        private Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Gameplay and balance options synced from the server.")
                    .push("general");

            debug = builder.comment("Enable debug options and unfinished content. Keep disabled for normal gameplay.")
                    .define("debug", false);
            upgradeXpCost = builder.comment("Base XP value for Upgrade Bench recipes and enchant upgrades.")
                    .defineInRange("UpgradeXPCost", 20, 0, 10000);
            restrictUnsafeModeToOp = builder.comment("Only opped players can use unsafe gun mode.")
                    .define("RestrictUnsafeModeToOP", false);
            disableAutofeeder = builder.comment("Disable automatic eating from the Techguns inventory food slots.")
                    .define("disableAutofeeder", false);
            machinesNeedNoPower = builder.comment("Machines do not need FE power when enabled.")
                    .define("machinesNeedNoPower", false);
            keepLavaRecipesWhenFuelIsPresent = builder.comment("Keep lava recipes even when compatible fuel fluids are present.")
                    .define("keepLavaRecipesWhenFuelIsPresent", false);
            disableRadiationSystem = builder.comment("Disable player radiation. Legacy default keeps the unfinished system disabled.")
                    .define("WIP_disableRadiationSystem", true);
            explosiveChargeMaxHardness = builder.comment("Highest block hardness normal explosive charges can break. Obsidian is 50.")
                    .defineInRange("ExplosiveChargeMaxHardness", 30.0D, 0.0D, Double.MAX_VALUE);
            explosiveChargeAdvancedMaxHardness = builder.comment("Highest block hardness advanced explosive charges can break. Obsidian is 50.")
                    .defineInRange("ExplosiveChargeAdvancedMaxHardness", 100.0D, 0.0D, Double.MAX_VALUE);

            builder.pop();

            builder.comment("Techguns NPC spawn balance. A weight of 0 disables that entry.")
                    .push("npc_spawn");

            distanceSpawnLevel0 = builder.comment("Distance from world spawn up to which only danger level 0 mobs can spawn.")
                    .defineInRange("DistanceSpawnLevel0", 500, 0, Integer.MAX_VALUE);
            distanceSpawnLevel1 = builder.comment("Distance from world spawn up to which danger level 1 mobs can spawn.")
                    .defineInRange("DistanceSpawnLevel1", 1000, 0, Integer.MAX_VALUE);
            distanceSpawnLevel2 = builder.comment("Distance from world spawn up to which danger level 2 mobs can spawn.")
                    .defineInRange("DistanceSpawnLevel2", 2500, 0, Integer.MAX_VALUE);
            spawnWeightTechgunsOverworld = builder.comment("Base spawn weight for Techguns NPCs in the overworld.")
                    .defineInRange("TechgunsSpawnWeightOverworld", 600, 0, 10000);
            spawnWeightTechgunsNether = builder.comment("Base spawn weight for Techguns NPCs in the Nether.")
                    .defineInRange("TechgunsSpawnWeightNether", 300, 0, 10000);
            spawnWeightZombieSoldier = builder.comment("Spawn weight for Zombie Soldiers.")
                    .defineInRange("SpawnWeightZombieSoldier", 100, 0, 10000);
            spawnWeightZombieFarmer = builder.comment("Spawn weight for Zombie Farmers.")
                    .defineInRange("SpawnWeightZombieFarmer", 200, 0, 10000);
            spawnWeightZombieMiner = builder.comment("Spawn weight for Zombie Miners.")
                    .defineInRange("SpawnWeightZombieMiner", 200, 0, 10000);
            spawnWeightZombiePigmanSoldier = builder.comment("Spawn weight for Zombie Pigman Soldiers in the Nether.")
                    .defineInRange("SpawnWeightZombiePigmanSoldier", 100, 0, 10000);
            spawnWeightCyberDemon = builder.comment("Spawn weight for Cyber Demons in the Nether.")
                    .defineInRange("SpawnWeightCyberDemon", 30, 0, 10000);
            spawnWeightBandit = builder.comment("Spawn weight for Bandit groups.")
                    .defineInRange("SpawnWeightBandit", 50, 0, 10000);
            spawnWeightSkeletonSoldier = builder.comment("Spawn weight for Skeleton Soldiers.")
                    .defineInRange("SpawnWeightSkeletonSoldier", 100, 0, 10000);
            spawnWeightPsychoSteve = builder.comment("Spawn weight for Psycho Steve, the early game boss.")
                    .defineInRange("SpawnWeightPsychoSteve", 3, 0, 10000);
            biomeBlacklist = defineStringList(builder, "BiomeBlacklist", List.of(""),
                    "Biome registry names excluded from Techguns monster spawning.");

            builder.pop();

            builder.comment("Damage multipliers for Techguns weapons and NPCs.")
                    .push("damage_factors");

            damagePvP = builder.comment("Techguns weapon damage multiplier from players against players.")
                    .defineInRange("DamagePvP", 0.5D, 0.0D, 100.0D);
            damageTurretToPlayer = builder.comment("Techguns turret damage multiplier against players.")
                    .defineInRange("DamageTurretToPlayer", 0.5D, 0.0D, 100.0D);
            damageFactorNPC = builder.comment("Damage multiplier for NPCs other than turrets.")
                    .defineInRange("DamageFactorNPC", 1.0D, 0.0D, 100.0D);

            builder.pop();

            builder.comment("World generation options.")
                    .push("world_generation");

            doOreGenCopper = builder.comment("Generate Techguns copper ore.")
                    .define("doOreGenCopper", true);
            doOreGenTin = builder.comment("Generate Techguns tin ore.")
                    .define("doOreGenTin", true);
            doOreGenLead = builder.comment("Generate Techguns lead ore.")
                    .define("doOreGenLead", true);
            doOreGenTitanium = builder.comment("Generate Techguns titanium ore.")
                    .define("doOreGenTitanium", true);
            doOreGenUranium = builder.comment("Generate Techguns uranium ore.")
                    .define("doOreGenUranium", true);
            spawnStructures = builder.comment("Spawn Techguns structures such as military bases.")
                    .define("SpawnStructures", true);
            structureSpawnWeightBig = builder.comment("Legacy big structure chunk-grid spacing.")
                    .defineInRange("StructureSpawnWeightBig", 64, 16, 100000);
            structureSpawnWeightSmall = builder.comment("Legacy small structure chunk-grid spacing.")
                    .defineInRange("StructureSpawnWeightSmall", 16, 4, 100000);
            structureSpawnWeightMedium = builder.comment("Legacy medium structure chunk-grid spacing.")
                    .defineInRange("StructureSpawnWeightMedium", 32, 8, 100000);
            spawnOreClusterStructures = builder.comment("Include worldgen structures that contain ore clusters.")
                    .define("SpawnOreClusterStructures", true);

            builder.pop();

            builder.comment("Fluid compatibility lists used by fuel tanks, recipes, and ore drill oil clusters.")
                    .push("fluid_recipes");

            fluidListFuel = defineStringList(builder, "FluidListFuel", List.of(
                    "fuel", "refined_fuel", "biofuel", "biodiesel", "diesel", "gasoline",
                    "fluiddiesel", "fluidnitrodiesel", "fliudnitrofuel", "refined_biofuel",
                    "fire_water", "rocket_fuel"
            ), "Fluids that can be used to fill fuel tanks.");
            fluidListOil = defineStringList(builder, "FluidListOil", List.of(
                    "oil", "tree_oil", "crude_oil", "fluidoil", "seed_oil"
            ), "Fluids treated as oil.");
            fluidListOilWorldspawn = defineStringList(builder, "FluidListOilWorldspawn", List.of(
                    "oil", "crude_oil"
            ), "Fluids treated as oil for world spawning and oil ore clusters.");

            builder.pop();

            builder.comment("Ore drill balance and ore cluster values.")
                    .push("ore_drills");

            oreDrillMultiplierOre = builder.comment("Multiplier for how many ores an ore drill produces.")
                    .defineInRange("oreDrillMultiplierOre", 1.0D, 0.001D, 1000.0D);
            oreDrillMultiplierPower = builder.comment("Multiplier for how much power an ore drill requires.")
                    .defineInRange("oreDrillMultiplierPower", 1.0D, 0.0D, 1000.0D);
            oreDrillFuelMultiplier = builder.comment("Furnace fuel conversion: burnTime * this value = internal ore drill FE.")
                    .defineInRange("oreDrillFuelMultiplier", 1000.0D, 1.0D, 100000.0D);
            oreDrillFuelValueFuel = builder.comment("Liquid fuel value per millibucket for ore drills.")
                    .defineInRange("oreDrillFuelValueFuel", 100.0D, 1.0D, 100000.0D);

            coalCluster = new OreClusterConfig(builder, "coal", 0, 10.0D, 0.1D);
            commonMetalCluster = new OreClusterConfig(builder, "common_metal", 0, 5.0D, 0.2D);
            rareMetalCluster = new OreClusterConfig(builder, "rare_metal", 1, 2.5D, 0.4D);
            shinyMetalCluster = new OreClusterConfig(builder, "shiny_metal", 2, 1.0D, 1.0D);
            uraniumCluster = new OreClusterConfig(builder, "uranium", 3, 0.5D, 1.0D);
            commonGemCluster = new OreClusterConfig(builder, "common_gem", 1, 5.0D, 0.2D);
            shinyGemCluster = new OreClusterConfig(builder, "shiny_gem", 3, 0.2D, 1.0D);
            netherCrystalCluster = new OreClusterConfig(builder, "nether_crystal", 2, 4.0D, 0.5D);
            oilCluster = new OreClusterConfig(builder, "oil", 2, 4.0D, 1.0D);

            builder.pop();
        }

        private static ForgeConfigSpec.ConfigValue<List<? extends String>> defineStringList(
                ForgeConfigSpec.Builder builder,
                String path,
                List<String> defaults,
                String comment
        ) {
            return builder.comment(comment)
                    .defineList(path, defaults, value -> value instanceof String);
        }
    }

    public static final class Client {
        public final ForgeConfigSpec.BooleanValue enableDeathEffects;
        public final ForgeConfigSpec.BooleanValue enableGoreDeathEffect;
        public final ForgeConfigSpec.BooleanValue lockSpeedDependantFov;
        public final ForgeConfigSpec.DoubleValue fixedSprintFovMultiplier;
        public final ForgeConfigSpec.IntValue particleDepthSortPasses;

        private Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client-only visual and input options.")
                    .push("client");

            enableDeathEffects = builder.comment("Enable Techguns death effects.")
                    .define("EnableDeathEffects", true);
            enableGoreDeathEffect = builder.comment("Enable gore death effects. Requires death effects to be enabled.")
                    .define("EnableGoreDeathEffect", true);
            lockSpeedDependantFov = builder.comment("Counter speed-dependent FOV changes.")
                    .define("LockSpeedDependantFov", true);
            fixedSprintFovMultiplier = builder.comment("FOV multiplier while sprinting when speed-dependent FOV is locked.")
                    .defineInRange("FixedSprintFovMultiplier", 1.15D, 1.0D, 10.0D);
            particleDepthSortPasses = builder.comment("Particle depth sort passes per tick. 0 disables sorting.")
                    .defineInRange("ParticleDepthSortPasses", 10, 0, 20);

            builder.pop();
        }
    }

    public static final class OreClusterConfig {
        public final ForgeConfigSpec.IntValue miningLevel;
        public final ForgeConfigSpec.DoubleValue oreMultiplier;
        public final ForgeConfigSpec.DoubleValue powerMultiplier;

        private OreClusterConfig(ForgeConfigSpec.Builder builder, String clusterId, int miningLevelDefault,
                                 double oreMultiplierDefault, double powerMultiplierDefault) {
            miningLevel = builder.comment("Mining level for " + clusterId + " ore clusters.")
                    .defineInRange("cluster_mininglevel_" + clusterId, miningLevelDefault, 0, 10);
            oreMultiplier = builder.comment("Ore amount multiplier for " + clusterId + " ore clusters.")
                    .defineInRange("cluster_oremult_" + clusterId, oreMultiplierDefault, 0.0001D, 1000.0D);
            powerMultiplier = builder.comment("Power multiplier for " + clusterId + " ore clusters.")
                    .defineInRange("cluster_powermult_" + clusterId, powerMultiplierDefault, 0.0001D, 1000.0D);
        }
    }
}
