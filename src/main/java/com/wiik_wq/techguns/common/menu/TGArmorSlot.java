package com.wiik_wq.techguns.common.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;

public class TGArmorSlot extends Slot {

    private final Player player;
    private final EquipmentSlot equipmentSlot;

    public TGArmorSlot(Container container, int slot, int x, int y, Player player, EquipmentSlot equipmentSlot) {
        super(container, slot, x, y);
        this.player = player;
        this.equipmentSlot = equipmentSlot;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return Mob.getEquipmentSlotForItem(stack) == equipmentSlot;
    }

    @Override
    public boolean mayPickup(Player player) {
        ItemStack stack = getItem();
        return (stack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(stack)) && super.mayPickup(player);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        player.getInventory().setChanged();
    }
}
