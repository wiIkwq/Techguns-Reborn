package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.menu.TGPlayerInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TGMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TechgunsReborn.MODID);

    public static final RegistryObject<MenuType<TGPlayerInventoryMenu>> PLAYER_INVENTORY = MENUS.register(
            "player_inventory",
            () -> IForgeMenuType.create((containerId, inventory, data) -> new TGPlayerInventoryMenu(containerId, inventory))
    );

    private TGMenus() {
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
