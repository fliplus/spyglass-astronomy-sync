package fliplus.spyglassastronomysync.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessor {
    //? if <=1.21.10 {
    /*@Accessor("permissionLevel")
    int permissionLevel();
    *///? }
}
