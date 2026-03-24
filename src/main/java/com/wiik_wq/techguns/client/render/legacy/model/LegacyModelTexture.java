package com.wiik_wq.techguns.client.render.legacy.model;

import net.minecraft.resources.ResourceLocation;

public final class LegacyModelTexture {

    private static final ThreadLocal<ResourceLocation> CURRENT = new ThreadLocal<>();

    private LegacyModelTexture() {
    }

    public static void set(ResourceLocation texture) {
        CURRENT.set(texture);
    }

    public static ResourceLocation current() {
        ResourceLocation texture = CURRENT.get();
        if (texture == null) {
            throw new IllegalStateException("Legacy model texture is not active");
        }
        return texture;
    }

    public static void clear() {
        CURRENT.remove();
    }
}
