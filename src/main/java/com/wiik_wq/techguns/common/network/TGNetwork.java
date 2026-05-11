package com.wiik_wq.techguns.common.network;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class TGNetwork {

    private static final String PROTOCOL = "1";
    private static int packetId;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TechgunsReborn.MODID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    private TGNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(OpenTGPlayerInventoryPacket.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(OpenTGPlayerInventoryPacket::encode)
                .decoder(OpenTGPlayerInventoryPacket::decode)
                .consumerMainThread(OpenTGPlayerInventoryPacket::handle)
                .add();
        CHANNEL.messageBuilder(ToggleTGPlayerOptionPacket.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(ToggleTGPlayerOptionPacket::encode)
                .decoder(ToggleTGPlayerOptionPacket::decode)
                .consumerMainThread(ToggleTGPlayerOptionPacket::handle)
                .add();
        CHANNEL.messageBuilder(SyncTGPlayerDataPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncTGPlayerDataPacket::encode)
                .decoder(SyncTGPlayerDataPacket::decode)
                .consumerMainThread(SyncTGPlayerDataPacket::handle)
                .add();
    }

    public static void openPlayerInventory() {
        CHANNEL.sendToServer(OpenTGPlayerInventoryPacket.INSTANCE);
    }

    public static void togglePlayerOption(int id) {
        CHANNEL.sendToServer(new ToggleTGPlayerOptionPacket(id));
    }

    public static void syncPlayerData(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        serverPlayer.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data ->
                CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SyncTGPlayerDataPacket(data.serializeNBT()))
        );
    }

    private static int nextId() {
        return packetId++;
    }
}
