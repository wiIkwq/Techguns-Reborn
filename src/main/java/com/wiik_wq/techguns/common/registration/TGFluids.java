package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.fluid.TGSimpleFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class TGFluids {

    public record FluidBlockEntry(
            String id,
            String itemTexture,
            RegistryObject<FluidType> fluidType,
            RegistryObject<FlowingFluid> source,
            RegistryObject<FlowingFluid> flowing,
            RegistryObject<LiquidBlock> block,
            RegistryObject<Item> item
    ) {
    }

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TechgunsReborn.MODID);
    public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TechgunsReborn.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TechgunsReborn.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechgunsReborn.MODID);

    public static final Map<String, FluidBlockEntry> BLOCK_ENTRIES = new LinkedHashMap<>();

    static {
        register("block_creeper_acid", "creeper_acid", "acid_still", "acid_flow");
        register("block_milk", "milk", "milk_still", "milk_flow");
    }

    private TGFluids() {
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }

    public static Collection<FluidBlockEntry> allBlocks() {
        return BLOCK_ENTRIES.values();
    }

    private static void register(String blockId, String fluidId, String stillTexture, String flowingTexture) {
        ResourceLocation still = modLoc("blocks/" + stillTexture);
        ResourceLocation flowing = modLoc("blocks/" + flowingTexture);

        RegistryObject<FluidType> fluidType = FLUID_TYPES.register(fluidId, () -> new TGSimpleFluidType(
                FluidType.Properties.create().density(1000).viscosity(1000),
                still,
                flowing,
                0xFFFFFFFF,
                new Vector3f(1.0F, 1.0F, 1.0F)
        ));

        ForgeFlowingFluid.Properties[] properties = new ForgeFlowingFluid.Properties[1];
        RegistryObject<FlowingFluid> source = registerFlowingFluid(fluidId, () -> new ForgeFlowingFluid.Source(properties[0]));
        RegistryObject<FlowingFluid> flowingFluid = registerFlowingFluid("flowing_" + fluidId, () -> new ForgeFlowingFluid.Flowing(properties[0]));
        RegistryObject<LiquidBlock> block = BLOCKS.register(blockId, () -> new LiquidBlock(source, BlockBehaviour.Properties.copy(Blocks.WATER)));
        RegistryObject<Item> item = BLOCK_ITEMS.register(blockId, () -> new BlockItem(block.get(), new Item.Properties()));

        properties[0] = new ForgeFlowingFluid.Properties(fluidType, source, flowingFluid)
                .block(block)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2);

        BLOCK_ENTRIES.put(blockId, new FluidBlockEntry(blockId, stillTexture, fluidType, source, flowingFluid, block, item));
    }

    @SuppressWarnings("unchecked")
    private static RegistryObject<FlowingFluid> registerFlowingFluid(String id, Supplier<? extends FlowingFluid> supplier) {
        return (RegistryObject<FlowingFluid>) (RegistryObject<?>) FLUIDS.register(id, supplier);
    }

    private static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, path);
    }
}
