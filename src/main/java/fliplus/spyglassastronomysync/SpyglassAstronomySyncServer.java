package fliplus.spyglassastronomysync;

import fliplus.spyglassastronomysync.client.config.SpyglassAstronomySyncConfig;
import fliplus.spyglassastronomysync.mixin.BiomeManagerAccessor;
import fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import fliplus.spyglassastronomysync.server.network.ServerNetworking;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SpyglassAstronomySyncServer implements DedicatedServerModInitializer {
    public static SpyglassAstronomySyncConfig CONFIG;

    @Override
    public void onInitializeServer() {
        loadConfig();

        ServerNetworking.registerPayloads();
        ServerNetworking.registerReceivers();

        ServerPlayConnectionEvents.JOIN.register((packet, sender, server) -> {
            long seedHash = ((BiomeManagerAccessor) packet.player.level().getBiomeManager()).getBiomeZoomSeed();
            ServerNetworking.sendData(packet.player, ServerSpaceDataManager.getData(server, seedHash), ServerSpaceDataManager.revision);
            ServerNetworking.sendAdminPrivileges(packet.player, SpyglassAstronomySyncServer.getConfig().AllowAdminCommands);
        });
    }

    private static void loadConfig() {
        CONFIG = SpyglassAstronomySyncConfig.loadConfig();
    }

    public static SpyglassAstronomySyncConfig getConfig() {
        return CONFIG;
    }
}
