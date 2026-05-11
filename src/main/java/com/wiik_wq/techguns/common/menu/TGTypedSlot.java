package com.wiik_wq.techguns.common.menu;

import com.wiik_wq.techguns.common.player.TGSlotType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TGTypedSlot extends SlotItemHandler {

    private final TGSlotType type;

    public TGTypedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TGSlotType type) {
        super(itemHandler, index, xPosition, yPosition);
        this.type = type;
    }

    public TGSlotType type() {
        return type;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return TGSlotRules.mayPlace(type, stack);
    }
}
