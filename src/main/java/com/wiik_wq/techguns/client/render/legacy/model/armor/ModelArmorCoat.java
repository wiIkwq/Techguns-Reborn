package com.wiik_wq.techguns.client.render.legacy.model.armor;

import com.wiik_wq.techguns.client.render.legacy.model.LegacyBipedModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelRenderer;

public class ModelArmorCoat extends LegacyBipedModel {

    public LegacyModelRenderer coatback;
    public LegacyModelRenderer coatLeftLeg;
    public LegacyModelRenderer coatRight;
    public LegacyModelRenderer coatLeft;
    public LegacyModelRenderer coatRightLeg;
    public LegacyModelRenderer leftShoulder;
    public LegacyModelRenderer rightShoulder;

    public ModelArmorCoat(int type, float scale) {
        super(scale, 0.0F, 64, 64);

        this.bipedHead = new LegacyModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);

        this.bipedHeadwear = new LegacyModelRenderer(this, 32, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);

        this.bipedBody = new LegacyModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);

        this.bipedRightArm = new LegacyModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);

        this.bipedLeftArm = new LegacyModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);

        this.bipedRightLeg = new LegacyModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);

        this.bipedLeftLeg = new LegacyModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);

        if (type == 1) {
            this.coatLeft = new LegacyModelRenderer(this, 50, 47);
            this.coatLeft.mirror = true;
            this.coatLeft.setRotationPoint(7.0F, -0.1F, -0.5F);
            this.coatLeft.addBox(-4.0F, 0.0F, -2.0F, 2, 12, 5, scale);
            this.bipedBody.addChild(coatLeft);

            this.coatback = new LegacyModelRenderer(this, 44, 35);
            this.coatback.setRotationPoint(-1.0F, -0.1F, 4.5F);
            this.coatback.addBox(-4.0F, 0.0F, -2.0F, 10, 12, 0, scale);
            this.bipedBody.addChild(coatback);

            this.coatRight = new LegacyModelRenderer(this, 50, 47);
            this.coatRight.setRotationPoint(-1.0F, -0.1F, -0.5F);
            this.coatRight.addBox(-4.0F, 0.0F, -2.0F, 2, 12, 5, scale);
            this.bipedBody.addChild(coatRight);

            this.leftShoulder = new LegacyModelRenderer(this, 0, 34);
            this.leftShoulder.mirror = true;
            this.leftShoulder.setRotationPoint(1.0F, -0.5F, -1.0F);
            this.leftShoulder.addBox(-2.0F, -2.5F, -2.0F, 5, 3, 6, scale);
            this.setRotation(leftShoulder, 0.0F, 0.0F, 0.2268928F);
            this.bipedLeftArm.addChild(leftShoulder);

            this.rightShoulder = new LegacyModelRenderer(this, 0, 34);
            this.rightShoulder.setRotationPoint(-1.0F, -0.5F, -1.0F);
            this.rightShoulder.addBox(-3.0F, -2.5F, -2.0F, 5, 3, 6, scale);
            this.setRotation(rightShoulder, 0.0F, 0.0F, -0.2268928F);
            this.bipedRightArm.addChild(rightShoulder);
        } else if (type == 2) {
            this.coatLeftLeg = new LegacyModelRenderer(this, 0, 48);
            this.coatLeftLeg.mirror = true;
            this.coatLeftLeg.setRotationPoint(0.1F, -0.1F, -0.5F);
            this.coatLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 5, 10, 5, scale);
            this.bipedLeftLeg.addChild(coatLeftLeg);

            this.coatRightLeg = new LegacyModelRenderer(this, 0, 48);
            this.coatRightLeg.setRotationPoint(-1.1F, -0.1F, -0.5F);
            this.coatRightLeg.addBox(-2.0F, 0.0F, -2.0F, 5, 10, 5, scale);
            this.bipedRightLeg.addChild(coatRightLeg);
        }
    }
}
