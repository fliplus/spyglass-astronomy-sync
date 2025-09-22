package fliplus.spyglassastronomysync;

import fliplus.spyglassastronomysync.network.AdminPrivilegesPacket;
import fliplus.spyglassastronomysync.network.DataPacket;
import fliplus.spyglassastronomysync.network.RequestDataPacket;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpyglassAstronomySync implements ModInitializer {
    public static final String MOD_ID = "spyglass-astronomy-sync";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {}

    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(DataPacket.TYPE, DataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(RequestDataPacket.TYPE, RequestDataPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(AdminPrivilegesPacket.TYPE, AdminPrivilegesPacket.STREAM_CODEC);
    }
}