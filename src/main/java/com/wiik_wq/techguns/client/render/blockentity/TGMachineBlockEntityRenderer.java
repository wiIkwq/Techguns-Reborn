package com.wiik_wq.techguns.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.wiik_wq.techguns.common.block.TGDirectionalBlock;
import com.wiik_wq.techguns.common.block.TGMachineBlock;
import com.wiik_wq.techguns.common.blockentity.TGMachineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TGMachineBlockEntityRenderer implements BlockEntityRenderer<TGMachineBlockEntity> {

    public TGMachineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TGMachineBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        Block block = state.getBlock();
        String id = net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(block).getPath();
        poseStack.pushPose();
        if ("turret_base".equals(id)) {
            applyTurretItemRotation(poseStack, state);
        } else {
            applyMachineItemRotation(poseStack, state);
        }
        poseStack.translate(0.5D, 0.5D, 0.5D);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(block.asItem());
        itemRenderer.renderStatic(stack, ItemDisplayContext.NONE, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }

    private static void applyMachineItemRotation(PoseStack poseStack, BlockState state) {
        poseStack.translate(0.5D, 0.5D, 0.5D);
        Direction facing = state.getValue(TGMachineBlock.FACING);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F * facing.get2DDataValue()));
        poseStack.translate(-0.5D, -0.5D, -0.5D);
    }

    private static void applyTurretItemRotation(PoseStack poseStack, BlockState state) {
        Direction facing = state.getValue(TGDirectionalBlock.FACING);
        poseStack.translate(0.5D, 0.5D, 0.5D);
        switch (facing) {
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            case UP -> {
            }
        }
        poseStack.translate(-0.5D, -0.5D, -0.5D);
    }
}
