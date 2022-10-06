package me.videogamesm12.w95.mixins.hooks;

import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface HudAccessor
{
    @Accessor
    public DebugHud getDebugHud();
}
