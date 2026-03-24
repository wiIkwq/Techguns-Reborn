package com.wiik_wq.techguns.common.item;

import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TGShieldItem extends ShieldItem {

    public TGShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(com.wiik_wq.techguns.client.render.item.TGItemRenderExtensions.INSTANCE);
    }
}
