package me.videogamesm12.w95.commands;

import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.w95.annotations.CommandInformation;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.templates.WCommand;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

@SuppressWarnings("unused")
@CommandInformation(description = "w95.commands.supervisor.description", usage = "w95.commands.supervisor.usage")
public class Command_supervisor extends WCommand
{
    @Override
    public boolean runCommand(WCommand command, CommandContext<FabricClientCommandSource> context, String[] args)
    {
        if (args.length == 0)
        {
            return false;
        }

        switch (args[0].toLowerCase())
        {
            case "show":
            case "showwindow":
            case "openwindow":
            case "open":
            {
                W95.instance.supervisor.openRecoveryWindow();
                break;
            }

            default:
            {
                return false;
            }
        }

        return true;
    }
}
