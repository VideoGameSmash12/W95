package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(InGameHud.class)
public class HudInjector
{
    @Inject(method = "addChatMessage", at = @At("RETURN"))
    public void injectAddMessage(MessageType type, Text message, UUID sender, CallbackInfo ci)
    {
        ClientEvents.CHAT_ADD_MESSAGE.invoker().onAddToHUD(type, message, sender, ci);
    }
}
