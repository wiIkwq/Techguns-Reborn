package com.wiik_wq.techguns.common.network;

import com.wiik_wq.techguns.client.network.TGClientPacketHandlers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SyncTGPlayerDataPacket(CompoundTag tag) {

    public static void encode(SyncTGPlayerDataPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt(packet.tag);
    }

    public static SyncTGPlayerDataPacket decode(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        return new SyncTGPlayerDataPacket(tag == null ? new CompoundTag() : tag);
    }

    public static void handle(SyncTGPlayerDataPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TGClientPacketHandlers.handlePlayerDataSync(packet.tag));
        contextSupplier.get().setPacketHandled(true);
    }
}
