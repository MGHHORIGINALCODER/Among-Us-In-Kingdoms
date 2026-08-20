package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.sound.SoundEvent;
import net.minecraft.registry.Registries;

public class ModSounds {
    public static final Identifier CALL_MEETING_ID = new Identifier("amoungusmod_kingdoms", "call_meeting");
    public static SoundEvent CALL_MEETING;
    public static final Identifier REPORT_SOUND_ID;
    public static SoundEvent REPORT_SOUND;
    public static final Identifier MURDER_ID;
    public static SoundEvent MURDER_SOUND;
    public static final Identifier START_SOUND_ID;
    public static SoundEvent START_SOUND;
    public static final Identifier VOTE_SOUND_ID;
    public static SoundEvent VOTE_SOUND;
    public static final Identifier EJECTED_SOUND_ID;
    public static SoundEvent EJECTED_SOUND;
    public static final Identifier TASK_COMPLETED_SOUND_ID;
    public static SoundEvent TASK_COMPLETED_SOUND;
    public static final Identifier VICTORY_SOUND_ID;
    public static SoundEvent VICTORY_SOUND;
    public static final Identifier DEFEAT_SOUND_ID;
    public static SoundEvent DEFEAT_SOUND;
    public static final Identifier CHANGE_SETTING_DOWN_ID;
    public static SoundEvent CHANGE_SETTING_DOWN;
    public static final Identifier CHANGE_SETTING_UP_ID;
    public static SoundEvent CHANGE_SETTING_UP;
    public static final Identifier VENT_SOUND_ID;
    public static SoundEvent VENT_SOUND;
    public static final Identifier FIX_SOUND_ID;
    public static SoundEvent FIX_SOUND;
    public static final Identifier COUNTDOWN_SOUND_ID;
    public static SoundEvent COUNTDOWN_SOUND;
    public static final Identifier LOBBY_MUSIC_ID;
    public static SoundEvent LOBBY_MUSIC;

    public ModSounds() {
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("amoungusmod_kingdoms", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Sounds for amoungusmod_kingdoms");
    }

    static {
        CALL_MEETING = SoundEvent.of(CALL_MEETING_ID);
        REPORT_SOUND_ID = new Identifier("amoungusmod_kingdoms", "report_sound");
        REPORT_SOUND = SoundEvent.of(REPORT_SOUND_ID);
        MURDER_ID = new Identifier("amoungusmod_kingdoms", "murder");
        MURDER_SOUND = SoundEvent.of(MURDER_ID);
        START_SOUND_ID = new Identifier("amoungusmod_kingdoms", "start_sound");
        START_SOUND = SoundEvent.of(START_SOUND_ID);
        VOTE_SOUND_ID = new Identifier("amoungusmod_kingdoms", "vote_sound");
        VOTE_SOUND = SoundEvent.of(VOTE_SOUND_ID);
        EJECTED_SOUND_ID = new Identifier("amoungusmod_kingdoms", "ejected");
        EJECTED_SOUND = SoundEvent.of(EJECTED_SOUND_ID);
        TASK_COMPLETED_SOUND_ID = new Identifier("amoungusmod_kingdoms", "task_complete");
        TASK_COMPLETED_SOUND = SoundEvent.of(TASK_COMPLETED_SOUND_ID);
        VICTORY_SOUND_ID = new Identifier("amoungusmod_kingdoms", "victory");
        VICTORY_SOUND = SoundEvent.of(VICTORY_SOUND_ID);
        DEFEAT_SOUND_ID = new Identifier("amoungusmod_kingdoms", "defeat");
        DEFEAT_SOUND = SoundEvent.of(DEFEAT_SOUND_ID);
        CHANGE_SETTING_DOWN_ID = new Identifier("amoungusmod_kingdoms", "changesettingsown");
        CHANGE_SETTING_DOWN = SoundEvent.of(CHANGE_SETTING_DOWN_ID);
        CHANGE_SETTING_UP_ID = new Identifier("amoungusmod_kingdoms", "changesettingup");
        CHANGE_SETTING_UP = SoundEvent.of(CHANGE_SETTING_UP_ID);
        VENT_SOUND_ID = new Identifier("amoungusmod_kingdoms", "vent_sound");
        VENT_SOUND = SoundEvent.of(VENT_SOUND_ID);
        FIX_SOUND_ID = new Identifier("amoungusmod_kingdoms", "fix_sound");
        FIX_SOUND = SoundEvent.of(FIX_SOUND_ID);
        COUNTDOWN_SOUND_ID = new Identifier("amoungusmod_kingdoms", "count_down_sound");
        COUNTDOWN_SOUND = SoundEvent.of(COUNTDOWN_SOUND_ID);
        LOBBY_MUSIC_ID = new Identifier("amoungusmod_kingdoms", "lobby_music");
        LOBBY_MUSIC = SoundEvent.of(LOBBY_MUSIC_ID);
    }
}
