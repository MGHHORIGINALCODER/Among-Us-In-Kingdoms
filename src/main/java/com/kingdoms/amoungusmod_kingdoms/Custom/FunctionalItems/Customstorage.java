package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;

public class Customstorage {
    public static int TASKS_DONE;
    public static int MAX_TASKS;
    public static int TASKS_PER_PLAYER = 3;
    public static int KILL_COOLDOWN = 30;
    public static int SAB_COOLDOWN = 30;
    public static int MEETING_USE = 2;
    public static int MEETING_COOLDOWN = 60;
    public static boolean CAN_KILL = true;
    public static int NUMBER_OF_IMPOSTERS = 1;
    public static int NUMBER_OF_ENGINEER = 0;
    public static int NUMBER_OF_SCIENTIST = 0;
    public static boolean ROUND_STARTED = false;
    public static int ALIVE_IMPOSTERS;
    public static int RAID_VENT = 150;
    public static BlockPos NormalSpawn;
    public static BlockPos MeetingSpawn;
    public static PlayerEntity Owner;
    public static Runnable TimerExacute = () -> {
    };

    public static boolean TIMER_ON = false;
    public static int LENGTH_TIMER = 20;
    public static String State_Timer = "CountDown: ";
    public static final List<PlayerEntity> IMPOSTERS = new ArrayList<>();
    public static final List<PlayerEntity> SCIENTIST = new ArrayList<>();
    public static final List<PlayerEntity> PLAYERS_IN_ROUNDS = new ArrayList<>();
    public static final List<PlayerEntity> VENTERS = new ArrayList<>();

    public Customstorage() {
    }

    public static void SendTitleAndSubtitle(ServerPlayerEntity player, String Title, String Subtitle, int FadeIn, int StayOn, int FadeOut, Formatting ColorSub, Formatting StyleSub, Formatting ColorTitle, Formatting StyleTitle) {
        player.networkHandler.sendPacket(new TitleFadeS2CPacket(FadeIn * 20, StayOn * 20, FadeOut * 20));

        Text SubT = Text.literal(Subtitle).formatted(new Formatting[]{ColorSub, StyleSub});
        player.networkHandler.sendPacket(new SubtitleS2CPacket(SubT));

        Text TitleT = Text.literal(Title).formatted(new Formatting[]{ColorTitle, StyleTitle});
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleT));
    }

    public static void RunCountDown(int Seconds, Runnable RunCode, String StateTimerString) {
        LENGTH_TIMER = Seconds;
        TimerExacute = RunCode;
        TIMER_ON = true;
        State_Timer = StateTimerString;
    }

    public static List<Item> INVIS_ITEMS=new ArrayList<>();
    // Comments are their to help Devs learn as well as me
    public static void AddAllINV_ITEMS(){
        for(Field field : ModItems.class.getFields()){ // Get all Field Arguments (Fields = Variable)
            if(Item.class.isAssignableFrom(field.getType())){ // Checks if its A ITEM
                try{
                    Item item = (Item) field.get(null); // Gets the Item
                    if(item!=null){
                        INVIS_ITEMS.add(item); // Adds to the List
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}
