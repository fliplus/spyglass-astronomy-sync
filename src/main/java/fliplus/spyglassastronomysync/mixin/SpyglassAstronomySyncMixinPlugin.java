package fliplus.spyglassastronomysync.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class SpyglassAstronomySyncMixinPlugin implements IMixinConfigPlugin {
    private static final boolean IS_SPYGLASS_ASTRONOMY_LOADED = FabricLoader.getInstance().isModLoaded("spyglass_astronomy");

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return IS_SPYGLASS_ASTRONOMY_LOADED;
    }

    // Unused
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return ""; }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return List.of(); }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
