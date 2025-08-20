package fliplus.spyglassastronomysync.client.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.network.HandShakePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ClientNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playC2S().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
    }

    public static void sendHandshake() {
        ClientPlayNetworking.send(new HandShakePacket());
    }

    public static void registerReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, ClientNetworking::handleHandshake);
    }

    private static void handleHandshake(HandShakePacket packet, ClientPlayNetworking.Context context) {
        SpyglassAstronomySyncClient.shouldSync = true;
        SpyglassAstronomySync.LOGGER.info("Spyglass Astronomy Sync detected on the server. Enabling sync.");
    }

}
