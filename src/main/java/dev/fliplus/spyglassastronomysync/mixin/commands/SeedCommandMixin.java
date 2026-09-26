package dev.fliplus.spyglassastronomysync.mixin.commands;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.SeedCommand;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SeedCommand.class)
public class SeedCommandMixin {
    @Inject(method = "setStarSeed(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At("HEAD"), cancellable = true, remap = false)
    private static void setStarSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "resetStarSeed", at = @At("HEAD"), cancellable = true, remap = false)
    private static void resetStarSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "queryStarSeed", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queryStarSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
       SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "setPlanetSeed(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At("HEAD"), cancellable = true, remap = false)
    private static void setPlanetSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "resetPlanetSeed", at = @At("HEAD"), cancellable = true, remap = false)
    private static void resetPlanetSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "queryPlanetSeed", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queryPlanetSeed(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }
}
