package fliplus.spyglassastronomysync.mixin;

import com.nettakrim.spyglass_astronomy.SpaceDataManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpaceDataManager.class)
public interface SpaceDataManagerAccessor {
    @Accessor("changesMade")
    void setChangesMade(int changesMade);
}
