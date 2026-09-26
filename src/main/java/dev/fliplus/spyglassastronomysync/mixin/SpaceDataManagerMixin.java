package dev.fliplus.spyglassastronomysync.mixin;

import com.nettakrim.spyglass_astronomy.SpaceDataManager;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import dev.fliplus.spyglassastronomysync.client.network.ClientNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpaceDataManager.class)
public class SpaceDataManagerMixin {
    @Inject(method = "saveDataToFile", at = @At("HEAD"), cancellable = true, remap = false)
    private void saveDataToFile(CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync) ci.cancel();
    }

    @Inject(method = "makeChange", at = @At("TAIL"), remap = false)
    private static void makeChange(CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync) {
            ((SpaceDataManagerAccessor) SpyglassAstronomyClient.spaceDataManager).setChangesMade(0);

            String data = SpyglassAstronomyClient.spaceDataManager.dataToString();
            ClientNetworking.sendData(data);
        }
    }
}
