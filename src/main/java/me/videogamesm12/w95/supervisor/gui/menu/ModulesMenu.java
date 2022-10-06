package me.videogamesm12.w95.supervisor.gui.menu;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.templates.WModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModulesMenu extends JMenu implements Events.W95.Modules.ModuleInitialized
{
    public static Logger logger = LogManager.getLogger("Supervisor-MM");

    public ModulesMenu()
    {
        setText("Modules");
        Events.W95.Modules.ModuleInitialized.EVENT.register(this);
        //
        for (WModule module : W95.instance.moduleManager.getModules())
        {
            add(new ModuleMenuItem(module));
        }
    }

    @Override
    public void onModuleInitialized(WModule module)
    {
        logger.info("Initialized module");
        add(new ModuleMenuItem(module));
    }

    public static class ModuleMenuItem extends JCheckBoxMenuItem implements ActionListener
    {
        private final WModule module;

        public ModuleMenuItem(final WModule module)
        {
            this.module = module;
            //--
            setText(module.getName());
            setToolTipText(module.getDescription());
            setSelected(module.isEnabled());
            //--
            addActionListener(this);
        }

        @Override
        public boolean isSelected()
        {
            return this.module.isEnabled();
        }

        @Override
        public void setSelected(boolean b)
        {
            if (module != null)
            {
                this.module.setEnabled(b);
            }
            //super.setSelected(b);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            this.module.setEnabled(!isSelected());
        }
    }
}
