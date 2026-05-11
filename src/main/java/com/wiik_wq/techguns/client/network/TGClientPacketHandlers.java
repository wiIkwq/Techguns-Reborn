package com.wiik_wq.techguns.client.network;

import com.wiik_wq.techguns.common.player.TGPlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

public final class TGClientPacketHandlers {

    private TGClientPacketHandlers() {
    }

    public static void handlePlayerDataSync(CompoundTag tag) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        minecraft.player.getCapability(TGPlayerDataProvider.CAPABILITY).ifPresent(data -> data.deserializeNBT(tag.copy()));
    }
}
