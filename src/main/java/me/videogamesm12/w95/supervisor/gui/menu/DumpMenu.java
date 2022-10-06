package me.videogamesm12.w95.supervisor.gui.menu;

import me.videogamesm12.w95.events.W95Events;
import me.videogamesm12.w95.mixins.hooks.ClientWorldAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>DumpMenu</b>
 * <p>Allows the user to dump various bits of information stored in memory.</p>
 * --
 */
public class DumpMenu extends JMenu
{
    public JMenuItem dumpAllEntities = new JMenuItem("Dump All Entities");

    public DumpMenu()
    {
        setText("Dump");
        //--
        dumpAllEntities.addActionListener(
            (type) ->
            {
                if (MinecraftClient.getInstance().world == null)
                {
                    IllegalStateException ex = new IllegalStateException("You're not currently in a world");
                    W95Events.DUMP_ENTITIES_FAILED.invoker().onDumpEntitiesFailed(ex);
                    return;
                }

                World world = MinecraftClient.getInstance().world;
                List<Entity> entities = new ArrayList<>(((ClientWorldAccessor) world).getRegularEntities().values());

                W95Events.DUMP_ENTITIES_CALLED.invoker().onDumpEntitiesRequestCall(entities);
            }
        );
        add(dumpAllEntities);
    }
}