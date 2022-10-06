package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * <b>GameRendererInjector</b>
 * <p>Completely disables the client's renderer if the user wants to disable it.</p>
 * --
 * @author Video
 */
@Mixin(GameRenderer.class)
public class GameRendererInjector
{
    /**
     * Calls an a "render started" event right before anything gets rendered. If it's not successful, then it doesn't
     *  render anything.
     * --
     * @param tickDelta float
     * @param startTime long
     * @param tick boolean
     * @param ci CallbackInfo
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void injectRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci)
    {
        ActionResult result = ClientEvents.RENDER_STARTED.invoker().onRenderStarted(tickDelta, startTime, tick, ci);

        if (result != ActionResult.PASS)
        {
            ci.cancel();
        }
    }
}
