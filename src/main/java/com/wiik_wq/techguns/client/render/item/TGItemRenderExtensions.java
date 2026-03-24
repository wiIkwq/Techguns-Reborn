package com.wiik_wq.techguns.client.render.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public enum TGItemRenderExtensions implements IClientItemExtensions {
    INSTANCE;

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return TGSpecialItemRenderer.get();
    }
}
