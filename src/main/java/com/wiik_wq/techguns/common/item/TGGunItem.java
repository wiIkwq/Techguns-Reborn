package com.wiik_wq.techguns.common.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TGGunItem extends Item {

    public TGGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(com.wiik_wq.techguns.client.render.item.TGItemRenderExtensions.INSTANCE);
    }
}
