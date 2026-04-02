package com.wiik_wq.techguns.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TGCrosshairOverlay {

    private static final ResourceLocation TG_CROSSHAIRS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, "textures/gui/tg_crosshairs.png");
    private static final int TEXTURE_SIZE = 256;

    private TGCrosshairOverlay() {
    }

    @SubscribeEvent
    public static void onRenderCrosshair(RenderGuiOverlayEvent.Pre event) {
        if (!VanillaGuiOverlay.CROSSHAIR.id().equals(event.getOverlay().id())) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        TGGunCrosshairProfiles.CrosshairProfile profile = TGGunCrosshairProfiles.profileFor(minecraft.player.getMainHandItem());
        if (profile.style() == TGCrosshairStyle.VANILLA) {
            return;
        }

        event.setCanceled(true);
        render(event.getGuiGraphics(), profile);
    }

    private static void render(GuiGraphics graphics, TGGunCrosshairProfiles.CrosshairProfile profile) {
        int x = graphics.guiWidth() / 2;
        int y = graphics.guiHeight() / 2;
        int spacing = profile.spacing();

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (profile.style() == TGCrosshairStyle.GUN_DYNAMIC) {
            blit(graphics, x - (4 + spacing), y, 3, 7, 4, 1);
            blit(graphics, x + (1 + spacing), y, 8, 7, 4, 1);
            blit(graphics, x, y, 7, 7, 1, 1);
            blit(graphics, x, y - (4 + spacing), 7, 3, 1, 4);
            blit(graphics, x, y + (1 + spacing), 7, 8, 1, 4);
        } else {
            renderStatic(graphics, profile.style(), x, y, spacing);
        }

        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private static void renderStatic(GuiGraphics graphics, TGCrosshairStyle style, int x, int y, int spacing) {
        int u = style.textureU();
        int v = style.textureV();

        switch (style.dynamicType) {
            case BOTH -> {
                blit(graphics, x - 1, y - 1, u + 6, v + 6, 3, 3);
                blit(graphics, x - 6 - spacing, y - 2, u, v + 5, 6, 5);
                blit(graphics, x + 1 + spacing, y - 2, u + 9, v + 5, 6, 5);
                blit(graphics, x - 2, y - 6 - spacing, u + 5, v, 5, 6);
                blit(graphics, x - 2, y + 1 + spacing, u + 5, v + 9, 5, 6);
            }
            case HORIZONTAL -> {
                blit(graphics, x - 1, y - 1, u + 6, v + 6, 3, 3);
                blit(graphics, x - 6 - spacing, y - 7, u, v, 6, 16);
                blit(graphics, x + 1 + spacing, y - 7, u + 9, v, 6, 16);
            }
            case X -> {
                blit(graphics, x - 1, y - 1, u + 6, v + 6, 3, 3);
                blit(graphics, x - 6 - spacing, y - 6 - spacing, u, v, 6, 6);
                blit(graphics, x + 1 + spacing, y - 6 - spacing, u + 9, v, 6, 6);
                blit(graphics, x - 6 - spacing, y + 1 + spacing, u, v + 9, 6, 6);
                blit(graphics, x + 1 + spacing, y + 1 + spacing, u + 9, v + 9, 6, 6);
            }
            case TRI -> {
                blit(graphics, x - 1, y - 1, u + 6, v + 6, 3, 3);
                blit(graphics, x - 7, y - 6 - spacing, u, v, 16, 6);
                blit(graphics, x - 6 - spacing, y + 1 + spacing, u, v + 9, 6, 6);
                blit(graphics, x + 1 + spacing, y + 1 + spacing, u + 9, v + 9, 6, 6);
            }
            case TRI_INV -> {
                blit(graphics, x - 1, y - 1, u + 6, v + 6, 3, 3);
                blit(graphics, x - 6 - spacing, y - 6 - spacing, u, v, 6, 6);
                blit(graphics, x + 1 + spacing, y - 6 - spacing, u + 9, v, 6, 6);
                blit(graphics, x - 7, y + 1 + spacing, u, v + 9, 16, 6);
            }
            case NONE, VERTICAL -> blit(graphics, x - 7, y - 7, u, v, 16, 16);
        }
    }

    private static void blit(GuiGraphics graphics, int x, int y, int u, int v, int width, int height) {
        graphics.blit(TG_CROSSHAIRS_TEXTURE, x, y, u, v, width, height, TEXTURE_SIZE, TEXTURE_SIZE);
    }
}
