package me.videogamesm12.w95.managers;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.videogamesm12.w95.templates.WCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * <b>CommandManager</b>
 * <p>Allows for Bukkit-style commands to be created, registered, and run.</p>
 */
public class CommandManager implements Command<FabricClientCommandSource>, SuggestionProvider<FabricClientCommandSource>
{
    public Logger logger = LogManager.getLogger("W95:CommandManager");
    Map<String, WCommand> commandMap = new HashMap<>();

    public CommandManager()
    {
        registerCommands();
    }

    @Override
    public final int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException
    {
        String[] args = StringArgumentType.getString(context, "arg").split(" ");

        if (commandMap.containsKey(args[0].toLowerCase()))
        {
            WCommand cmd = commandMap.get(args[0].toLowerCase());
            try
            {
                List<String> processedArgs = new ArrayList<>(Arrays.asList(args));
                processedArgs.remove(0);

                boolean success = cmd.runCommand(cmd, context, processedArgs.toArray(new String[0]));
                if (!success)
                {
                    cmd.header(context.getSource(), new TranslatableText("w95.commands.general.help.header"));
                    //
                    cmd.msg(context.getSource(), new TranslatableText("w95.commands.general.help.description",
                            new TranslatableText(cmd.getDescription()).formatted(Formatting.WHITE)));
                    //
                    cmd.msg(context.getSource(), new TranslatableText("w95.commands.general.help.usage",
                            new TranslatableText(cmd.getUsage()).formatted(Formatting.WHITE)));
                }
            }
            catch (Exception | Error ex)
            {
                cmd.error(context.getSource(), new TranslatableText("w95.errors.command_error"));
                ex.printStackTrace();
            }

            return 1;
        }

        SimpleCommandExceptionType type = CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand();
        throw type.create();
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context,
         SuggestionsBuilder builder)
    {
        return CommandSource.suggestMatching(new ArrayList<>(commandMap.keySet()), builder);
    }

    public void registerCommands()
    {
        logger.info("Registering commands");

        Reflections cmds = new Reflections("me.videogamesm12.w95.commands");
        Set<Class<? extends WCommand>> classes = cmds.getSubTypesOf(WCommand.class);
        for (Class<? extends WCommand> clazz : classes)
        {
            try
            {
                logger.debug("Registering command "
                        + clazz.getSimpleName().replace("Command_", ""));
                String id = clazz.getSimpleName().replace("Command_", "").toLowerCase();
                commandMap.put(id, clazz.getDeclaredConstructor().newInstance());
            }
            catch (Exception | Error ex)
            {
                logger.error("An error occurred whilst attempting to register command "
                        + clazz.getSimpleName().replace("Command_", ""));
                logger.error(ex);
            }
        }

        logger.info("Registering root-level command...");

        // Registers the root-level command
        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("w95")
                .then(ClientCommandManager.argument("arg", StringArgumentType.greedyString())
                        .executes(this)
                        .suggests(this)));

        logger.info("Commands registered");
    }
}
