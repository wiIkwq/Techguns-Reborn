package com.wiik_wq.techguns.client.render.item;

import com.wiik_wq.techguns.common.content.TGItemCatalog;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public enum TGGunItemRenderExtensions implements IClientItemExtensions {
    INSTANCE;

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return TGSpecialItemRenderer.get();
    }

    @Override
    public @Nullable HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if (key == null || !TGItemCatalog.usesBowArmPose(key.getPath())) {
            return null;
        }

        if (hand == InteractionHand.MAIN_HAND) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }

        return entityLiving.isUsingItem() && ItemStack.isSameItemSameTags(entityLiving.getUseItem(), itemStack)
                ? HumanoidModel.ArmPose.BOW_AND_ARROW
                : null;
    }

    @Override
    public boolean applyForgeHandTransform(com.mojang.blaze3d.vertex.PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
                                           ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        return false;
    }
}
