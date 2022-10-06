package me.videogamesm12.w95.utilities;

import com.mojang.bridge.game.GameVersion;
import net.minecraft.SharedConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>VersionChecker</b>
 * <p>Checks the current version string of Minecraft against various patterns to determine what version is currently running.</p>
 */
public class VersionChecker
{
    private static Pattern PATTERN_1_16 = Pattern.compile("^(1)\\.(16)(\\.[0-5])?$");

    public static boolean is1_16()
    {
        GameVersion version = SharedConstants.getGameVersion();
        //--
        Matcher matcher = PATTERN_1_16.matcher(version.getName());
        //--
        return matcher.find();
    }

    public static class UnsupportedVersionException extends Exception
    {
        public UnsupportedVersionException()
        {
            super("This version isn't supported");
        }

        public UnsupportedVersionException(String product)
        {
            super("The version of %s you are currently running is not supported");
        }
    }
}
