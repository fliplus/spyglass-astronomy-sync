package fliplus.spyglassastronomysync.mixin.commands;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.ConstellationsCommand;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConstellationsCommand.class)
public class ConstellationsCommandMixin {
    @Inject(method = "removeAllConstellations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void removeAllConstellations(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "generateConstellations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void generateConstellations(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }
}
