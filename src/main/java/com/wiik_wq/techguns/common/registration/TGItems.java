package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGItemCatalog;
import com.wiik_wq.techguns.common.item.TGArmorItem;
import com.wiik_wq.techguns.common.item.TGGunItem;
import com.wiik_wq.techguns.common.item.TGShieldItem;
import com.wiik_wq.techguns.common.item.TGSpecialRendererItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class TGItems {

    public enum ItemStyle {
        GENERATED,
        HANDHELD,
        GUN,
        ARMOR,
        SHIELD
    }

    public record ItemEntry(String id, RegistryObject<Item> item, ItemStyle style) {
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TechgunsReborn.MODID);
    public static final Map<String, ItemEntry> ENTRIES = new LinkedHashMap<>();

    static {
        TGItemCatalog.SIMPLE_ITEMS.forEach(id -> register(id, ItemStyle.GENERATED,
                () -> TGItemCatalog.usesSpecialItemRenderer(id) ? new TGSpecialRendererItem(defaultProps()) : new Item(defaultProps())));
        TGItemCatalog.GUN_ITEMS.forEach(id -> register(id, ItemStyle.GUN, () -> new TGGunItem(singleStackProps())));
        TGItemCatalog.HANDHELD_ITEMS.forEach(id -> register(id, ItemStyle.HANDHELD, () -> new SwordItem(Tiers.IRON, 3, -2.4F, singleStackProps())));
        TGItemCatalog.SHIELD_ITEMS.forEach(id -> register(id, ItemStyle.SHIELD, () -> new TGShieldItem(singleStackProps().durability(512))));
        TGItemCatalog.ARMOR_ITEMS.forEach(TGItems::registerArmor);
    }

    private TGItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static Collection<ItemEntry> all() {
        return ENTRIES.values();
    }

    public static Stream<RegistryObject<Item>> displayItems() {
        return ENTRIES.values().stream().map(ItemEntry::item);
    }

    private static void registerArmor(String id) {
        ArmorItem.Type armorType = armorType(id);
        register(id, ItemStyle.ARMOR, () -> new TGArmorItem(ArmorMaterials.IRON, armorType, singleStackProps()));
    }

    private static void register(String id, ItemStyle style, java.util.function.Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(id, supplier);
        ENTRIES.put(id, new ItemEntry(id, item, style));
    }

    private static Item.Properties defaultProps() {
        return new Item.Properties();
    }

    private static Item.Properties singleStackProps() {
        return new Item.Properties().stacksTo(1);
    }

    private static ArmorItem.Type armorType(String id) {
        if (id.endsWith("_helmet") || id.endsWith("_beret")) {
            return ArmorItem.Type.HELMET;
        }
        if (id.endsWith("_chestplate")) {
            return ArmorItem.Type.CHESTPLATE;
        }
        if (id.endsWith("_leggings")) {
            return ArmorItem.Type.LEGGINGS;
        }
        return ArmorItem.Type.BOOTS;
    }
}
