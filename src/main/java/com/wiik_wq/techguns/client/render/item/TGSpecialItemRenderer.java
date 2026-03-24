package com.wiik_wq.techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class TGSpecialItemRenderer extends BlockEntityWithoutLevelRenderer {

    private static TGSpecialItemRenderer instance;

    private TGSpecialItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public static TGSpecialItemRenderer get() {
        if (instance == null) {
            instance = new TGSpecialItemRenderer();
        }
        return instance;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, com.mojang.blaze3d.vertex.PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        TGSpecialItemRenderRegistry.ItemDefinition definition = TGSpecialItemRenderRegistry.definition(stack);
        if (definition == null) {
            super.renderByItem(stack, context, poseStack, bufferSource, packedLight, packedOverlay);
            return;
        }
        definition.render(context, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
