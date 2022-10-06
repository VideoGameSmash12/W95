package me.videogamesm12.w95.commands;

import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.annotations.CommandInformation;
import me.videogamesm12.w95.events.W95Events;
import me.videogamesm12.w95.mixins.hooks.ClientWorldAccessor;
import me.videogamesm12.w95.templates.WCommand;
import me.videogamesm12.w95.utilities.Formatter;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.*;

@SuppressWarnings("unused")
@CommandInformation(description = "w95.commands.entities.description", usage = "w95.commands.entities.usage")
public class Command_entities extends WCommand
{
    @Override
    public boolean runCommand(WCommand command, CommandContext<FabricClientCommandSource> context, String[] args)
    {
        if (MinecraftClient.getInstance().world == null)
        {
            error(context.getSource(), new TranslatableText("w95.errors.not_in_game"));
        }
        else
        {
            if (args.length == 0)
            {
                return false;
            }

            ClientWorld world = MinecraftClient.getInstance().world;

            // Primary first, shortcuts second
            switch (args[0].toLowerCase())
            {
                case "dump":
                case "save":
                case "export":
                {
                    if (args.length < 2)
                    {
                        return false;
                    }

                    int id;

                    try
                    {
                        id = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException ex)
                    {
                        error(context.getSource(), new TranslatableText("w95.errors.invalid_number", args[1]));
                        break;
                    }

                    if (world.getEntityById(id) == null)
                    {
                        error(context.getSource(), new TranslatableText("w95.commands.entities.query.no_entity_found"));
                    }
                    else
                    {
                        msg(context.getSource(), new TranslatableText("w95.commands.entities.dump.started"));
                        W95Events.DUMP_ENTITIES_CALLED.invoker().onDumpEntitiesRequestCall(
                                Collections.singletonList(world.getEntityById(id)));
                    }

                    break;
                }

                case "dumpall":
                case "dall":
                {
                    List<Entity> entities = new ArrayList<>(((ClientWorldAccessor) world).getRegularEntities().values());

                    msg(context.getSource(), new TranslatableText("w95.commands.entities.dumpall.started"));
                    W95Events.DUMP_ENTITIES_CALLED.invoker().onDumpEntitiesRequestCall(entities);
                    break;
                }

                case "info":
                {
                    header(context.getSource(), new TranslatableText("w95.commands.entityinfo.response.header").formatted(Formatting.BLUE));
                    //
                    int blockEntities = world.blockEntities.size();
                    int tickingBlockEntities = world.tickingBlockEntities.size();
                    int entities = ((ClientWorldAccessor) world).getRegularEntities().size();
                    //
                    msg(context.getSource(), new TranslatableText("w95.commands.entityinfo.response.entities",
                            new LiteralText(Integer.toString(entities)).formatted(Formatting.WHITE)));
                    msg(context.getSource(), new TranslatableText("w95.commands.entityinfo.response.tile_entities",
                            new LiteralText(Integer.toString(blockEntities)).formatted(Formatting.WHITE)));
                    msg(context.getSource(), new TranslatableText("w95.commands.entityinfo.response.tile_entities_ticking",
                            new LiteralText(Integer.toString(tickingBlockEntities)).formatted(Formatting.WHITE)));

                    break;
                }

                case "list":
                {
                    header(context.getSource(), new TranslatableText("w95.commands.entities.list.header").formatted(Formatting.BLUE));

                    for (Entity entity : ((ClientWorldAccessor) world).getRegularEntities().values())
                    {
                        msg(context.getSource(), new LiteralText(Formatter.entityToString(entity)));
                    }
                    break;
                }

                case "query":
                case "get":
                {
                    if (args.length < 2)
                    {
                        return false;
                    }

                    int id;

                    try
                    {
                        id = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException ex)
                    {
                        error(context.getSource(), new TranslatableText("w95.errors.invalid_number", args[1]));
                        break;
                    }

                    if (world.getEntityById(id) == null)
                    {
                        error(context.getSource(), new TranslatableText("w95.commands.entities.query.no_entity_found"));
                    }
                    else
                    {
                        header(context.getSource(), new TranslatableText("w95.commands.entities.query.header").formatted(Formatting.BLUE));
                        msg(context.getSource(), new LiteralText(Formatter.entityToString(world.getEntityById(id))));
                    }

                    break;
                }

                default:
                {
                    return false;
                }
            }
        }

        return true;
    }
}
