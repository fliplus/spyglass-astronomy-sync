package dev.fliplus.spyglassastronomysync.mixin.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(com.nettakrim.spyglass_astronomy.commands.admin_subcommands.StarCountCommand.class)
public class StarCountCommandMixin {
    @Inject(method = "setStarCount(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At("HEAD"), cancellable = true, remap = false)
    private static void setStarCount(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "resetStarCount", at = @At("HEAD"), cancellable = true, remap = false)
    private static void resetStarCount(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
       SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "queryStarCount", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queryStarCount(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }
}
