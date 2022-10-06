package me.videogamesm12.w95.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.videogamesm12.w95.config.Configuration;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.templates.WModule;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigurationManager
{
    private Configuration config;
    private File configFile;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Logger logger = LogManager.getLogger("W95:ConfigurationManager");

    public ConfigurationManager()
    {
        load();
    }

    public void load()
    {
        configFile = new File(W95.getW95Folder(), "config.json");

        if (!configFile.exists())
        {
            config = new Configuration();
        }
        else
        {
            try
            {
                config = gson.fromJson(new FileReader(configFile), Configuration.class);
            }
            catch (Exception ex)
            {
                logger.error("Failed to read W95's configuration file!");
                logger.error(ex);
                //
                config = new Configuration();
            }
        }
    }

    public void save()
    {
        if (W95.instance.moduleManager != null)
        {
            for (WModule module : W95.instance.moduleManager.getEnabled())
            {
                config.modules.enabled.add(module.getName().toLowerCase());
            }
        }

        try
        {
            FileWriter writer = new FileWriter(configFile);
            writer.write(gson.toJson(config));
            writer.close();
        }
        catch (Exception ex)
        {
            logger.error("Failed to write W95's configuration file!");
            logger.error(ex);
        }
    }

    public Configuration getConfig()
    {
        return config;
    }
}
