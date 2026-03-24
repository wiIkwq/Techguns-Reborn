package com.wiik_wq.techguns.client.render.legacy.model;

public abstract class LegacySimpleModel extends LegacyModelBase {

    public abstract void render(float scale);

    protected void setRotation(LegacyModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
