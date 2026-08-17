package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.Death_Block;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.DissapearAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class reportfunction extends Item {
    public reportfunction(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(context.getBlockPos());
        PlayerEntity user = context.getPlayer();

        // 1. CHECK THE BLOCK FIRST (Moved outside the entity loop)
        if (blockState.isOf(ModBlocks.DEATH_BLOCK) && Customstorage.CAN_KILL) {
            world.setBlockState(context.getBlockPos(), Blocks.AIR.getDefaultState());

            for(PlayerEntity p : world.getPlayers()){
                if(p.isSpectator()){
                    MutableText message = Text.literal(p.getName().getString()+" Is Dead");
                    world.getServer().getPlayerManager().broadcast(message, false);
                }
            }


            // Only run logic on the logical server
            if (!world.isClient) {

                // Debug logs (Now they will print properly!)
                Identifier blockId = Registries.BLOCK.getId(blockState.getBlock());
                System.out.println("blockId: " + blockId + " | namespace: " + blockId.getNamespace() + " | path: " + blockId.getPath());

                ItemStack[] tasks = {
                        new ItemStack(ModItems.TASK1),
                        new ItemStack(ModItems.TASK2),
                        new ItemStack(ModItems.TASK3),
                        new ItemStack(ModItems.TASK4),
                        new ItemStack(ModItems.TASK5),
                        new ItemStack(ModItems.TASK6),
                        new ItemStack(ModItems.TASK7),
                        new ItemStack(ModItems.TASK8),
                        new ItemStack(ModItems.TASK9),
                        new ItemStack(ModItems.TASK10)
                };

                // OPTIONAL: If you still need to find nearby entities when reporting
                Box searchBox = new Box(context.getBlockPos()).expand(1.0);
                List<Entity> entities = world.getOtherEntities(user, searchBox);
                System.out.println("Entities nearby during report: " + entities.size());

                Customstorage.CAN_KILL = false;
                Random random = new Random();

                for (PlayerEntity player : world.getPlayers()) {
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "tp " + player.getName().getString() + " 27 70 118"
                    );

                    int random1 = random.nextInt(tasks.length);
                    player.giveItemStack(tasks[random1].copy());
                    player.giveItemStack(new ItemStack(ModItems.VOTE));
                    if (player.isCreativeLevelTwoOp()) {
                        player.giveItemStack(new ItemStack(ModItems.VOTE_CHECKER));
                    }
                }

                // Play sound and broadcast the chat message
                if (user != null) {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(),
                            ModSounds.REPORT_SOUND, SoundCategory.PLAYERS, 1.0f, 1.0f);

                    if(blockState.getBlock() instanceof Death_Block deathBlock){
                        MutableText message = Text.literal(user.getName().getString() + " reported "+deathBlock.Owner.getName().getString());
                        world.getServer().getPlayerManager().broadcast(message, false);
                    }else{
                        MutableText message = Text.literal(user.getName().getString() + " reported "+ "Failer to Obtain Player Data");
                        world.getServer().getPlayerManager().broadcast(message, false);
                    }


                }


                DissapearAfterCall.DisableSelf(world,context.getBlockPos(),150, ModBlocks.DEATH_BLOCK);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
