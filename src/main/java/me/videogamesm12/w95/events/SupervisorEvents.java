package me.videogamesm12.w95.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

import java.util.List;

public class SupervisorEvents
{
    public static Event<SupervisorEvents.UpdateEntities> UPDATE_ENTITIES = EventFactory.createArrayBacked(
        SupervisorEvents.UpdateEntities.class,
        (hooks) -> (entities) ->
        {
            for (SupervisorEvents.UpdateEntities hook : hooks)
            {
                ActionResult result = hook.onUpdateEntities(entities);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.SUCCESS;
        }
    );

    public static Event<SupervisorEvents.UpdateBlockEntities> UPDATE_BLOCK_ENTITIES = EventFactory.createArrayBacked(
        SupervisorEvents.UpdateBlockEntities.class,
        (hooks) -> (entities) ->
        {
            for (SupervisorEvents.UpdateBlockEntities hook : hooks)
            {
                ActionResult result = hook.onUpdateBlockEntities(entities);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.SUCCESS;
        }
    );

    public static Event<SupervisorEvents.UpdateDebugInfo> UPDATE_DEBUG_INFO = EventFactory.createArrayBacked(
        SupervisorEvents.UpdateDebugInfo.class,
        (hooks) -> (left) ->
        {
            for (SupervisorEvents.UpdateDebugInfo hook : hooks)
            {
                ActionResult result = hook.onUpdateDebugInfo(left);

                if (result != ActionResult.PASS)
                {
                    return result;
                }
            }

            return ActionResult.SUCCESS;
        }
    );

    public interface UpdateEntities
    {
        ActionResult onUpdateEntities(List<Entity> entities);
    }

    public interface UpdateBlockEntities
    {
        ActionResult onUpdateBlockEntities(List<BlockEntity> entities);
    }

    public interface UpdateDebugInfo
    {
        ActionResult onUpdateDebugInfo(List<String> data);
    }
}
