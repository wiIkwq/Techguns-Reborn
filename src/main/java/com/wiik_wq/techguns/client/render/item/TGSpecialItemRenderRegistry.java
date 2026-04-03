package com.wiik_wq.techguns.client.render.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.wiik_wq.techguns.client.render.legacy.LegacyRenderContext;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyBipedModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyModelTexture;
import com.wiik_wq.techguns.client.render.legacy.model.LegacyMultipartModel;
import com.wiik_wq.techguns.client.render.legacy.model.LegacySimpleModel;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelSteamArmor;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelExoSuit;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelT3PowerArmor;
import com.wiik_wq.techguns.client.render.legacy.model.armor.ModelT4PowerArmorMk2;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelAK;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelAS50;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelAUG;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelAlienBlaster;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelBiogun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelBlasterRifle;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelBoltaction;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelChainsaw;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelCombatShotgun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelFlamethrower;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelFragGrenade;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelGoldenRevolver;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelGrimReaper;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelGuidedMissileLauncher;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelHandgun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelLMG;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelLaserPistol;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelLasergun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelLasergun2;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelM4;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelM4Infiltrator;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelMac10;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelMibGun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelMinigun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelMiningDrill;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelNDR;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelNetherBlaster;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelPDW;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelPistol;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelPowerHammer;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelPulseRifle;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelRevolver;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelRocketLauncher;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelSawedOff;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelScar;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelShishkebap;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelSonicShotgun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelStielgranate;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelTFG;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelTeslaGun;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelThompson;
import com.wiik_wq.techguns.client.render.legacy.model.gun.ModelVector;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelARMagazine;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelAS50Mag;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelAdvancedShield;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelBallisticShield;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelLmgMag;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelRiotShield;
import com.wiik_wq.techguns.client.render.legacy.model.item.ModelRocket;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelAmmoPress;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelChemLab;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelMetalPress;
import com.wiik_wq.techguns.client.render.legacy.model.machine.ModelTurretBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.HashMap;
import java.util.Map;

public final class TGSpecialItemRenderRegistry {

