package me.videogamesm12.w95.modules;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.templates.WModule;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.ActionResult;

@SuppressWarnings("unused")
public class AntiLockup extends WModule implements Events.Client.Network.OpenScreenS2C
{
    public AntiLockup()
    {
        super("AntiLockup", "Resists the effects of /lockup, an evil feature in the TotalFreedomMod.");
        //
        Events.Client.Network.OpenScreenS2C.EVENT.register(this);
    }

    @Override
    public ActionResult onOpenScreen(OpenScreenS2CPacket packet)
    {
        if (packet.getScreenHandlerType() != ScreenHandlerType.GENERIC_9X4)
        {
            return ActionResult.PASS;
        }

        return isEnabled() ? ActionResult.FAIL : ActionResult.PASS;
    }
}
