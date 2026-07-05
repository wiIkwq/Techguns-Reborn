package com.wiik_wq.techguns.common.gun;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public final class TGGunState {

    private static final String AMMO_TAG = "TGAmmo";
    private static final String VARIANT_TAG = "TGAmmoVariant";
    private static final String NEXT_FIRE_TAG = "TGNextFireTick";
    private static final String RELOAD_START_TAG = "TGReloadStartTick";
    private static final String RELOAD_END_TAG = "TGReloadEndTick";
    private static final String PENDING_AMMO_TAG = "TGPendingAmmo";
    private static final String PENDING_VARIANT_TAG = "TGPendingAmmoVariant";

    private TGGunState() {
    }

    public static void ensureInitialized(ItemStack stack, TGGunDefinition definition) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(AMMO_TAG)) {
            tag.putInt(AMMO_TAG, definition.clipSize());
        }
        if (!tag.contains(VARIANT_TAG)) {
            tag.putString(VARIANT_TAG, definition.ammoProfile().defaultVariant().id());
        }
    }

    public static int ammo(ItemStack stack, TGGunDefinition definition) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(AMMO_TAG)) {
            return definition.clipSize();
        }
        return Mth.clamp(tag.getInt(AMMO_TAG), 0, definition.clipSize());
    }

    public static String variant(ItemStack stack, TGGunDefinition definition) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(VARIANT_TAG)) {
            return definition.ammoProfile().defaultVariant().id();
        }
        return tag.getString(VARIANT_TAG);
    }

    public static void setAmmo(ItemStack stack, int ammo, String variant, TGGunDefinition definition) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(AMMO_TAG, Mth.clamp(ammo, 0, definition.clipSize()));
        tag.putString(VARIANT_TAG, variant);
    }

    public static long nextFireTick(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(NEXT_FIRE_TAG);
    }

    public static void setNextFireTick(ItemStack stack, long nextFireTick) {
        stack.getOrCreateTag().putLong(NEXT_FIRE_TAG, nextFireTick);
    }

    public static boolean isReloading(ItemStack stack, long gameTime) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(RELOAD_END_TAG) && gameTime < tag.getLong(RELOAD_END_TAG);
    }

    public static float reloadProgress(ItemStack stack, long gameTime) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return 0.0F;
        }
        if (!tag.contains(RELOAD_END_TAG) || !tag.contains(RELOAD_START_TAG)) {
            return 0.0F;
        }

        long start = tag.getLong(RELOAD_START_TAG);
        long end = tag.getLong(RELOAD_END_TAG);
        if (end <= start) {
            return 1.0F;
        }
        return Mth.clamp((float) (gameTime - start) / (float) (end - start), 0.0F, 1.0F);
    }

    public static void startReload(ItemStack stack, long gameTime, int pendingAmmo, String pendingVariant, TGGunDefinition definition) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putLong(RELOAD_START_TAG, gameTime);
        tag.putLong(RELOAD_END_TAG, gameTime + definition.reloadTicks());
        tag.putInt(PENDING_AMMO_TAG, Mth.clamp(pendingAmmo, 0, definition.clipSize()));
        tag.putString(PENDING_VARIANT_TAG, pendingVariant);
        tag.putInt(AMMO_TAG, 0);
    }

    public static boolean completeReloadIfReady(ItemStack stack, long gameTime, TGGunDefinition definition) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(RELOAD_END_TAG) || gameTime < tag.getLong(RELOAD_END_TAG)) {
            return false;
        }

        String pendingVariant = tag.contains(PENDING_VARIANT_TAG)
                ? tag.getString(PENDING_VARIANT_TAG)
                : definition.ammoProfile().defaultVariant().id();
        setAmmo(stack, tag.getInt(PENDING_AMMO_TAG), pendingVariant, definition);
        clearReload(tag);
        return true;
    }

    public static void cancelReload(ItemStack stack) {
        clearReload(stack.getOrCreateTag());
    }

    private static void clearReload(CompoundTag tag) {
        tag.remove(RELOAD_START_TAG);
        tag.remove(RELOAD_END_TAG);
        tag.remove(PENDING_AMMO_TAG);
        tag.remove(PENDING_VARIANT_TAG);
    }
}
