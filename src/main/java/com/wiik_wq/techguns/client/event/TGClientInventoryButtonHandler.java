package com.wiik_wq.techguns.client.event;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.client.gui.screen.TGInventoryTabButton;
import com.wiik_wq.techguns.common.network.TGNetwork;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TGClientInventoryButtonHandler {

    private TGClientInventoryButtonHandler() {
    }

    @SubscribeEvent
    public static void onInventoryInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) {
            return;
        }

        event.addListener(new TGInventoryTabButton(
                screen.getGuiLeft() + TGInventoryTabButton.FIRST_TAB_X_OFFSET,
                screen.getGuiTop() + TGInventoryTabButton.TAB_Y_OFFSET,
                true,
                TGInventoryTabButton.vanillaInventoryIcon(),
                Component.translatable("container.crafting"),
                () -> {
                }
        ));
        event.addListener(new TGInventoryTabButton(
                screen.getGuiLeft() + TGInventoryTabButton.SECOND_TAB_X_OFFSET,
                screen.getGuiTop() + TGInventoryTabButton.TAB_Y_OFFSET,
                false,
                TGInventoryTabButton.techgunsInventoryIcon(),
                Component.translatable("container.techguns.player_inventory"),
                TGNetwork::openPlayerInventory
        ));
    }
}
