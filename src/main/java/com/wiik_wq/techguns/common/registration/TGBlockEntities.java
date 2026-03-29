package com.wiik_wq.techguns.common.registration;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.blockentity.TGMachineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TGBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TechgunsReborn.MODID);

    public static final RegistryObject<BlockEntityType<TGMachineBlockEntity>> MACHINE = BLOCK_ENTITIES.register(
            "machine",
            () -> BlockEntityType.Builder.of(
                    TGMachineBlockEntity::new,
                    TGBlocks.ENTRIES.get("ammo_press").block().get(),
                    TGBlocks.ENTRIES.get("chem_lab").block().get(),
                    TGBlocks.ENTRIES.get("turret_base").block().get()
            ).build(null)
    );

    private TGBlockEntities() {
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
