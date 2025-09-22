package fliplus.spyglassastronomysync;

import fliplus.spyglassastronomysync.client.config.SpyglassAstronomySyncConfig;
import fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import fliplus.spyglassastronomysync.server.network.ServerNetworking;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SpyglassAstronomySyncServer implements DedicatedServerModInitializer {
    public static SpyglassAstronomySyncConfig CONFIG;

    @Override
    public void onInitializeServer() {
        loadConfig();

        ServerNetworking.registerPayloads();
        ServerNetworking.registerReceivers();

        ServerWorldEvents.LOAD.register((server, level) -> {
            ServerSpaceDataManager.createDataFile(server);
        });

        ServerPlayConnectionEvents.JOIN.register((packet, sender, server) -> {
            ServerNetworking.sendData(packet.player, ServerSpaceDataManager.getData(server), ServerSpaceDataManager.revision);
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
