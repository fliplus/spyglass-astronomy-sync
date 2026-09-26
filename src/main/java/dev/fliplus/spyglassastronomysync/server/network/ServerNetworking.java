package dev.fliplus.spyglassastronomysync.server.network;

import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import dev.fliplus.spyglassastronomysync.mixin.BiomeManagerAccessor;
import dev.fliplus.spyglassastronomysync.network.AdminPrivilegesPayload;
import dev.fliplus.spyglassastronomysync.network.DataPayload;
import dev.fliplus.spyglassastronomysync.network.RequestDataPayload;
import dev.fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetworking {
    public static void registerPayloads() {
        SpyglassAstronomySync.registerPayloads();
    }

    public static void sendData(ServerPlayer player, String data) {
        ServerPlayNetworking.send(player, new DataPayload(data));
    }

    public static void sendAdminPrivileges(ServerPlayer player, boolean allowAdminPrivileges) {
        ServerPlayNetworking.send(player, new AdminPrivilegesPayload(allowAdminPrivileges));
    }

    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(DataPayload.TYPE, ServerNetworking::handleData);
        ServerPlayNetworking.registerGlobalReceiver(RequestDataPayload.TYPE, ServerNetworking::handleDataRequest);
    }

    private static void handleData(DataPayload payload, ServerPlayNetworking.Context context) {
        String data = payload.data();

        long seedHash = ((BiomeManagerAccessor) context.player().level().getBiomeManager()).getBiomeZoomSeed();

        ServerSpaceDataManager.saveData(data, context.server(), seedHash);

        for (ServerPlayer player : context.server().getPlayerList().getPlayers()) {
            if (player == context.player()) continue;
            sendData(player, data);
        }
    }

    private static void handleDataRequest(RequestDataPayload payload, ServerPlayNetworking.Context context) {
        long seedHash = ((BiomeManagerAccessor) context.player().level().getBiomeManager()).getBiomeZoomSeed();
        String storedData = ServerSpaceDataManager.getData(context.server(), seedHash);
        sendData(context.player(), storedData);
    }
}
