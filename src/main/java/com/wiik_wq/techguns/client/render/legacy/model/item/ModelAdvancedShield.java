package com.wiik_wq.techguns.client.render.legacy.model.item;

import com.wiik_wq.techguns.client.render.legacy.model.LegacySimpleModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelRenderer;

public class ModelAdvancedShield extends LegacySimpleModel {
    public LegacyModelRenderer Plate;
    public LegacyModelRenderer Handle;
    public LegacyModelRenderer TopBorder;
    public LegacyModelRenderer BottomBorder;
    public LegacyModelRenderer SideBorder0;
    public LegacyModelRenderer SideBorder1;
    public LegacyModelRenderer Glass;
    public LegacyModelRenderer SideBorder2;
    public LegacyModelRenderer SideBorder3;

    public ModelAdvancedShield() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.SideBorder0 = new LegacyModelRenderer(this, 20, 0);
        this.SideBorder0.setRotationPoint(4.0F, -4.0F, -2.0F);
        this.SideBorder0.addBox(0.0F, 0.0F, 0.0F, 3, 15, 1, 0.0F);
        this.setRotateAngle(SideBorder0, 0.0F, -0.39269908169872414F, 0.0F);
        this.BottomBorder = new LegacyModelRenderer(this, 0, 16);
        this.BottomBorder.setRotationPoint(-4.0F, 10.0F, -2.0F);
        this.BottomBorder.addBox(0.0F, 0.0F, 0.0F, 8, 2, 1, 0.0F);
        this.setRotateAngle(BottomBorder, 0.39269908169872414F, 0.0F, 0.0F);
        this.Glass = new LegacyModelRenderer(this, 26, 17);
        this.Glass.setRotationPoint(-4.0F, -10.5F, -1.8F);
        this.Glass.addBox(0.0F, 0.0F, 0.0F, 8, 7, 1, 0.0F);
        this.SideBorder3 = new LegacyModelRenderer(this, 29, 0);
        this.SideBorder3.setRotationPoint(4.0F, -11.0F, -2.0F);
        this.SideBorder3.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
        this.setRotateAngle(SideBorder3, 0.0F, -0.39269908169872414F, 0.0F);
        this.Handle = new LegacyModelRenderer(this, 14, 20);
        this.Handle.setRotationPoint(-1.0F, -3.0F, -1.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
        this.SideBorder1 = new LegacyModelRenderer(this, 20, 0);
        this.SideBorder1.mirror = true;
        this.SideBorder1.setRotationPoint(-4.0F, -4.0F, -2.0F);
        this.SideBorder1.addBox(-3.0F, 0.0F, 0.0F, 3, 15, 1, 0.0F);
        this.setRotateAngle(SideBorder1, 0.0F, 0.39269908169872414F, 0.0F);
        this.Plate = new LegacyModelRenderer(this, 0, 0);
        this.Plate.setRotationPoint(-4.0F, -4.0F, -2.0F);
        this.Plate.addBox(0.0F, 0.0F, 0.0F, 8, 14, 1, 0.0F);
        this.TopBorder = new LegacyModelRenderer(this, 0, 20);
        this.TopBorder.setRotationPoint(-4.0F, -10.5F, -2.1F);
        this.TopBorder.addBox(0.0F, -1.0F, 0.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(TopBorder, -0.39269908169872414F, 0.0F, 0.0F);
        this.SideBorder2 = new LegacyModelRenderer(this, 29, 0);
        this.SideBorder2.mirror = true;
        this.SideBorder2.setRotationPoint(-4.0F, -11.0F, -2.0F);
        this.SideBorder2.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
        this.setRotateAngle(SideBorder2, 0.0F, 0.39269908169872414F, 0.0F);
    }

    @Override
    public void render(float scale) {
        this.SideBorder0.render(scale);
        this.BottomBorder.render(scale);
        this.Glass.render(scale);
        this.SideBorder3.render(scale);
        this.Handle.render(scale);
        this.SideBorder1.render(scale);
        this.Plate.render(scale);
        this.TopBorder.render(scale);
        this.SideBorder2.render(scale);
    }

    public void setRotateAngle(LegacyModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
