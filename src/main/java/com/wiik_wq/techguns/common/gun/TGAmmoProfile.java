package com.wiik_wq.techguns.common.gun;

import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public record TGAmmoProfile(String id, String emptyContainerId, List<Variant> variants) {

    public TGAmmoProfile {
        variants = List.copyOf(variants);
    }

    public Optional<Variant> variantFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }

        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null || !TechgunsReborn.MODID.equals(key.getNamespace())) {
            return Optional.empty();
        }

        String path = key.getPath();
        return variants.stream().filter(variant -> variant.reloadItemId().equals(path)).findFirst();
    }

    public Variant defaultVariant() {
        return variants.get(0);
    }

    public Item emptyContainerItem() {
        return item(emptyContainerId);
    }

    public static Item item(String id) {
        if (id == null || id.isBlank()) {
            return Items.AIR;
        }
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, id));
        return item == null ? Items.AIR : item;
    }

    public record Variant(String id, String reloadItemId) {
    }
}
