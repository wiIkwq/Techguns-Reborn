package com.wiik_wq.techguns.common.gun;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public final class TGGunActions {

    private static final double ENTITY_PICK_PADDING = 1.0D;
    private static final float ENTITY_HIT_INFLATION = 0.3F;

    private TGGunActions() {
    }

    public static GunActionResult tryFire(ServerPlayer player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Optional<TGGunDefinition> definition = TGGunDefinitions.forStack(stack);
        if (definition.isEmpty()) {
            return GunActionResult.NOT_A_GUN;
        }

        TGGunDefinition gun = definition.get();
        long gameTime = player.level().getGameTime();
        TGGunState.ensureInitialized(stack, gun);
        TGGunState.completeReloadIfReady(stack, gameTime, gun);
        if (TGGunState.isReloading(stack, gameTime)) {
            return GunActionResult.RELOADING;
        }
        if (gameTime < TGGunState.nextFireTick(stack)) {
            return GunActionResult.COOLDOWN;
        }

        int ammo = TGGunState.ammo(stack, gun);
        if (ammo <= 0) {
            return tryStartReload(player, hand);
        }

        if (!player.getAbilities().instabuild) {
            TGGunState.setAmmo(stack, ammo - 1, TGGunState.variant(stack, gun), gun);
        }
        TGGunState.setNextFireTick(stack, gameTime + gun.fireDelayTicks());
        player.getCooldowns().addCooldown(stack.getItem(), Math.max(1, gun.fireDelayTicks()));
        fireHitscan(player, gun);
        player.swing(hand, true);
        player.level().playSound(null, player.blockPosition(), gun.fireSound(), SoundSource.PLAYERS, 0.7F,
                0.85F + player.getRandom().nextFloat() * 0.15F);
        return GunActionResult.FIRED;
    }

    public static GunActionResult tryStartReload(ServerPlayer player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Optional<TGGunDefinition> definition = TGGunDefinitions.forStack(stack);
        if (definition.isEmpty()) {
            return GunActionResult.NOT_A_GUN;
        }

        TGGunDefinition gun = definition.get();
        long gameTime = player.level().getGameTime();
        TGGunState.ensureInitialized(stack, gun);
        TGGunState.completeReloadIfReady(stack, gameTime, gun);
        if (TGGunState.isReloading(stack, gameTime)) {
            return GunActionResult.RELOADING;
        }
        if (TGGunState.ammo(stack, gun) >= gun.clipSize()) {
            return GunActionResult.FULL;
        }

        Optional<TGAmmoInventory.ReloadAmmo> reloadAmmo = TGAmmoInventory.consumeReloadAmmo(player, gun.ammoProfile());
        if (reloadAmmo.isEmpty()) {
            return GunActionResult.NO_AMMO;
        }

        TGAmmoProfile.Variant variant = reloadAmmo.get().variant();
        TGGunState.startReload(stack, gameTime, gun.clipSize(), variant.id(), gun);
        TGAmmoInventory.giveEmptyContainer(player, gun.ammoProfile());
        player.getCooldowns().addCooldown(stack.getItem(), Math.max(1, gun.reloadTicks()));
        player.level().playSound(null, player.blockPosition(), gun.reloadSound(), SoundSource.PLAYERS, 0.5F, 1.0F);
        return GunActionResult.RELOAD_STARTED;
    }

    public static void tick(ItemStack stack, ServerLevel level) {
        TGGunDefinitions.forStack(stack).ifPresent(definition ->
                TGGunState.completeReloadIfReady(stack, level.getGameTime(), definition)
        );
    }

    private static void fireHitscan(ServerPlayer player, TGGunDefinition gun) {
        Vec3 origin = player.getEyePosition(1.0F);
        Vec3 direction = applySpread(player, player.getViewVector(1.0F), gun.spread());
        Vec3 target = origin.add(direction.scale(gun.range()));
        BlockHitResult blockHit = player.level().clip(new ClipContext(
                origin,
                target,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 blockLimitedTarget = blockHit.getType() == HitResult.Type.MISS ? target : blockHit.getLocation();
        double maxDistanceSqr = origin.distanceToSqr(blockLimitedTarget);
        AABB searchBox = player.getBoundingBox().expandTowards(direction.scale(gun.range())).inflate(ENTITY_PICK_PADDING);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                origin,
                blockLimitedTarget,
                searchBox,
                entity -> canHit(player, entity),
                ENTITY_HIT_INFLATION
        );

        if (entityHit == null || origin.distanceToSqr(entityHit.getLocation()) > maxDistanceSqr) {
            return;
        }

        Entity targetEntity = entityHit.getEntity();
        targetEntity.invulnerableTime = 0;
        float damage = damageAtDistance(gun, (float) Math.sqrt(origin.distanceToSqr(entityHit.getLocation())));
        targetEntity.hurt(player.damageSources().playerAttack(player), damage);
        if (targetEntity instanceof LivingEntity living) {
            living.invulnerableTime = 0;
        }
    }

    private static Vec3 applySpread(ServerPlayer player, Vec3 direction, float spread) {
        if (spread <= 0.0F) {
            return direction.normalize();
        }

        double x = (player.getRandom().nextDouble() - 0.5D) * spread;
        double y = (player.getRandom().nextDouble() - 0.5D) * spread;
        double z = (player.getRandom().nextDouble() - 0.5D) * spread;
        return direction.add(x, y, z).normalize();
    }

    private static boolean canHit(ServerPlayer shooter, Entity entity) {
        return entity != shooter && !entity.isSpectator() && entity.isPickable() && shooter.hasLineOfSight(entity);
    }

    private static float damageAtDistance(TGGunDefinition gun, float distance) {
        if (distance <= gun.damageDropStart()) {
            return gun.damage();
        }
        if (gun.damageDropEnd() <= gun.damageDropStart()) {
            return gun.minimumDamage();
        }

        float progress = Mth.clamp((distance - gun.damageDropStart()) / (gun.damageDropEnd() - gun.damageDropStart()), 0.0F, 1.0F);
        return Mth.lerp(progress, gun.damage(), gun.minimumDamage());
    }

    public enum GunActionResult {
        FIRED,
        RELOAD_STARTED,
        RELOADING,
        COOLDOWN,
        FULL,
        NO_AMMO,
        NOT_A_GUN
    }
}
