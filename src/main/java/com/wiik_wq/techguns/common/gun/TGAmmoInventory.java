package com.wiik_wq.techguns.common.gun;

import com.wiik_wq.techguns.common.network.TGNetwork;
import com.wiik_wq.techguns.common.player.TGPlayerData;
import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class TGAmmoInventory {

    private TGAmmoInventory() {
    }

    public static Optional<ReloadAmmo> consumeReloadAmmo(ServerPlayer player, TGAmmoProfile profile) {
        if (player.getAbilities().instabuild) {
            return Optional.of(new ReloadAmmo(profile.defaultVariant()));
        }

        AtomicReference<ReloadAmmo> consumed = new AtomicReference<>();
        player.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> {
            ReloadAmmo ammo = consumeFromTechgunsAmmoSlots(data.inventory(), profile);
            if (ammo != null) {
                consumed.set(ammo);
            }
        });
        if (consumed.get() != null) {
            TGNetwork.syncPlayerData(player);
            return Optional.of(consumed.get());
        }

        ReloadAmmo ammo = consumeFromPlayerInventory(player.getInventory(), profile);
        return Optional.ofNullable(ammo);
    }

    public static void giveEmptyContainer(ServerPlayer player, TGAmmoProfile profile) {
        Item emptyItem = profile.emptyContainerItem();
        if (emptyItem == Items.AIR || player.getAbilities().instabuild) {
            return;
        }

        ItemStack empty = new ItemStack(emptyItem);
        AtomicReference<ItemStack> remaining = new AtomicReference<>(empty);
        player.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> {
            remaining.set(insertIntoAmmoSlots(data.inventory(), remaining.get()));
            TGNetwork.syncPlayerData(player);
        });

        if (!remaining.get().isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, remaining.get());
        }
    }

    private static ReloadAmmo consumeFromTechgunsAmmoSlots(ItemStackHandler inventory, TGAmmoProfile profile) {
        for (int slot = TGPlayerData.SLOTS_AMMO_START; slot <= TGPlayerData.SLOTS_AMMO_END; slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            Optional<TGAmmoProfile.Variant> variant = profile.variantFor(stack);
            if (variant.isEmpty()) {
                continue;
            }

            inventory.extractItem(slot, 1, false);
            return new ReloadAmmo(variant.get());
        }
        return null;
    }

    private static ReloadAmmo consumeFromPlayerInventory(Inventory inventory, TGAmmoProfile profile) {
        for (int slot = 0; slot < inventory.items.size(); slot++) {
            ItemStack stack = inventory.items.get(slot);
            Optional<TGAmmoProfile.Variant> variant = profile.variantFor(stack);
            if (variant.isEmpty()) {
                continue;
            }

            stack.shrink(1);
            if (stack.isEmpty()) {
                inventory.items.set(slot, ItemStack.EMPTY);
            }
            inventory.setChanged();
            return new ReloadAmmo(variant.get());
        }
        return null;
    }

    private static ItemStack insertIntoAmmoSlots(ItemStackHandler inventory, ItemStack stack) {
        ItemStack remaining = stack;
        for (int slot = TGPlayerData.SLOTS_AMMO_START; slot <= TGPlayerData.SLOTS_AMMO_END && !remaining.isEmpty(); slot++) {
            remaining = inventory.insertItem(slot, remaining, false);
        }
        return remaining;
    }

    public record ReloadAmmo(TGAmmoProfile.Variant variant) {
    }
}
