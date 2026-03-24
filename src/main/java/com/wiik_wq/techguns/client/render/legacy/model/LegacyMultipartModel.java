package com.wiik_wq.techguns.client.render.legacy.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;

public abstract class LegacyMultipartModel extends LegacyModelBase {

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale) {
        render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
                100, 0.0F, ItemDisplayContext.GROUND, 0, 0.0F, 0.0F);
    }

    public abstract void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                                float netHeadYaw, float headPitch, float scale, int ammoLeft, float reloadProgress,
                                ItemDisplayContext transformType, int part, float fireProgress, float chargeProgress);

    protected void setRotation(LegacyModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
