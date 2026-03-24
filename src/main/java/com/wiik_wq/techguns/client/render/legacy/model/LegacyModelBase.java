package com.wiik_wq.techguns.client.render.legacy.model;

import net.minecraft.world.entity.Entity;

public class LegacyModelBase {

    public int textureWidth = 64;
    public int textureHeight = 32;

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale) {
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    }
}
