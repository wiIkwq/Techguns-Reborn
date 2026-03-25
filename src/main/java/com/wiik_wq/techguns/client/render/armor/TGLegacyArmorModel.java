package com.wiik_wq.techguns.client.render.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyBipedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EquipmentSlot;

public final class TGLegacyArmorModel extends Model {

    private final LegacyBipedModel legacyModel;

    public TGLegacyArmorModel(LegacyBipedModel legacyModel) {
        super(RenderType::entityCutoutNoCull);
        this.legacyModel = legacyModel;
    }

    public TGLegacyArmorModel sync(HumanoidModel<?> sourceModel, EquipmentSlot slot) {
        legacyModel.copyPoseFrom(sourceModel);
        legacyModel.setVisibilityForSlot(slot);
        return this;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        legacyModel.renderVisibleParts(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
