package fliplus.spyglassastronomysync;

import com.mojang.brigadier.context.CommandContext;
import fliplus.spyglassastronomysync.client.network.ClientNetworking;
import fliplus.spyglassastronomysync.mixin.LocalPlayerAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SpyglassAstronomySyncClient implements ClientModInitializer {
    public static boolean shouldSync;
    public static boolean adminPrivileges;

    @Override
    public void onInitializeClient() {
        verifyDependency();

        ClientNetworking.registerPayloads();
        ClientNetworking.registerReceivers();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            shouldSync = false;
            adminPrivileges = false;
        });
    }

    private static void verifyDependency() {
        if (!FabricLoader.getInstance().isModLoaded("spyglass_astronomy")) {
            TinyFileDialogs.tinyfd_messageBox(
                "Minecraft Error - Missing Dependency",
                "Please install Spyglass Astronomy to use Spyglass Astronomy Sync",
                "ok",
                "error",
                false
            );
            throw new IllegalStateException("Spyglass Astronomy is not present");
        }
    }

    public static void validateCommand(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir, boolean isAdminCommand) {
        if (SpyglassAstronomySyncClient.shouldSync) {
            if (isAdminCommand) {
                int permissionLevel = ((LocalPlayerAccessor) context.getSource().getPlayer()).permissionLevel();
                if (permissionLevel < 2 && !adminPrivileges) {
                    context.getSource().sendError(Component.literal("You do not have permission to execute this command"));
                    cir.setReturnValue(0);
                }
            } else {
                context.getSource().sendError(Component.literal("You cannot execute this command while sync is enabled"));
                cir.setReturnValue(0);
            }
        }
    }
}
