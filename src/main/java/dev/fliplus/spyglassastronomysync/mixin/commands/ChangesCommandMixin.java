package dev.fliplus.spyglassastronomysync.mixin.commands;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.ChangesCommand;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChangesCommand.class)
public class ChangesCommandMixin {
    @Inject(method = "saveChanges", at = @At("HEAD"), cancellable = true, remap = false)
    private static void saveChanges(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, false);
    }

    @Inject(method = "discardUnsavedChanges", at = @At("HEAD"), cancellable = true, remap = false)
    private static void discardUnsavedChanges(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, false);
    }

    @Inject(method = "queryChanges", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queryChanges(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, false);
    }
}
