package com.wiik_wq.techguns.client.render.legacy.model;

import net.minecraft.world.entity.Entity;

public class LegacyBipedModel extends LegacyModelBase {

    public LegacyModelRenderer bipedHead;
    public LegacyModelRenderer bipedHeadwear;
    public LegacyModelRenderer bipedBody;
    public LegacyModelRenderer bipedRightArm;
    public LegacyModelRenderer bipedLeftArm;
    public LegacyModelRenderer bipedRightLeg;
    public LegacyModelRenderer bipedLeftLeg;

    public LegacyBipedModel() {
    }

    public LegacyBipedModel(float modelSize) {
    }

    public LegacyBipedModel(float modelSize, float yOffset, int textureWidthIn, int textureHeightIn) {
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale) {
        renderVisibleParts(scale);
    }

    public void renderVisibleParts(float scale) {
        renderPart(bipedHead, scale);
        renderPart(bipedHeadwear, scale);
        renderPart(bipedBody, scale);
        renderPart(bipedRightArm, scale);
        renderPart(bipedLeftArm, scale);
        renderPart(bipedRightLeg, scale);
        renderPart(bipedLeftLeg, scale);
    }

    protected void renderPart(LegacyModelRenderer part, float scale) {
        if (part != null) {
            part.render(scale);
        }
    }

    protected void setRotation(LegacyModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
