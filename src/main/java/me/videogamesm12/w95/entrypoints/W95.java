package me.videogamesm12.w95.entrypoints;

import me.videogamesm12.w95.config.Configuration;
import me.videogamesm12.w95.managers.CommandManager;
import me.videogamesm12.w95.managers.ConfigurationManager;
import me.videogamesm12.w95.managers.DumpManager;
import me.videogamesm12.w95.managers.ModuleManager;
import me.videogamesm12.w95.supervisor.Supervisor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public class W95 implements ClientModInitializer, ClientLifecycleEvents.ClientStopping
{
    public static W95 instance;
    //
    public CommandManager commandManager;
    public ConfigurationManager configManager;
    public ModuleManager moduleManager;
    public DumpManager dumpManager;
    public Supervisor supervisor;

    public W95()
    {
        System.setProperty("java.awt.headless", "false");
    }

    @Override
    public void onInitializeClient()
    {
        instance = this;
        //
        this.supervisor = new Supervisor();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigurationManager();
        this.moduleManager = new ModuleManager();
        this.dumpManager = new DumpManager();
        //
        this.supervisor.start();
        this.supervisor.openRecoveryWindow(); // REMOVE THIS ON LAUNCH
        //
        ClientLifecycleEvents.CLIENT_STOPPING.register(this);
    }

    @Override
    public void onClientStopping(MinecraftClient client)
    {
        this.configManager.save();
    }

    /*---=== CONFIGURATION ===---*/

    /**
     * Shortcut method to get the configuration from the Configuration Manager.
     * --
     * @return Configuration
     */
    public Configuration getConfig()
    {
        return configManager.getConfig();
    }

    public static File getW95Folder()
    {
        File folder = new File(MinecraftClient.getInstance().runDirectory, "W95");

        if (!folder.isDirectory())
        {
            folder.mkdir();
        }

        return folder;
    }

    public static String getModVersion()
    {
        if (FabricLoader.getInstance().isModLoaded("w95"))
        {
            return FabricLoader.getInstance().getModContainer("w95").get().getMetadata().getVersion().getFriendlyString();
        }
        else
        {
            return "unknown";
        }
    }
}
