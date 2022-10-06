package me.videogamesm12.w95.mixins.hooks;

import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TextureManager.class)
public class TextureManagerMixin
{
    @Mixin(TextureManager.class)
    public interface TMAccessor
    {
        @Accessor("textures")
        public Map<Identifier, AbstractTexture> getTextureMap();
    }
}
