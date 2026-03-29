package com.wiik_wq.techguns.client.render.legacy.model;

import com.wiik_wq.techguns.client.render.legacy.LegacyRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class LegacyModelRenderer {

    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public boolean mirror;
    public boolean showModel = true;
    public boolean isHidden;

    private final List<Box> boxes = new ArrayList<>();
    private final List<LegacyModelRenderer> children = new ArrayList<>();
    private int textureOffsetX;
    private int textureOffsetY;
    private int textureWidth;
    private int textureHeight;

    public LegacyModelRenderer(LegacyModelBase baseModel, int textureOffsetX, int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.textureWidth = baseModel.textureWidth;
        this.textureHeight = baseModel.textureHeight;
    }

    public void addBox(float x, float y, float z, int dx, int dy, int dz) {
        addBox(x, y, z, dx, dy, dz, 0.0F);
    }

    public void addBox(float x, float y, float z, int dx, int dy, int dz, float inflate) {
        boxes.add(new Box(textureOffsetX, textureOffsetY, textureWidth, textureHeight, x, y, z, dx, dy, dz, inflate, mirror));
    }

    public void addBox(float x, float y, float z, float dx, float dy, float dz, float inflate) {
        boxes.add(new Box(textureOffsetX, textureOffsetY, textureWidth, textureHeight, x, y, z, dx, dy, dz, inflate, mirror));
    }

    public void setRotationPoint(float x, float y, float z) {
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.rotationPointZ = z;
    }

    public void setTextureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void addChild(LegacyModelRenderer child) {
        children.add(child);
    }

    public void render(float scale) {
        if (isHidden || !showModel) {
            return;
        }

        render(
                LegacyRenderContext.poseStack(),
                LegacyRenderContext.consumer(LegacyModelTexture.current()),
                LegacyRenderContext.light(),
                LegacyRenderContext.overlay(),
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }

    public void render(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        if (isHidden || !showModel) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(offsetX, offsetY, offsetZ);
        translateAndRotate(poseStack, 0.0625F);
        renderBoxes(poseStack, consumer, light, overlay, red, green, blue, alpha, 0.0625F);
        for (LegacyModelRenderer child : children) {
            child.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
        }

        poseStack.popPose();
    }

    public void copyFrom(ModelPart source) {
        this.rotationPointX = source.x;
        this.rotationPointY = source.y;
        this.rotationPointZ = source.z;
        this.rotateAngleX = source.xRot;
        this.rotateAngleY = source.yRot;
        this.rotateAngleZ = source.zRot;
        this.showModel = source.visible;
    }

    private void translateAndRotate(PoseStack poseStack, float scale) {
        poseStack.translate(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
        if (rotateAngleZ != 0.0F) {
            poseStack.mulPose(com.mojang.math.Axis.ZP.rotation(rotateAngleZ));
        }
        if (rotateAngleY != 0.0F) {
            poseStack.mulPose(com.mojang.math.Axis.YP.rotation(rotateAngleY));
        }
        if (rotateAngleX != 0.0F) {
            poseStack.mulPose(com.mojang.math.Axis.XP.rotation(rotateAngleX));
        }
    }

    private void renderBoxes(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                             float red, float green, float blue, float alpha, float scale) {
        for (Box box : boxes) {
            box.render(poseStack, consumer, light, overlay, red, green, blue, alpha, scale);
        }
    }

    private static final class Box {
        private final int textureOffsetX;
        private final int textureOffsetY;
        private final int textureWidth;
        private final int textureHeight;
        private final float x;
        private final float y;
        private final float z;
        private final float dx;
        private final float dy;
        private final float dz;
        private final float inflate;
        private final boolean mirror;

        private Box(int textureOffsetX, int textureOffsetY, int textureWidth, int textureHeight, float x, float y, float z,
                    float dx, float dy, float dz, float inflate, boolean mirror) {
            this.textureOffsetX = textureOffsetX;
            this.textureOffsetY = textureOffsetY;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.x = x;
            this.y = y;
            this.z = z;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            this.inflate = inflate;
            this.mirror = mirror;
        }

        private void render(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                            float red, float green, float blue, float alpha, float scale) {
            float minX = x - inflate;
            float minY = y - inflate;
            float minZ = z - inflate;
            float maxX = x + dx + inflate;
            float maxY = y + dy + inflate;
            float maxZ = z + dz + inflate;

            if (mirror) {
                float swapped = maxX;
                maxX = minX;
                minX = swapped;
            }

            Vertex v0 = new Vertex(minX * scale, minY * scale, minZ * scale, 0.0F, 0.0F);
            Vertex v1 = new Vertex(maxX * scale, minY * scale, minZ * scale, 0.0F, 8.0F);
            Vertex v2 = new Vertex(maxX * scale, maxY * scale, minZ * scale, 8.0F, 8.0F);
            Vertex v3 = new Vertex(minX * scale, maxY * scale, minZ * scale, 8.0F, 0.0F);
            Vertex v4 = new Vertex(minX * scale, minY * scale, maxZ * scale, 0.0F, 0.0F);
            Vertex v5 = new Vertex(maxX * scale, minY * scale, maxZ * scale, 0.0F, 8.0F);
            Vertex v6 = new Vertex(maxX * scale, maxY * scale, maxZ * scale, 8.0F, 8.0F);
            Vertex v7 = new Vertex(minX * scale, maxY * scale, maxZ * scale, 8.0F, 0.0F);

            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v5, v1, v2, v6),
                    textureOffsetX + dz + dx, textureOffsetY + dz,
                    textureOffsetX + dz + dx + dz, textureOffsetY + dz + dy,
                    mirror);
            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v0, v4, v7, v3),
                    textureOffsetX, textureOffsetY + dz,
                    textureOffsetX + dz, textureOffsetY + dz + dy,
                    mirror);
            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v5, v4, v0, v1),
                    textureOffsetX + dz, textureOffsetY,
                    textureOffsetX + dz + dx, textureOffsetY + dz,
                    mirror);
            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v2, v3, v7, v6),
                    textureOffsetX + dz + dx, textureOffsetY + dz,
                    textureOffsetX + dz + dx + dx, textureOffsetY,
                    mirror);
            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v1, v0, v3, v2),
                    textureOffsetX + dz, textureOffsetY + dz,
                    textureOffsetX + dz + dx, textureOffsetY + dz + dy,
                    mirror);
            renderQuad(poseStack, consumer, light, overlay, red, green, blue, alpha, textureWidth, textureHeight,
                    quad(v4, v5, v6, v7),
                    textureOffsetX + dz + dx + dz, textureOffsetY + dz,
                    textureOffsetX + dz + dx + dz + dx, textureOffsetY + dz + dy,
                    mirror);
        }
    }

    private static void renderQuad(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                                   float red, float green, float blue, float alpha,
                                   int textureWidth, int textureHeight, Vertex[] vertices,
                                   float minU, float minV, float maxU, float maxV, boolean flipFace) {
        Vertex[] quad = vertices.clone();
        if (flipFace) {
            for (int i = 0; i < quad.length / 2; i++) {
                Vertex temp = quad[i];
                quad[i] = quad[quad.length - 1 - i];
                quad[quad.length - 1 - i] = temp;
            }
        }

        float texWidth = (float) textureWidth;
        float texHeight = (float) textureHeight;
        quad[0] = quad[0].remap(maxU / texWidth, minV / texHeight);
        quad[1] = quad[1].remap(minU / texWidth, minV / texHeight);
        quad[2] = quad[2].remap(minU / texWidth, maxV / texHeight);
        quad[3] = quad[3].remap(maxU / texWidth, maxV / texHeight);

        Vector3f edgeA = new Vector3f(
                quad[1].x - quad[0].x,
                quad[1].y - quad[0].y,
                quad[1].z - quad[0].z
        );
        Vector3f edgeB = new Vector3f(
                quad[1].x - quad[2].x,
                quad[1].y - quad[2].y,
                quad[1].z - quad[2].z
        );
        edgeB.cross(edgeA).normalize();

        PoseStack.Pose pose = poseStack.last();
        for (Vertex vertex : quad) {
            consumer.vertex(pose.pose(), vertex.x, vertex.y, vertex.z)
                    .color(red, green, blue, alpha)
                    .uv(vertex.u, vertex.v)
                    .overlayCoords(overlay)
                    .uv2(light)
                    .normal(pose.normal(), edgeB.x(), edgeB.y(), edgeB.z())
                    .endVertex();
        }
    }

    private static Vertex[] quad(Vertex a, Vertex b, Vertex c, Vertex d) {
        return new Vertex[]{a, b, c, d};
    }

    private record Vertex(float x, float y, float z, float u, float v) {
        private Vertex remap(float newU, float newV) {
            return new Vertex(x, y, z, newU, newV);
        }
    }
}
