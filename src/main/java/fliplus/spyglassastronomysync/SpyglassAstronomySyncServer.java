package fliplus.spyglassastronomysync;

import fliplus.spyglassastronomysync.server.ServerSpaceDataManager;
import fliplus.spyglassastronomysync.server.network.ServerNetworking;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SpyglassAstronomySyncServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerNetworking.registerPayloads();
        ServerNetworking.registerReceivers();

        ServerWorldEvents.LOAD.register((server, level) -> {
            ServerSpaceDataManager.createDataFile(server);
        });

        ServerPlayConnectionEvents.JOIN.register((packet, sender, server) -> {
            ServerNetworking.sendData(packet.player, ServerSpaceDataManager.getData(server), ServerSpaceDataManager.revision);
        });

        ServerPlayConnectionEvents.DISCONNECT.register((packet, server) -> {
            ServerSpaceDataManager.removePlayer(packet.player);
        });
    }
}
