package com.wiik_wq.techguns.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public final class TGKeyMappings {

    public static final String CATEGORY = "key.categories.techguns";

    public static final KeyMapping RELOAD = new KeyMapping(
            "key.techguns.reload",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
    );

    private TGKeyMappings() {
    }

    @Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBus {

        private ModBus() {
        }

        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(RELOAD);
        }
    }
}
