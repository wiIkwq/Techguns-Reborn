package com.wiik_wq.techguns.common.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.items.ItemStackHandler;

public class TGPlayerData {

    public static final int SLOT_FACE = 0;
    public static final int SLOT_BACK = 1;
    public static final int SLOT_HAND = 2;
    public static final int SLOTS_AUTOFOOD_START = 3;
    public static final int SLOTS_AUTOFOOD_END = 5;
    public static final int SLOT_AUTOHEAL = 6;
    public static final int SLOTS_AMMO_START = 7;
    public static final int SLOTS_AMMO_END = 14;
    public static final int SLOT_COUNT = 15;

    public static final int TOGGLE_HUD = 0;
    public static final int TOGGLE_NIGHT_VISION = 1;
    public static final int TOGGLE_SAFE_MODE = 2;
    public static final int TOGGLE_STEP_ASSIST = 3;
    public static final int TOGGLE_JETPACK = 4;

    private static final String INVENTORY_TAG = "Inventory";
    private static final String HUD_TAG = "ShowHud";
    private static final String NIGHT_VISION_TAG = "NightVision";
    private static final String SAFE_MODE_TAG = "SafeMode";
    private static final String STEP_ASSIST_TAG = "StepAssist";
    private static final String JETPACK_TAG = "Jetpack";

    private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT);
    private boolean showHud = true;
    private boolean nightVision = false;
    private boolean safeMode = false;
    private boolean stepAssist = true;
    private boolean jetpack = true;

    public ItemStackHandler inventory() {
        return inventory;
    }

    public boolean toggle(int id) {
        boolean value = !isEnabled(id);
        setEnabled(id, value);
        return value;
    }

    public boolean isEnabled(int id) {
        return switch (id) {
            case TOGGLE_HUD -> showHud;
            case TOGGLE_NIGHT_VISION -> nightVision;
            case TOGGLE_SAFE_MODE -> safeMode;
            case TOGGLE_STEP_ASSIST -> stepAssist;
            case TOGGLE_JETPACK -> jetpack;
            default -> false;
        };
    }

    public void setEnabled(int id, boolean value) {
        switch (id) {
            case TOGGLE_HUD -> showHud = value;
            case TOGGLE_NIGHT_VISION -> nightVision = value;
            case TOGGLE_SAFE_MODE -> safeMode = value;
            case TOGGLE_STEP_ASSIST -> stepAssist = value;
            case TOGGLE_JETPACK -> jetpack = value;
            default -> {
            }
        }
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put(INVENTORY_TAG, inventory.serializeNBT());
        tag.putBoolean(HUD_TAG, showHud);
        tag.putBoolean(NIGHT_VISION_TAG, nightVision);
        tag.putBoolean(SAFE_MODE_TAG, safeMode);
        tag.putBoolean(STEP_ASSIST_TAG, stepAssist);
        tag.putBoolean(JETPACK_TAG, jetpack);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains(INVENTORY_TAG, CompoundTag.TAG_COMPOUND)) {
            inventory.deserializeNBT(tag.getCompound(INVENTORY_TAG));
        }
        showHud = readBoolean(tag, HUD_TAG, true);
        nightVision = readBoolean(tag, NIGHT_VISION_TAG, false);
        safeMode = readBoolean(tag, SAFE_MODE_TAG, false);
        stepAssist = readBoolean(tag, STEP_ASSIST_TAG, true);
        jetpack = readBoolean(tag, JETPACK_TAG, true);
    }

    public void copyFrom(TGPlayerData other) {
        deserializeNBT(other.serializeNBT());
    }

    private boolean readBoolean(CompoundTag tag, String key, boolean fallback) {
        return tag.contains(key) ? tag.getBoolean(key) : fallback;
    }
}
