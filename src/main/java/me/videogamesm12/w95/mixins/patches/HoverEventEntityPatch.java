package me.videogamesm12.w95.mixins.patches;

import net.minecraft.text.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.UUID;

/**
 * <b>HoverEventEntityPatch</b>
 * <p>Patches an exploit that causes clients to crash trying to process malicious text.</p>
 */
@Mixin(HoverEvent.EntityContent.class)
public class HoverEventEntityPatch
{
    private static final String REPLACEMENT_UUID = "DEADBEEF-DEAD-DEAD-DEAD-DEADDEADDEAD";

    @ModifyArg(method = "parse(Lnet/minecraft/text/Text;)Lnet/minecraft/text/HoverEvent$EntityContent;",
            at = @At(value = "INVOKE", target = "Ljava/util/UUID;fromString(Ljava/lang/String;)Ljava/util/UUID;"))
    private static String injectParseText(String uuid)
    {
        try
        {
            return UUID.fromString(uuid).toString();
        }
        catch (Exception ex)
        {
            return REPLACEMENT_UUID;
        }
    }

    @ModifyArg(method = "parse(Lcom/google/gson/JsonElement;)Lnet/minecraft/text/HoverEvent$EntityContent;",
            at = @At(value = "INVOKE", target = "Ljava/util/UUID;fromString(Ljava/lang/String;)Ljava/util/UUID;"))
    private static String injectParseJson(String uuid)
    {
        try
        {
            return UUID.fromString(uuid).toString();
        }
        catch (Exception ex)
        {
            return REPLACEMENT_UUID;
        }
    }
}
