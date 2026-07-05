package com.wiik_wq.techguns.common.gun;

import net.minecraft.sounds.SoundEvent;

public record TGGunDefinition(
        String id,
        TGAmmoProfile ammoProfile,
        FireMode fireMode,
        int fireDelayTicks,
        int clipSize,
        int reloadTicks,
        float damage,
        double range,
        float spread,
        float damageDropStart,
        float damageDropEnd,
        float minimumDamage,
        float bulletSpeed,
        SoundEvent fireSound,
        SoundEvent reloadSound
) {

    public boolean automatic() {
        return fireMode == FireMode.AUTOMATIC;
    }

    public enum FireMode {
        SEMI_AUTO,
        AUTOMATIC
    }
}
