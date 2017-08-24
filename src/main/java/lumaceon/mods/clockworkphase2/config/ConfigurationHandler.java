package lumaceon.mods.clockworkphase2.config;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.*;
import java.nio.file.*;

public class ConfigurationHandler
{
    public static void init(File directory)
    {
        String CATEGORY_WORLDGEN = "worldgen";
        String CATEGORY_CLOCKWORK = "clockwork";
        String CATEGORY_TIME = "time";

        directory = new File(directory, Reference.MOD_ID + "/general" + ".cfg");
        try {
            if(!Files.exists(Paths.get(directory.toURI()).getParent()))
                Files.createDirectories(Paths.get(directory.toURI()).getParent());
            if(!Files.exists(Paths.get(directory.toURI())))
                Files.createFile(Paths.get(directory.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration config = new Configuration(directory);

        try
        {
            config.load();
            ConfigValues.DEVELOPING = config.get(Configuration.CATEGORY_GENERAL, "DEV_MODE", ConfigValues.DEVELOPING, "For developers. This should almost always be false, unless you're developing an add-on mod.").getBoolean();

            ConfigValues.SPAWN_WORLD_CRATER = config.get(CATEGORY_WORLDGEN, "SpawnWorldCrater", ConfigValues.SPAWN_WORLD_CRATER, "False to cancel generation of the crater in the center of the world. False also cancels end-game content by extension.").getBoolean();

            ConfigValues.SPAWN_COPPER = config.get(CATEGORY_WORLDGEN, "SpawnCopper", ConfigValues.SPAWN_COPPER, "False to cancel copper worldgen.").getBoolean();
            ConfigValues.SPAWN_ZINC = config.get(CATEGORY_WORLDGEN, "SpawnZinc", ConfigValues.SPAWN_ZINC, "False to cancel zinc worldgen.").getBoolean();
            ConfigValues.SPAWN_ALUMINUM = config.get(CATEGORY_WORLDGEN, "SpawnAluminum", ConfigValues.SPAWN_ALUMINUM, "False to cancel aluminum worldgen.").getBoolean();
            ConfigValues.SPAWN_MOON_FLOWER_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnMoonFlowerRelic", ConfigValues.SPAWN_MOON_FLOWER_RELIC, "False to cancel moon flower relic worldgen.").getBoolean();
            ConfigValues.SPAWN_UNKNOWN_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnUnknownRelic", ConfigValues.SPAWN_UNKNOWN_RELIC, "False to cancel unknown relic worldgen.").getBoolean();

            ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostBlockBreak", ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK, "The cost in tension to break a block if speed and quality are the same.").getInt();
            ConfigValues.BASE_TENSION_COST_PER_ATTACK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostAttack", ConfigValues.BASE_TENSION_COST_PER_ATTACK, "The cost in tension to attack an entity if speed and quality are the same.").getInt();
            ConfigValues.MAX_MAINSPRING_TENSION = config.get(CATEGORY_CLOCKWORK, "MaxMainspringTension", ConfigValues.MAX_MAINSPRING_TENSION, "The maximum tension a mainspring can hold.").getInt();

            ConfigValues.TEMPORAL_HOURGLASS_MAX = config.get(CATEGORY_TIME, "TemporalHourglassStartCap", ConfigValues.TEMPORAL_HOURGLASS_MAX, "The start cap for the temporal hourglass.").getInt();
            ConfigValues.HOURGLASS_XP_LEVEL_TIER_1 = config.get(CATEGORY_TIME, "HourglassTier1Level", ConfigValues.HOURGLASS_XP_LEVEL_TIER_1, "The level requirement for the temporal hourglass to generate time.").getInt();
            ConfigValues.HOURGLASS_XP_LEVEL_TIER_2 = config.get(CATEGORY_TIME, "HourglassTier1Leve2", ConfigValues.HOURGLASS_XP_LEVEL_TIER_2, "The level threshold for the second tier of time hourglass time generation.").getInt();
            ConfigValues.HOURGLASS_XP_LEVEL_TIER_3 = config.get(CATEGORY_TIME, "HourglassTier1Leve3", ConfigValues.HOURGLASS_XP_LEVEL_TIER_3, "The level threshold for the third tier of time hourglass time generation.").getInt();
            ConfigValues.HOURGLASS_XP_LEVEL_TIER_4 = config.get(CATEGORY_TIME, "HourglassTier1Leve4", ConfigValues.HOURGLASS_XP_LEVEL_TIER_4, "The level threshold for the forth tier of time hourglass time generation.").getInt();
            ConfigValues.HOURGLASS_XP_LEVEL_TIER_5 = config.get(CATEGORY_TIME, "HourglassTier1Leve5", ConfigValues.HOURGLASS_XP_LEVEL_TIER_5, "The level threshold for the final tier of time hourglass time generation.").getInt();

            ConfigValues.TEMPORAL_HOURGLASS_CONSUMES_XP = config.get(CATEGORY_TIME, "HourglassConsumesXP", ConfigValues.TEMPORAL_HOURGLASS_CONSUMES_XP, "If true, generating time in an hourglass drains xp from the player.").getBoolean();
        }
        catch(Exception ex)
        {
            System.err.println("[" + Reference.MOD_NAME + "] Exception caught in ConfigurationHandler:");
            ex.printStackTrace();
        }
        finally
        {
            config.save();
        }
    }
}
