package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.DissapearAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.murdered.DEAD;

public class vote_checker extends Item {
    public vote_checker(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        ItemStack stack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState block = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (block.isOf(Blocks.BARREL)) {
            if (!world.isClient) {
                DissapearAfterCall.DisableSelf(world,context.getBlockPos(),150, ModBlocks.DEATH_BLOCK);
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "clear @a minecraft:player_head"
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "clear @a amoungusmod_kingdoms:vote_button"
                );

                Customstorage.CAN_KILL=true;
                stack.decrement(1);

                if (blockEntity instanceof Inventory inventory) {

                    Map<String, Long> voteCounts = IntStream.range(0, inventory.size())
                            .mapToObj(inventory::getStack)
                            .filter(item -> item.isOf(Items.PLAYER_HEAD))
                            .map(item -> {
                                NbtCompound nbt = item.getNbt();
                                if (nbt == null || !nbt.contains("SkullOwner", NbtElement.COMPOUND_TYPE)) {
                                    return null;
                                }
                                GameProfile profile = NbtHelper.toGameProfile(nbt.getCompound("SkullOwner"));
                                return profile != null ? profile.getName() : null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

                    long voteSkips = IntStream.range(0, inventory.size())
                            .mapToObj(inventory::getStack)
                            .filter(item -> item.isOf(ModItems.VOTE))
                            .count();

                    Optional<Map.Entry<String, Long>> mostVoted = voteCounts.entrySet().stream()
                            .max(Map.Entry.comparingByValue());

                    // Check if skips win first
                    if (mostVoted.isEmpty() || voteSkips > mostVoted.get().getValue()) {
                        MutableText message = Text.literal("No one was ejected (skipped)");
                        world.getServer().getPlayerManager().broadcast(message, false);
                    } else {
                        long maxVotes = mostVoted.get().getValue();
                        List<String> tied = voteCounts.entrySet().stream()
                                .filter(e -> e.getValue() == maxVotes)
                                .map(Map.Entry::getKey)
                                .toList();

                        if (tied.size() > 1 || voteSkips == maxVotes) {
                            // Tie between players or tie with skips
                            MutableText message = Text.literal("No one was ejected (Tie)");
                            world.getServer().getPlayerManager().broadcast(message, false);
                        } else {
                            String ejected = tied.get(0);

                            for (PlayerEntity player : world.getPlayers()) {
                                if (player.getName().getString().equals(ejected)) {


                                    boolean isImposter = false;
                                    for (ItemStack item : player.getInventory().main) {
                                        if (item.isOf(ModItems.MURDERED)) {
                                            isImposter = true;
                                            break;
                                        }
                                    }

                                    if (isImposter) {
                                        MutableText message = Text.literal(ejected+" Was the Imposter");
                                        world.getServer().getPlayerManager().broadcast(message, false);
                                        Customstorage.IMPOSTERS.clear();
                                        for(PlayerEntity play : world.getPlayers()){
                                            play.getInventory().clear();
                                            world.getServer().getCommandManager().executeWithPrefix(
                                                    world.getServer().getCommandSource(),
                                                    "tp " + play.getName().getString() + " 27 70 118"
                                            );


                                            play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));

                                        }
                                        world.playSound(
                                                null,
                                                player.getX(),
                                                player.getY(),
                                                player.getZ(),
                                                ModSounds.VICTORY_SOUND,
                                                SoundCategory.PLAYERS,
                                                1.0f,
                                                1.0f
                                        );
                                        MutableText message2 = Text.literal("Crewmate Wins!");
                                        world.getServer().getPlayerManager().broadcast(message2, false);
                                    } else {
                                        DEAD+=1;
                                        if(DEAD>=world.getPlayers().size()-1){
                                            for(PlayerEntity play : world.getPlayers()){
                                                play.getInventory().clear();
                                                world.getServer().getCommandManager().executeWithPrefix(
                                                        world.getServer().getCommandSource(),
                                                        "tp " + play.getName().getString() + " 27 70 118"
                                                );


                                                play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));
                                                if(play instanceof ServerPlayerEntity sp){
                                                    sp.changeGameMode(GameMode.SURVIVAL);
                                                }

                                            }
                                            world.playSound(
                                                    null,
                                                    player.getX(),
                                                    player.getY(),
                                                    player.getZ(),
                                                    ModSounds.DEFEAT_SOUND,
                                                    SoundCategory.PLAYERS,
                                                    1.0f,
                                                    1.0f
                                            );
                                            MutableText message2 = Text.literal("Imposters Win!");
                                            world.getServer().getPlayerManager().broadcast(message2, false);
                                        }else{
                                            MutableText message = Text.literal(ejected+" Was not the Imposter");
                                            world.getServer().getPlayerManager().broadcast(message, false);
                                        }

                                    }

                                    if (player instanceof ServerPlayerEntity serverPlayerEntity) {

                                        world.getServer().getCommandManager().executeWithPrefix(
                                                world.getServer().getCommandSource(),
                                                "tp " + ejected + "0 0 0"
                                        );
                                        player.kill();
                                        serverPlayerEntity.changeGameMode(GameMode.SPECTATOR);
                                        world.playSound(
                                                null,
                                                user.getX(),
                                                user.getY(),
                                                user.getZ(),
                                                ModSounds.EJECTED_SOUND,
                                                SoundCategory.PLAYERS,
                                                1.0f,
                                                1.0f
                                        );
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    inventory.clear();
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}