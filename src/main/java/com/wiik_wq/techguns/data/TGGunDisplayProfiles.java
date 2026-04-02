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

    private static final TGDisplayTransform STANDARD_THIRD_PERSON =
            new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.55F, 0.55F, 0.55F);
    private static final TGDisplayTransform STANDARD_GROUND =
            new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.5F, 0.5F, 0.5F);
    private static final TGDisplayTransform STANDARD_FIXED =
            new TGDisplayTransform(0.0F, 180.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    private static final TGDisplayTransform STANDARD_HEAD =
            new TGDisplayTransform(0.0F, 180.0F, 0.0F, 0.0F, 13.0F, 7.0F, 1.0F, 1.0F, 1.0F);

    private static final Map<String, ObjGunProfile> OBJ_GUN_PROFILES = Map.of(
            "gaussrifle", new ObjGunProfile(
                    FirstPersonCalibration.identity(),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.5F, 0.5F, 0.5F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 13.0F, 7.0F, 1.0F, 1.0F, 1.0F),
                    STANDARD_THIRD_PERSON
            ),
            "grenadelauncher", new ObjGunProfile(
                    new FirstPersonCalibration(-10.0F, 65.0F, 0.0F, 0.18F, 0.68F, 0.42F, 0.045F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.28F, 0.28F, 0.28F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.14F, 0.14F, 0.14F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.28F, 0.28F, 0.28F),
                    new TGDisplayTransform(0.0F, 90.0F, 0.0F, 0.0F, 13.0F, 7.0F, 0.28F, 0.28F, 0.28F),
                    new TGDisplayTransform(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.154F, 0.154F, 0.154F)
            )
    );

    private TGGunDisplayProfiles() {
    }

    static Map<ItemDisplayContext, TGDisplayTransform> standardTransforms(String id) {
        EnumMap<ItemDisplayContext, TGDisplayTransform> transforms = new EnumMap<>(ItemDisplayContext.class);
        transforms.put(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, legacyFirstPerson(id));
        transforms.put(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, STANDARD_THIRD_PERSON);
        transforms.put(ItemDisplayContext.GROUND, STANDARD_GROUND);
        transforms.put(ItemDisplayContext.FIXED, STANDARD_FIXED);
        transforms.put(ItemDisplayContext.HEAD, STANDARD_HEAD);
        return Map.copyOf(transforms);
    }

    static Map<ItemDisplayContext, TGDisplayTransform> objTransforms(String id) {
        ObjGunProfile profile = OBJ_GUN_PROFILES.get(id);
        if (profile == null) {
            return Map.of();
        }
        return profile.transformsFor(legacyFirstPerson(id));
    }

    private static TGDisplayTransform legacyFirstPerson(String id) {
        return LEGACY_FIRST_PERSON_OVERRIDES.getOrDefault(id, LEGACY_FIRST_PERSON_BASE);
    }

    private record ObjGunProfile(FirstPersonCalibration firstPersonCalibration,
                                 TGDisplayTransform gui,
                                 TGDisplayTransform ground,
                                 TGDisplayTransform fixed,
                                 TGDisplayTransform head,
                                 TGDisplayTransform thirdPerson) {

        private Map<ItemDisplayContext, TGDisplayTransform> transformsFor(TGDisplayTransform legacyFirstPerson) {
            EnumMap<ItemDisplayContext, TGDisplayTransform> transforms = new EnumMap<>(ItemDisplayContext.class);
            transforms.put(ItemDisplayContext.GUI, gui);
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
