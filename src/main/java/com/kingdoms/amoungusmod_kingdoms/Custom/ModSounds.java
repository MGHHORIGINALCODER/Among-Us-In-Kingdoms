package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    // 1. Define the Sound
    public static final Identifier CALL_MEETING_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "call_meeting");
    public static SoundEvent CALL_MEETING = SoundEvent.of(CALL_MEETING_ID);

    public static final Identifier REPORT_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "report_sound");
    public static SoundEvent REPORT_SOUND = SoundEvent.of(REPORT_SOUND_ID);

    public static final Identifier MURDER_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "murder");
    public static SoundEvent MURDER_SOUND = SoundEvent.of(MURDER_ID);

    public static final Identifier START_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "start_sound");
    public static SoundEvent START_SOUND = SoundEvent.of(START_SOUND_ID);

    public static final Identifier VOTE_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "vote_sound");
    public static SoundEvent VOTE_SOUND = SoundEvent.of(VOTE_SOUND_ID);

    public static final Identifier EJECTED_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "ejected");
    public static SoundEvent EJECTED_SOUND = SoundEvent.of(EJECTED_SOUND_ID);

    public static final Identifier TASK_COMPLETED_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "task_complete");
    public static SoundEvent TASK_COMPLETED_SOUND = SoundEvent.of(TASK_COMPLETED_SOUND_ID);

    public static final Identifier VICTORY_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "victory");
    public static SoundEvent VICTORY_SOUND = SoundEvent.of(VICTORY_SOUND_ID);

    public static final Identifier DEFEAT_SOUND_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "defeat");
    public static SoundEvent DEFEAT_SOUND = SoundEvent.of(DEFEAT_SOUND_ID);

    public static final Identifier CHANGE_SETTING_DOWN_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "changesettingsown");
    public static SoundEvent CHANGE_SETTING_DOWN = SoundEvent.of(CHANGE_SETTING_DOWN_ID);
    public static final Identifier CHANGE_SETTING_UP_ID = Identifier.of(Amoungusmod_kingdoms.MOD_ID, "changesettingup");
    public static SoundEvent CHANGE_SETTING_UP = SoundEvent.of(CHANGE_SETTING_UP_ID);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Amoungusmod_kingdoms.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Sounds for " + Amoungusmod_kingdoms.MOD_ID);
    }
}