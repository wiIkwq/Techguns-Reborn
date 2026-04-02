package com.wiik_wq.techguns.client;

import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import com.wiik_wq.techguns.common.registration.TGFluids;
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

        TGFluids.allBlocks().forEach(entry -> {
            ItemBlockRenderTypes.setRenderLayer(entry.source().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(entry.flowing().get(), RenderType.translucent());
        });
    }
}
