package com.wiik_wq.techguns.client;

import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public final class TGRenderLayers {

    private TGRenderLayers() {
    }

    public static void register() {
        TGBlockCatalog.CUTOUT_BLOCKS.stream()
                .map(TGBlocks.ENTRIES::get)
                .filter(entry -> entry != null)
                .forEach(entry -> ItemBlockRenderTypes.setRenderLayer(entry.block().get(), RenderType.cutout()));
    }
}
