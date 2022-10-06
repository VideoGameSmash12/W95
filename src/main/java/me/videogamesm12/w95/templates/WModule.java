package me.videogamesm12.w95.templates;

import me.videogamesm12.w95.events.W95Events;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class WModule
{
    private final String name;
    private final String description;
    //
    private boolean enabled;

    public WModule()
    {
        this.name = getClass().getSimpleName();
        this.description = "No description";
    }

    public WModule(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public void setEnabled(boolean bool)
    {
        this.enabled = bool;
    }

    public void toggle()
    {
        setEnabled(!enabled);
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
