package fliplus.spyglassastronomysync;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import fliplus.spyglassastronomysync.client.network.ClientNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpyglassAstronomySyncClient implements ClientModInitializer {
    public static Boolean shouldSync;

    @Override
    public void onInitializeClient() {
        verifyDependency();

        ClientNetworking.registerPayloads();
        ClientNetworking.registerReceivers();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (Minecraft.getInstance().getSingleplayerServer() != null) {
                shouldSync = false;
                return;
            }

            ClientNetworking.sendHandshake();

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                if (shouldSync == null && client.level != null) {
                    shouldSync = false;
                    SpyglassAstronomyClient.loadSpace(client.level, true);
                }
                executor.shutdown();
            }, 2, TimeUnit.SECONDS);
        });
    }

    private static void verifyDependency() {
        if (!FabricLoader.getInstance().isModLoaded("spyglass_astronomy")) {
            TinyFileDialogs.tinyfd_messageBox(
                "Minecraft Error - Missing Dependency",
                "Spyglass Astronomy is not installed!\nSpyglass Astronomy Sync will not work without it.\nPlease install Spyglass Astronomy to use this mod.",
                "ok",
                "error",
                false
            );
            throw new IllegalStateException("Spyglass Astronomy is not present");
        }
    }

    public static void validateCommand(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        if (SpyglassAstronomySyncClient.shouldSync) {
            context.getSource().sendError(Component.literal("You cannot execute this command while sync is enabled."));
            cir.setReturnValue(0);
        }
    }
}
