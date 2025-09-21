package fliplus.spyglassastronomysync.mixin;

import fliplus.spyglassastronomysync.SpyglassAstronomySyncClient;
import fliplus.spyglassastronomysync.client.network.ClientNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "setLevel", at = @At("RETURN"), order = 1500)
    private void setLevel(ClientLevel level, ReceivingLevelScreen.Reason reason, CallbackInfo ci) {
        if (SpyglassAstronomySyncClient.shouldSync && Minecraft.getInstance().player != null) ClientNetworking.requestData();
    }
}
