package com.wiik_wq.techguns.data;

import net.minecraft.world.item.ItemDisplayContext;

import java.util.EnumMap;
import java.util.Map;

final class TGGunDisplayProfiles {

    private static final TGDisplayTransform LEGACY_FIRST_PERSON_BASE =
            new TGDisplayTransform(5.0F, 10.0F, 5.0F, 10.0F, 9.0F, 3.0F, 1.0F, 1.0F, 1.0F);

    private static final Map<String, TGDisplayTransform> LEGACY_FIRST_PERSON_OVERRIDES = Map.of(
            "flamethrower", LEGACY_FIRST_PERSON_BASE
    );

    private static final Map<GunDisplayFamily, FamilyProfile> FAMILY_PROFILES = Map.of(
            GunDisplayFamily.STANDARD, new FamilyProfile(
                    FirstPersonCalibration.identity(),
                    null,
                    new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.5F, 0.5F, 0.5F),
                    new TGDisplayTransform(0.0F, 180.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 180.0F, 0.0F, 0.0F, 13.0F, 7.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.55F, 0.55F, 0.55F)
            ),
            GunDisplayFamily.OBJ_RIFLE, new FamilyProfile(
                    FirstPersonCalibration.identity(),
                    new TGDisplayTransform(10.0F, -45.0F, -25.0F, 2F, -0.5F, 0.0F, 0.20F, 0.20F, 0.20F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.5F, 0.5F, 0.5F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 13.0F, 7.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.55F, 0.55F, 0.55F)
            ),
            GunDisplayFamily.OBJ_LAUNCHER, new FamilyProfile(
                    new FirstPersonCalibration(0.0F, 75.0F, -1F, 0F, 0.58F, -0.2F, 0.055F),
                    new TGDisplayTransform(45.0F, 135.0F, 0.0F, -1.2F, 2F, 0.0F, 0.05F, 0.05F, 0.05F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.14F, 0.14F, 0.14F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.28F, 0.28F, 0.28F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 13.0F, 7.0F, 0.28F, 0.28F, 0.28F),
                    new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.154F, 0.154F, 0.154F)
            )
    );

    private static final Map<String, GunDisplayFamily> ITEM_FAMILIES = Map.of(
            "gaussrifle", GunDisplayFamily.OBJ_RIFLE,
            "grenadelauncher", GunDisplayFamily.OBJ_LAUNCHER
    );

    private TGGunDisplayProfiles() {
    }

    static Map<ItemDisplayContext, TGDisplayTransform> standardTransforms(String id) {
        return transformsForFamily(GunDisplayFamily.STANDARD, id);
    }

    static Map<ItemDisplayContext, TGDisplayTransform> objTransforms(String id) {
        GunDisplayFamily family = ITEM_FAMILIES.get(id);
        if (family == null) {
            return Map.of();
        }
        return transformsForFamily(family, id);
    }

    static String familyName(String id) {
        GunDisplayFamily family = ITEM_FAMILIES.get(id);
        return family == null ? GunDisplayFamily.STANDARD.id() : family.id();
    }

    private static Map<ItemDisplayContext, TGDisplayTransform> transformsForFamily(GunDisplayFamily family, String id) {
        FamilyProfile profile = FAMILY_PROFILES.get(family);
        if (profile == null) {
            return Map.of();
        }
        return profile.transformsFor(legacyFirstPerson(id));
    }

    private static TGDisplayTransform legacyFirstPerson(String id) {
        return LEGACY_FIRST_PERSON_OVERRIDES.getOrDefault(id, LEGACY_FIRST_PERSON_BASE);
    }

    enum GunDisplayFamily {
        STANDARD("standard"),
        OBJ_RIFLE("obj_rifle"),
        OBJ_LAUNCHER("obj_launcher");

        private final String id;

        GunDisplayFamily(String id) {
            this.id = id;
        }

        String id() {
            return id;
        }
    }

    private record FamilyProfile(FirstPersonCalibration firstPersonCalibration,
                                 TGDisplayTransform gui,
                                 TGDisplayTransform ground,
                                 TGDisplayTransform fixed,
                                 TGDisplayTransform head,
                                 TGDisplayTransform thirdPerson) {

        private Map<ItemDisplayContext, TGDisplayTransform> transformsFor(TGDisplayTransform legacyFirstPerson) {
            EnumMap<ItemDisplayContext, TGDisplayTransform> transforms = new EnumMap<>(ItemDisplayContext.class);
            if (gui != null) {
                transforms.put(ItemDisplayContext.GUI, gui);
            }
            transforms.put(ItemDisplayContext.GROUND, ground);
            transforms.put(ItemDisplayContext.FIXED, fixed);
            transforms.put(ItemDisplayContext.HEAD, head);
            transforms.put(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, firstPersonCalibration.apply(legacyFirstPerson));
            transforms.put(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, thirdPerson);
            return Map.copyOf(transforms);
        }
    }

    /**
     * Calibrates a legacy first-person transform into the modern 1.20.1 item-display pipeline.
     * This is not a perfect 1:1 converter; it gives a consistent family-specific mapping that
     * drastically reduces the amount of manual per-weapon tweaking.
     */
    private record FirstPersonCalibration(float rotationXOffset,
                                          float rotationYOffset,
                                          float rotationZOffset,
                                          float translationXMultiplier,
                                          float translationYMultiplier,
                                          float translationZMultiplier,
                                          float uniformScale) {

        private static FirstPersonCalibration identity() {
            return new FirstPersonCalibration(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        private TGDisplayTransform apply(TGDisplayTransform legacyTransform) {
            return new TGDisplayTransform(
                    legacyTransform.rotationX() + rotationXOffset,
                    legacyTransform.rotationY() + rotationYOffset,
                    legacyTransform.rotationZ() + rotationZOffset,
                    legacyTransform.translationX() * translationXMultiplier,
                    legacyTransform.translationY() * translationYMultiplier,
                    legacyTransform.translationZ() * translationZMultiplier,
                    uniformScale,
                    uniformScale,
                    uniformScale
            );
        }
    }
}
