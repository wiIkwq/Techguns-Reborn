package com.wiik_wq.techguns.common.item;

import com.wiik_wq.techguns.client.render.armor.TGArmorRenderRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TGArmorItem extends ArmorItem {

    public TGArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(com.wiik_wq.techguns.client.render.item.TGArmorItemRenderExtensions.INSTANCE);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TGArmorRenderRegistry.getArmorTexture(stack, slot);
    }
}
