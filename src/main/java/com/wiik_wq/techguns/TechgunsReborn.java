package com.wiik_wq.techguns;

import com.wiik_wq.techguns.client.TGRenderLayers;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGCreativeTabs;
import com.wiik_wq.techguns.common.registration.TGItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(TechgunsReborn.MODID)
public class TechgunsReborn {

    public static final String MODID = "techguns";

    public TechgunsReborn() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TGItems.register(modEventBus);
        TGBlocks.register(modEventBus);
        TGCreativeTabs.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(TGRenderLayers::register);
        }
    }
}
