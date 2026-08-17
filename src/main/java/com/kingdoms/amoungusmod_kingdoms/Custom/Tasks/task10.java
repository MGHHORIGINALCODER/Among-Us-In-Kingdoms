package com.kingdoms.amoungusmod_kingdoms.Custom.Tasks;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ImpCheck;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class task10 extends Item {
    public task10(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, player, entity, hand);
        World world = player.getEntityWorld();

        if(!world.isClient()){

            boolean CompletedTask= entity instanceof CowEntity;


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
                }
            }




        }
        return ActionResult.SUCCESS;




    }
}
