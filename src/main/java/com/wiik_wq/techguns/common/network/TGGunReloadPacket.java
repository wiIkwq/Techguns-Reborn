package com.wiik_wq.techguns.common.network;

import com.wiik_wq.techguns.common.gun.TGGunActions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record TGGunReloadPacket(InteractionHand hand) {

    public static void encode(TGGunReloadPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.hand);
    }

    public static TGGunReloadPacket decode(FriendlyByteBuf buffer) {
        return new TGGunReloadPacket(buffer.readEnum(InteractionHand.class));
    }

    public static void handle(TGGunReloadPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            TGGunActions.tryStartReload(player, packet.hand);
        }
        context.setPacketHandled(true);
    }
}
