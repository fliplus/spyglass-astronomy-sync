package dev.fliplus.spyglassastronomysync;

import dev.fliplus.spyglassastronomysync.network.AdminPrivilegesPayload;
import dev.fliplus.spyglassastronomysync.network.DataPayload;
import dev.fliplus.spyglassastronomysync.network.RequestDataPayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpyglassAstronomySync implements ModInitializer {
    public static final String MOD_ID = /*$ mod_id */ "spyglassastronomysync";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {}

    public static void registerPayloads() {
        PayloadTypeRegistry.clientboundPlay().register(DataPayload.TYPE, DataPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DataPayload.TYPE, DataPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(RequestDataPayload.TYPE, RequestDataPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(AdminPrivilegesPayload.TYPE, AdminPrivilegesPayload.STREAM_CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
