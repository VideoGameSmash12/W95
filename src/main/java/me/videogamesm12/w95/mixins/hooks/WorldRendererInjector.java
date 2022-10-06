package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * <b>WorldRenderer</b>
 * <p>Disables rendering of certain aspects of Windows</p>
 * --
 * @author Video
 */
@Mixin(WorldRenderer.class)
public class WorldRendererInjector
{
    /**
     * Disables the world from rendering if World Rendering is disabled.
     * --
     * @param matrices MatrixStack
     * @param tickDelta float
     * @param limitTime long
     * @param renderBlockOutline boolean
     * @param camera Camera
     * @param gameRenderer GameRenderer
     * @param lightmapTextureManager LightMapTextureManager
     * @param matrix4f Matrix4f
     * @param ci CallbackInfo
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void startRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
        Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f,
        CallbackInfo ci)
    {
        ActionResult result = Events.Client.Render.WorldRenderStart.EVENT.invoker().onStartWorldRender(matrices, tickDelta, limitTime,
                renderBlockOutline, camera, gameRenderer, lightmapTextureManager, matrix4f);

        if (result == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }

    /**
     * Disable entities from rendering if Entity Rendering is disabled.
     * --
     * @param entity Entity
     * @param cameraX double
     * @param cameraY double
     * @param cameraZ double
     * @param tickDelta float
     * @param matrices MatrixStack
     * @param vertexConsumers VertexConsumerProvider
     * @param ci CallbackInfo
     */
    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    public void startRenderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta,
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci)
    {
        ActionResult result = ClientEvents.WORLD_RENDER_ENTITY_STARTED.invoker().onWorldRenderEntityStarted(entity,
            cameraX, cameraY, cameraZ, tickDelta, matrices, vertexConsumers, ci);

        if (result == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }
}
