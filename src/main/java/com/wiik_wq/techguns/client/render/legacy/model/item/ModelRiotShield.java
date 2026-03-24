package com.wiik_wq.techguns.client.render.legacy.model.item;

import com.wiik_wq.techguns.client.render.legacy.model.LegacySimpleModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelRenderer;

public class ModelRiotShield extends LegacySimpleModel {
    public LegacyModelRenderer Plate;
    public LegacyModelRenderer Handle;
    public LegacyModelRenderer TopBorder;
    public LegacyModelRenderer BottomBorder;
    public LegacyModelRenderer SideBorder0;
    public LegacyModelRenderer SideBorder1;

    public ModelRiotShield() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.BottomBorder = new LegacyModelRenderer(this, 0, 22);
        this.BottomBorder.setRotationPoint(-5.0F, 10.0F, -1.5F);
        this.BottomBorder.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.SideBorder1 = new LegacyModelRenderer(this, 27, 0);
        this.SideBorder1.setRotationPoint(-6.0F, -10.5F, -1.5F);
        this.SideBorder1.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1, 0.0F);
        this.Plate = new LegacyModelRenderer(this, 0, 0);
        this.Plate.setRotationPoint(-5.0F, -10.0F, -2.0F);
        this.Plate.addBox(0.0F, 0.0F, 0.0F, 10, 20, 1, 0.0F);
        this.Handle = new LegacyModelRenderer(this, 16, 20);
        this.Handle.setRotationPoint(-1.0F, -3.0F, -1.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
        this.SideBorder0 = new LegacyModelRenderer(this, 27, 0);
        this.SideBorder0.setRotationPoint(5.0F, -10.5F, -1.5F);
        this.SideBorder0.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1, 0.0F);
        this.TopBorder = new LegacyModelRenderer(this, 0, 22);
        this.TopBorder.setRotationPoint(-5.0F, -11.0F, -1.5F);
        this.TopBorder.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
    }

    @Override
    public void render(float scale) {
        this.BottomBorder.render(scale);
        this.SideBorder1.render(scale);
        this.Plate.render(scale);
        this.Handle.render(scale);
        this.SideBorder0.render(scale);
        this.TopBorder.render(scale);
    }
}
