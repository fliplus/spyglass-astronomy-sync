package fliplus.spyglassastronomysync.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.commands.ShareCommand;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShareCommand.class)
public class ShareCommandMixin {
    @Inject(method = "run", at = @At("HEAD"), cancellable = true, remap = false)
    private void run(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir);
    }

    @Inject(method = "shareConstellation", at = @At("HEAD"), cancellable = true, remap = false)
    private static void shareConstellation(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir);
    }

    @Inject(method = "shareStar", at = @At("HEAD"), cancellable = true, remap = false)
    private static void shareStar(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir);
    }

    @Inject(method = "shareOrbitingBody", at = @At("HEAD"), cancellable = true, remap = false)
    private static void shareOrbitingBody(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir);
    }
}
