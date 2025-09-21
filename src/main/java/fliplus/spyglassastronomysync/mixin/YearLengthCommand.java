package fliplus.spyglassastronomysync.mixin;

import com.mojang.brigadier.context.CommandContext;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(com.nettakrim.spyglass_astronomy.commands.admin_subcommands.YearLengthCommand.class)
public class YearLengthCommand {
    @Inject(method = "setYearLength(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At("HEAD"), cancellable = true, remap = false)
    private static void setYearLength(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "resetYearLength", at = @At("HEAD"), cancellable = true, remap = false)
    private static void resetYearLength(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
         SpyglassAstronomySyncClient.validateCommand(context, cir, true);
    }

    @Inject(method = "queryYearLength", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queryYearLength(CommandContext<FabricClientCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        SpyglassAstronomySyncClient.validateCommand(context, cir, false);
    }
}
