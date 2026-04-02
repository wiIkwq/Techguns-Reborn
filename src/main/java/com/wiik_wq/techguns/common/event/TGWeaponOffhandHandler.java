package com.wiik_wq.techguns.common.event;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.item.TGGunItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID)
public final class TGWeaponOffhandHandler {

    private TGWeaponOffhandHandler() {
    }

    @SubscribeEvent
    public static void onSwapHands(LivingSwapItemsEvent.Hands event) {
        if (isGun(event.getItemSwappedToOffHand())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() != EquipmentSlot.OFFHAND || !isGun(event.getTo())) {
            return;
        }

        LivingEntity entity = event.getEntity();
        ItemStack offhandGun = event.getTo().copy();
        entity.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

        if (entity.getMainHandItem().isEmpty()) {
            entity.setItemSlot(EquipmentSlot.MAINHAND, offhandGun);
            return;
        }

        if (entity instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, offhandGun);
            return;
        }

        entity.spawnAtLocation(offhandGun);
    }

    private static boolean isGun(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof TGGunItem;
    }
}
