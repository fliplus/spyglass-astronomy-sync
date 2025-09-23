package fliplus.spyglassastronomysync.server.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import fliplus.spyglassastronomysync.mixin.BiomeManagerAccessor;
import fliplus.spyglassastronomysync.network.AdminPrivilegesPacket;
import fliplus.spyglassastronomysync.network.DataPacket;
import fliplus.spyglassastronomysync.network.RequestDataPacket;
import fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetworking {
    public static void registerPayloads() {
        SpyglassAstronomySync.registerPayloads();
    }

    public static void sendData(ServerPlayer player, String data, int revision) {
        ServerPlayNetworking.send(player, new DataPacket(data, revision));
    }

    public static void sendAdminPrivileges(ServerPlayer player, boolean allowAdminPrivileges) {
        ServerPlayNetworking.send(player, new AdminPrivilegesPacket(allowAdminPrivileges));
    }

    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(DataPacket.TYPE, ServerNetworking::handleData);
        ServerPlayNetworking.registerGlobalReceiver(RequestDataPacket.TYPE, ServerNetworking::handleDataRequest);
    }

    private static void handleData(DataPacket packet, ServerPlayNetworking.Context context) {
        String data = packet.data();
        int revision = packet.revision();

        long seedHash = ((BiomeManagerAccessor) context.player().level().getBiomeManager()).getBiomeZoomSeed();

        boolean isDesynced = ServerSpaceDataManager.isDesynced(revision);
        if (isDesynced) {
            String storedData = ServerSpaceDataManager.getData(context.server(), seedHash);

            context.player().sendSystemMessage(Component
                .literal("You were desynced from the server. Please make your change again")
                .withStyle(ChatFormatting.RED)
            );
            sendData(context.player(), storedData, ServerSpaceDataManager.revision);
            return;
        }

        ServerSpaceDataManager.saveData(data, context.server(), seedHash);

        for (ServerPlayer player : context.server().getPlayerList().getPlayers()) {
            if (player == context.player()) continue;
            sendData(player, data, revision);
        }
    }

    private static void handleDataRequest(RequestDataPacket packet, ServerPlayNetworking.Context context) {
        long seedHash = ((BiomeManagerAccessor) context.player().level().getBiomeManager()).getBiomeZoomSeed();
        String storedData = ServerSpaceDataManager.getData(context.server(), seedHash);
        sendData(context.player(), storedData, ServerSpaceDataManager.revision);
    }
}
