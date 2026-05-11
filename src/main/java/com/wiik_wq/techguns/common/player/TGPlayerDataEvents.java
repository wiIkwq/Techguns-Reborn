package com.wiik_wq.techguns.common.player;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.network.TGNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
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
                event.getEntity().getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(newData -> {
                    newData.copyFrom(oldData);
                    if (event.isWasDeath()) {
                        newData.clearAutoFoodRemainder();
                    }
                })
        );
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        TGNetwork.syncPlayerData(event.getEntity());
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        TGNetwork.syncPlayerData(event.getEntity());
    }

    @SubscribeEvent
    public static void onChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        TGNetwork.syncPlayerData(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !event.side.isServer() || !(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        serverPlayer.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> {
            if (TGAutoFoodHandler.tick(serverPlayer, data)) {
                TGNetwork.syncPlayerData(serverPlayer);
            }
        });
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)
                || serverPlayer.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }

        serverPlayer.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> {
            if (data.dropInventory(serverPlayer, event.getDrops())) {
                TGNetwork.syncPlayerData(serverPlayer);
            }
        });
    }
}
