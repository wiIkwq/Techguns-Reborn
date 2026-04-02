package com.wiik_wq.techguns.client.gui;

enum TGCrosshairStyle {
    VANILLA,
    GUN_DYNAMIC,
    TRI(TGCrosshairDynamicType.TRI),
    QUAD_CORNERS_DOT(TGCrosshairDynamicType.X),
    TRI_INV(TGCrosshairDynamicType.TRI_INV),
    FOUR_PARTS(TGCrosshairDynamicType.BOTH),
    FOUR_PARTS_SPIKED(TGCrosshairDynamicType.BOTH),
    HORIZONTAL_TWO_PART(TGCrosshairDynamicType.HORIZONTAL),
    HORIZONTAL_TWO_PART_LARGE(TGCrosshairDynamicType.HORIZONTAL),
    HORIZONTAL_TWO_PART_E(TGCrosshairDynamicType.HORIZONTAL),
    HORIZONTAL_SMALL(TGCrosshairDynamicType.HORIZONTAL),
    QUAD_NO_CORNERS(TGCrosshairDynamicType.BOTH);

    final TGCrosshairDynamicType dynamicType;

    TGCrosshairStyle() {
        this(TGCrosshairDynamicType.NONE);
    }

    TGCrosshairStyle(TGCrosshairDynamicType dynamicType) {
        this.dynamicType = dynamicType;
    }

    int textureU() {
        if (this == VANILLA) {
            return 0;
        }
        return ((ordinal() - 1) % 16) * 16;
    }

    int textureV() {
        if (this == VANILLA) {
            return 0;
        }
        return ((ordinal() - 1) / 16) * 16;
    }
}
