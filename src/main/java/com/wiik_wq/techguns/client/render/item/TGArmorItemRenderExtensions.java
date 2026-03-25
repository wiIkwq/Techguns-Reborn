package com.wiik_wq.techguns.client.render.item;

import com.wiik_wq.techguns.client.render.armor.TGArmorRenderRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public enum TGArmorItemRenderExtensions implements IClientItemExtensions {
    INSTANCE;

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return TGSpecialItemRenderer.get();
    }

    @Override
    public Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot,
                                      HumanoidModel<?> originalModel) {
        Model model = TGArmorRenderRegistry.getArmorModel(itemStack, equipmentSlot, originalModel);
        return model != null ? model : originalModel;
    }
}
