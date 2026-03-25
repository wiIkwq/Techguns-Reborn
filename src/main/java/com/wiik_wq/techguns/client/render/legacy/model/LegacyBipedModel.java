package com.wiik_wq.techguns.client.render.legacy.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
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

    public void renderVisibleParts(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                                   float red, float green, float blue, float alpha) {
        renderPart(bipedHead, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedHeadwear, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedBody, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedRightArm, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedLeftArm, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedRightLeg, poseStack, consumer, light, overlay, red, green, blue, alpha);
        renderPart(bipedLeftLeg, poseStack, consumer, light, overlay, red, green, blue, alpha);
    }

    protected void renderPart(LegacyModelRenderer part, float scale) {
        if (part != null) {
            part.render(scale);
        }
    }

    protected void renderPart(LegacyModelRenderer part, PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                              float red, float green, float blue, float alpha) {
        if (part != null) {
            part.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
        }
    }

    public void copyPoseFrom(HumanoidModel<?> source) {
        copyPose(bipedHead, source.head);
        copyPose(bipedHeadwear, source.hat);
        copyPose(bipedBody, source.body);
        copyPose(bipedRightArm, source.rightArm);
        copyPose(bipedLeftArm, source.leftArm);
        copyPose(bipedRightLeg, source.rightLeg);
        copyPose(bipedLeftLeg, source.leftLeg);
    }

    public void setAllPartsVisible(boolean visible) {
        setVisible(bipedHead, visible);
        setVisible(bipedHeadwear, visible);
        setVisible(bipedBody, visible);
        setVisible(bipedRightArm, visible);
        setVisible(bipedLeftArm, visible);
        setVisible(bipedRightLeg, visible);
        setVisible(bipedLeftLeg, visible);
    }

    public void setVisibilityForSlot(EquipmentSlot slot) {
        setAllPartsVisible(false);
        switch (slot) {
            case HEAD -> {
                setVisible(bipedHead, true);
                setVisible(bipedHeadwear, true);
            }
            case CHEST -> {
                setVisible(bipedBody, true);
                setVisible(bipedRightArm, true);
                setVisible(bipedLeftArm, true);
            }
            case LEGS -> {
                setVisible(bipedBody, true);
                setVisible(bipedRightLeg, true);
                setVisible(bipedLeftLeg, true);
            }
            case FEET -> {
                setVisible(bipedRightLeg, true);
                setVisible(bipedLeftLeg, true);
            }
            default -> {
            }
        }
    }

    private void copyPose(LegacyModelRenderer target, ModelPart source) {
        if (target != null && source != null) {
            target.copyFrom(source);
        }
    }

    private void setVisible(LegacyModelRenderer part, boolean visible) {
        if (part != null) {
            part.showModel = visible;
        }
    }

    protected void setRotation(LegacyModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
