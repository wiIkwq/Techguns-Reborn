package com.wiik_wq.techguns.common.network;

import com.wiik_wq.techguns.common.menu.TGPlayerInventoryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public final class OpenTGPlayerInventoryPacket {

    public static final OpenTGPlayerInventoryPacket INSTANCE = new OpenTGPlayerInventoryPacket();

    private OpenTGPlayerInventoryPacket() {
    }

    public static void encode(OpenTGPlayerInventoryPacket packet, FriendlyByteBuf buffer) {
    }

    public static OpenTGPlayerInventoryPacket decode(FriendlyByteBuf buffer) {
        return INSTANCE;
    }

    public static void handle(OpenTGPlayerInventoryPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            TGNetwork.syncPlayerData(player);
            NetworkHooks.openScreen(player, new SimpleMenuProvider(
                    (containerId, playerInventory, ignored) -> new TGPlayerInventoryMenu(containerId, playerInventory),
                    Component.translatable("container.techguns.player_inventory")
            ));
        }
        context.setPacketHandled(true);
    }
}
