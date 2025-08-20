package fliplus.spyglassastronomysync;

import com.nettakrim.spyglass_astronomy.SpaceDataManager;
import fliplus.spyglassastronomysync.network.AddConstellationPacket;
import fliplus.spyglassastronomysync.network.HandShakePacket;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Optional;

public class SpyglassAstronomySync implements ModInitializer {
	public static final String MOD_ID = "spyglass-astronomy-sync";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(HandShakePacket.TYPE, HandShakePacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, (packet, context) -> {
            ServerPlayNetworking.send(context.player(), new HandShakePacket());
        });
    }
}