package dev.fliplus.spyglassastronomysync;

import dev.fliplus.spyglassastronomysync.server.config.SpyglassAstronomySyncConfig;
import dev.fliplus.spyglassastronomysync.mixin.BiomeManagerAccessor;
import dev.fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import dev.fliplus.spyglassastronomysync.server.network.ServerNetworking;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SpyglassAstronomySyncServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        SpyglassAstronomySyncConfig.load();

        ServerNetworking.registerPayloads();
        ServerNetworking.registerReceivers();

        ServerPlayConnectionEvents.JOIN.register((packet, _, server) -> {
            long seedHash = ((BiomeManagerAccessor) packet.player.level().getBiomeManager()).getBiomeZoomSeed();
            ServerNetworking.sendData(packet.player, ServerSpaceDataManager.getData(server, seedHash));
            ServerNetworking.sendAdminPrivileges(packet.player, SpyglassAstronomySyncConfig.instance().allowAdminCommands);
        });
    }
}
