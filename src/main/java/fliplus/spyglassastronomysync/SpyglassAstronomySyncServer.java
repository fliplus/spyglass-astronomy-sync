package fliplus.spyglassastronomysync;

import fliplus.spyglassastronomysync.server.network.ServerNetworking;
import net.fabricmc.api.DedicatedServerModInitializer;

public class SpyglassAstronomySyncServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerNetworking.registerPayloads();
        ServerNetworking.registerReceivers();
    }
}
