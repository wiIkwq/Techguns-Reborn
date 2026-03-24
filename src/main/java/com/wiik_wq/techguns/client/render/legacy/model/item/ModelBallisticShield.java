package com.wiik_wq.techguns.client.render.legacy.model.item;

import com.wiik_wq.techguns.client.render.legacy.model.LegacySimpleModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelRenderer;

public class ModelBallisticShield extends LegacySimpleModel {
	public LegacyModelRenderer Plate;
    public LegacyModelRenderer Handle;
    public LegacyModelRenderer TopBorder;
    public LegacyModelRenderer BottomBorder;
    public LegacyModelRenderer SideBorder0;
    public LegacyModelRenderer SideBorder1;
    public LegacyModelRenderer SideBorder3;
    public LegacyModelRenderer SideBorder2;
    public LegacyModelRenderer Glass;

    public ModelBallisticShield() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.BottomBorder = new LegacyModelRenderer(this, 0, 15);
        this.BottomBorder.setRotationPoint(-5.0F, 10.0F, -1.5F);
        this.BottomBorder.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.SideBorder1 = new LegacyModelRenderer(this, 27, 0);
        this.SideBorder1.setRotationPoint(-6.0F, -4.5F, -1.5F);
        this.SideBorder1.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.SideBorder3 = new LegacyModelRenderer(this, 23, 0);
        this.SideBorder3.setRotationPoint(-5.0F, -10.5F, -1.5F);
        this.SideBorder3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.SideBorder0 = new LegacyModelRenderer(this, 27, 0);
        this.SideBorder0.setRotationPoint(5.0F, -4.5F, -1.5F);
        this.SideBorder0.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.Glass = new LegacyModelRenderer(this, 0, 17);
        this.Glass.setRotationPoint(-4.0F, -8.0F, -1.8F);
        this.Glass.addBox(0.0F, 0.0F, 0.0F, 8, 4, 1, 0.0F);
        this.Handle = new LegacyModelRenderer(this, 16, 20);
        this.Handle.setRotationPoint(-1.0F, -3.0F, -1.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
        this.TopBorder = new LegacyModelRenderer(this, 0, 22);
        this.TopBorder.setRotationPoint(-4.0F, -11.0F, -1.5F);
        this.TopBorder.addBox(0.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
        this.SideBorder2 = new LegacyModelRenderer(this, 23, 0);
        this.SideBorder2.setRotationPoint(4.0F, -10.5F, -1.5F);
        this.SideBorder2.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.Plate = new LegacyModelRenderer(this, 0, 0);
        this.Plate.setRotationPoint(-5.0F, -4.0F, -2.0F);
        this.Plate.addBox(0.0F, 0.0F, 0.0F, 10, 14, 1, 0.0F);
    }

    @Override
    public void render(float scale) {
        this.BottomBorder.render(scale);
        this.SideBorder1.render(scale);
        this.SideBorder3.render(scale);
        this.SideBorder0.render(scale);
        this.Glass.render(scale);
        this.Handle.render(scale);
        this.TopBorder.render(scale);
        this.SideBorder2.render(scale);
        this.Plate.render(scale);
    }
}
