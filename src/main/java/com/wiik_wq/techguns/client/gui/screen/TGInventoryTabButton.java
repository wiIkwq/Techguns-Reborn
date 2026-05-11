package com.wiik_wq.techguns.client.gui.screen;

import com.wiik_wq.techguns.common.registration.TGItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TGInventoryTabButton extends AbstractWidget {

    private static final int OUTLINE = 0xFF000000;
    private static final int BACKGROUND = 0xFFC6C6C6;
    private static final int BACKGROUND_HOVERED = 0xFFD2D2D2;
    private static final int LIGHT_EDGE = 0xFFFFFFFF;
    private static final int DARK_EDGE = 0xFF555555;
    private static final int SELECTED_DOT = 0xFF4D4D4D;
    private static final int ICON_X_OFFSET = 6;
    private static final int ICON_Y_OFFSET = 6;
    public static final int FIRST_TAB_X_OFFSET = 0;
    public static final int SECOND_TAB_X_OFFSET = 28;
    public static final int TAB_Y_OFFSET = -26;

    private final ItemStack icon;
    private final boolean selected;
    private final Runnable action;

    public TGInventoryTabButton(int x, int y, boolean selected, ItemStack icon, Component label, Runnable action) {
        super(x, y, 28, 26, label);
        this.icon = icon;
        this.selected = selected;
        this.action = action;
        this.active = !selected;
    }

    public static ItemStack vanillaInventoryIcon() {
        return new ItemStack(Items.CHEST);
    }

    public static ItemStack techgunsInventoryIcon() {
        return TGItems.ENTRIES.containsKey("m4")
                ? new ItemStack(TGItems.ENTRIES.get("m4").item().get())
                : ItemStack.EMPTY;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (!selected) {
            action.run();
        }
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int y = getY();
        int fill = isHoveredOrFocused() && !selected ? BACKGROUND_HOVERED : BACKGROUND;

        graphics.fill(x + 3, y, x + width - 3, y + 1, OUTLINE);
        graphics.fill(x + 1, y + 1, x + width - 1, y + 2, OUTLINE);
        graphics.fill(x, y + 2, x + width, y + height, OUTLINE);
        graphics.fill(x + 2, y + 2, x + width - 2, y + height - 1, fill);
        graphics.fill(x + 3, y + 1, x + width - 3, y + 2, LIGHT_EDGE);
        graphics.fill(x + 2, y + 3, x + 3, y + height - 1, LIGHT_EDGE);

        graphics.fill(x + 2, y + height - 2, x + width - 2, y + height - 1, DARK_EDGE);
        if (selected) {
            graphics.fill(x + width - 7, y + 5, x + width - 4, y + 8, SELECTED_DOT);
            graphics.fill(x + width - 6, y + 6, x + width - 5, y + 7, LIGHT_EDGE);
        }

        graphics.renderFakeItem(icon, x + ICON_X_OFFSET, y + ICON_Y_OFFSET);
        graphics.renderItemDecorations(Minecraft.getInstance().font, icon, x + ICON_X_OFFSET, y + ICON_Y_OFFSET);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }
}
