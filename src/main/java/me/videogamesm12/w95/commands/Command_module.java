package me.videogamesm12.w95.commands;

import com.mojang.brigadier.context.CommandContext;
import me.videogamesm12.w95.annotations.CommandInformation;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.managers.ModuleManager;
import me.videogamesm12.w95.templates.WCommand;
import me.videogamesm12.w95.templates.WModule;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
@CommandInformation(description = "xd", usage = "xd")
public class Command_module extends WCommand
{
    private final MutableText OFF = new TranslatableText("w95.general.toggle.disabled").formatted(Formatting.RED);
    private final MutableText ON = new TranslatableText("w95.general.toggle.enabled").formatted(Formatting.GREEN);

    @Override
    public boolean runCommand(WCommand command, CommandContext<FabricClientCommandSource> context, String[] args)
    {
        ModuleManager manager = W95.instance.moduleManager;

        if (args.length == 0)
        {
            Collection<WModule> modules = manager.getModules();
            List<WModule> enabled = manager.getEnabled();

            header(context.getSource(), new TranslatableText("w95.commands.modules.list.header"));
            msg(context.getSource(), new TranslatableText("w95.commands.modules.list",
                new LiteralText(String.valueOf(enabled.size())).formatted(Formatting.WHITE)));

            for (WModule module : modules)
            {
                msg(context.getSource(), new TranslatableText("w95.commands.modules.template",
                    new LiteralText(module.getName()).formatted(Formatting.WHITE),
                    new LiteralText(module.getDescription())));
            }

            return true;
        }
        else if (args.length == 1)
        {
            if (manager.isRegistered(args[0].toLowerCase()))
            {
                WModule module = manager.getModule(args[0].toLowerCase());

                MutableText text = module.isEnabled() ? OFF : ON;

                msg(context.getSource(), new TranslatableText("w95.commands.modules.toggled",
                    new LiteralText(module.getName()).formatted(Formatting.WHITE),
                    text));

                module.toggle();
            }
            else
            {
                error(context.getSource(), new TranslatableText("w95.commands.modules.not_found",
                    new LiteralText("w95.commands.modules.not_found").formatted(Formatting.DARK_RED)));
            }

            return true;
        }

        return false;
    }
}
