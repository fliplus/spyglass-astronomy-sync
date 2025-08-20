package fliplus.spyglassastronomysync.mixin;

import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpyglassAstronomyClient.class)
public class SpyglassAstronomyClientMixin {

    @Inject(method = "saveSpace", at = @At("HEAD"), cancellable = true, remap = false)
    private static void saveSpace(CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync == null || SpyglassAstronomySyncClient.shouldSync) {
            ci.cancel();
        }
    }
}