package com.kingdoms.amoungusmod_kingdoms.Custom.Tasks;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.AutoRounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ImpCheck;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class task6 extends Item {
    public task6(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = player.getActiveHand();
        ItemStack stack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState block = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(!world.isClient()){

            boolean CompletedTask= block.isOf(Blocks.EMERALD_BLOCK);

            if(CompletedTask && !ImpCheck.I(player)){
                ItemStack itemStack = player.getStackInHand(hand);
                itemStack.decrement(1);
                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ModSounds.TASK_COMPLETED_SOUND,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                );
                Customstorage.TASKS_DONE+=1;
                MutableText message = Text.literal(Customstorage.TASKS_DONE+"/"+Customstorage.MAX_TASKS+" Tasks Completed");
                world.getServer().getPlayerManager().broadcast(message, false);
                if(Customstorage.TASKS_DONE>=Customstorage.MAX_TASKS){
                    for(PlayerEntity play : world.getPlayers()){
                        play.getInventory().clear();
                        world.getServer().getCommandManager().executeWithPrefix(
                                world.getServer().getCommandSource(),
                                "tp " + play.getName().getString() + " 27 70 118"
                        );
                        if(play instanceof ServerPlayerEntity sp){
                            sp.changeGameMode(GameMode.SURVIVAL);
                        }



                        play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));

                    }
                    world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), ModSounds.VICTORY_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);

                    world.getPlayers().forEach((player1) ->
                            Customstorage.SendTitleAndSubtitle((ServerPlayerEntity)player1, "Crewmates Win!", "", 2, 3, 2, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_AQUA, Formatting.DARK_RED)
                    );

                    AutoRounds.RoundEnded(world, player, player.getActiveHand());

                }
            }




        }
        return ActionResult.SUCCESS;




    }
}
