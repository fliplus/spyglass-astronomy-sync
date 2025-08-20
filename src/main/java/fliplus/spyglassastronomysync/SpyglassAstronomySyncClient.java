package fliplus.spyglassastronomysync;

import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import fliplus.spyglassastronomysync.network.HandShakePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpyglassAstronomySyncClient implements ClientModInitializer {
    public static Boolean shouldSync;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (Minecraft.getInstance().getSingleplayerServer() != null) {
                shouldSync = false;
                return;
            }

            ClientPlayNetworking.send(new HandShakePacket());

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                if (shouldSync == null && client.level != null) {
                    shouldSync = false;
                    SpyglassAstronomyClient.loadSpace(client.level, true);
                }
                executor.shutdown();
            }, 2500, TimeUnit.MILLISECONDS);
        });

        ClientPlayNetworking.registerGlobalReceiver(HandShakePacket.TYPE, (packet, context) -> {
            shouldSync = true;
        });
    }
}
