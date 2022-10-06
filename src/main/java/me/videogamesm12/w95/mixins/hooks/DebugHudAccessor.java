package me.videogamesm12.w95.mixins.hooks;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(DebugHud.class)
public interface DebugHudAccessor
{
    @Invoker
    public List<String> invokeGetLeftText();
}
