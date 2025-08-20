package fliplus.spyglassastronomysync.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 1500)
public class MinecraftMixin {
    @TargetHandler(
        mixin = "com.nettakrim.spyglass_astronomy.mixin.MinecraftClientMixin",
        name = "loadSpace"
    )
    @ModifyArg(
        method = "@MixinSquared:Handler",
        at = @At(
            value = "INVOKE",
            target = "Lcom/nettakrim/spyglass_astronomy/SpyglassAstronomyClient;loadSpace(Lnet/minecraft/client/multiplayer/ClientLevel;Z)V"
        ),
        index = 1
    )
    private boolean allowSpace(boolean original) {
        return false;
    }

    @Inject(method = "disconnect", at = @At("RETURN"))
    private void disconnect(CallbackInfo ci) {
        SpyglassAstronomySyncClient.shouldSync = null;
    }
}
