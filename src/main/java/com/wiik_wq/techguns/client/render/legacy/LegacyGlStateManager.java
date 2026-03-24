package com.wiik_wq.techguns.client.render.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class LegacyGlStateManager {

    private LegacyGlStateManager() {
    }

    public static void pushMatrix() {
        poseStack().pushPose();
    }

    public static void popMatrix() {
        poseStack().popPose();
    }

    public static void translate(double x, double y, double z) {
        poseStack().translate(x, y, z);
    }

    public static void rotate(float angleDegrees, float axisX, float axisY, float axisZ) {
        Vector3f axis = new Vector3f(axisX, axisY, axisZ);
        if (axis.lengthSquared() == 0.0F) {
            return;
        }
        axis.normalize();
        poseStack().mulPose(new Quaternionf().rotationAxis((float) Math.toRadians(angleDegrees), axis.x(), axis.y(), axis.z()));
    }

    public static void scale(double x, double y, double z) {
        poseStack().scale((float) x, (float) y, (float) z);
    }

    public static void enableCull() {
    }

    public static void disableCull() {
    }

    public static void enableBlend() {
    }

    public static void disableBlend() {
    }

    public static void color(float red, float green, float blue, float alpha) {
    }

    private static PoseStack poseStack() {
        return LegacyRenderContext.poseStack();
    }
}
