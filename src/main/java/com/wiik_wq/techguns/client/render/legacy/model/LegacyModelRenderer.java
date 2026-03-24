package com.wiik_wq.techguns.client.render.legacy.model;

import com.wiik_wq.techguns.client.render.legacy.LegacyRenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private final LegacyModelBase baseModel;
    private final List<Box> boxes = new ArrayList<>();
    private final List<LegacyModelRenderer> children = new ArrayList<>();
    private int textureOffsetX;
    private int textureOffsetY;
    private int textureWidth;
    private int textureHeight;
    private ModelPart modelPart;

    public LegacyModelRenderer(LegacyModelBase baseModel, int textureOffsetX, int textureOffsetY) {
        this.baseModel = baseModel;
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.textureWidth = baseModel.textureWidth;
        this.textureHeight = baseModel.textureHeight;
    }

    public void addBox(float x, float y, float z, int dx, int dy, int dz) {
        addBox(x, y, z, dx, dy, dz, 0.0F);
    }

    public void addBox(float x, float y, float z, int dx, int dy, int dz, float inflate) {
        boxes.add(new Box(textureOffsetX, textureOffsetY, x, y, z, dx, dy, dz, inflate));
        modelPart = null;
    }

    public void addBox(float x, float y, float z, float dx, float dy, float dz, float inflate) {
        boxes.add(new Box(textureOffsetX, textureOffsetY, x, y, z, dx, dy, dz, inflate));
        modelPart = null;
    }

    public void setRotationPoint(float x, float y, float z) {
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.rotationPointZ = z;
    }

    public void setTextureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        modelPart = null;
    }

    public void addChild(LegacyModelRenderer child) {
        children.add(child);
    }

    public void render(float scale) {
        if (isHidden || !showModel) {
            return;
        }

        PoseStack poseStack = LegacyRenderContext.poseStack();
        poseStack.pushPose();
        poseStack.translate(offsetX, offsetY, offsetZ);

        ModelPart part = syncPartTree();
        part.render(poseStack, LegacyRenderContext.consumer(LegacyModelTexture.current()),
                LegacyRenderContext.light(), LegacyRenderContext.overlay());

        poseStack.popPose();
    }

    private ModelPart syncPartTree() {
        ModelPart part = modelPart();
        part.visible = showModel && !isHidden;
        part.setPos(rotationPointX, rotationPointY, rotationPointZ);
        part.setRotation(rotateAngleX, rotateAngleY, rotateAngleZ);
        for (int i = 0; i < children.size(); i++) {
            children.get(i).syncPartTree();
        }
        return part;
    }

    private ModelPart modelPart() {
        if (modelPart != null) {
            return modelPart;
        }

        CubeListBuilder builder = CubeListBuilder.create();
        for (Box box : boxes) {
            builder.texOffs(box.textureOffsetX(), box.textureOffsetY())
                    .mirror(mirror)
                    .addBox(box.x(), box.y(), box.z(), box.dx(), box.dy(), box.dz(), new CubeDeformation(box.inflate()));
        }

        List<ModelPart.Cube> cubes = new ArrayList<>();
        for (var definition : builder.getCubes()) {
            cubes.add(definition.bake(textureWidth, textureHeight));
        }
        Map<String, ModelPart> childParts = new java.util.LinkedHashMap<>();
        for (int i = 0; i < children.size(); i++) {
            childParts.put("child_" + i, children.get(i).modelPart());
        }
        modelPart = new ModelPart(cubes, childParts);
        return modelPart;
    }

    private record Box(int textureOffsetX, int textureOffsetY, float x, float y, float z,
                       float dx, float dy, float dz, float inflate) {
    }
}
