package me.videogamesm12.w95.commands;

import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.annotations.CommandInformation;
import me.videogamesm12.w95.managers.DumpManager;
import me.videogamesm12.w95.mixins.hooks.ClientWorldAccessor;
import me.videogamesm12.w95.templates.WCommand;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

@CommandInformation(description = "w95.commands.entities.description", usage = "w95.commands.entities.usage")
public class Command_dump extends WCommand
{
    @Override
    public boolean runCommand(WCommand command, CommandContext<FabricClientCommandSource> context, String[] args)
    {
        if (args.length == 0)
        {
            return false;
        }

        DumpManager.Type type;

        try
        {
            type = DumpManager.Type.valueOf(args[0].toUpperCase());
        }
        catch (Exception ex)
        {
            error(context.getSource(), new TranslatableText("w95.commands.dump.error.invalid_type"));
            return false;
        }

        switch (type)
        {
            case TEXTURES:
            {

                break;
            }

            case ENTITIES:
            {
                ClientWorld world = MinecraftClient.getInstance().world;

                if (world == null)
                {
                    error(context.getSource(), new TranslatableText("w95.errors.not_in_game"));
                    return true;
                }

                List<Entity> list;
                //if (args.length == 1)
                //{
                /*    */list = new ArrayList<>(((ClientWorldAccessor) world).getRegularEntities().values());
                //}
                //else
                //{
                //    String[] ids = args[2].split(",");
                //}
                msg(context.getSource(), new LiteralText("REQUESTING NOW"));
                Events.W95.Dumper.RequestDump.EVENT.invoker().onRequestDump(type, list);
            }
        }

        return true;
    }
}
