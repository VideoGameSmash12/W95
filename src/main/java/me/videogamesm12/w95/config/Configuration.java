package me.videogamesm12.w95.config;

import java.util.ArrayList;
import java.util.List;

public class Configuration
{
    public Modules modules = new Modules();

    public Supervisor supervisor = new Supervisor();

    public static class Modules
    {
        public List<String> enabled = new ArrayList<>();
    }

    public static class Supervisor
    {
        public boolean disableWorldRendering = false;

        public boolean disableEntityRendering = false;

        public boolean disableItemRendering = false;

        public boolean disableLighting = false;

        public boolean disableCustomNames = false;

        public boolean disableBlockEntities = false;

        public boolean disableRendering = false;

        //--

        public boolean useDarkMode = true;
    }
}