    private static final float LEGACY_SCALE = 0.0625F;
    private static final float[][] DEFAULT_ITEM_TRANSLATIONS = translations(
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, -0.05F)
    );
    private static final float[][] DEFAULT_GUN_TRANSLATIONS = translations(
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, -0.05F)
    );
    private static final float GUN_GUI_SCALE_MULTIPLIER = 0.9F;
    private static final float GUN_GUI_X_OFFSET = 0.5F;
    private static final float GUN_GUI_Y_OFFSET = 0.5F;
    private static final float[][] DEFAULT_ARMOR_TRANSLATIONS = translations(
            vec(0.0F, 0.1F, 0.0F),
            vec(0.0F, 0.04F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.07F)
    );
    private static final float[][] M4_MAG_TRANSLATIONS = translations(
            vec(0.0F, 0.25F, 0.0F),
            vec(0.0F, 0.25F, 0.05F),
            vec(0.14F, 0.33F, 0.0F),
            vec(0.0F, -0.1F, 0.0F),
            vec(0.0F, 0.0F, -0.05F)
    );
    private static final float[][] AS50_MAG_TRANSLATIONS = translations(
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.08F, 0.13F, 0.0F),
            vec(0.0F, -0.1F, 0.0F),
            vec(0.0F, 0.0F, 0.0F)
    );
    private static final float[][] LMG_MAG_TRANSLATIONS = translations(
            vec(0.0F, 0.35F, 0.0F),
            vec(0.0F, 0.15F, 0.0F),
            vec(0.14F, 0.33F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.25F, -0.05F)
    );
    private static final float[][] ROCKET_TRANSLATIONS = translations(
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, -0.1F, 0.02F),
            vec(0.08F, 0.1F, 0.0F),
            vec(0.0F, 0.0F, 0.0F),
            vec(0.0F, 0.0F, 0.0F)
    );
    private static final Map<String, ItemDefinition> DEFINITIONS = new HashMap<>();

    static {
        registerGun("m4", new ModelM4(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("m4texture"),
                translations(vec(0.0F, 0.0F, -0.05F), vec(0.0F, 0.01F, -0.1F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("ak47", new ModelAK(), 1, rotation(RotationMode.GUN), 0.75F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("ak47texture"),
                translations(vec(0.0F, 0.06F, -0.02F), vec(0.0F, 0.0F, -0.08F), vec(0.06F, -0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("lmg", new ModelLMG(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("mg2_texture"),
                translations(vec(0.0F, 0.02F, -0.09F), vec(0.0F, 0.0F, -0.06F), vec(0.05F, -0.03F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("handcannon", new ModelHandgun(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.1F), gunTexture("handgun"),
                translations(vec(0.0F, 0.03F, -0.12F), vec(0.0F, -0.05F, -0.09F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("sawedoff", new ModelSawedOff(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.1F), gunTexture("sawedoff"),
                translations(vec(0.0F, 0.0F, 0.0F), vec(0.0F, -0.04F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("thompson", new ModelThompson(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(0.5F) - 0.1F), gunTexture("thompson"),
                DEFAULT_GUN_TRANSLATIONS);
        registerGun("boltaction", new ModelBoltaction(), 1, rotation(RotationMode.GUN_90), 1.35F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.1F), gunTexture("boltactionrifle"),
                translations(vec(0.0F, -0.02F, -0.09F), vec(0.0F, -0.04F, -0.11F), vec(0.1F, -0.08F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.025F)));
        registerGun("biogun", new ModelBiogun(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.35F, -0.2F, scale(1.0F) - 0.1F), gunTexture("biogun"),
                translations(vec(0.0F, 0.16F, 0.05F), vec(0.0F, 0.07F, -0.05F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("flamethrower", new ModelFlamethrower(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.1F), gunTexture("flamethrower"),
                translations(vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.08F), vec(0.05F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("pistol", new ModelPistol(), 2, rotation(RotationMode.GUN), 1.2F, 0.5F, 0.35F, 0.9F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.3F, -0.4F), gunTextures("pistol3", "pistol3"),
                translations(vec(0.0F, 0.09F, -0.02F), vec(0.0F, -0.03F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("rocketlauncher", new ModelRocketLauncher(), 2, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(-0.4F, -0.2F, -scale(0.5F)), gunTextures("rocketlauncher", "rocket"),
                translations(vec(-0.13F, 0.3F, 0.32F), vec(0.0F, 0.02F, 0.07F), vec(-0.06F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("minigun", new ModelMinigun(), 2, rotation(RotationMode.GUN_90), 1.2F, 0.5F, 0.35F, 0.3F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(0.5F)), gunTextures("minigun", "minigun"),
                translations(vec(0.0F, -0.16F, 0.1F), vec(0.0F, -0.53F, 0.2F), vec(0.12F, -0.02F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("combatshotgun", new ModelCombatShotgun(), 2, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.1F), gunTextures("combatshotgun", "combatshotgun"),
                translations(vec(0.0F, 0.03F, 0.0F), vec(0.0F, -0.01F, -0.10F), vec(0.05F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("revolver", new ModelRevolver(), 1, rotation(RotationMode.GUN_90), 0.75F, 0.5F, 0.35F, 0.75F, 0.35F, 0.5F,
                vec(-0.35F, -0.2F, scale(0.5F) - 0.1F), gunTexture("revolver"),
                translations(vec(0.0F, 0.14F, 0.01F), vec(0.0F, 0.0F, 0.0F), vec(0.05F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("grimreaper", new ModelGrimReaper(), 1, rotation(RotationMode.GUN_90), 1.25F, 0.4F, 0.35F, 0.3F, 0.35F, 0.35F,
                vec(0.3F, -0.2F, scale(0.5F)), gunTexture("grimreaper"),
                translations(vec(0.0F, 0.25F, 0.18F), vec(0.0F, 0.13F, 0.1F), vec(-0.02F, 0.01F, 0.0F), vec(0.0F, 0.1F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("pdw", new ModelPDW(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.55F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.5F) - 0.1F), gunTexture("pdw"),
                translations(vec(0.0F, 0.09F, -0.04F), vec(0.0F, 0.06F, -0.02F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("as50", new ModelAS50(), 1, rotation(RotationMode.GUN), 0.85F, 0.5F, 0.35F, 0.3F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("as50texture"),
                translations(vec(0.0F, 0.06F, -0.1F), vec(0.0F, 0.0F, -0.05F), vec(0.13F, -0.09F, -0.05F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, -0.2F, -0.05F)));
        registerGun("m4_infiltrator", new ModelM4Infiltrator(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("m4_uq_texture"),
                translations(vec(0.0F, 0.0F, -0.05F), vec(0.0F, 0.01F, -0.1F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("goldenrevolver", new ModelGoldenRevolver(), 1, rotation(RotationMode.GUN_90), 0.75F, 0.5F, 0.35F, 0.75F, 0.35F, 0.5F,
                vec(-0.35F, -0.2F, scale(0.5F) - 0.1F), gunTexture("goldenrevolver"),
                translations(vec(0.0F, 0.14F, 0.01F), vec(0.0F, 0.0F, 0.0F), vec(0.05F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("pulserifle", new ModelPulseRifle(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.5F) - 0.09F), gunTexture("pulserifle"),
                translations(vec(0.0F, 0.16F, 0.01F), vec(0.0F, 0.05F, 0.08F), vec(0.05F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("teslagun", new ModelTeslaGun(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.25F, -0.2F, scale(1.0F) - 0.09F), gunTexture("teslagun"),
                translations(vec(0.0F, 0.15F, 0.04F), vec(0.0F, 0.05F, -0.08F), vec(0.03F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("netherblaster", new ModelNetherBlaster(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.6F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(1.0F) - 0.09F), gunTexture("cyberdemonblaster"),
                translations(vec(0.0F, 0.15F, 0.04F), vec(0.0F, -0.16F, -0.24F), vec(-0.1F, 0.01F, 0.0F), vec(0.0F, 0.0F, -0.11F), vec(0.16F, -0.07F, -0.16F)));
        registerGun("lasergun", new ModelLasergun(), 1, rotation(RotationMode.GUN_90), 1.1F, 0.5F, 0.35F, 0.4F, 0.35F, 0.5F,
                vec(0.25F, -0.2F, scale(0.5F) - 0.1F), gunTexture("lasergun"),
                translations(vec(0.0F, 0.15F, 0.04F), vec(0.0F, 0.02F, 0.01F), vec(0.13F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.15F), vec(-0.18F, 0.0F, -0.05F)));
        registerGun("alienblaster", new ModelAlienBlaster(), 1, rotation(RotationMode.GUN), 0.75F, 0.5F, 0.35F, 0.75F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.3F, -0.2F), gunTexture("alien_blaster"),
                translations(vec(0.0F, 0.06F, -0.02F), vec(0.0F, -0.07F, -0.01F), vec(0.02F, -0.08F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(-0.04F, -0.09F, 0.0F)));
        registerGun("blasterrifle", new ModelBlasterRifle(), 1, rotation(RotationMode.GUN), 0.9F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.0F), gunTexture("blasterrifle"),
                translations(vec(0.0F, 0.11F, -0.2F), vec(0.0F, 0.0F, -0.04F), vec(0.0F, 0.0F, 0.03F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("powerhammer", new ModelPowerHammer(), 2, rotation(RotationMode.GUN_90), 1.25F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.15F, -0.2F, scale(1.0F) - 0.09F), gunTextures("powerhammer", "powerhammer"),
                translations(vec(0.0F, 0.18F, 0.09F), vec(0.0F, 0.04F, 0.04F), vec(0.03F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(-0.07F, -0.03F, -0.05F)));
        registerGun("aug", new ModelAUG(), 2, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.1F), gunTextures("augtexture", "augtexture"),
                translations(vec(0.0F, 0.01F, -0.15F), vec(0.0F, 0.0F, -0.01F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.02F, 0.0F, -0.05F)));
        registerGun("sonicshotgun", new ModelSonicShotgun(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, scale(0.5F) - 0.1F), gunTexture("sonicshotgun"),
                translations(vec(0.0F, 0.1F, 0.04F), vec(0.0F, -0.01F, -0.03F), vec(0.08F, 0.0F, 0.0F), vec(0.0F, 0.05F, 0.15F), vec(-0.18F, 0.0F, -0.05F)));
        registerGun("chainsaw", new ModelChainsaw(), 2, rotation(RotationMode.GUN_90), 0.95F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(-0.4F, -0.2F, scale(1.0F) - 0.09F), gunTextures("chainsaw", "chainsaw"),
                translations(vec(0.0F, -0.08F, 0.15F), vec(0.0F, -0.5F, 0.04F), vec(0.03F, 0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(-0.07F, -0.03F, -0.11F)));
        registerGun("scatterbeamrifle", new ModelLasergun2(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.1F), gunTexture("lasergunnew"),
                translations(vec(0.0F, 0.04F, -0.05F), vec(0.0F, 0.01F, -0.1F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGun("nucleardeathray", new ModelNDR(), 1, rotation(RotationMode.GUN_90), 1.2F, 0.5F, 0.35F, 0.4F, 0.35F, 0.5F,
                vec(1.0F, -0.2F, scale(1.5F) - 0.09F), gunTexture("ndr"),
                translations(vec(0.0F, 0.02F, 0.09F), vec(-0.01F, 0.04F, 0.3F), vec(0.11F, -0.08F, 0.0F), vec(0.0F, 0.0F, 0.15F), vec(-0.23F, -0.08F, -0.05F)));
        registerGun("scar", new ModelScar(), 2, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.1F, 0.1F), gunTextures("scar_texture", "scar_texture"),
                translations(vec(0.0F, 0.04F, -0.15F), vec(0.0F, 0.02F, -0.11F), vec(0.05F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.02F, -0.09F, -0.05F)));
        registerGun("vector", new ModelVector(), 1, rotation(RotationMode.GUN), 1.1F, 0.5F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.57F, -0.2F), gunTexture("vector_texture"),
                translations(vec(0.0F, -0.17F, 0.0F), vec(0.0F, -0.2F, -0.04F), vec(-0.08F, -0.09F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.05F, -0.17F, -0.05F)));
        registerGun("mac10", new ModelMac10(), 1, rotation(RotationMode.GUN), 1.2F, 0.5F, 0.35F, 0.55F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.45F, -0.3F), gunTexture("mac10texture"),
                translations(vec(0.0F, 0.0F, -0.05F), vec(0.0F, -0.1F, 0.01F), vec(-0.02F, -0.02F, 0.0F), vec(0.0F, 0.03F, 0.0F), vec(0.0F, -0.05F, -0.05F)));
        registerGun("mibgun", new ModelMibGun(), 1, rotation(RotationMode.GUN), 1.2F, 0.5F, 0.35F, 0.75F, 0.35F, 0.5F,
                vec(0.0F, -0.56F, -0.02F), gunTexture("mibgun"),
                translations(vec(0.0F, 0.1F, -0.02F), vec(0.0F, -0.04F, -0.01F), vec(0.02F, -0.08F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(-0.04F, -0.09F, 0.0F)));
        registerModelBackedGun("gaussrifle", rotation(0.0F, -90.0F, 0.0F), 0.9F, 0.5F, 0.35F, 0.25F, 0.35F, 0.5F,
                vec(0.6F, 0.0F, scale(0.5F) - 0.09F),
                translations(vec(0.0F, 0.12F, -0.1F), vec(0.0F, 0.05F, -0.17F), vec(0.0F, 0.06F, 0.06F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerModelBackedGun("grenadelauncher", rotation(0.0F, 90.0F, 0.0F), 0.00625F, 1.0F, 0.35F, 0.45F, 0.35F, 0.5F,
                vec(0.0F, 0.0F, 0.0F),
                translations(vec(0.0F, 0.19F, -0.09F), vec(0.0F, 0.06F, -0.2F), vec(-0.05F, 0.08F, 0.0F), vec(0.0F, 0.05F, -0.09F), vec(0.11F, 0.01F, -0.05F)));
        registerGun("guidedmissilelauncher", new ModelGuidedMissileLauncher(), 1, rotation(RotationMode.GUN_90), 1.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(-0.4F, -0.2F, scale(0.5F)), gunTexture("guidedmissilelauncher"),
                translations(vec(-0.13F, 0.3F, 0.62F), vec(0.0F, 0.09F, 0.28F), vec(0.0F, 0.03F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.04F)));
        registerGun("miningdrill", new ModelMiningDrill(), 2, rotation(RotationMode.GUN_90), 2.0F, 0.5F, 0.35F, 0.35F, 0.35F, 0.5F,
                vec(0.0F, -0.2F, -scale(0.5F)), gunTextures("miningdrill_obsidian", "miningdrill_obsidian"),
                translations(vec(0.0F, -0.03F, 0.0F), vec(0.0F, -0.57F, 0.08F), vec(0.01F, -0.01F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, -0.08F, -0.05F)));
        registerGun("tfg", new ModelTFG(), 1, rotation(RotationMode.GUN_90), 1.2F, 0.45F, 0.35F, 0.3F, 0.35F, 0.5F,
                vec(-0.46F, -0.38F, scale(1.0F) - 0.125F), gunTexture("tfg"),
                translations(vec(0.0F, -0.03F, 0.16F), vec(0.0F, -0.09F, -0.26F), vec(0.04F, -0.04F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(-0.07F, 0.0F, -0.05F)));
        registerGun("laserpistol", new ModelLaserPistol(), 1, rotation(RotationMode.GUN), 1.2F, 0.5F, 0.35F, 0.7F, 0.35F, 0.5F,
                vec(scale(0.5F), -0.3F, -0.4F), gunTexture("laser_pistol"),
                translations(vec(0.0F, 0.09F, -0.02F), vec(0.0F, -0.03F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(0.02F, -0.08F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGun("shishkebap", new ModelShishkebap(), 1, rotation(RotationMode.GUN), 1.0F, 0.5F, 0.35F, 0.3F, 0.35F, 0.5F,
                vec(0.0F, 0.0F, 0.0F), gunTexture("shishkebab_texture"),
                translations(vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, -0.25F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, 0.0F)));
        registerGrenade("stielgranate", new ModelStielgranate(), 1, rotation(RotationMode.GRENADE_180), 1.0F, 0.5F, 0.35F, 0.8F, 0.5F, 0.5F,
                vec(-scale(0.5F), 0.48F, -scale(1.0F)), gunTexture("stielgranate"),
                translations(vec(0.0F, -0.06F, 0.0F), vec(0.0F, -0.15F, 0.06F), vec(-0.05F, -0.49F, 0.0F), vec(0.0F, -0.15F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerGrenade("fraggrenade", new ModelFragGrenade(true), 1, rotation(RotationMode.GRENADE_90), 1.25F, 0.5F, 0.35F, 1.35F, 0.5F, 0.5F,
                vec(-0.02F, 0.65F, -scale(1.0F) + 0.02F), gunTexture("frag_grenade_texture"),
                translations(vec(0.0F, -0.06F, 0.0F), vec(0.0F, -0.11F, -0.01F), vec(-0.05F, -0.49F, 0.0F), vec(0.0F, 0.0F, 0.0F), vec(0.0F, 0.0F, -0.05F)));
        registerMultipartItem("assaultriflemagazine", new ModelARMagazine(false), 1, rotation(-180.0F, 180.0F, 0.0F), 1.25F, 0.5F, 0.35F, 0.85F, 0.5F, 0.5F,
                vec(0.0F, -0.2F, 0.0F), gunTexture("ar_mag"), M4_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("assaultriflemagazineempty", new ModelARMagazine(true), 1, rotation(-180.0F, 180.0F, 0.0F), 1.25F, 0.5F, 0.35F, 0.85F, 0.5F, 0.5F,
                vec(0.0F, -0.2F, 0.0F), gunTexture("ar_mag"), M4_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("assaultriflemagazine_incendiary", new ModelARMagazine(false), 1, rotation(-180.0F, 180.0F, 0.0F), 1.25F, 0.5F, 0.35F, 0.85F, 0.5F, 0.5F,
                vec(0.0F, -0.2F, 0.0F), gunTexture("ar_mag_inc"), M4_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("lmgmagazine", new ModelLmgMag(false), 1, lmgMagazineRotation(), 1.25F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.2F), gunTexture("lmg_mag"), LMG_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("lmgmagazineempty", new ModelLmgMag(true), 1, lmgMagazineRotation(), 1.25F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.2F), gunTexture("lmg_mag"), LMG_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("lmgmagazine_incendiary", new ModelLmgMag(false), 1, lmgMagazineRotation(), 1.25F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.2F), gunTexture("lmg_mag_inc"), LMG_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("as50magazine", new ModelAS50Mag(false), 1, rotation(-180.0F, 180.0F, 0.0F), 1.5F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0325F, -0.2F, 0.33F), gunTexture("as50_mag"), AS50_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("as50magazineempty", new ModelAS50Mag(true), 1, rotation(-180.0F, 180.0F, 0.0F), 1.5F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0325F, -0.2F, 0.33F), gunTexture("as50_mag"), AS50_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("as50magazine_incendiary", new ModelAS50Mag(false), 1, rotation(-180.0F, 180.0F, 0.0F), 1.5F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0325F, -0.2F, 0.33F), gunTexture("as50_mag_inc"), AS50_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("as50magazine_explosive", new ModelAS50Mag(false), 1, rotation(-180.0F, 180.0F, 0.0F), 1.5F, 0.5F, 0.35F, 0.75F, 0.5F, 0.5F,
                vec(0.0325F, -0.2F, 0.33F), gunTexture("as50_mag_exp"), AS50_MAG_TRANSLATIONS, true, true);
        registerMultipartItem("rocket", new ModelRocket(), 1, rotation(-180.0F, -90.0F, -90.0F), 1.5F, 0.35F, 0.35F, 0.5F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.1F), gunTexture("rocket"), ROCKET_TRANSLATIONS, true, true);
        registerMultipartItem("rocket_nuke", new ModelRocket(), 1, rotation(-180.0F, -90.0F, -90.0F), 1.5F, 0.35F, 0.35F, 0.5F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.1F), gunTexture("rocket_nuke"), ROCKET_TRANSLATIONS, true, true);
        registerMultipartItem("rocket_high_velocity", new ModelRocket(), 1, rotation(-180.0F, -90.0F, -90.0F), 1.5F, 0.35F, 0.35F, 0.5F, 0.5F, 0.5F,
                vec(0.0F, 0.0F, 0.1F), gunTexture("rocket_hv"), ROCKET_TRANSLATIONS, true, true);
        registerShield("riot_shield", new ModelRiotShield(), armorItemTexture("riot_shield"));
        registerShield("ballistic_shield", new ModelBallisticShield(), armorItemTexture("ballistic_shield"));
        registerShield("advanced_shield", new ModelAdvancedShield(), armorItemTexture("advanced_shield_silver"));
        registerMachineBlockItem("ammo_press", new ModelAmmoPress(), blockTexture("ammopress"));
        registerMachineBlockItem("metal_press", new ModelMetalPress(), blockTexture("metalpress"));
        registerMachineBlockItem("chem_lab", new ModelChemLab(), blockTexture("chemlab"));
        registerMachineBlockItem("turret_base", new ModelTurretBase(), blockTexture("turret_base"));

        registerArmor("steam_helmet", armor(new ModelSteamArmor(0), ArmorItem.Type.HELMET), armorTexture("steam_armor"), ArmorItem.Type.HELMET);
        registerArmor("steam_chestplate", armor(new ModelSteamArmor(0), ArmorItem.Type.CHESTPLATE), armorTexture("steam_armor"), ArmorItem.Type.CHESTPLATE);
        registerArmor("steam_leggings", armor(new ModelSteamArmor(1), ArmorItem.Type.LEGGINGS), armorTexture("steam_armor"), ArmorItem.Type.LEGGINGS);
        registerArmor("steam_boots", armor(new ModelSteamArmor(0), ArmorItem.Type.BOOTS), armorTexture("steam_armor"), ArmorItem.Type.BOOTS);
        registerArmor("t3_power_helmet", armor(new ModelT3PowerArmor(0), ArmorItem.Type.HELMET), armorTexture("powerarmor"), ArmorItem.Type.HELMET);
        registerArmor("t3_power_chestplate", armor(new ModelT3PowerArmor(0), ArmorItem.Type.CHESTPLATE), armorTexture("powerarmor"), ArmorItem.Type.CHESTPLATE);
        registerArmor("t3_power_leggings", armor(new ModelT3PowerArmor(1), ArmorItem.Type.LEGGINGS), armorTexture("powerarmor"), ArmorItem.Type.LEGGINGS);
        registerArmor("t3_power_boots", armor(new ModelT3PowerArmor(0), ArmorItem.Type.BOOTS), armorTexture("powerarmor"), ArmorItem.Type.BOOTS);
        registerArmor("t3_exo_helmet", armor(new ModelExoSuit(0, 1.0F), ArmorItem.Type.HELMET), armorTexture("t3_exo_layer_1"), ArmorItem.Type.HELMET);
        registerArmor("t3_exo_chestplate", armor(new ModelExoSuit(2, 0.75F), ArmorItem.Type.CHESTPLATE), armorTexture("t3_exo_layer_1"), ArmorItem.Type.CHESTPLATE);
        registerArmor("t3_exo_leggings", armor(new ModelExoSuit(1, 0.5F), ArmorItem.Type.LEGGINGS), armorTexture("t3_exo_layer_2"), ArmorItem.Type.LEGGINGS);
        registerArmor("t3_exo_boots", armor(new ModelExoSuit(2, 0.75F), ArmorItem.Type.BOOTS), armorTexture("t3_exo_layer_1"), ArmorItem.Type.BOOTS);
        registerArmor("t4_power_helmet", armor(new ModelT4PowerArmorMk2(0), ArmorItem.Type.HELMET), armorTexture("powerarmor_mk2_darkgrey"), ArmorItem.Type.HELMET);
        registerArmor("t4_power_chestplate", armor(new ModelT4PowerArmorMk2(0), ArmorItem.Type.CHESTPLATE), armorTexture("powerarmor_mk2_darkgrey"), ArmorItem.Type.CHESTPLATE);
        registerArmor("t4_power_leggings", armor(new ModelT4PowerArmorMk2(1), ArmorItem.Type.LEGGINGS), armorTexture("powerarmor_mk2_darkgrey"), ArmorItem.Type.LEGGINGS);
        registerArmor("t4_power_boots", armor(new ModelT4PowerArmorMk2(0), ArmorItem.Type.BOOTS), armorTexture("powerarmor_mk2_darkgrey"), ArmorItem.Type.BOOTS);
    }

    private TGSpecialItemRenderRegistry() {
    }

    public static ItemDefinition definition(ItemStack stack) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null) {
            return null;
        }
        return DEFINITIONS.get(key.getPath());
    }

    private static void registerGun(String id, LegacyMultipartModel model, int parts, ItemRotation rotation, float baseScale, float firstScale,
                                    float thirdScale, float guiScale, float groundScale, float fixedScale, float[] baseTranslation,
                                    ResourceLocation[] textures, float[][] translations) {
        registerMultipartItem(
                id,
                model,
                parts,
                rotation,
                baseScale,
                firstScale,
                thirdScale,
                guiScale * GUN_GUI_SCALE_MULTIPLIER,
                groundScale,
                fixedScale,
                baseTranslation,
                textures,
                withGuiOffset(translations, GUN_GUI_X_OFFSET, GUN_GUI_Y_OFFSET, 0.0F),
                true
        );
    }

    private static void registerGrenade(String id, LegacyMultipartModel model, int parts, ItemRotation rotation, float baseScale, float firstScale,
                                        float thirdScale, float guiScale, float groundScale, float fixedScale, float[] baseTranslation,
                                        ResourceLocation[] textures, float[][] translations) {
        registerGun(id, model, parts, rotation, baseScale, firstScale, thirdScale, guiScale, groundScale, fixedScale, baseTranslation, textures, translations);
    }

    private static void registerModelBackedGun(String id, ItemRotation rotation, float baseScale, float firstScale,
                                               float thirdScale, float guiScale, float groundScale, float fixedScale,
                                               float[] baseTranslation, float[][] translations) {
        DEFINITIONS.put(id, new ModelBackedItemDefinition(
                modelLocation(id),
                rotation,
                baseScale,
                firstScale,
                thirdScale,
                guiScale * GUN_GUI_SCALE_MULTIPLIER,
                groundScale,
                fixedScale,
                baseTranslation,
                withGuiOffset(translations, GUN_GUI_X_OFFSET, GUN_GUI_Y_OFFSET, 0.0F),
                true
        ));
    }

    private static void registerMultipartItem(String id, LegacyMultipartModel model, int parts, ItemRotation rotation, float baseScale,
                                              float firstScale, float thirdScale, float guiScale, float groundScale, float fixedScale,
                                              float[] baseTranslation, ResourceLocation[] textures, float[][] translations,
                                              boolean applyBuiltinRotations) {
        registerMultipartItem(id, model, parts, rotation, baseScale, firstScale, thirdScale, guiScale, groundScale, fixedScale,
                baseTranslation, textures, translations, applyBuiltinRotations, false);
    }

    private static void registerMultipartItem(String id, LegacyMultipartModel model, int parts, ItemRotation rotation, float baseScale,
                                              float firstScale, float thirdScale, float guiScale, float groundScale, float fixedScale,
                                              float[] baseTranslation, ResourceLocation[] textures, float[][] translations,
                                              boolean applyBuiltinRotations, boolean compensateGuiOrigin) {
        DEFINITIONS.put(id, new MultipartItemDefinition(model, parts, rotation, baseScale, firstScale, thirdScale, guiScale, groundScale,
                fixedScale, baseTranslation, textures, translations, applyBuiltinRotations, compensateGuiOrigin));
    }

    private static void registerSimpleItem(String id, LegacySimpleModel model, ItemRotation rotation, float baseScale,
                                           float firstScale, float thirdScale, float guiScale, float groundScale, float fixedScale,
                                           float[] baseTranslation, ResourceLocation texture, float[][] translations,
                                           boolean applyBuiltinRotations) {
        DEFINITIONS.put(id, new SimpleItemDefinition(model, rotation, baseScale, firstScale, thirdScale, guiScale, groundScale,
                fixedScale, baseTranslation, texture, translations, applyBuiltinRotations));
    }

    private static void registerArmor(String id, LegacyBipedModel model, ResourceLocation texture, ArmorItem.Type armorType) {
        float groundScale = armorType == ArmorItem.Type.HELMET ? 1.25F : 1.5F;
        DEFINITIONS.put(id, new ArmorItemDefinition(model, texture, armorType, groundScale));
    }

    private static void registerShield(String id, LegacySimpleModel model, ResourceLocation texture) {
        DEFINITIONS.put(id, new ShieldItemDefinition(model, texture));
    }

    private static void registerMachineBlockItem(String id, LegacySimpleModel model, ResourceLocation texture) {
        DEFINITIONS.put(id, new MachineBlockItemDefinition(model, texture));
    }

    private static LegacyBipedModel armor(LegacyBipedModel model, ArmorItem.Type armorType) {
        boolean head = armorType == ArmorItem.Type.HELMET;
        boolean chest = armorType == ArmorItem.Type.CHESTPLATE;
        boolean legs = armorType == ArmorItem.Type.LEGGINGS;
        boolean feet = armorType == ArmorItem.Type.BOOTS;
        if (model.bipedHead != null) model.bipedHead.showModel = head;
        if (model.bipedHeadwear != null) model.bipedHeadwear.showModel = head;
        if (model.bipedBody != null) model.bipedBody.showModel = chest || legs;
        if (model.bipedRightArm != null) model.bipedRightArm.showModel = chest;
        if (model.bipedLeftArm != null) model.bipedLeftArm.showModel = chest;
        if (model.bipedRightLeg != null) model.bipedRightLeg.showModel = legs || feet;
        if (model.bipedLeftLeg != null) model.bipedLeftLeg.showModel = legs || feet;
        return model;
    }

    private static ItemRotation rotation(RotationMode mode) {
        return switch (mode) {
            case GUN -> rotation(-180.0F, 180.0F, 0.0F);
            case GUN_90 -> rotation(-180.0F, -90.0F, 0.0F);
            case GRENADE_180 -> rotation(-180.0F, 180.0F, 0.0F);
            case GRENADE_90 -> rotation(-180.0F, 90.0F, 0.0F);
        };
    }

    private static ItemRotation rotation(float xRotation, float yRotation, float zRotation) {
        return (poseStack, context) -> {
            if (xRotation != 0.0F) {
                poseStack.mulPose(Axis.XP.rotationDegrees(xRotation));
            }
            if (yRotation != 0.0F) {
                poseStack.mulPose(Axis.YP.rotationDegrees(yRotation));
            }
            if (zRotation != 0.0F) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(zRotation));
            }
        };
    }

    private static ItemRotation lmgMagazineRotation() {
        return (poseStack, context) -> {
            poseStack.mulPose(Axis.XP.rotationDegrees(-180.0F));
            float yRotation = (context == ItemDisplayContext.GUI || context == ItemDisplayContext.FIXED) ? 90.0F : 180.0F;
            poseStack.mulPose(Axis.YP.rotationDegrees(yRotation));
        };
    }

    private static ResourceLocation[] gunTexture(String name) {
        return gunTextures(name);
    }

    private static ResourceLocation[] gunTextures(String... names) {
        ResourceLocation[] textures = new ResourceLocation[names.length];
        for (int i = 0; i < names.length; i++) {
            textures[i] = ResourceLocation.parse("techguns:textures/guns/" + names[i] + ".png");
        }
        return textures;
    }

    private static ResourceLocation armorTexture(String name) {
        return ResourceLocation.parse("techguns:textures/models/armor/" + name + ".png");
    }

    private static ResourceLocation armorItemTexture(String name) {
        return ResourceLocation.parse("techguns:textures/armors/" + name + ".png");
    }

    private static ResourceLocation blockTexture(String name) {
        return ResourceLocation.parse("techguns:textures/blocks/" + name + ".png");
    }

    private static ModelResourceLocation modelLocation(String id) {
        return new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath("techguns", id + "_legacy"), "inventory");
    }

    private static float[] vec(float x, float y, float z) {
        return new float[]{x, y, z};
    }

    private static float[][] translations(float[] first, float[] third, float[] gui, float[] ground, float[] fixed) {
        return new float[][]{first, third, gui, ground, fixed};
    }

    private static float[][] withGuiOffset(float[][] translations, float x, float y, float z) {
        float[][] adjusted = new float[translations.length][];
        for (int i = 0; i < translations.length; i++) {
            adjusted[i] = translations[i].clone();
        }
        adjusted[2][0] += x;
        adjusted[2][1] += y;
        adjusted[2][2] += z;
        return adjusted;
    }

    private static float scale(float factor) {
        return LEGACY_SCALE * factor;
    }

    private static void applySharedContextTransforms(PoseStack poseStack, ItemDisplayContext context, float[][] translations) {
        int index = switch (context) {
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> 0;
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> 1;
            case GUI -> 2;
            case GROUND -> 3;
            case FIXED -> 4;
            default -> -1;
        };
        if (index < 0) {
            return;
        }
        float mirror = (context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) ? -1.0F : 1.0F;
        float[] translation = translations[index];
        poseStack.translate(translation[0] * mirror, translation[1], translation[2]);
    }

    private static void applyBuiltinContextRotations(PoseStack poseStack, ItemDisplayContext context, boolean armorPreview) {
        if (context == ItemDisplayContext.GUI) {
            if (armorPreview) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            } else {
                poseStack.mulPose(Axis.YP.rotationDegrees(40.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
            }
        } else if (context == ItemDisplayContext.FIXED && !armorPreview) {
            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        }
    }

    private static float scaleFor(ItemDisplayContext context, float baseScale, float firstScale, float thirdScale,
                                  float guiScale, float groundScale, float fixedScale) {
        return switch (context) {
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> baseScale * firstScale;
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> baseScale * thirdScale;
            case GUI -> baseScale * guiScale;
            case GROUND -> baseScale * groundScale;
            case FIXED -> baseScale * fixedScale;
            default -> baseScale;
        };
    }

    public interface ItemDefinition {
        void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay);
    }

    @FunctionalInterface
    private interface ItemRotation {
        void apply(PoseStack poseStack, ItemDisplayContext context);
    }

    private enum RotationMode {
        GUN,
        GUN_90,
        GRENADE_180,
        GRENADE_90
    }

    private record MultipartItemDefinition(LegacyMultipartModel model, int parts, ItemRotation rotation, float baseScale,
                                           float firstScale, float thirdScale, float guiScale, float groundScale,
                                           float fixedScale, float[] baseTranslation, ResourceLocation[] textures,
                                           float[][] translations, boolean applyBuiltinRotations,
                                           boolean compensateGuiOrigin) implements ItemDefinition {

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            LegacyRenderContext.begin(poseStack, bufferSource, light, overlay);
            try {
                poseStack.pushPose();
                if (context == ItemDisplayContext.GUI && compensateGuiOrigin) {
                    poseStack.translate(0.5F, 0.5F, 0.5F);
                }
                applySharedContextTransforms(poseStack, context, translations);
                if (applyBuiltinRotations) {
                    applyBuiltinContextRotations(poseStack, context, false);
                }

                float scaleFactor = scaleFor(context, baseScale, firstScale, thirdScale, guiScale, groundScale, fixedScale);
                poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                rotation.apply(poseStack, context);
                poseStack.translate(baseTranslation[0], baseTranslation[1], baseTranslation[2]);

                for (int part = 0; part < parts; part++) {
                    LegacyModelTexture.set(textures[Math.min(part, textures.length - 1)]);
                    model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, LEGACY_SCALE, 100, 0.0F, context, part, 0.0F, 0.0F);
                }
                poseStack.popPose();
            } finally {
                LegacyModelTexture.clear();
                LegacyRenderContext.end();
            }
        }
    }

    private record MachineBlockItemDefinition(LegacySimpleModel model, ResourceLocation texture) implements ItemDefinition {

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            LegacyRenderContext.begin(poseStack, bufferSource, light, overlay);
            LegacyModelTexture.set(texture);
            try {
                poseStack.pushPose();
                poseStack.translate(0.5F, 1.5F, 0.5F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                model.render(LEGACY_SCALE);
                poseStack.popPose();
            } finally {
                LegacyModelTexture.clear();
                LegacyRenderContext.end();
            }
        }
    }

    private record ShieldItemDefinition(LegacySimpleModel model, ResourceLocation texture) implements ItemDefinition {

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            int renderLight = context == ItemDisplayContext.GUI ? LightTexture.FULL_BRIGHT : light;
            LegacyRenderContext.begin(poseStack, bufferSource, renderLight, overlay);
            LegacyModelTexture.set(texture);
            try {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.5F, 0.5F);
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                model.render(LEGACY_SCALE);
                poseStack.popPose();
            } finally {
                LegacyModelTexture.clear();
                LegacyRenderContext.end();
            }
        }
    }

    private record SimpleItemDefinition(LegacySimpleModel model, ItemRotation rotation, float baseScale, float firstScale,
                                        float thirdScale, float guiScale, float groundScale, float fixedScale,
                                        float[] baseTranslation, ResourceLocation texture, float[][] translations,
                                        boolean applyBuiltinRotations) implements ItemDefinition {

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            int renderLight = context == ItemDisplayContext.GUI ? LightTexture.FULL_BRIGHT : light;
            LegacyRenderContext.begin(poseStack, bufferSource, renderLight, overlay);
            LegacyModelTexture.set(texture);
            try {
                poseStack.pushPose();
                applySharedContextTransforms(poseStack, context, translations);
                if (applyBuiltinRotations) {
                    applyBuiltinContextRotations(poseStack, context, false);
                }

                float scaleFactor = scaleFor(context, baseScale, firstScale, thirdScale, guiScale, groundScale, fixedScale);
                poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                rotation.apply(poseStack, context);
                poseStack.translate(baseTranslation[0], baseTranslation[1], baseTranslation[2]);
                model.render(LEGACY_SCALE);
                poseStack.popPose();
            } finally {
                LegacyModelTexture.clear();
                LegacyRenderContext.end();
            }
        }
    }

    private record ModelBackedItemDefinition(ModelResourceLocation modelLocation, ItemRotation rotation, float baseScale,
                                             float firstScale, float thirdScale, float guiScale, float groundScale,
                                             float fixedScale, float[] baseTranslation, float[][] translations,
                                             boolean applyBuiltinRotations) implements ItemDefinition {

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            poseStack.pushPose();
            try {
                applySharedContextTransforms(poseStack, context, translations);
                if (applyBuiltinRotations) {
                    applyBuiltinContextRotations(poseStack, context, false);
                }

                float scaleFactor = scaleFor(context, baseScale, firstScale, thirdScale, guiScale, groundScale, fixedScale);
                poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                rotation.apply(poseStack, context);
                poseStack.translate(baseTranslation[0], baseTranslation[1], baseTranslation[2]);

                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                BakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(modelLocation);
                itemRenderer.render(stack, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay, bakedModel);
            } finally {
                poseStack.popPose();
            }
        }
    }

    private record ArmorItemDefinition(LegacyBipedModel model, ResourceLocation texture, ArmorItem.Type armorType,
                                       float groundScale) implements ItemDefinition {

        private static final float GUI_ARMOR_SCALE_MULTIPLIER = 0.72F;
        private static final float GUI_ARMOR_BRIGHTNESS_MULTIPLIER = 1.1F;

        @Override
        public void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
            int renderLight = context == ItemDisplayContext.GUI ? LightTexture.FULL_BRIGHT : light;
            LegacyRenderContext.begin(poseStack, bufferSource, renderLight, overlay);
            LegacyModelTexture.set(texture);
            try {
                poseStack.pushPose();
                // ItemRenderer shifts BEWLR items to block-space origin before calling us.
                // Old Techguns armor preview renderers worked from an unshifted origin, so
                // armor items need this compensation to match the legacy slot placement.
                poseStack.translate(0.5F, 0.5F, 0.5F);
                applySharedContextTransforms(poseStack, context, DEFAULT_ARMOR_TRANSLATIONS);
                applyBuiltinContextRotations(poseStack, context, true);

                float scaleFactor = scaleFor(context, 1.0F, 1.0F, 1.0F, 1.5F, groundScale, 1.5F);
                if (context == ItemDisplayContext.GUI) {
                    scaleFactor *= guiScaleMultiplier(armorType);
                }
                poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                poseStack.mulPose(Axis.XP.rotationDegrees(-180.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                poseStack.translate(0.0F, previewYOffset(context, armorType), 0.0F);
                if (context == ItemDisplayContext.GUI) {
                    RenderSystem.setShaderColor(
                            GUI_ARMOR_BRIGHTNESS_MULTIPLIER,
                            GUI_ARMOR_BRIGHTNESS_MULTIPLIER,
                            GUI_ARMOR_BRIGHTNESS_MULTIPLIER,
                            1.0F
                    );
                    model.renderVisibleParts(LEGACY_SCALE);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                } else {
                    model.renderVisibleParts(LEGACY_SCALE);
                }
                poseStack.popPose();
            } finally {
                LegacyModelTexture.clear();
                LegacyRenderContext.end();
            }
        }

        private static float baseYOffset(ArmorItem.Type armorType) {
            return switch (armorType) {
                case HELMET -> -0.54F;
                case CHESTPLATE -> -0.9F;
                case LEGGINGS -> -1.29F;
                case BOOTS -> -1.42F;
            };
        }

        private static float previewYOffset(ItemDisplayContext context, ArmorItem.Type armorType) {
            if (context == ItemDisplayContext.GUI) {
                return switch (armorType) {
                    case BOOTS -> -1.42F;
                    case LEGGINGS -> -1.05F;
                    case CHESTPLATE -> -0.35F;
                    case HELMET ->  0.25F;
                };
            }
            return baseYOffset(armorType);
        }

        private static float guiScaleMultiplier(ArmorItem.Type armorType) {
            return switch (armorType) {
                case LEGGINGS -> GUI_ARMOR_SCALE_MULTIPLIER * 0.7F;
                case CHESTPLATE -> GUI_ARMOR_SCALE_MULTIPLIER * 0.67F;
                case HELMET, BOOTS -> GUI_ARMOR_SCALE_MULTIPLIER;
            };
        }
    }
}
