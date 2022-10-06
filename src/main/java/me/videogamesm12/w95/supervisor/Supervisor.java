package me.videogamesm12.w95.supervisor;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.events.ClientEvents;
import me.videogamesm12.w95.events.SupervisorEvents;
import me.videogamesm12.w95.mixins.hooks.DebugHudAccessor;
import me.videogamesm12.w95.mixins.hooks.HudAccessor;
import me.videogamesm12.w95.supervisor.gui.SupervisorFrame;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;
import java.util.*;
import java.util.Timer;

/**
 * <b>Supervisor</b>
 * <p>A feature in W95 that runs on a separate thread and allows for the user to control their client to an extent, even
 *    when their client is completely frozen.</p>
 * @author Video
 */
public class Supervisor extends Thread implements ClientPlayConnectionEvents.Disconnect,
        ClientPlayConnectionEvents.Join, ClientEvents.RenderStarted, ClientEvents.RenderFinished,
        ClientEvents.WorldRenderEntityStarted, Events.Client.Render.WorldRenderStart, Events.Client.Render.ItemRenderStart
{
    public static Logger logger = LogManager.getLogger("W95:Supervisor");
    //
    public static long lastTick;
    //
    public static Timer lchkTimer;
    public static LagCheckTask lchkTask;
    public static UpdateTask updTask;
    //
    public static boolean doCheck = false;

    public Supervisor()
    {
        super();

        ClientPlayConnectionEvents.JOIN.register(this);
        ClientPlayConnectionEvents.DISCONNECT.register(this);

        ClientEvents.RENDER_STARTED.register(this);
        ClientEvents.RENDER_FINISHED.register(this);

        ClientEvents.WORLD_RENDER_ENTITY_STARTED.register(this);

        // Item render injection
        Events.Client.Render.ItemRenderStart.EVENT.register(this);

        // World renderer injection
        Events.Client.Render.WorldRenderStart.EVENT.register(this);
    }

    @Override
    public synchronized void start()
    {
        // Preliminary code
        super.start();
        logger.info("Starting up...");

        // Disables headless mode, as JSwing doesn't work when headless mode is enabled.
        //System.setProperty("java.awt.headless", "false");

        // Sets up the timer
        lchkTimer = new Timer();
        lchkTask = new LagCheckTask();
        updTask = new UpdateTask();
        lchkTimer.schedule(lchkTask, 0, 5000);
        lchkTimer.schedule(updTask, 0, 500);

        // Announces that the startup was successful
        logger.info("Successfully started");
    }

    /*---------========= HOOKS =========---------*/

    /**
     * Disables the lag checker when the player disconnects.
     * --
     * @param handler ClientPlayNetworkHandlerPatch
     * @param client MinecraftClient
     */
    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client)
    {
        doCheck = false;
    }

    /**
     * Enables the lag checker when the player connects.
     * --
     * @param handler ClientPlayNetworkHandlerPatch
     * @param sender PacketSender
     * @param client MinecraftClient
     */
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client)
    {
        // Re-enables the client freeze detector
        if (!doCheck)
        {
            doCheck = true;
        }
    }

    /**
     * Disables rendering entirely if disabled in the Supervisor settings.
     * --
     * @param tickDelta float
     * @param startTime long
     * @param tick boolean
     * @param ci CallbackInfo
     * @return ActionResult
     */
    @Override
    public ActionResult onRenderStarted(float tickDelta, long startTime, boolean tick, CallbackInfo ci)
    {
        if (W95.instance.getConfig().supervisor.disableRendering)
        {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

    /**
     * Updates the last tick time after a frame has finished rendering.
     * --
     * @param bool Boolean
     * @param ci CallbackInfo
     * @return ActionResult
     */
    @Override
    public ActionResult onRenderFinished(boolean bool, CallbackInfo ci)
    {
        // Sets the last tick time to the current time.
        lastTick = Instant.now().toEpochMilli();

        // That's about it.
        return ActionResult.PASS;
    }

    /**
     * Disables entities from being rendered if disabled in the Supervisor settings.
     * --
     * @param entity Entity
     * @param cameraX double
     * @param cameraY double
     * @param cameraZ double
     * @param tickDelta float
     * @param matrices MatrixStack
     * @param vertexConsumers VertexConsumerProvider
     * @param ci CallbackInfo
     * @return ActionResult
     */
    @Override
    public ActionResult onWorldRenderEntityStarted(Entity entity, double cameraX, double cameraY, double cameraZ,
        float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci)
    {
        if (W95.instance.getConfig().supervisor.disableEntityRendering)
        {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

    /**
     * Disables the world rendering system if disabled in the Supervisor settings.
     * --
     * @param matrices MatrixStack
     * @param delta float
     * @param limit long
     * @param renderOutline boolean
     * @param camera Camera
     * @param renderer GameRenderer
     * @param textureManager LightMapTextureManager
     * @param matrix4f Matrix4f
     * @return ActionResult
     */
    @Override
    public ActionResult onStartWorldRender(MatrixStack matrices, float delta, long limit, boolean renderOutline,
        Camera camera, GameRenderer renderer, LightmapTextureManager textureManager, Matrix4f matrix4f)
    {
        if (W95.instance.getConfig().supervisor.disableWorldRendering)
        {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

    /**
     * Disables item rendering if disabled in the Supervisor settings.
     * --
     * @param stack ItemStack
     * @param transformationType ModelTransformation.Mode
     * @param light int
     * @param overlay int
     * @param matrices MatrixStack
     * @param vertexConsumer VertexConsumerProvider
     * @return ActionResult
     */
    @Override
    public ActionResult onStartItemRender(ItemStack stack, ModelTransformation.Mode transformationType, int light,
        int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumer)
    {
        if (W95.instance.getConfig().supervisor.disableItemRendering)
        {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

    /*---------========= END OF HOOKS =========---------*/

    public synchronized void openRecoveryWindow()
    {
        SupervisorFrame.start();
    }

    public static class LagCheckTask extends TimerTask
    {
        @Override
        public void run()
        {
            if (doCheck && SupervisorFrame.instance == null)
            {
                if (Instant.now().toEpochMilli() - Supervisor.lastTick >= 5000)
                {
                    Supervisor.logger.warn("W95 has detected a client-side freeze!");
                    Supervisor.logger.warn("The last render successfully occurred on " + new Date(Supervisor.lastTick));
                    //
                    Supervisor.logger.warn("Opening the Supervisor menu...");
                    //
                    SupervisorFrame.start();
                    doCheck = false;
                }
            }
        }
    }

    public static class UpdateTask extends TimerTask
    {
        @Override
        public void run()
        {
            if (MinecraftClient.getInstance().world == null
                    || MinecraftClient.getInstance().getNetworkHandler() == null
                    || MinecraftClient.getInstance().getCameraEntity() == null)
            {
                return;
            }
            //
            try
            {
                Iterator<Entity> worldEntities = MinecraftClient.getInstance().world.getEntities().iterator();
                List<Entity> entities = new ArrayList<>();
                worldEntities.forEachRemaining(entities::add);
                SupervisorEvents.UPDATE_ENTITIES.invoker().onUpdateEntities(entities);
                //
                List<BlockEntity> blockEntities = MinecraftClient.getInstance().world.blockEntities;
                SupervisorEvents.UPDATE_BLOCK_ENTITIES.invoker().onUpdateBlockEntities(blockEntities);
                //
                DebugHud hud = ((HudAccessor) MinecraftClient.getInstance().inGameHud).getDebugHud();
                List<String> left = ((DebugHudAccessor) hud).invokeGetLeftText();
                SupervisorEvents.UPDATE_DEBUG_INFO.invoker().onUpdateDebugInfo(left);

            }
            catch (Exception | Error ex)
            {
                Supervisor.logger.error("Failed to update entity/block entity data");
                ex.printStackTrace();
            }
        }
    }
}
