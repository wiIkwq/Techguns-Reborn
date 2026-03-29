package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.block.TGDirectionalBlock;
import com.wiik_wq.techguns.common.block.TGHorizontalBlock;
import com.wiik_wq.techguns.common.block.TGMachineBlock;
import com.wiik_wq.techguns.common.block.TGTurretBlock;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.item.TGSpecialRendererBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class TGBlocks {

    public record BlockEntry(String id, RegistryObject<Block> block, RegistryObject<Item> item) {
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechgunsReborn.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechgunsReborn.MODID);
    public static final Map<String, BlockEntry> ENTRIES = new LinkedHashMap<>();
    private static final Set<String> SPECIAL_MACHINE_BLOCKS = Set.of(
            "ammo_press",
            "chem_lab",
            "turret_base"
    );

    static {
        TGBlockCatalog.CUBE_MODEL_BLOCKS.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id))));
        TGBlockCatalog.GENERATED_CUBE_ALL_BLOCKS.keySet().stream()
                .filter(id -> !SPECIAL_MACHINE_BLOCKS.contains(id))
                .forEach(id -> register(id, () -> new Block(defaultProps(id))));
        TGBlockCatalog.HORIZONTAL_MODEL_BLOCKS.forEach(id -> register(id, () -> new TGHorizontalBlock(defaultProps(id))));
        TGBlockCatalog.DIRECTIONAL_MODEL_BLOCKS.forEach(id -> register(id, () -> new TGDirectionalBlock(defaultProps(id))));
        TGBlockCatalog.ROTATED_MODEL_BLOCKS.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id))));
        TGBlockCatalog.MILITARY_CRATE_TEXTURES.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id).sound(SoundType.WOOD))));
        TGBlockCatalog.CAMONET_BLOCKS.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id).noOcclusion())));
        TGBlockCatalog.CAMONET_TOP_BLOCKS.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id).noOcclusion())));
        TGBlockCatalog.LADDER_BLOCKS.keySet().forEach(id -> register(id, () -> new LadderBlock(defaultProps(id).noOcclusion())));
        TGBlockCatalog.STATIC_MODEL_BLOCKS.keySet().forEach(id -> register(id, () -> new Block(defaultProps(id).noOcclusion())));
        TGBlockCatalog.STAIRS.keySet().forEach(id -> register(id, () -> new StairBlock(Blocks.STONE.defaultBlockState(), defaultProps(id))));
        register("ammo_press", () -> new TGMachineBlock(defaultProps("ammo_press").noOcclusion()));
        register("chem_lab", () -> new TGMachineBlock(defaultProps("chem_lab").noOcclusion()));
        register("turret_base", () -> new TGTurretBlock(defaultProps("turret_base").noOcclusion()));
    }

    private TGBlocks() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }

    public static Collection<BlockEntry> all() {
        return ENTRIES.values();
    }

    public static Stream<RegistryObject<Item>> displayItems() {
        return ENTRIES.values().stream().map(BlockEntry::item);
    }

    private static void register(String id, java.util.function.Supplier<Block> supplier) {
        RegistryObject<Block> block = BLOCKS.register(id, supplier);
        RegistryObject<Item> item = BLOCK_ITEMS.register(id, () ->
                SPECIAL_MACHINE_BLOCKS.contains(id)
                        ? new TGSpecialRendererBlockItem(block.get(), new Item.Properties())
                        : new BlockItem(block.get(), new Item.Properties()));
        ENTRIES.put(id, new BlockEntry(id, block, item));
    }

    private static BlockBehaviour.Properties defaultProps(String id) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(defaultMapColor(id))
                .strength(2.0F, 6.0F);

        Integer lightLevel = TGBlockCatalog.LIGHT_BLOCKS.get(id);
        if (lightLevel != null && lightLevel > 0) {
            properties = properties.lightLevel(state -> lightLevel);
        }

        if (TGBlockCatalog.CUTOUT_BLOCKS.contains(id) || id.contains("glass") || id.contains("lamp") || id.contains("lantern")) {
            properties = properties.noOcclusion();
        }

        if (id.contains("glass")) {
            properties = properties.sound(SoundType.GLASS);
        } else if (id.contains("ladder")) {
            properties = properties.sound(SoundType.METAL);
        } else if (id.contains("crate")) {
            properties = properties.sound(SoundType.WOOD);
        } else if (id.contains("door")) {
            properties = properties.sound(SoundType.METAL);
        }

        return properties;
    }

    private static MapColor defaultMapColor(String id) {
        if (id.contains("nether")) {
            return MapColor.NETHER;
        }
        if (id.contains("crate") || id.contains("camonet")) {
            return MapColor.WOOD;
        }
        if (id.contains("glass") || id.contains("lamp") || id.contains("lantern")) {
            return MapColor.COLOR_LIGHT_BLUE;
        }
        return MapColor.METAL;
    }
}
