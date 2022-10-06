package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * <b>MinecraftClientInjector</b>
 * <p>Injects into the `render` method to report the last frame update timestamp.</p>
 */
@Mixin(MinecraftClient.class)
public class MinecraftClientInjector
{
    /**
     * <p>When a frame has finished rendering, this will tell the client to call `onRenderFinished` to update the
     *    "last frame rendered" timestamp. </p>
     * @param bool Boolean
     * @param ci CallbackInfo
     */
    @Inject(method = "render", at = @At("RETURN"))
    public void onPostRender(boolean bool, CallbackInfo ci)
    {
        ClientEvents.RENDER_FINISHED.invoker().onRenderFinished(bool, ci);
    }
}
