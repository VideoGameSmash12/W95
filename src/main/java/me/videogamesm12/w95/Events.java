package me.videogamesm12.w95;

import me.videogamesm12.w95.managers.DumpManager;
import me.videogamesm12.w95.templates.WModule;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Matrix4f;

import java.io.File;
import java.util.List;

public class Events
{
    public static class W95
    {
        public static class Dumper
        {
            public interface EntityDumpCompleted
            {
                ActionResult onEntityDumpCompleted(List<Entity> entities, File file);
            }

            public interface EntityDumpFailed
            {
                void onEntityDumpFailed(Exception ex);
            }

            /**
             * <b>RequestEntityDump</b>
             * <p>Called when the user requests a dump of all entities in memory.</p>
             */
            public interface RequestDump
            {
                Event<RequestDump> EVENT = EventFactory.createArrayBacked(RequestDump.class,
                    (hooks) -> (type, data) ->
                    {
                        for (RequestDump hook : hooks)
                        {
                            ActionResult result = hook.onRequestDump(type, data);

                            if (result != ActionResult.PASS)
                            {
                                return result;
                            }
                        }

                        return ActionResult.PASS;
                    }
                );

                ActionResult onRequestDump(DumpManager.Type type, List<?> data);
            }
        }

        public static class Modules
        {
            public interface ModuleInitialized
            {
                Event<ModuleInitialized> EVENT = EventFactory.createArrayBacked(ModuleInitialized.class,
                        (hooks) -> (module) ->
                        {
                            for (ModuleInitialized hook : hooks)
                            {
                                hook.onModuleInitialized(module);
                            }
                        }
                );

                void onModuleInitialized(final WModule module);
            }
        }

        public static class Supervisor
        {
            public static class Update
            {
                public interface UpdateEntities
                {

                }
            }
        }
    }

    public static class Client
    {
        public static class Network
        {
            public interface AchievementUpdateS2CEvent
            {

            }

            public interface EntitySpawnS2C
            {
                Event<EntitySpawnS2C> EVENT = EventFactory.createArrayBacked(EntitySpawnS2C.class,
                    (hooks) -> (packet) ->
                    {
                        for (EntitySpawnS2C hook : hooks)
                        {
                            ActionResult result = hook.onEntitySpawn(packet);

                            if (result != ActionResult.PASS)
                            {
                                return result;
                            }
                        }

                        return ActionResult.PASS;
                    }
                );

                ActionResult onEntitySpawn(EntitySpawnS2CPacket packet);
            }

            public interface OpenScreenS2C
            {
                Event<OpenScreenS2C> EVENT = EventFactory.createArrayBacked(OpenScreenS2C.class,
                    (hooks) -> (packet) ->
                    {
                        for (OpenScreenS2C hook : hooks)
                        {
                            ActionResult result = hook.onOpenScreen(packet);

                            if (result != ActionResult.PASS)
                            {
                                return result;
                            }
                        }

                        return ActionResult.PASS;
                    }
                );

                ActionResult onOpenScreen(OpenScreenS2CPacket packet);
            }

            public interface ExpOrbSpawnS2C
            {
                Event<ExpOrbSpawnS2C> EVENT = EventFactory.createArrayBacked(ExpOrbSpawnS2C.class,
                        (hooks) -> (packet) ->
                        {
                            for (ExpOrbSpawnS2C hook : hooks)
                            {
                                ActionResult result = hook.onExpOrbSpawn(packet);

                                if (result != ActionResult.PASS)
                                {
                                    return result;
                                }
                            }

                            return ActionResult.PASS;
                        }
                );

                ActionResult onExpOrbSpawn(ExperienceOrbSpawnS2CPacket packet);
            }
        }

        public static class Render
        {
            public interface ItemRenderStart
            {
                Event<ItemRenderStart> EVENT = EventFactory.createArrayBacked(ItemRenderStart.class,
                    (hooks) -> (stack, transformation, light, overlay, matrices, consumer) ->
                    {
                        for (ItemRenderStart hook : hooks)
                        {
                            ActionResult result = hook.onStartItemRender(stack, transformation, light, overlay, matrices, consumer);

                            if (result != ActionResult.PASS)
                            {
                                return result;
                            }
                        }

                        return ActionResult.PASS;
                    }
                );

                ActionResult onStartItemRender(ItemStack stack, ModelTransformation.Mode transformationType, int light,
                                               int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumer);
            }

            public interface WorldRenderStart
            {
                Event<WorldRenderStart> EVENT = EventFactory.createArrayBacked(WorldRenderStart.class,
                    (hooks) -> (matrixes, delta, limitTime, renderOutline, camera, renderer, lmap, matrix4f) ->
                    {
                        for (WorldRenderStart hook : hooks)
                        {
                            ActionResult result = hook.onStartWorldRender(matrixes, delta, limitTime, renderOutline, camera,
                                    renderer, lmap, matrix4f);

                            if (result != ActionResult.PASS)
                            {
                                return result;
                            }
                        }

                        return ActionResult.PASS;
                    }
                );

                ActionResult onStartWorldRender(MatrixStack matrices, float delta, long limit, boolean renderOutline,
                                                Camera camera, GameRenderer renderer, LightmapTextureManager textureManager,
                                                Matrix4f matrix4f);
            }
        }

    }
}
