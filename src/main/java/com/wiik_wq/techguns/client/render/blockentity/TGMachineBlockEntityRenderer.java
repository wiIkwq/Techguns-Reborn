package com.wiik_wq.techguns.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.wiik_wq.techguns.client.render.legacy.LegacyRenderContext;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelTexture;
import com.wiik_wq.techguns.client.render.legacy.model.LegacySimpleModel;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelAmmoPress;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelChemLab;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelTurretBase;
import com.wiik_wq.techguns.common.block.TGDirectionalBlock;
import com.wiik_wq.techguns.common.block.TGMachineBlock;
import com.wiik_wq.techguns.common.blockentity.TGMachineBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class TGMachineBlockEntityRenderer implements BlockEntityRenderer<TGMachineBlockEntity> {

    private static final float LEGACY_SCALE = 0.0625F;
    private static final Map<String, LegacySimpleModel> MODELS = Map.of(
            "ammo_press", new ModelAmmoPress(),
            "chem_lab", new ModelChemLab(),
            "turret_base", new ModelTurretBase()
    );
    private static final Map<String, ResourceLocation> TEXTURES = Map.of(
            "ammo_press", ResourceLocation.parse("techguns:textures/blocks/ammopress.png"),
            "chem_lab", ResourceLocation.parse("techguns:textures/blocks/chemlab.png"),
            "turret_base", ResourceLocation.parse("techguns:textures/blocks/turret_base.png")
    );

    public TGMachineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TGMachineBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        Block block = state.getBlock();
        String id = net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(block).getPath();
        LegacySimpleModel model = MODELS.get(id);
        ResourceLocation texture = TEXTURES.get(id);
        if (model == null || texture == null) {
            return;
        }

        poseStack.pushPose();
        LegacyRenderContext.begin(poseStack, bufferSource, packedLight, packedOverlay);
        LegacyModelTexture.set(texture);
        try {
            if ("turret_base".equals(id)) {
                applyTurretTransform(poseStack, state);
            } else {
                applyMachineTransform(poseStack, state);
            }
            model.render(LEGACY_SCALE);
        } finally {
            LegacyModelTexture.clear();
            LegacyRenderContext.end();
        }
        poseStack.popPose();
    }

    private static void applyMachineTransform(PoseStack poseStack, BlockState state) {
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        Direction facing = state.getValue(TGMachineBlock.FACING);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F * facing.get2DDataValue()));
    }

    private static void applyTurretTransform(PoseStack poseStack, BlockState state) {
        Direction facing = state.getValue(TGDirectionalBlock.FACING);
        switch (facing) {
            case DOWN -> {
                poseStack.translate(0.5D, -0.5D, 0.5D);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            }
            case EAST -> {
                poseStack.translate(1.5D, 0.5D, 0.5D);
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }
            case NORTH -> {
                poseStack.translate(0.5D, 0.5D, -0.5D);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }
            case SOUTH -> {
                poseStack.translate(0.5D, 0.5D, 1.5D);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }
            case WEST -> {
                poseStack.translate(-0.5D, 0.5D, 0.5D);
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }
            case UP -> {
                poseStack.translate(0.5D, 1.5D, 0.5D);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            }
        }
    }
}
