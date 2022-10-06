package me.videogamesm12.w95.templates;

import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.w95.annotations.CommandInformation;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public abstract class WCommand
{
    private final CommandInformation cmdInfo;
    //
    private final String description;
    private final String usage;

    public WCommand()
    {
        this.cmdInfo = this.getClass().getAnnotation(CommandInformation.class);
        //
        this.description = cmdInfo.description();
        this.usage = cmdInfo.usage();
    }

    public abstract boolean runCommand(WCommand command, CommandContext<FabricClientCommandSource> context, String[] args);

    public final String getName()
    {
        return this.getClass().getSimpleName().replace("Command_", "").toLowerCase();
    }

    public final String getDescription()
    {
        return this.description;
    }

    public final String getUsage()
    {
        return this.usage;
    }

    public final void msg(FabricClientCommandSource source, MutableText message)
    {
        msg(source, message, true);
    }

    public final void msg(FabricClientCommandSource source, MutableText message, boolean color)
    {
        if (color)
        {
            message = message.formatted(Formatting.GRAY);
        }
        //
        source.sendFeedback(message);
    }

    public final void error(FabricClientCommandSource source, MutableText message)
    {
        msg(source, message.formatted(Formatting.RED), false);
    }

    public final void header(FabricClientCommandSource source, MutableText message)
    {
        msg(source, new TranslatableText("w95.templates.header", message.formatted(Formatting.BLUE)).formatted(Formatting.DARK_GRAY, Formatting.BOLD), false);
    }
}
