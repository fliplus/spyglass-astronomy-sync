package fliplus.spyglassastronomysync.server.network;

import fliplus.spyglassastronomysync.network.HandShakePacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playC2S().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
    }

    public static void sendHandshake(ServerPlayer player) {
        ServerPlayNetworking.send(player, new HandShakePacket());
    }

    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, ServerNetworking::handleHandshake);
    }

    private static void handleHandshake(HandShakePacket packet, ServerPlayNetworking.Context context) {
        sendHandshake(context.player());
    }
}
