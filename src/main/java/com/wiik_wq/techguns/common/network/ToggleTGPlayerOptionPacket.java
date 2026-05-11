package com.wiik_wq.techguns.common.network;

import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ToggleTGPlayerOptionPacket(int id) {

    public static void encode(ToggleTGPlayerOptionPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.id);
    }

    public static ToggleTGPlayerOptionPacket decode(FriendlyByteBuf buffer) {
        return new ToggleTGPlayerOptionPacket(buffer.readVarInt());
    }

    public static void handle(ToggleTGPlayerOptionPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            player.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> {
                data.toggle(packet.id);
                TGNetwork.syncPlayerData(player);
            });
        }
        context.setPacketHandled(true);
    }
}
