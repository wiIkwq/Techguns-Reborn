package com.wiik_wq.techguns.common.menu;

import com.wiik_wq.techguns.common.player.TGPlayerData;
import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import com.wiik_wq.techguns.common.player.TGSlotType;
import com.wiik_wq.techguns.common.registration.TGMenus;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class TGPlayerInventoryMenu extends AbstractContainerMenu {

    public static final int CUSTOM_SLOT_COUNT = TGPlayerData.SLOT_COUNT;
    private static final int ARMOR_START = CUSTOM_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_START = ARMOR_START + 4;
    private static final int HOTBAR_START = PLAYER_INVENTORY_START + 27;
    private static final int OFFHAND_SLOT = HOTBAR_START + 9;
    private static final int PLAYER_INVENTORY_END = OFFHAND_SLOT + 1;

    private final Player player;

    public TGPlayerInventoryMenu(int containerId, Inventory playerInventory) {
        super(TGMenus.PLAYER_INVENTORY.get(), containerId);
        this.player = playerInventory.player;

        ItemStackHandler tgInventory = player.getCapability(TGPlayerDataProvider.CAPABILITY)
                .map(TGPlayerData::inventory)
                .orElseGet(() -> new ItemStackHandler(TGPlayerData.SLOT_COUNT));

        addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOT_FACE, 77, 8, TGSlotType.FACE));
        addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOT_BACK, 77, 26, TGSlotType.BACK));
        addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOT_HAND, 77, 44, TGSlotType.HAND));

        for (int i = 0; i < 3; i++) {
            addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOTS_AUTOFOOD_START + i, 116 + i * 18, 24, TGSlotType.FOOD));
        }

        addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOT_AUTOHEAL, 97, 24, TGSlotType.HEAL));

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                addSlot(new TGTypedSlot(tgInventory, TGPlayerData.SLOTS_AMMO_START + row * 4 + col, 98 + col * 18, 44 + row * 18, TGSlotType.AMMO));
            }
        }

        addArmorSlot(playerInventory, 39, 8, 8, EquipmentSlot.HEAD, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
        addArmorSlot(playerInventory, 38, 8, 26, EquipmentSlot.CHEST, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
        addArmorSlot(playerInventory, 37, 8, 44, EquipmentSlot.LEGS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS);
        addArmorSlot(playerInventory, 36, 8, 62, EquipmentSlot.FEET, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        addSlot(new Slot(playerInventory, 40, 77, 62).setBackground(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack moved = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return moved;
        }

        ItemStack stack = slot.getItem();
        moved = stack.copy();

        if (index < CUSTOM_SLOT_COUNT) {
            if (!moveItemStackTo(stack, PLAYER_INVENTORY_START, HOTBAR_START + 9, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= PLAYER_INVENTORY_START && index < HOTBAR_START + 9) {
            if (!moveIntoArmorSlots(stack) && !moveIntoCustomSlots(stack) && !moveBetweenInventoryAndHotbar(stack, index)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, PLAYER_INVENTORY_START, HOTBAR_START + 9, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == moved.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return moved;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.player == player && player.isAlive();
    }

    private void addArmorSlot(Inventory inventory, int slot, int x, int y, EquipmentSlot equipmentSlot, net.minecraft.resources.ResourceLocation icon) {
        addSlot(new TGArmorSlot(inventory, slot, x, y, player, equipmentSlot).setBackground(InventoryMenu.BLOCK_ATLAS, icon));
    }

    private boolean moveIntoCustomSlots(ItemStack stack) {
        for (int i = 0; i < CUSTOM_SLOT_COUNT; i++) {
            Slot customSlot = slots.get(i);
            if (customSlot.mayPlace(stack) && moveItemStackTo(stack, i, i + 1, false)) {
                return true;
            }
        }
        return false;
    }

    private boolean moveIntoArmorSlots(ItemStack stack) {
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(stack);
        int target = switch (equipmentSlot) {
            case HEAD -> ARMOR_START;
            case CHEST -> ARMOR_START + 1;
            case LEGS -> ARMOR_START + 2;
            case FEET -> ARMOR_START + 3;
            default -> -1;
        };
        return target >= 0 && !slots.get(target).hasItem() && moveItemStackTo(stack, target, target + 1, false);
    }

    private boolean moveBetweenInventoryAndHotbar(ItemStack stack, int index) {
        if (index >= PLAYER_INVENTORY_START && index < HOTBAR_START) {
            return moveItemStackTo(stack, HOTBAR_START, HOTBAR_START + 9, false);
        }
        if (index >= HOTBAR_START && index < HOTBAR_START + 9) {
            return moveItemStackTo(stack, PLAYER_INVENTORY_START, HOTBAR_START, false);
        }
        return false;
    }
}
