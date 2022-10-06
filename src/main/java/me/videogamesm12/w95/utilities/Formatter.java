package me.videogamesm12.w95.utilities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Formatter
{
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH-mm-ss.SSS");

    public static String formattedDateNow()
    {
        return DATE_FORMAT.format(new Date());
    }

    public static String entityToString(Entity entity)
    {
        return String.format("%s{id=%s, xyz=[%s, %s, %s], type=%s, displayName=%s, uuid=%s}",
                entity.getClass().getSimpleName(),
                entity.getEntityId(),
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                entity.getType().getName().getString(),
                entity.getEntityName(),
                entity.getUuidAsString());
    }

    public static String entityToDisplay(Entity entity)
    {
        return String.format("%s at {%s, %s, %s} - ID: %s - UUID: %s",
            entity.getDisplayName().getString(),
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            entity.getEntityId(),
            entity.getUuidAsString()
        );
    }

    public static String locationToDisplay(double x, double y, double z)
    {
        return String.format("{%s, %s, %s}", x, y, z);
    }

    public static String blockEntityToDisplay(BlockEntity entity)
    {
        NbtCompound compound = entity.toInitialChunkDataNbt();
        String id = compound.getString("id");
        //
        return String.format("%s at {%s, %s, %s} - NBT: %s",
            id,
            entity.getPos().getX(),
            entity.getPos().getY(),
            entity.getPos().getZ(),
            compound
        );
    }

    public static String[] entitiesListToStringArray(List<Entity> entities)
    {
        List<Entity> entityList = new ArrayList<>(entities);
        List<String> entityData = new ArrayList<>();

        for (Entity entity : entityList)
        {
            entityData.add(entityToDisplay(entity));
        }

        Collections.sort(entityData);

        return entityData.toArray(new String[0]);
    }

    public static String[] blockEntitiesListToStringArray(List<BlockEntity> entities)
    {
        List<BlockEntity> entityList = new ArrayList<>(entities);
        List<String> entityData = new ArrayList<>();

        for (BlockEntity entity : entityList)
        {
            entityData.add(blockEntityToDisplay(entity));
        }

        Collections.sort(entityData);

        return entityData.toArray(new String[0]);
    }
}
