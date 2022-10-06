package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.Events;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererInjector
{
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At("HEAD"), cancellable = true)
    public void injectItem(ItemStack stack, ModelTransformation.Mode transformationType, int light, int overlay,
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci)
    {
        ActionResult event = Events.Client.Render.ItemRenderStart.EVENT.invoker().onStartItemRender(stack, transformationType,
                light, overlay, matrices, vertexConsumers);

        if (event == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }
}
