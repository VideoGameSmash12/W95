package me.videogamesm12.w95.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

public class ClientEvents
{
    public static Event<RenderStarted> RENDER_STARTED = EventFactory.createArrayBacked(RenderStarted.class,
        (hooks) -> (delta, time, tick, callback) ->
        {
            for (RenderStarted hook : hooks)
            {
                ActionResult result = hook.onRenderStarted(delta, time, tick, callback);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.PASS;
        }
    );

    public static Event<RenderFinished> RENDER_FINISHED = EventFactory.createArrayBacked(RenderFinished.class,
        (hooks) -> (bool, callback) ->
        {
            for (RenderFinished hook : hooks)
            {
                ActionResult result = hook.onRenderFinished(bool, callback);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.PASS;
        }
    );

    public static Event<ServerOpenScreen> SERVER_OPEN_SCREEN = EventFactory.createArrayBacked(ServerOpenScreen.class,
        (hooks) -> (packet, callback) ->
        {
            for (ServerOpenScreen hook : hooks)
            {
                ActionResult result = hook.onS2COpenScreen(packet, callback);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.PASS;
        }
    );

    public static Event<WorldRenderEntityStarted> WORLD_RENDER_ENTITY_STARTED = EventFactory.createArrayBacked(
        WorldRenderEntityStarted.class,
        (hooks) -> (entity, cameraX, cameraY, cameraZ, tickDelta, matrices, vertexConsumers, ci) ->
        {
            for (WorldRenderEntityStarted hook : hooks)
            {
                ActionResult result = hook.onWorldRenderEntityStarted(entity, cameraX, cameraY, cameraZ, tickDelta,
                    matrices, vertexConsumers, ci);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.PASS;
        }
    );

    public static Event<ChatReceiveEvent> CHAT_RECEIVE = EventFactory.createArrayBacked(ChatReceiveEvent.class,
        (hooks) -> (packet) ->
        {
            for (ChatReceiveEvent hook : hooks)
            {
                ActionResult result = hook.onChatReceive(packet);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.PASS;
        }
    );

    public static Event<ChatAddToHUDEvent> CHAT_ADD_MESSAGE = EventFactory.createArrayBacked(ChatAddToHUDEvent.class,
            (hooks) -> (type, message, sender, ci) ->
            {
                for (ChatAddToHUDEvent hook : hooks)
                {
                    ActionResult result = hook.onAddToHUD(type, message, sender, ci);

                    if (result != ActionResult.PASS)
                    {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    /**
     * <b>ChatAddToHUDEvent</b>
     */
    public interface ChatAddToHUDEvent
    {
        ActionResult onAddToHUD(MessageType type, Text message, UUID sender, CallbackInfo ci);
    }

    public interface ChatReceiveEvent
    {
        ActionResult onChatReceive(GameMessageS2CPacket message);
    }

    public interface WorldRenderEntityStarted
    {
        ActionResult onWorldRenderEntityStarted(Entity entity, double cameraX, double cameraY, double cameraZ,
            float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci);
    }

    public interface RenderStarted
    {
        ActionResult onRenderStarted(float tickDelta, long startTime, boolean tick, CallbackInfo ci);
    }

    public interface RenderFinished
    {
        ActionResult onRenderFinished(boolean bool, CallbackInfo ci);
    }

    public interface ServerOpenScreen
    {
        ActionResult onS2COpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci);
    }
}