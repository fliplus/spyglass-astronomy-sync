package dev.fliplus.spyglassastronomysync;

import com.mojang.brigadier.context.CommandContext;
import dev.fliplus.spyglassastronomysync.client.network.ClientNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SpyglassAstronomySyncClient implements ClientModInitializer {
    public static boolean shouldSync;
    public static boolean adminPrivileges;

    @Override
    public void onInitializeClient() {
        verifyDependency();

        ClientNetworking.registerPayloads();
        ClientNetworking.registerReceivers();

        ClientPlayConnectionEvents.JOIN.register((_, _, _) -> {
            shouldSync = false;
            adminPrivileges = false;
        });
    }

    private static void verifyDependency() {
        if (!FabricLoader.getInstance().isModLoaded("spyglass_astronomy")) {
            throw new IllegalStateException("Spyglass Astronomy is not present");
        }
    }

    public static void validateCommand(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir, boolean isAdminCommand) {
        if (SpyglassAstronomySyncClient.shouldSync) {
            if (isAdminCommand) {
                boolean hasPermission = context.getSource().getPlayer().permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.GAMEMASTERS));
                if (!hasPermission && !adminPrivileges) {
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
