package me.videogamesm12.w95.managers;

import java.util.*;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.templates.WModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

public class ModuleManager
{
    public static Logger logger = LogManager.getLogger("W95:ModuleManager");
    //
    private final Map<String, WModule> moduleMap = new HashMap<>();

    public ModuleManager()
    {
        setup();
    }

    public void setup()
    {
        Reflections modules = new Reflections("me.videogamesm12.w95.modules");
        Set<Class<? extends WModule>> classes = modules.getSubTypesOf(WModule.class);
        for (Class<? extends WModule> clazz : classes)
        {
            try
            {
                logger.debug("Registering module " + clazz.getSimpleName());
                //
                register(clazz.getDeclaredConstructor().newInstance());
            }
            catch (Exception | Error ex)
            {
                logger.error("Failed to register module " + clazz.getSimpleName());
                logger.error(ex);
                ex.printStackTrace();
            }
        }
        logger.info(moduleMap.size() + " modules initialized");
    }

    public boolean isRegistered(String name)
    {
        return moduleMap.containsKey(name.toLowerCase());
    }

    public boolean isRegistered(Class<? extends WModule> clazz)
    {
        return moduleMap.containsKey(clazz.getSimpleName().toLowerCase());
    }

    public List<WModule> getEnabled()
    {
        List<WModule> enabled = new ArrayList<>();

        for (WModule module : moduleMap.values())
        {
            if (module.isEnabled())
            {
                enabled.add(module);
            }
        }

        return enabled;
    }

    public Collection<WModule> getModules()
    {
        return moduleMap.values();
    }

    public WModule getModule(String name)
    {
        return moduleMap.get(name.toLowerCase());
    }

    public WModule getModule(Class<? extends WModule> clazz)
    {
        if (!moduleMap.containsKey(clazz.getSimpleName().toLowerCase()))
        {
            throw new IllegalArgumentException("WModule does not exist or is not registered yet");
        }

        return moduleMap.get(clazz.getSimpleName().toLowerCase());
    }

    public void register(WModule module)
    {
        logger.debug("Registering module " + module.getName() + "...");
        //
        if (W95.instance.getConfig().modules.enabled.contains(module.getName()))
        {
            module.setEnabled(true);
        }
        //
        Events.W95.Modules.ModuleInitialized.EVENT.invoker().onModuleInitialized(module);
        //
        moduleMap.put(module.getClass().getSimpleName().toLowerCase(), module);
    }
}
