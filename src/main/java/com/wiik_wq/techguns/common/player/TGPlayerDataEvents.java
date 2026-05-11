package com.wiik_wq.techguns.common.player;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.network.TGNetwork;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID)
public final class TGPlayerDataEvents {

    private TGPlayerDataEvents() {
    }

    @Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBus {

        private ModBus() {
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(TGPlayerData.class);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        TGPlayerDataProvider provider = new TGPlayerDataProvider();
        event.addCapability(TGPlayerDataProvider.ID, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(oldData ->
                event.getEntity().getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(newData -> newData.copyFrom(oldData))
        );
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        TGNetwork.syncPlayerData(event.getEntity());
    }
}
