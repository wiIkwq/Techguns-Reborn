package com.wiik_wq.techguns.client.input;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.item.TGGunItem;
import com.wiik_wq.techguns.common.network.TGNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TGClientGunInputHandler {

    private TGClientGunInputHandler() {
    }

    @SubscribeEvent
    public static void onInteractionKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack() || event.getHand() != InteractionHand.MAIN_HAND || !holdingActiveGun()) {
            return;
        }

        event.setCanceled(true);
        event.setSwingHand(false);
        TGNetwork.fireGun(InteractionHand.MAIN_HAND);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !TGKeyMappings.RELOAD.consumeClick() || !holdingActiveGun()) {
            return;
        }

        TGNetwork.reloadGun(InteractionHand.MAIN_HAND);
    }

    private static boolean holdingActiveGun() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen != null) {
            return false;
        }

        ItemStack stack = minecraft.player.getMainHandItem();
        return stack.getItem() instanceof TGGunItem gunItem && gunItem.hasGunDefinition();
    }
}
