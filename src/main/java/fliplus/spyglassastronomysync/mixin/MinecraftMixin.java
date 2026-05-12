package fliplus.spyglassastronomysync.mixin;

import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.client.network.ClientNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V", at = @At("RETURN"), order = 500)
    private void disconnect(CallbackInfo ci) {
        SpyglassAstronomySyncClient.shouldSync = false;
        SpyglassAstronomySyncClient.adminPrivileges = false;
    }

    @Inject(method = "setLevel", at = @At("TAIL"), order = 1500)
    private void setLevel(ClientLevel level, CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync && Minecraft.getInstance().player != null) ClientNetworking.requestData();
    }
}
