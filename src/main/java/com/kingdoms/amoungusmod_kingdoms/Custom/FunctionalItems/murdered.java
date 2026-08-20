package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.AutoRounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.Death_Block;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class murdered extends Item {
    public static int DEAD = 0;

    public murdered(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, player, entity, hand);
        World world = player.getWorld();

        // Server side verification and cooldown restrictions
        if (!world.isClient() && !player.getItemCooldownManager().isCoolingDown(stack.getItem()) && Customstorage.CAN_KILL && entity instanceof ServerPlayerEntity player1) {
            // Cancel internal friendly-fire if targeted player is also an Imposter
            if (ImpCheck.I(player1)) {
                return ActionResult.PASS;
            }

            // Execute kill logic
            player1.discard();
            player1.changeGameMode(GameMode.SPECTATOR);
            ++DEAD;

            // Place a death block marker one unit higher than kill coordinate position
            world.setBlockState(new BlockPos(player.getBlockPos().getX(), player.getBlockPos().getY() + 1, player.getBlockPos().getZ()), ModBlocks.DEATH_BLOCK.getDefaultState());
            BlockState state = world.getBlockState(player.getBlockPos());
            Block blockInstance = state.getBlock();
            if (blockInstance instanceof Death_Block deathBlock) {
                deathBlock.Owner = player;
            }

            // Put weapon item on cooldown configuration limits
            player.getItemCooldownManager().set(player.getStackInHand(hand).getItem(), Customstorage.KILL_COOLDOWN * 20);
            world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), ModSounds.MURDER_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);

            // Win Condition Check: If all crewmates are eliminated, Imposters secure immediate victory
            if (DEAD + 1 >= world.getPlayers().size() - Customstorage.NUMBER_OF_IMPOSTERS) {
                for (PlayerEntity play : world.getPlayers()) {
                    play.getInventory().clear();
                    play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));
                    if (play instanceof ServerPlayerEntity sp) {
                        sp.changeGameMode(GameMode.ADVENTURE);
                    }
                }

                // Global lobby teleport loop execution
                world.getPlayers().forEach((player2) -> {
                    CommandManager commandManager = world.getServer().getCommandManager();
                    ServerCommandSource commandSource = world.getServer().getCommandSource();
                    String playerName = player2.getName().getString(); // Converted toString() safely to getString() for clean translation
                    commandManager.executeWithPrefix(commandSource, "tp " + playerName + " " + Customstorage.NormalSpawn.getX() + " " + Customstorage.NormalSpawn.getY() + " " + Customstorage.NormalSpawn.getZ());
                });

                world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), ModSounds.DEFEAT_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
                world.getPlayers().forEach((player21) -> Customstorage.SendTitleAndSubtitle((ServerPlayerEntity)player21, "Imposters Win!", "", 2, 3, 2, Formatting.RED, Formatting.DARK_RED, Formatting.RED, Formatting.DARK_RED));
                AutoRounds.RoundEnded(world, player, player.getActiveHand());
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Fallback execution pattern when right clicking open air space context
        return new TypedActionResult<>(ActionResult.PASS, user.getInventory().getMainHandStack());
    }
}
