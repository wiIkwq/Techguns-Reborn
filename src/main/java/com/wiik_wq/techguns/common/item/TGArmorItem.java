package com.wiik_wq.techguns.common.item;

import com.wiik_wq.techguns.client.render.armor.TGArmorRenderRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && TGArmorCamoSupport.supports(stack)) {
            if (!level.isClientSide) {
                TGArmorCamoSupport.cycleForward(stack);
                player.displayClientMessage(
                        Component.translatable("techguns.message.camoswitch")
                                .append(" ")
                                .append(TGArmorCamoSupport.currentCamoComponent(stack)),
                        true
                );
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player != null && player.isShiftKeyDown() && TGArmorCamoSupport.supports(stack)) {
            if (!context.getLevel().isClientSide) {
                TGArmorCamoSupport.cycleForward(stack);
                player.displayClientMessage(
                        Component.translatable("techguns.message.camoswitch")
                                .append(" ")
                                .append(TGArmorCamoSupport.currentCamoComponent(stack)),
                        true
                );
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        if (TGArmorCamoSupport.supports(stack)) {
            tooltipComponents.add(
                    Component.translatable("techguns.tooltip.currentcamo")
                            .append(": ")
                            .append(TGArmorCamoSupport.currentCamoComponent(stack))
            );
        }
    }
}
