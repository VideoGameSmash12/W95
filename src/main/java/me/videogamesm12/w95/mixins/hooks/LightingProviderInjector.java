package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.entrypoints.W95;
import net.minecraft.world.chunk.light.LightingProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * <b>LightingProviderInjector</b>
 * <p>Disables light updates in the client-side level in case the packet-level blocker fails.</p>
 * --
 * @author Video
 */
@Mixin(LightingProvider.class)
public class LightingProviderInjector
{
    /**
     * Disables doLightUpdates by making it return 0 if light updates are disabled.
     * --
     * @param maxUpdateCount int
     * @param doSkylight boolean
     * @param skipEdgeLightPropagation boolean
     * @param cir CallbackInfoReturnable<Integer>
     */
    @Inject(method = "doLightUpdates", at = @At("HEAD"), cancellable = true)
    public void startDoLightUpdates(int maxUpdateCount, boolean doSkylight, boolean skipEdgeLightPropagation,
        CallbackInfoReturnable<Integer> cir)
    {
        if (W95.instance.getConfig().supervisor.disableLighting)
        {
            cir.setReturnValue(0);
        }
    }
}
