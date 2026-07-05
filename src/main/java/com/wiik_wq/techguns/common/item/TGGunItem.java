package com.wiik_wq.techguns.common.item;

import com.wiik_wq.techguns.common.gun.TGGunActions;
import com.wiik_wq.techguns.common.gun.TGGunDefinition;
import com.wiik_wq.techguns.common.gun.TGGunDefinitions;
import com.wiik_wq.techguns.common.gun.TGGunState;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TGGunItem extends Item {

    private static final int AMMO_BAR_COLOR = 0x22C55E;
    private final String gunId;

    public TGGunItem(String gunId, Properties properties) {
        super(properties);
        this.gunId = gunId;
    }

    public String gunId() {
        return gunId;
    }

    public boolean hasGunDefinition() {
        return TGGunDefinitions.hasDefinition(gunId);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(com.wiik_wq.techguns.client.render.item.TGGunItemRenderExtensions.INSTANCE);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            TGGunActions.tick(stack, serverLevel);
        }
        TGGunDefinitions.forStack(stack).ifPresent(definition -> TGGunState.ensureInitialized(stack, definition));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return TGGunDefinitions.forStack(stack).isPresent();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        Optional<TGGunDefinition> definition = TGGunDefinitions.forStack(stack);
        if (definition.isEmpty()) {
            return super.getBarWidth(stack);
        }

        TGGunDefinition gun = definition.get();
        float fraction = gun.clipSize() <= 0 ? 0.0F : (float) TGGunState.ammo(stack, gun) / (float) gun.clipSize();
        return Math.round(Mth.clamp(fraction, 0.0F, 1.0F) * 13.0F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return AMMO_BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        TGGunDefinitions.forStack(stack).ifPresent(definition -> {
            tooltip.add(Component.translatable(
                    "tooltip.techguns.ammo",
                    TGGunState.ammo(stack, definition),
                    definition.clipSize()
            ).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(
                    "tooltip.techguns.ammo_variant",
                    Component.literal(TGGunState.variant(stack, definition))
            ).withStyle(ChatFormatting.DARK_GRAY));
        });
    }
}
