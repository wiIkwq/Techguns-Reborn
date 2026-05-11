package com.wiik_wq.techguns.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.menu.TGPlayerInventoryMenu;
import com.wiik_wq.techguns.common.network.TGNetwork;
import com.wiik_wq.techguns.common.player.TGPlayerData;
import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import com.wiik_wq.techguns.common.player.TGSlotType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Map;

public class TGPlayerInventoryScreen extends AbstractContainerScreen<TGPlayerInventoryMenu> {

    private static final ResourceLocation BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, "textures/gui/tgplayerinventory.png");
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int TOGGLE_BUTTON_X_OFFSET = -8;

    private static final Map<TGSlotType, ResourceLocation> EMPTY_SLOT_TEXTURES = Map.of(
            TGSlotType.FACE, emptySlot("face"),
            TGSlotType.BACK, emptySlot("back"),
            TGSlotType.HAND, emptySlot("hand"),
            TGSlotType.FOOD, emptySlot("food"),
            TGSlotType.HEAL, emptySlot("heal"),
            TGSlotType.AMMO, emptySlot("bullet")
    );

    private float mouseX;
    private float mouseY;

    public TGPlayerInventoryScreen(TGPlayerInventoryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        addInventoryTabs();
        for (int i = 0; i < 5; i++) {
            addRenderableWidget(new ToggleButton(leftPos + TOGGLE_BUTTON_X_OFFSET, topPos + 7 + i * 11, i));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        drawEmptyCustomSlots(graphics);

        if (minecraft != null && minecraft.player != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics,
                    leftPos + 51,
                    topPos + 75,
                    30,
                    (float) (leftPos + 51) - this.mouseX,
                    (float) (topPos + 25) - this.mouseY,
                    minecraft.player
            );
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    private void addInventoryTabs() {
        addRenderableWidget(new TGInventoryTabButton(
                leftPos + TGInventoryTabButton.FIRST_TAB_X_OFFSET,
                topPos + TGInventoryTabButton.TAB_Y_OFFSET,
                false,
                TGInventoryTabButton.vanillaInventoryIcon(),
                Component.translatable("container.crafting"),
                this::openVanillaInventory
        ));
        addRenderableWidget(new TGInventoryTabButton(
                leftPos + TGInventoryTabButton.SECOND_TAB_X_OFFSET,
                topPos + TGInventoryTabButton.TAB_Y_OFFSET,
                true,
                TGInventoryTabButton.techgunsInventoryIcon(),
                Component.translatable("container.techguns.player_inventory"),
                () -> {
                }
        ));
    }

    private void openVanillaInventory() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.connection.send(new ServerboundContainerClosePacket(menu.containerId));
            minecraft.player.containerMenu = minecraft.player.inventoryMenu;
            minecraft.setScreen(new InventoryScreen(minecraft.player));
        }
    }

    private void drawEmptyCustomSlots(GuiGraphics graphics) {
        for (int i = 0; i < TGPlayerInventoryMenu.CUSTOM_SLOT_COUNT; i++) {
            Slot slot = menu.slots.get(i);
            if (slot.hasItem() || !(slot instanceof com.wiik_wq.techguns.common.menu.TGTypedSlot typedSlot)) {
                continue;
            }

            ResourceLocation texture = EMPTY_SLOT_TEXTURES.get(typedSlot.type());
            if (texture != null) {
                graphics.blit(texture, leftPos + slot.x, topPos + slot.y, 0, 0, 16, 16, 16, 16);
            }
        }
    }

    private boolean optionEnabled(int id) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return false;
        }
        return minecraft.player.getCapability(TGPlayerDataProvider.CAPABILITY)
                .map(data -> data.isEnabled(id))
                .orElse(false);
    }

    private void toggleOption(int id) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> data.toggle(id));
        }
        TGNetwork.togglePlayerOption(id);
    }

    private static ResourceLocation emptySlot(String name) {
        return ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, "textures/gui/emptyslots/emptyslot_" + name + ".png");
    }

    private final class ToggleButton extends AbstractWidget {

        private final int id;

        private ToggleButton(int x, int y, int id) {
            super(x, y, 11, 11, Component.translatable(tooltipKey(id)));
            this.id = id;
            setTooltip(Tooltip.create(Component.translatable(tooltipKey(id))));
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            toggleOption(id);
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            int hoverState = isHoveredOrFocused() ? 2 : 1;
            graphics.blit(WIDGETS_LOCATION, getX(), getY(), 0, 46 + hoverState * 20, width / 2, height / 2);
            graphics.blit(WIDGETS_LOCATION, getX() + width / 2, getY(), 200 - width / 2, 46 + hoverState * 20, width / 2, height / 2);
            graphics.blit(WIDGETS_LOCATION, getX(), getY() + height / 2, 0, 46 + hoverState * 20 + 20 - height / 2, width / 2, height / 2);
            graphics.blit(WIDGETS_LOCATION, getX() + width / 2, getY() + height / 2, 200 - width / 2, 46 + hoverState * 20 + 20 - height / 2, width / 2, height / 2);

            int state = optionEnabled(id) ? 1 : 0;
            int iconY = 7 * id;
            if (id > 3) {
                iconY += 14;
            }
            graphics.blit(BACKGROUND, getX() + 2, getY() + 2, 242 + 7 * state, iconY, 7, 7, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput output) {
            defaultButtonNarrationText(output);
        }
    }

    private static String tooltipKey(int id) {
        return switch (id) {
            case TGPlayerData.TOGGLE_HUD -> "techguns.tgguitooltip.togglehud";
            case TGPlayerData.TOGGLE_NIGHT_VISION -> "techguns.tgguitooltip.togglenv";
            case TGPlayerData.TOGGLE_SAFE_MODE -> "techguns.tgguitooltip.togglesafe";
            case TGPlayerData.TOGGLE_STEP_ASSIST -> "techguns.tgguitooltip.togglestep";
            case TGPlayerData.TOGGLE_JETPACK -> "techguns.tgguitooltip.togglejetpack";
            default -> "container.techguns.player_inventory";
        };
    }
}
