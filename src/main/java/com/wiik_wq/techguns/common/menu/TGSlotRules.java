package com.wiik_wq.techguns.common.menu;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.player.TGSlotType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

final class TGSlotRules {

    private static final Set<String> BACK_IDS = Set.of("glider", "jumppack", "scubatanks", "jetpack", "antigravpack");
    private static final Set<String> FACE_IDS = Set.of("gasmask", "oxygenmask", "nightvisiongoggles", "tacticalmask");
    private static final Set<String> HAND_IDS = Set.of("workinggloves");
    private static final Set<String> HEAL_IDS = Set.of("infusionbag", "radaway", "radpills");
    private static final Set<String> EXACT_AMMO_IDS = Set.of(
            "stonebullets",
            "pistolrounds",
            "shotgunrounds",
            "riflerounds",
            "sniperrounds",
            "40mmgrenade",
            "advancedrounds",
            "rocket",
            "rocket_nuke",
            "rocket_high_velocity",
            "gaussrifleslugs"
    );

    private TGSlotRules() {
    }

    static boolean mayPlace(TGSlotType type, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return switch (type) {
            case FOOD -> stack.getItem().isEdible();
            case AMMO -> isAmmo(stack);
            case BACK -> matches(stack, BACK_IDS);
            case FACE -> matches(stack, FACE_IDS);
            case HAND -> matches(stack, HAND_IDS);
            case HEAL -> matches(stack, HEAL_IDS) || stack.getItem().isEdible();
        };
    }

    private static boolean isAmmo(ItemStack stack) {
        ResourceLocation id = itemId(stack);
        if (id == null || !TechgunsReborn.MODID.equals(id.getNamespace())) {
            return false;
        }

        String path = id.getPath();
        return EXACT_AMMO_IDS.contains(path)
                || path.contains("round")
                || path.contains("magazine")
                || path.contains("drum")
                || path.contains("tank")
                || path.contains("cell");
    }

    private static boolean matches(ItemStack stack, Set<String> ids) {
        ResourceLocation id = itemId(stack);
        return id != null && TechgunsReborn.MODID.equals(id.getNamespace()) && ids.contains(id.getPath());
    }

    private static ResourceLocation itemId(ItemStack stack) {
        return net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(stack.getItem());
    }
}
