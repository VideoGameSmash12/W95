package me.videogamesm12.w95.mixins.patches;

import net.minecraft.nbt.NbtTagSizeTracker;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * <b></b>
 */
@Mixin(PacketByteBuf.class)
public class PacketNBTOverloadPatch
{
    @ModifyArg(
        method = "readNbt*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/PacketByteBuf;readNbt(Lnet/minecraft/nbt/NbtTagSizeTracker;)Lnet/minecraft/nbt/NbtCompound;")
    )
    public NbtTagSizeTracker patchNBT(NbtTagSizeTracker original)
    {
        return NbtTagSizeTracker.EMPTY;
    }
}
