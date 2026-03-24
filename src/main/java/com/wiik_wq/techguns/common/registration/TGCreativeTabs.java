package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class TGCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TechgunsReborn.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.techguns"))
            .icon(() -> TGItems.ENTRIES.getOrDefault("m4", null) != null
                    ? new ItemStack(TGItems.ENTRIES.get("m4").item().get())
                    : new ItemStack(Items.IRON_INGOT))
            .displayItems((parameters, output) -> {
                TGBlocks.displayItems().forEach(blockItem -> output.accept(blockItem.get()));
                TGItems.displayItems().forEach(item -> output.accept(item.get()));
            })
            .build());

    private TGCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
