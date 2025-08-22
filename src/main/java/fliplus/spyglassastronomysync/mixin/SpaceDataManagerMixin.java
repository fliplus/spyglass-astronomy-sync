package fliplus.spyglassastronomysync.mixin;

import com.nettakrim.spyglass_astronomy.SpaceDataManager;
import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.client.ClientSpaceDataManager;
import fliplus.spyglassastronomysync.client.network.ClientNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpaceDataManager.class)
public class SpaceDataManagerMixin {
    @Inject(method = "saveData", at = @At("HEAD"), cancellable = true, remap = false)
    private static void saveData(CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync == null || SpyglassAstronomySyncClient.shouldSync) {
            ci.cancel();
        }
    }

    @Inject(method = "makeChange", at = @At("HEAD"), remap = false)
    private static void makeChange(CallbackInfo ci) {
        if (Boolean.TRUE.equals(SpyglassAstronomySyncClient.shouldSync)) {
            String data = ClientSpaceDataManager.dataToString();
            ClientSpaceDataManager.revision++;
            ClientNetworking.sendData(data, ClientSpaceDataManager.revision);
        }
    }
}
