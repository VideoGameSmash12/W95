package me.videogamesm12.w95.events;

import me.videogamesm12.w95.templates.WModule;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

import java.io.File;
import java.util.List;

/**
 * <b>W95Events</b>
 * <p>Client events specific to W95.</p>
 * @author Video
 */
public class W95Events
{
    public static Event<DumpEntitiesRequestCall> DUMP_ENTITIES_CALLED = EventFactory.createArrayBacked(DumpEntitiesRequestCall.class,
            (hooks) -> (entities) ->
            {
                for (DumpEntitiesRequestCall listener : hooks)
                {
                    ActionResult result = listener.onDumpEntitiesRequestCall(entities);

                    if (result != ActionResult.PASS)
                    {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    public static Event<DumpEntitiesSuccessful> DUMP_ENTITIES_SUCCESS = EventFactory.createArrayBacked(DumpEntitiesSuccessful.class,
            (hooks) -> (location) ->
            {
                for (DumpEntitiesSuccessful listener : hooks)
                {
                    ActionResult result = listener.onDumpEntitiesSuccessful(location);

                    if (result != ActionResult.PASS)
                    {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    public static Event<DumpEntitiesFailed> DUMP_ENTITIES_FAILED = EventFactory.createArrayBacked(DumpEntitiesFailed.class,
            (hooks) -> (exception) ->
            {
                for (DumpEntitiesFailed listener : hooks)
                {
                    ActionResult result = listener.onDumpEntitiesFailed(exception);
                }

                return ActionResult.PASS;
            }
    );

    public interface DumpEntitiesRequestCall
    {
        ActionResult onDumpEntitiesRequestCall(List<Entity> entities);
    }

    public interface DumpEntitiesFailed
    {
        ActionResult onDumpEntitiesFailed(Exception ex);
    }

    public interface DumpEntitiesSuccessful
    {
        ActionResult onDumpEntitiesSuccessful(File location);
    }
}
