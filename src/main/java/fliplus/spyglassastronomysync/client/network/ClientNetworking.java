package fliplus.spyglassastronomysync.client.network;

import com.nettakrim.spyglass_astronomy.Knowledge;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.StarCountCommand;
import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.client.ClientSpaceDataManager;
import fliplus.spyglassastronomysync.network.AdminPrivilegesPacket;
import fliplus.spyglassastronomysync.network.DataPacket;
import fliplus.spyglassastronomysync.network.RequestDataPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.ArrayList;

public class ClientNetworking {
    public static void registerPayloads() {
        SpyglassAstronomySync.registerPayloads();
    }

    public static void sendData(String data, int revision) {
        ClientPlayNetworking.send(new DataPacket(data, revision));
    }

    public static void requestData() {
        ClientPlayNetworking.send(new RequestDataPacket());
    }

    public static void registerReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(DataPacket.TYPE, ClientNetworking::handleData);
        ClientPlayNetworking.registerGlobalReceiver(AdminPrivilegesPacket.TYPE, ClientNetworking::handleAdminPrivileges);
    }

    private static void handleData(DataPacket packet, ClientPlayNetworking.Context context) {
        if (!SpyglassAstronomySyncClient.shouldSync) {
            SpyglassAstronomySync.LOGGER.info("Spyglass Astronomy Sync detected on the server. Enabling sync.");
            SpyglassAstronomySyncClient.shouldSync = true;
        }

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

    private static void handleAdminPrivileges(AdminPrivilegesPacket packet, ClientPlayNetworking.Context context) {
        SpyglassAstronomySyncClient.adminPrivileges = packet.allowAdminPrivileges();
    }
}
