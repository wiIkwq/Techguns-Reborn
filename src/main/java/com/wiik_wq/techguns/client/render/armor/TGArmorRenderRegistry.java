package com.wiik_wq.techguns.client.render.armor;

import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelArmorCoat;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelExoSuit;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelSteamArmor;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelT3PowerArmor;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelT4PowerArmorMk2;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public final class TGArmorRenderRegistry {

    private static final TextureDefinition T1_COMBAT = layered("t1_combat");
    private static final TextureDefinition T1_SCOUT = layered("t1_scout");
    private static final TextureDefinition T1_MINER = layered("t1_miner");
    private static final TextureDefinition STEAM = single("steam_armor");
    private static final TextureDefinition HAZMAT = layered("hazmatsuit");
    private static final TextureDefinition T2_COMBAT = layered("t2_combat");
    private static final TextureDefinition T2_COMMANDO = layered("t2_commando");
    private static final TextureDefinition T2_RIOT = layered("t2_riot");
    private static final TextureDefinition T3_COMBAT = layered("t3_combat");
    private static final TextureDefinition T3_POWER = single("powerarmor");
    private static final TextureDefinition T3_MINER = layered("hevsuit");
    private static final TextureDefinition T3_EXO = layered("t3_exo");
    private static final TextureDefinition BERET = single("beret_texture");
    private static final TextureDefinition T4_PRAETOR = layered("t4_praetor");
    private static final TextureDefinition T4_POWER = single("powerarmor_mk2_darkgrey");

    private static final SpecialArmorDefinition STEAM_SPECIAL = new SpecialArmorDefinition(
            new TGLegacyArmorModel(new ModelSteamArmor(0, 0.01F)),
            new TGLegacyArmorModel(new ModelSteamArmor(0)),
            new TGLegacyArmorModel(new ModelSteamArmor(1)),
            new TGLegacyArmorModel(new ModelSteamArmor(0))
    );
    private static final SpecialArmorDefinition T3_POWER_SPECIAL = new SpecialArmorDefinition(
            new TGLegacyArmorModel(new ModelT3PowerArmor(0, 0.01F)),
            new TGLegacyArmorModel(new ModelT3PowerArmor(0)),
            new TGLegacyArmorModel(new ModelT3PowerArmor(1)),
            new TGLegacyArmorModel(new ModelT3PowerArmor(0))
    );
    private static final SpecialArmorDefinition T4_POWER_SPECIAL = new SpecialArmorDefinition(
            new TGLegacyArmorModel(new ModelT4PowerArmorMk2(0, 0.01F)),
            new TGLegacyArmorModel(new ModelT4PowerArmorMk2(0)),
            new TGLegacyArmorModel(new ModelT4PowerArmorMk2(1)),
            new TGLegacyArmorModel(new ModelT4PowerArmorMk2(0))
    );
    private static final SpecialArmorDefinition T2_RIOT_SPECIAL = new SpecialArmorDefinition(
            new TGLegacyArmorModel(new ModelArmorCoat(0, 1.0F)),
            new TGLegacyArmorModel(new ModelArmorCoat(1, 0.51F)),
            new TGLegacyArmorModel(new ModelArmorCoat(2, 0.49F)),
            new TGLegacyArmorModel(new ModelArmorCoat(3, 0.75F))
    );
    private static final SpecialArmorDefinition T3_EXO_SPECIAL = new SpecialArmorDefinition(
            new TGLegacyArmorModel(new ModelExoSuit(0, 1.0F)),
            new TGLegacyArmorModel(new ModelExoSuit(2, 0.75F)),
            new TGLegacyArmorModel(new ModelExoSuit(1, 0.5F)),
            new TGLegacyArmorModel(new ModelExoSuit(2, 0.75F))
    );

    private TGArmorRenderRegistry() {
    }

    public static @Nullable Model getArmorModel(ItemStack stack, EquipmentSlot slot, HumanoidModel<?> originalModel) {
        String itemId = itemId(stack);
        if (itemId == null) {
            return null;
        }

        SpecialArmorDefinition definition = specialDefinition(itemId);
        return definition == null ? null : definition.model(slot, originalModel);
    }

    public static @Nullable String getArmorTexture(ItemStack stack, EquipmentSlot slot) {
        String itemId = itemId(stack);
        if (itemId == null) {
            return null;
        }

        TextureDefinition definition = textureDefinition(itemId);
        return definition == null ? null : definition.path(slot);
    }

    private static @Nullable String itemId(ItemStack stack) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key == null ? null : key.getPath();
    }

    private static @Nullable SpecialArmorDefinition specialDefinition(String itemId) {
        if (itemId.startsWith("steam_")) {
            return STEAM_SPECIAL;
        }
        if (itemId.startsWith("t3_power_")) {
            return T3_POWER_SPECIAL;
        }
        if (itemId.startsWith("t2_riot_")) {
            return T2_RIOT_SPECIAL;
        }
        if (itemId.startsWith("t3_exo_")) {
            return T3_EXO_SPECIAL;
        }
        if (itemId.startsWith("t4_power_")) {
            return T4_POWER_SPECIAL;
        }
        return null;
    }

    private static @Nullable TextureDefinition textureDefinition(String itemId) {
        if (itemId.startsWith("t1_combat_")) {
            return T1_COMBAT;
        }
        if (itemId.startsWith("t1_scout_")) {
            return T1_SCOUT;
        }
        if (itemId.startsWith("t1_miner_")) {
            return T1_MINER;
        }
        if (itemId.startsWith("steam_")) {
            return STEAM;
        }
        if (itemId.startsWith("hazmat_")) {
            return HAZMAT;
        }
        if (itemId.startsWith("t2_combat_")) {
            return T2_COMBAT;
        }
        if (itemId.startsWith("t2_commando_")) {
            return T2_COMMANDO;
        }
        if (itemId.startsWith("t2_riot_")) {
            return T2_RIOT;
        }
        if (itemId.startsWith("t3_combat_")) {
            return T3_COMBAT;
        }
        if (itemId.startsWith("t3_power_")) {
            return T3_POWER;
        }
        if (itemId.startsWith("t3_miner_")) {
            return T3_MINER;
        }
        if (itemId.startsWith("t3_exo_")) {
            return T3_EXO;
        }
        if ("t2_beret".equals(itemId)) {
            return BERET;
        }
        if (itemId.startsWith("t4_praetor_")) {
            return T4_PRAETOR;
        }
        if (itemId.startsWith("t4_power_")) {
            return T4_POWER;
        }
        return null;
    }

    private static TextureDefinition layered(String basePath) {
        return new TextureDefinition(basePath, true);
    }

    private static TextureDefinition single(String basePath) {
        return new TextureDefinition(basePath, false);
    }

    private record TextureDefinition(String basePath, boolean layered) {
        private String path(EquipmentSlot slot) {
            if (!layered) {
                return "techguns:textures/models/armor/" + basePath + ".png";
            }
            int layer = slot == EquipmentSlot.LEGS ? 2 : 1;
            return "techguns:textures/models/armor/" + basePath + "_layer_" + layer + ".png";
        }
    }

    private record SpecialArmorDefinition(TGLegacyArmorModel headModel, TGLegacyArmorModel chestModel,
                                          TGLegacyArmorModel legsModel, TGLegacyArmorModel feetModel) {
        private Model model(EquipmentSlot slot, HumanoidModel<?> originalModel) {
            return switch (slot) {
                case HEAD -> headModel.sync(originalModel, slot);
                case CHEST -> chestModel.sync(originalModel, slot);
                case LEGS -> legsModel.sync(originalModel, slot);
                case FEET -> feetModel.sync(originalModel, slot);
                default -> chestModel.sync(originalModel, slot);
            };
        }
    }
}
