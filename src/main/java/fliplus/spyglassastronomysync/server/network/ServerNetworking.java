package fliplus.spyglassastronomysync.server.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySyncServer;
import fliplus.spyglassastronomysync.network.AdminPrivilegesPacket;
import fliplus.spyglassastronomysync.network.HandShakePacket;
import fliplus.spyglassastronomysync.network.DataPacket;
import fliplus.spyglassastronomysync.network.RequestDataPacket;
import fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ServerNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(RequestDataPacket.TYPE, RequestDataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(AdminPrivilegesPacket.TYPE, AdminPrivilegesPacket.STREAM_CODEC);
    }

    public static void sendHandshake(ServerPlayer player) {
        ServerPlayNetworking.send(player, new HandShakePacket());
    }

    public static void sendData(ServerPlayer player, String data, int revision) {
        ServerPlayNetworking.send(player, new DataPacket(data, revision));
    }

    public static void sendAdminPrivileges(ServerPlayer player, boolean allowAdminPrivileges) {
        ServerPlayNetworking.send(player, new AdminPrivilegesPacket(allowAdminPrivileges));
    }

    public static void registerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, ServerNetworking::handleHandshake);
        ServerPlayNetworking.registerGlobalReceiver(DataPacket.TYPE, ServerNetworking::handleData);
        ServerPlayNetworking.registerGlobalReceiver(RequestDataPacket.TYPE, ServerNetworking::handleDataRequest);
    }

    private static void handleHandshake(HandShakePacket packet, ServerPlayNetworking.Context context) {
        sendHandshake(context.player());
        sendAdminPrivileges(context.player(), SpyglassAstronomySyncServer.getConfig().AllowAdminCommands);
        ServerSpaceDataManager.addPlayer(context.player());
    }

    private static void handleData(DataPacket packet, ServerPlayNetworking.Context context) {
        String data = packet.data();
        int revision = packet.revision();

        boolean isDesynced = ServerSpaceDataManager.isDesynced(revision);
        if (isDesynced) {
            String storedData = ServerSpaceDataManager.getData(context.server());

            context.player().sendSystemMessage(Component
                .literal("You were desynced from the server. Please make your change again.")
                .withStyle(ChatFormatting.RED)
            );
            sendData(context.player(), storedData, ServerSpaceDataManager.revision);
            return;
        }

        ServerSpaceDataManager.saveData(data, context.server());

        for (ServerPlayer player : ServerSpaceDataManager.players) {
            if (player != context.player()) {
                sendData(player, data, revision);
            }
        }
    }

    private static void handleDataRequest(RequestDataPacket packet, ServerPlayNetworking.Context context) {
        String storedData = ServerSpaceDataManager.getData(context.server());
        sendData(context.player(), storedData, ServerSpaceDataManager.revision);
    }
}
