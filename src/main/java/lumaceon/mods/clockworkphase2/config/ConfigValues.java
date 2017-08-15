package lumaceon.mods.clockworkphase2.config;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;

public class ConfigValues
{
    public static boolean DEVELOPING = false;

    public static boolean RAGNAROK_MODE = true;

    public static boolean SPAWN_COPPER = true;
    public static boolean SPAWN_ZINC = true;
    public static boolean SPAWN_ALUMINUM = true;
    public static boolean SPAWN_MOON_FLOWER_RELIC = true;
    public static boolean SPAWN_UNKNOWN_RELIC = true;

    public static int BASE_TENSION_COST_PER_BLOCK_BREAK = 100;
    public static int BASE_TENSION_COST_PER_ATTACK = 1000;
    public static int MAX_MAINSPRING_TENSION = 1000000;

    public static int HOURGLASS_XP_LEVEL_TIER_1 = 10;
    public static int HOURGLASS_XP_LEVEL_TIER_2 = 30;
    public static int HOURGLASS_XP_LEVEL_TIER_3 = 60;
    public static int HOURGLASS_XP_LEVEL_TIER_4 = 100;
    public static int HOURGLASS_XP_LEVEL_TIER_5 = 150;

    public static int TEMPORAL_HOURGLASS_START = TimeConverter.MINUTE;
    public static int TEMPORAL_HOURGLASS_HARD_CAP = TEMPORAL_HOURGLASS_START * 60;
    public static int ETHEREAL_HOURGLASS_START = TimeConverter.HOUR;
    public static int ETHEREAL_HOURGLASS_HARD_CAP = ETHEREAL_HOURGLASS_START * 24;

    public static int SIMPLE_OVERCLOCKER_TIME = TimeConverter.MINUTE * 5;
}
