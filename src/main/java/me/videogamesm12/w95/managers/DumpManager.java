package me.videogamesm12.w95.managers;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.events.W95Events;
import me.videogamesm12.w95.utilities.Formatter;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DumpManager extends Thread implements W95Events.DumpEntitiesRequestCall, Events.W95.Dumper.RequestDump
{
    private Logger logger = LogManager.getLogger("W95:DumpManager");

    public DumpManager()
    {
        start();
        W95Events.DUMP_ENTITIES_CALLED.register(this);
        Events.W95.Dumper.RequestDump.EVENT.register(this);
    }

    /**
     * Singular form of dumpEntities
     * @param entity Entity
     */
    public void dumpEntity(Entity entity)
    {
        dumpEntities(Collections.singletonList(entity));
    }

    public void dumpEntities(List<Entity> list)
    {
        // This is where all of the entities will go
        NbtCompound baseCompound = new NbtCompound();

        // This will let people know what the hell it is
        NbtString dump_note = NbtString.of("W95 Entity Dump");
        baseCompound.put("Type", dump_note);

        // Throws all of the data into the baseCompound.
        for (Entity entity : list)
        {
            NbtCompound data = entity.writeNbt(new NbtCompound());
            baseCompound.put(entity.getEntityName() + " (" + entity.getEntityId() + ")", data);
        }

        String fileName = UUID.randomUUID() + " - " + baseCompound.hashCode() + ".nbt";
        File file = new File(getDumpsFolder(), fileName);

        try
        {
            NbtIo.writeCompressed(baseCompound, file);
        }
        catch (IOException ex)
        {
            W95Events.DUMP_ENTITIES_FAILED.invoker().onDumpEntitiesFailed(ex);
            ex.printStackTrace();
            return;
        }

        W95Events.DUMP_ENTITIES_SUCCESS.invoker().onDumpEntitiesSuccessful(file);
    }

    @Override
    public ActionResult onDumpEntitiesRequestCall(List<Entity> entities)
    {
        dumpEntities(entities);

        return ActionResult.PASS;
    }

    public static File getDumpsFolder()
    {
        File folder = new File(W95.getW95Folder(), "dumps");

        if (!folder.isDirectory())
        {
            folder.mkdir();
        }

        return folder;
    }

    @Override
    public ActionResult onRequestDump(Type type, List<?> data)
    {
        // This is used in every dump;
        NbtCompound baseCompound = new NbtCompound();

        switch (type)
        {
            /*case COMMANDS:
            {
                break;
            }*/
            case TEXTURES:
            {

            }

            case ENTITIES:
            {
                for (Entity entity : (List<Entity>) data)
                {
                    NbtCompound entityData = entity.writeNbt(new NbtCompound());
                    baseCompound.put(entity.getEntityName() + " (" + entity.getEntityId() + ")", entityData);
                }
                break;
            }
        }
        baseCompound.put("type", NbtString.of(type.typeString));

        String fileName = String.format("%s %s - %s", type.typeString, baseCompound.hashCode(), Formatter.formattedDateNow());
        File file = new File(getDumpsFolder(), fileName);

        try
        {
            NbtIo.writeCompressed(baseCompound, file);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        logger.info("Successfully saved dump as " + fileName);

        return ActionResult.PASS;
    }

    public enum Type
    {
        /*COMMANDS(String.class, "Command dump"),*/
        ENTITIES(Entity.class, "Entity dump"),
        TEXTURES(Identifier.class, "Texture dump");

        public final Class<?> clazz;
        public final String typeString;

        Type(Class<?> type, String typeString)
        {
            this.clazz = type;
            this.typeString = typeString;
        }
    }
}
