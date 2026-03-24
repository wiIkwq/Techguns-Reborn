package com.wiik_wq.techguns.client.render.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class LegacyRenderContext {

    private static final ThreadLocal<Context> CURRENT = new ThreadLocal<>();

    private LegacyRenderContext() {
    }

    public static void begin(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        CURRENT.set(new Context(poseStack, bufferSource, light, overlay));
    }

    public static void end() {
        CURRENT.remove();
    }

    public static PoseStack poseStack() {
        return require().poseStack();
    }

    public static VertexConsumer consumer(ResourceLocation texture) {
        return require().bufferSource().getBuffer(RenderType.entityCutoutNoCull(texture));
    }

    public static int light() {
        return require().light();
    }

    public static int overlay() {
        return require().overlay();
    }

    private static Context require() {
        Context context = CURRENT.get();
        if (context == null) {
            throw new IllegalStateException("Legacy render context is not active");
        }
        return context;
    }

    private record Context(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
    }
}
