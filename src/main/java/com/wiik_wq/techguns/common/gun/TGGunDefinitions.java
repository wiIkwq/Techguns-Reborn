package com.wiik_wq.techguns.common.gun;

import com.wiik_wq.techguns.common.item.TGGunItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TGGunDefinitions {

    private static final String DEFAULT = "default";
    private static final String INCENDIARY = "incendiary";

    private static final TGAmmoProfile ASSAULT_RIFLE_MAGAZINE = new TGAmmoProfile(
            "assault_rifle_magazine",
            "assaultriflemagazineempty",
            List.of(
                    new TGAmmoProfile.Variant(DEFAULT, "assaultriflemagazine"),
                    new TGAmmoProfile.Variant(INCENDIARY, "assaultriflemagazine_incendiary")
            )
    );

    private static final Map<String, TGGunDefinition> DEFINITIONS = createDefinitions();

    private TGGunDefinitions() {
    }

    public static Optional<TGGunDefinition> byId(String id) {
        return Optional.ofNullable(DEFINITIONS.get(id));
    }

    public static Optional<TGGunDefinition> forStack(ItemStack stack) {
        if (stack.getItem() instanceof TGGunItem gunItem) {
            return byId(gunItem.gunId());
        }
        return Optional.empty();
    }

    public static boolean hasDefinition(String id) {
        return DEFINITIONS.containsKey(id);
    }

    private static Map<String, TGGunDefinition> createDefinitions() {
        Map<String, TGGunDefinition> definitions = new LinkedHashMap<>();
        assaultRifle(definitions, "ak47", 3, 30, 45, 9.0F, 0.030F, 20.0F, 30.0F, 5.0F, 2.5F);
        assaultRifle(definitions, "m4", 3, 30, 45, 8.0F, 0.015F, 25.0F, 40.0F, 6.0F, 3.0F);
        assaultRifle(definitions, "m4_infiltrator", 3, 30, 45, 8.0F, 0.010F, 25.0F, 35.0F, 6.0F, 2.0F);
        assaultRifle(definitions, "aug", 3, 30, 45, 8.0F, 0.010F, 30.0F, 45.0F, 7.0F, 3.0F);
        assaultRifle(definitions, "scar", 4, 20, 45, 12.0F, 0.015F, 35.0F, 60.0F, 10.0F, 3.0F);
        return Map.copyOf(definitions);
    }

    private static void assaultRifle(Map<String, TGGunDefinition> definitions, String id, int fireDelayTicks, int clipSize,
                                     int reloadTicks, float damage, float spread, float damageDropStart,
                                     float damageDropEnd, float minimumDamage, float bulletSpeed) {
        definitions.put(id, new TGGunDefinition(
                id,
                ASSAULT_RIFLE_MAGAZINE,
                TGGunDefinition.FireMode.AUTOMATIC,
                fireDelayTicks,
                clipSize,
                reloadTicks,
                damage,
                60.0D,
                spread,
                damageDropStart,
                damageDropEnd,
                minimumDamage,
                bulletSpeed,
                SoundEvents.CROSSBOW_SHOOT,
                SoundEvents.CROSSBOW_LOADING_END
        ));
    }
}
