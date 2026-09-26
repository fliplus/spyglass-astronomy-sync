package dev.fliplus.spyglassastronomysync.client.network;

import com.nettakrim.spyglass_astronomy.Constellation;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.StarCountCommand;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import dev.fliplus.spyglassastronomysync.network.AdminPrivilegesPayload;
import dev.fliplus.spyglassastronomysync.network.DataPayload;
import dev.fliplus.spyglassastronomysync.network.RequestDataPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.ArrayList;

public class ClientNetworking {
    public static void registerPayloads() {
        SpyglassAstronomySync.registerPayloads();
    }

    public static void sendData(String data) {
        ClientPlayNetworking.send(new DataPayload(data));
    }

    public static void requestData() {
        ClientPlayNetworking.send(new RequestDataPayload());
    }

    public static void registerReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(DataPayload.TYPE, ClientNetworking::handleData);
        ClientPlayNetworking.registerGlobalReceiver(AdminPrivilegesPayload.TYPE, ClientNetworking::handleAdminPrivileges);
    }

    private static void handleData(DataPayload payload, ClientPlayNetworking.Context context) {
        if (!SpyglassAstronomySyncClient.shouldSync) {
            SpyglassAstronomySync.LOGGER.info("Spyglass Astronomy Sync detected on the server. Enabling sync.");
            SpyglassAstronomySyncClient.shouldSync = true;
        }

        SpyglassAstronomyClient.constellations = new ArrayList<>();

        SpyglassAstronomyClient.spaceDataManager.loadDataFromLineIterator(payload.data().lines().iterator());

        SpyglassAstronomyClient.generateStars(null, false);
        SpyglassAstronomyClient.generatePlanets(null);
        for (Constellation constellation : SpyglassAstronomyClient.constellations) {
            constellation.initaliseStarLines();
        }
        SpyglassAstronomyClient.spaceRenderingManager.scheduleConstellationsUpdate();
        SpyglassAstronomyClient.spaceRenderingManager.updateSpace();

        StarCountCommand.invalidatedConstellations.clear();

        SpyglassAstronomyClient.updateKnowledge();
    }

    private static void handleAdminPrivileges(AdminPrivilegesPayload payload, ClientPlayNetworking.Context context) {
        SpyglassAstronomySyncClient.adminPrivileges = payload.allowAdminPrivileges();
    }
}
