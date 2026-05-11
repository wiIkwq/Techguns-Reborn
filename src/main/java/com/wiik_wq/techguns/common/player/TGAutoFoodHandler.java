package com.wiik_wq.techguns.common.player;

import com.mojang.datafixers.util.Pair;
import com.wiik_wq.techguns.common.config.TGConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public final class TGAutoFoodHandler {

    private TGAutoFoodHandler() {
    }

    public static boolean tick(ServerPlayer player, TGPlayerData data) {
        if (TGConfig.SERVER.disableAutofeeder.get() || player.isSpectator()) {
            return false;
        }

        FoodData foodData = player.getFoodData();
        if (foodData.getFoodLevel() > 19) {
            return false;
        }

        int needed = 20 - foodData.getFoodLevel();
        if (data.autoFoodLeft() > 0) {
            int consumed = Math.min(needed, data.autoFoodLeft());
            foodData.eat(consumed, data.autoFoodSaturationModifier());
            data.setAutoFoodRemainder(data.autoFoodLeft() - consumed, data.autoFoodSaturationModifier());
            return true;
        }

        FoodProperties food = consumeNextFood(player, data.inventory());
        if (food == null) {
            return false;
        }

        applyFoodEffects(player, food);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 1.0F, 1.0F);

        int nutrition = food.getNutrition();
        int nutritionLeft = nutrition - needed;
        if (nutritionLeft > 0) {
            foodData.eat(needed, food.getSaturationModifier());
            data.setAutoFoodRemainder(nutritionLeft, food.getSaturationModifier());
        } else {
            foodData.eat(nutrition, food.getSaturationModifier());
            data.clearAutoFoodRemainder();
        }
        return true;
    }

    private static FoodProperties consumeNextFood(ServerPlayer player, ItemStackHandler inventory) {
        for (int i = TGPlayerData.SLOTS_AUTOFOOD_START; i <= TGPlayerData.SLOTS_AUTOFOOD_END; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            FoodProperties food = stack.getFoodProperties(player);
            if (food == null) {
                continue;
            }

            ItemStack consumed = inventory.extractItem(i, 1, false);
            if (!consumed.isEmpty()) {
                return food;
            }
        }
        return null;
    }

    private static void applyFoodEffects(ServerPlayer player, FoodProperties food) {
        for (Pair<MobEffectInstance, Float> effect : food.getEffects()) {
            if (player.getRandom().nextFloat() < effect.getSecond()) {
                player.addEffect(new MobEffectInstance(effect.getFirst()));
            }
        }
    }
}
