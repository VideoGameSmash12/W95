package me.videogamesm12.w95.mixins.hooks;

import me.videogamesm12.w95.Events;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * <b>ClientPlayNetworkHandlerInjector</b>
 * <p>Hooks into the client's network handler to add </p>
 */
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerInjector
{
    @Inject(method = "onGameMessage", at = @At("HEAD"))
    public void onChatReceived(GameMessageS2CPacket packet, CallbackInfo ci)
    {
        ClientEvents.CHAT_RECEIVE.invoker().onChatReceive(packet);
    }

    @Inject(method = "onLightUpdate", at = @At("HEAD"), cancellable = true)
    public void startOnLightUpdate(LightUpdateS2CPacket packet, CallbackInfo ci)
    {
        if (W95.instance.getConfig().supervisor.disableLighting)
        {
            ci.cancel();
        }
    }

    /**
     * <p>Hook for onEntitySpawn that modules can use. Cancels when an event listener returns FAIL.</p>
     * @param packet EntitySpawnS2CPacket
     * @param ci CallbackInfo
     */
    @Inject(method = "onEntitySpawn", at = @At("HEAD"), cancellable = true)
    public void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci)
    {
        ActionResult result = Events.Client.Network.EntitySpawnS2C.EVENT.invoker().onEntitySpawn(packet);
        if (result == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }

    /**
     * <p>Hook for onOpenScreen that modules can use. Cancels when an event listener returns FAIL.</p>
     * @param packet OpenScreenS2CPacket
     * @param ci CallbackInfo
     */
    @Inject(method = "onOpenScreen", at = @At("HEAD"), cancellable = true)
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci)
    {
        ActionResult result = Events.Client.Network.OpenScreenS2C.EVENT.invoker().onOpenScreen(packet);
        if (result == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }

    @Inject(method = "onExperienceOrbSpawn", at = @At("HEAD"), cancellable = true)
    public void onExperienceOrbSpawn(ExperienceOrbSpawnS2CPacket packet, CallbackInfo ci)
    {
        ActionResult result = Events.Client.Network.ExpOrbSpawnS2C.EVENT.invoker().onExpOrbSpawn(packet);
        if (result == ActionResult.FAIL)
        {
            ci.cancel();
        }
    }

    /*
    public void onMobSpawn(MobSpawnS2CPacket packet)
    {

    }

    public void onScoreboardObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket packet)
    {

    }

    public void onPaintingSpawn(PaintingSpawnS2CPacket packet)
    {

    }

    public void onPlayerSpawn(PlayerSpawnS2CPacket packet)
    {

    }

    public void onEntityAnimation(EntityAnimationS2CPacket packet)
    {

    }

    public void onStatistics(StatisticsS2CPacket packet)
    {

    }

    public void onUnlockRecipes(UnlockRecipesS2CPacket packet)
    {

    }

    public void onBlockDestroyProgress(BlockBreakingProgressS2CPacket packet)
    {

    }

    public void onSignEditorOpen(SignEditorOpenS2CPacket packet)
    {

    }

    public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet)
    {

    }

    public void onBlockEvent(BlockEventS2CPacket packet)
    {

    }

    public void onBlockUpdate(BlockUpdateS2CPacket packet)
    {

    }

    public void onGameMessage(GameMessageS2CPacket packet)
    {

    }

    public void onChunkDeltaUpdate(ChunkDeltaUpdateS2CPacket packet)
    {

    }

    public void onMapUpdate(MapUpdateS2CPacket packet)
    {

    }

    public void onConfirmScreenAction(ConfirmScreenActionS2CPacket packet)
    {

    }

    public void onCloseScreen(CloseScreenS2CPacket packet)
    {

    }

    public void onInventory(InventoryS2CPacket packet)
    {

    }

    public void onOpenHorseScreen(OpenHorseScreenS2CPacket packet)
    {

    }

    public void onScreenHandlerPropertyUpdate(ScreenHandlerPropertyUpdateS2CPacket packet)
    {

    }

    public void onScreenHandlerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet)
    {

    }

    public void onCustomPayload(CustomPayloadS2CPacket packet)
    {

    }

    public void onDisconnect(DisconnectS2CPacket packet)
    {

    }

    public void onEntityStatus(EntityStatusS2CPacket packet)
    {

    }

    public void onEntityAttach(EntityAttachS2CPacket packet)
    {

    }

    public void onEntityPassengersSet(EntityPassengersSetS2CPacket packet)
    {

    }

    public void onExplosion(ExplosionS2CPacket packet)
    {

    }

    public void onGameStateChange(GameStateChangeS2CPacket packet)
    {

    }

    public void onKeepAlive(KeepAliveS2CPacket packet)
    {

    }

    public void onChunkData(ChunkDataS2CPacket packet)
    {

    }

    public void onUnloadChunk(UnloadChunkS2CPacket packet)
    {

    }

    public void onWorldEvent(WorldEventS2CPacket packet)
    {

    }

    public void onGameJoin(GameJoinS2CPacket packet)
    {

    }

    public void onEntityUpdate(EntityS2CPacket packet)
    {

    }

    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet)
    {

    }

    public void onParticle(ParticleS2CPacket packet)
    {

    }

    public void onPlayerAbilities(PlayerAbilitiesS2CPacket packet)
    {

    }

    public void onPlayerList(PlayerListS2CPacket packet)
    {

    }

    public void onEntitiesDestroy(EntitiesDestroyS2CPacket packet)
    {

    }

    public void onRemoveEntityEffect(RemoveEntityStatusEffectS2CPacket packet)
    {

    }

    public void onPlayerRespawn(PlayerRespawnS2CPacket packet)
    {

    }

    public void onEntitySetHeadYaw(EntitySetHeadYawS2CPacket packet)
    {

    }

    public void onHeldItemChange(UpdateSelectedSlotS2CPacket packet)
    {

    }

    public void onScoreboardDisplay(ScoreboardDisplayS2CPacket packet)
    {

    }

    public void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet)
    {

    }

    public void onVelocityUpdate(EntityVelocityUpdateS2CPacket packet)
    {

    }

    public void onEquipmentUpdate(EntityEquipmentUpdateS2CPacket packet)
    {

    }

    public void onExperienceBarUpdate(ExperienceBarUpdateS2CPacket packet)
    {

    }

    public void onHealthUpdate(HealthUpdateS2CPacket packet)
    {

    }

    public void onTeam(TeamS2CPacket packet)
    {

    }

    public void onScoreboardPlayerUpdate(ScoreboardPlayerUpdateS2CPacket packet)
    {

    }

    public void onPlayerSpawnPosition(PlayerSpawnPositionS2CPacket packet)
    {

    }

    public void onWorldTimeUpdate(WorldTimeUpdateS2CPacket packet)
    {

    }

    public void onPlaySound(PlaySoundS2CPacket packet)
    {

    }

    public void onPlaySoundFromEntity(PlaySoundFromEntityS2CPacket packet)
    {

    }

    public void onPlaySoundId(PlaySoundIdS2CPacket packet)
    {

    }

    public void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet)
    {

    }

    public void onEntityPosition(EntityPositionS2CPacket packet)
    {

    }

    public void onEntityAttributes(EntityAttributesS2CPacket packet)
    {

    }

    public void onEntityPotionEffect(EntityStatusEffectS2CPacket packet)
    {

    }

    public void onSynchronizeTags(SynchronizeTagsS2CPacket packet)
    {

    }

    public void onCombatEvent(CombatEventS2CPacket packet)
    {

    }

    public void onDifficulty(DifficultyS2CPacket packet)
    {

    }

    public void onSetCameraEntity(SetCameraEntityS2CPacket packet)
    {

    }

    public void onWorldBorder(WorldBorderS2CPacket packet)
    {

    }

    public void onTitle(TitleS2CPacket packet)
    {

    }

    public void onPlayerListHeader(PlayerListHeaderS2CPacket packet)
    {

    }

    public void onResourcePackSend(ResourcePackSendS2CPacket packet)
    {

    }

    public void onBossBar(BossBarS2CPacket packet)
    {

    }

    public void onCooldownUpdate(CooldownUpdateS2CPacket packet)
    {

    }

    public void onVehicleMove(VehicleMoveS2CPacket packet)
    {

    }

    public void onAdvancements(AdvancementUpdateS2CPacket packet)
    {

    }

    public void onSelectAdvancementTab(SelectAdvancementTabS2CPacket packet)
    {

    }

    public void onCraftFailedResponse(CraftFailedResponseS2CPacket packet)
    {

    }

    public void onCommandTree(CommandTreeS2CPacket packet)
    {

    }

    public void onStopSound(StopSoundS2CPacket packet)
    {

    }

    public void onCommandSuggestions(CommandSuggestionsS2CPacket packet)
    {

    }

    public void onSynchronizeRecipes(SynchronizeRecipesS2CPacket packet)
    {

    }

    public void onLookAt(LookAtS2CPacket packet)
    {

    }

    public void onTagQuery(NbtQueryResponseS2CPacket packet)
    {

    }

    public void onLightUpdate(LightUpdateS2CPacket packet)
    {

    }

    public void onOpenWrittenBook(OpenWrittenBookS2CPacket packet)
    {

    }

    public void onSetTradeOffers(SetTradeOffersS2CPacket packet)
    {

    }

    public void onChunkLoadDistance(ChunkLoadDistanceS2CPacket packet)
    {

    }

    public void onChunkRenderDistanceCenter(ChunkRenderDistanceCenterS2CPacket packet)
    {

    }

    public void onPlayerActionResponse(PlayerActionResponseS2CPacket packet)
    {

    }*/
}
