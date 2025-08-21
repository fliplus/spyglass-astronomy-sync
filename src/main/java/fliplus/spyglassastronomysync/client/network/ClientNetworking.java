package fliplus.spyglassastronomysync.client.network;

import com.nettakrim.spyglass_astronomy.Knowledge;
import com.nettakrim.spyglass_astronomy.SpaceRenderingManager;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.StarCountCommand;
import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.client.ClientSpaceDataManager;
import fliplus.spyglassastronomysync.network.DataPacket;
import fliplus.spyglassastronomysync.network.HandShakePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.ArrayList;

public class ClientNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
    }

    public static void sendHandshake() {
        ClientPlayNetworking.send(new HandShakePacket());
    }

    public static void sendData(String data, int revision) {
        ClientPlayNetworking.send(new DataPacket(data, revision));
    }

    public static void registerReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, ClientNetworking::handleHandshake);
        ClientPlayNetworking.registerGlobalReceiver(DataPacket.TYPE, ClientNetworking::handleData);
    }

    private static void handleHandshake(HandShakePacket packet, ClientPlayNetworking.Context context) {
        SpyglassAstronomySync.LOGGER.info("Spyglass Astronomy Sync detected on the server. Enabling sync.");
        SpyglassAstronomySyncClient.shouldSync = true;
    }

    private static void handleData(DataPacket packet, ClientPlayNetworking.Context context) {
        ClientSpaceDataManager.revision = packet.revision();

        SpyglassAstronomyClient.stars = new ArrayList<>();
        SpyglassAstronomyClient.constellations = new ArrayList<>();
        SpyglassAstronomyClient.orbitingBodies = new ArrayList<>();

        ClientSpaceDataManager.loadData(packet.data());

        SpyglassAstronomyClient.generateSpace(false);
        StarCountCommand.invalidatedConstellations.clear();

        SpyglassAstronomyClient.knowledge = new Knowledge();
        SpyglassAstronomyClient.updateKnowledge();
    }
}
