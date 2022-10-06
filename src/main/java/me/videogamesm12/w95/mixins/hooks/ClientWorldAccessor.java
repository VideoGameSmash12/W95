package me.videogamesm12.w95.mixins.hooks;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientWorld.class)
public interface ClientWorldAccessor
{
    @Accessor(value = "regularEntities")
    public Int2ObjectMap<Entity> getRegularEntities();
}
