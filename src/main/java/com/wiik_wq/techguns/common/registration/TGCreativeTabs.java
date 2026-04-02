package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGCreativeTabOrder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public final class TGCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TechgunsReborn.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.techguns"))
            .icon(() -> TGItems.ENTRIES.getOrDefault("m4", null) != null
                    ? new ItemStack(TGItems.ENTRIES.get("m4").item().get())
                    : new ItemStack(Items.IRON_INGOT))
            .displayItems((parameters, output) -> displayLegacyOrdered(output))
            .build());

    private TGCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    private static void displayLegacyOrdered(CreativeModeTab.Output output) {
        Set<String> emittedItems = new HashSet<>();
        Set<String> emittedBlocks = new HashSet<>();

        TGCreativeTabOrder.legacyBlockOrder().forEach(id -> acceptBlock(output, emittedBlocks, id));
        TGCreativeTabOrder.legacyItemOrder().forEach(id -> acceptItem(output, emittedItems, id));
        TGCreativeTabOrder.legacyStandaloneItemOrder().forEach(id -> acceptItem(output, emittedItems, id));
        TGCreativeTabOrder.legacyItemBlockOrder().forEach(id -> acceptBlock(output, emittedBlocks, id));
        TGCreativeTabOrder.legacyToolOrder().forEach(id -> acceptItem(output, emittedItems, id));
        TGCreativeTabOrder.legacyArmorOrder().forEach(id -> acceptItem(output, emittedItems, id));
        TGCreativeTabOrder.legacyShieldOrder().forEach(id -> acceptItem(output, emittedItems, id));
        TGCreativeTabOrder.legacyGunOrder().forEach(id -> acceptItem(output, emittedItems, id));
    }

    private static void acceptItem(CreativeModeTab.Output output, Set<String> emittedItems, String id) {
        TGItems.ItemEntry entry = TGItems.ENTRIES.get(id);
        if (entry != null && emittedItems.add(id)) {
            output.accept(entry.item().get());
        }
    }

    private static void acceptBlock(CreativeModeTab.Output output, Set<String> emittedBlocks, String id) {
        TGBlocks.BlockEntry entry = TGBlocks.ENTRIES.get(id);
        if (entry != null && emittedBlocks.add(id)) {
            output.accept(entry.item().get());
            return;
        }

        TGFluids.FluidBlockEntry fluidEntry = TGFluids.BLOCK_ENTRIES.get(id);
        if (fluidEntry != null && emittedBlocks.add(id)) {
            output.accept(fluidEntry.item().get());
        }
    }
}
