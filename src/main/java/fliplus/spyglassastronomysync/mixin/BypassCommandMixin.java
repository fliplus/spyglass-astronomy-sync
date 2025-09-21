package fliplus.spyglassastronomysync.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.nettakrim.spyglass_astronomy.commands.admin_subcommands.BypassCommand;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BypassCommand.class)
public class BypassCommandMixin {
    @Inject(method = "bypassKnowledge", at = @At("HEAD"), cancellable = true, remap = false)
    private static void bypassKnowledge(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }
}
