package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ToolsAndBits;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.murdered;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

import static com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.murdered.DEAD;

public class AutoRounds extends Block {
    public AutoRounds(Settings settings) {
        super(settings);
    }
    public static final List<ItemStack> Votes = new ArrayList<>();

    public static void RoundEnded(World world,PlayerEntity player,Hand hand){
        Customstorage.VENTERS.clear();
        Customstorage.IMPOSTERS.clear();
        Customstorage.PLAYERS_IN_ROUNDS.clear();


        Customstorage.ROUND_STARTED=false;
        ServerWorld targetWorld = world.getServer().getWorld(World.OVERWORLD);
        world.getPlayers().forEach(player1->{
            if(player1 instanceof ServerPlayerEntity player1server){
                player1server.changeGameMode(GameMode.SURVIVAL);
            }

            player1.playSound(ModSounds.LOBBY_MUSIC,SoundCategory.MASTER,0.4f,1);


        });
        world.getServer().getCommandManager().executeWithPrefix(
                world.getServer().getCommandSource(),
                "tp @a "+Customstorage.NormalSpawn.getX()+" "+Customstorage.NormalSpawn.getY()+" "+Customstorage.NormalSpawn.getZ()
        );
        Customstorage.RunCountDown(35,()->{
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "stopsound @a"
            );
            Customstorage.PLAYERS_IN_ROUNDS.addAll(world.getPlayers());
            Customstorage.ROUND_STARTED=true;
            Customstorage.VENTERS.clear();
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

            ItemStack itemStack = player.getStackInHand(hand);

            if(!world.isClient){
                Customstorage.IMPOSTERS.clear();
                Customstorage.MAX_TASKS= (world.getPlayers().size() - Customstorage.NUMBER_OF_IMPOSTERS) * Customstorage.TASKS_PER_PLAYER;
                Customstorage.TASKS_DONE= 0;
                Customstorage.CAN_KILL=true;
                LockBlock.On_=true;

                UpdateAfterCall.DisableSelf(player.getWorld(),player.getBlockPos(),500,ModBlocks.LOCKER_BLOCK);



                DissapearAfterCall.DisableSelf(world,player.getBlockPos(),150, ModBlocks.DEATH_BLOCK);
                murdered.DEAD=0;
                itemStack.decrement(1);
                for (PlayerEntity play : world.getPlayers()) {
                    play.getInventory().clear();
                }

                for(PlayerEntity play : world.getPlayers()){
                    //play.playSound(ModSounds.CALL_MEETING);





                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "tp " + play.getName().getString() + " "+Customstorage.MeetingSpawn.getX()+" "+Customstorage.MeetingSpawn.getY()+" "+Customstorage.MeetingSpawn.getZ()
                    );

                    play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));

                    for (int i = 0; i < Customstorage.TASKS_PER_PLAYER; i++) {
                        int random1 = new Random().nextInt(0, tasks.length);

                        world.getServer().getCommandManager().executeWithPrefix(
                                world.getServer().getCommandSource(),
                                "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(tasks[random1].getItem())
                        );
                    }




                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.REPORT)
                    );
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.EMERGENCY_MEETING)+" "+Customstorage.MEETING_USE
                    );
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(Items.REDSTONE)+" 64"
                    );
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "team join tagVis "+play.getName().getString()
                    );
                    if(play instanceof ServerPlayerEntity serverPlayerEntity){
                        Customstorage.SendTitleAndSubtitle(serverPlayerEntity,"Crewmate","Complete Tasks and Find the Imposter",5,5,5,Formatting.WHITE,Formatting.ITALIC,Formatting.AQUA,Formatting.BOLD);
                    }

                    if(play instanceof ServerPlayerEntity sp){
                        sp.changeGameMode(GameMode.SURVIVAL);
                    }



                }

                List<? extends PlayerEntity> allPlayers = new ArrayList<>(world.getPlayers());
                Collections.shuffle(allPlayers);

                int impostersToAssign = Math.min(Customstorage.NUMBER_OF_IMPOSTERS,allPlayers.size());
                Customstorage.ALIVE_IMPOSTERS=Customstorage.NUMBER_OF_IMPOSTERS;


                for (int i = 0; i < impostersToAssign; i++) {
                    PlayerEntity player_imposter = allPlayers.get(i);
                    if(Customstorage.IMPOSTERS.contains(player_imposter)){
                        impostersToAssign+=1;
                    }else {
                        Customstorage.IMPOSTERS.add(player_imposter);
                    }
                }


                int engineersToAssign = Math.min(Customstorage.NUMBER_OF_ENGINEER,allPlayers.size());

                if(!(Customstorage.NUMBER_OF_ENGINEER==0)){
                for (int i = 0; i < engineersToAssign; i++) {
                    PlayerEntity player_engineers = allPlayers.get(i);
                    if(Customstorage.VENTERS.contains(player_engineers) || Customstorage.IMPOSTERS.contains(player_engineers)){
                        engineersToAssign+=1;
                    }else {
                        Customstorage.VENTERS.add(player_engineers);
                    }
                }
                }

                int scientistToAssign = Math.min(Customstorage.NUMBER_OF_SCIENTIST,allPlayers.size());

                if(!(Customstorage.NUMBER_OF_SCIENTIST==0)){
                    for (int i = 0; i < scientistToAssign; i++) {
                        PlayerEntity player_engineers = allPlayers.get(i);
                        if(Customstorage.SCIENTIST.contains(player_engineers) || Customstorage.IMPOSTERS.contains(player_engineers) || Customstorage.VENTERS.contains(player_engineers)){
                            scientistToAssign+=1;
                        }else {
                            Customstorage.SCIENTIST.add(player_engineers);
                        }
                    }
                }
                for(PlayerEntity ip : Customstorage.IMPOSTERS){
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + ip.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.SAB)
                    );
                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + ip.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.MURDERED)
                    );

                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "team join imposters "+ip.getName().getString()
                    );
                    Customstorage.VENTERS.add(ip);
                    if(ip instanceof ServerPlayerEntity serverPlayerEntity){
                        Customstorage.SendTitleAndSubtitle(serverPlayerEntity,"Imposter","Kill all crewmates and be hidden",5,5,5,Formatting.WHITE,Formatting.ITALIC,Formatting.RED,Formatting.BOLD);
                    }

                    ip.sendMessage(Text.literal("ROLE: Imposter : >\nTEAM:").styled(style -> style.withColor(Formatting.RED)),false);
                    for(PlayerEntity ipe : Customstorage.IMPOSTERS){
                        ip.sendMessage(Text.literal(ipe.getName().getString()).styled(style -> style.withColor(Formatting.RED)),false);
                    }

                }
                for (PlayerEntity venter:Customstorage.VENTERS){
                    if(!Customstorage.IMPOSTERS.contains(venter) && venter instanceof ServerPlayerEntity VentServer){
                        Customstorage.SendTitleAndSubtitle(VentServer,"Engineer","You have the ability of Venting",5,5,5,Formatting.AQUA,Formatting.ITALIC,Formatting.AQUA,Formatting.BOLD);
                    }
                }
                for (PlayerEntity scientist:Customstorage.SCIENTIST){
                    if(!Customstorage.IMPOSTERS.contains(scientist) && scientist instanceof ServerPlayerEntity scientistServer){
                        scientist.giveItemStack(new ItemStack(ModItems.ALIVE_VIEW));

                        Customstorage.SendTitleAndSubtitle(scientistServer,"Scientist","You have the ability of Seeings Peoples health",5,5,5,Formatting.AQUA,Formatting.ITALIC,Formatting.AQUA,Formatting.BOLD);
                    }

                }
                world.getPlayers().forEach(player1 -> {
                    world.playSound(
                            null,
                            player1.getX(),
                            player1.getY(),
                            player1.getZ(),
                            ModSounds.START_SOUND,
                            SoundCategory.PLAYERS,
                            1.0f,
                            1.0f
                    );
                });









            }

        },"Intermission: ");
    }


    public static void VoteEnd(World world, BlockPos blockPos, PlayerEntity user){

        DissapearAfterCall.DisableSelf(world,blockPos,150, ModBlocks.DEATH_BLOCK);
        LockBlock.On_=true;

        UpdateAfterCall.DisableSelf(user.getWorld(),user.getBlockPos(),500,ModBlocks.LOCKER_BLOCK);




        Customstorage.CAN_KILL=true;




        Map<String, Long> voteCounts = Votes.stream()
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

        long voteSkips = Votes.stream()
                .filter(item -> item.isOf(ModItems.VOTE))
                .count();

            Optional<Map.Entry<String, Long>> mostVoted = voteCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue());


            if (mostVoted.isEmpty() || voteSkips > mostVoted.get().getValue()) {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        ModSounds.EJECTED_SOUND,
                        SoundCategory.PLAYERS,
                        5.0f,
                        1.0f
                );
                ToolsAndBits.broadcastTypewriterActionbar(user.getServer(),"No one was ejected (Skipped)", Formatting.WHITE);
            } else {
                long maxVotes = mostVoted.get().getValue();
                List<String> tied = voteCounts.entrySet().stream()
                        .filter(e -> e.getValue() == maxVotes)
                        .map(Map.Entry::getKey)
                        .toList();

                if (tied.size() > 1 || voteSkips == maxVotes) {

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
                    ToolsAndBits.broadcastTypewriterActionbar(user.getServer(),"No one was ejected (Tie)",Formatting.WHITE);
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
                                Customstorage.ALIVE_IMPOSTERS-=1;
                                ToolsAndBits.broadcastTypewriterActionbar(player.getServer(),ejected+" Was an Imposter. "+Customstorage.ALIVE_IMPOSTERS+" Imposters Remain", Formatting.WHITE);


                                if(Customstorage.ALIVE_IMPOSTERS<=0) {
                                    Customstorage.IMPOSTERS.clear();
                                    for (PlayerEntity play : world.getPlayers()) {
                                        world.getServer().getCommandManager().executeWithPrefix(
                                                world.getServer().getCommandSource(),
                                                "tp " + player.getName().toString() + " " + Customstorage.NormalSpawn.getX() + " " + Customstorage.NormalSpawn.getY() + " " + Customstorage.NormalSpawn.getZ()
                                        );
                                        play.getInventory().clear();



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
                                    world.getPlayers().forEach(player1 -> {
                                        Customstorage.SendTitleAndSubtitle((ServerPlayerEntity) player1,"Crewmates Win!","",2,3,2,Formatting.AQUA,Formatting.BOLD,Formatting.AQUA,Formatting.BOLD);
                                    });
                                    RoundEnded(world,player,player.getActiveHand());

                                }
                            } else {
                                DEAD+=1;
                                if(DEAD+1>=world.getPlayers().size()-Customstorage.NUMBER_OF_IMPOSTERS){
                                    for(PlayerEntity play : world.getPlayers()){
                                        play.getInventory().clear();



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

                                    world.getPlayers().forEach(player1 -> {
                                        Customstorage.SendTitleAndSubtitle((ServerPlayerEntity) player1,"Imposters Win!","",2,3,2,Formatting.RED,Formatting.BOLD,Formatting.RED,Formatting.BOLD);
                                    });
                                    RoundEnded(world,player,player.getActiveHand());
                                }else{
                                    ToolsAndBits.broadcastTypewriterActionbar(player.getServer(),ejected+" Was an the Imposter",Formatting.WHITE);
                                }

                            }

                            if (player instanceof ServerPlayerEntity serverPlayerEntity) {

                                world.getServer().getCommandManager().executeWithPrefix(
                                        world.getServer().getCommandSource(),
                                        "tp " + ejected + " " + Customstorage.NormalSpawn.getX() + " " + Customstorage.NormalSpawn.getY() + " " + Customstorage.NormalSpawn.getZ()
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
            for (ItemStack v : Votes) {
                NbtCompound nbt = v.getNbt();
                System.out.println("Vote item: " + v.getItem() + " | nbt: " + nbt);
            }
            System.out.println("Votes size: " + Votes.size() + " | voteCounts: " + voteCounts + " | skips: " + voteSkips);
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "clear @a minecraft:player_head"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "clear @a amoungusmod_kingdoms:vote_button"
            );
            Votes.clear();
        }

    public static void VotingBegins(World world, BlockPos blockPos, PlayerEntity user){
        for(PlayerEntity player : world.getPlayers()){
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "give " + player.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.VOTE)
            );
        }
        Customstorage.RunCountDown(15,()->{
            VoteEnd(world, blockPos,user);

        },"Voting: ");

    }


    public static void CallMeeting(PlayerEntity player, World world, Hand hand, boolean PlaySound) {
        DissapearAfterCall.DisableSelf(world, player.getBlockPos(), 150, ModBlocks.DEATH_BLOCK);
        LockBlock.On_ = false;

        UpdateAfterCall.DisableSelf(player.getWorld(), player.getBlockPos(), 500, ModBlocks.LOCKER_BLOCK);


        for (PlayerEntity p : world.getPlayers()) {
            if (p.isSpectator()) {
                MutableText message = Text.literal(p.getName().getString() + " Is Dead");
                world.getServer().getPlayerManager().broadcast(message, false);
            }
        }

        DissapearAfterCall.DisableSelf(world, player.getBlockPos(), 150, ModBlocks.DEATH_BLOCK);
        Customstorage.CAN_KILL = false;
        for (PlayerEntity play : world.getPlayers()) {
            //play.playSound(ModSounds.CALL_MEETING);
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "tp " + play.getName().getString() + " " + Customstorage.MeetingSpawn.getX() + " " + Customstorage.MeetingSpawn.getY() + " " + Customstorage.MeetingSpawn.getZ()
            );



        }
        if (PlaySound) {
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.CALL_MEETING,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );
        }else{
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.REPORT_SOUND,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );
        }
        Customstorage.RunCountDown(30,()->{
            VotingBegins(world,player.getBlockPos(),player);

        },"Discussing Time: ");



    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack=player.getStackInHand(hand);
        if (!world.isClient() && player.getStackInHand(hand).isOf(ModItems.EMERGENCY_MEETING)) {

            itemStack.decrement(1);

            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.getItemCooldownManager().set(itemStack.getItem(), Customstorage.MEETING_COOLDOWN*20);
            }


            CallMeeting(player,world,hand,true);

            return ActionResult.SUCCESS;
        }
        if(itemStack.isOf(Items.PLAYER_HEAD) || itemStack.isOf(ModItems.VOTE)){
            Votes.add(itemStack);
            itemStack.decrement(1);
        }

        return ActionResult.PASS;

    }


}
