package lumaceon.mods.clockworkphase2.config;

import lumaceon.mods.clockworkphase2.api.AchievementTiering;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.util.Logger;
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

            ConfigValues.SPAWN_COPPER = config.get(CATEGORY_WORLDGEN, "SpawnCopper", ConfigValues.SPAWN_COPPER, "False to cancel copper generation.").getBoolean();
            ConfigValues.SPAWN_ZINC = config.get(CATEGORY_WORLDGEN, "SpawnZinc", ConfigValues.SPAWN_ZINC, "False to cancel zinc generation.").getBoolean();
            ConfigValues.SPAWN_MOON_FLOWER_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnMoonFlowerRelic", ConfigValues.SPAWN_MOON_FLOWER_RELIC, "False to cancel moon flower relic generation.").getBoolean();
            ConfigValues.SPAWN_UNKNOWN_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnUnknownRelic", ConfigValues.SPAWN_UNKNOWN_RELIC, "False to cancel unknown relic generation.").getBoolean();

            ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostBlockBreak", ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK, "The cost in tension to break a block if speed and quality are the same.").getInt();
            ConfigValues.BASE_TENSION_COST_PER_ATTACK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostAttack", ConfigValues.BASE_TENSION_COST_PER_ATTACK, "The cost in tension to attack an entity if speed and quality are the same.").getInt();
            ConfigValues.MAX_MAINSPRING_TENSION = config.get(CATEGORY_CLOCKWORK, "MaxMainspringTension", ConfigValues.MAX_MAINSPRING_TENSION, "The maximum tension a mainspring can hold.").getInt();

            ConfigValues.TEMPORAL_DRIVE_TIME_PER_TICK = config.get(CATEGORY_TIME, "TemporalDriveTimePerTick", ConfigValues.TEMPORAL_DRIVE_TIME_PER_TICK, "The amount of ticks to generate per tick.").getInt();
            ConfigValues.TEMPORAL_DRIVE_TICKS = config.get(CATEGORY_TIME, "TemporalDriveTotalTicks", ConfigValues.TEMPORAL_DRIVE_TICKS, "The amount of ticks in which time will be generated.").getInt();
            ConfigValues.ETHEREAL_DRIVE_TIME_PER_TICK = config.get(CATEGORY_TIME, "EtherealDriveTimePerTick", ConfigValues.ETHEREAL_DRIVE_TIME_PER_TICK).getInt();
            ConfigValues.ETHEREAL_DRIVE_TICKS = config.get(CATEGORY_TIME, "EtherealDriveTotalTicks", ConfigValues.ETHEREAL_DRIVE_TICKS).getInt();
            ConfigValues.TEMPORAL_HOURGLASS_START = config.get(CATEGORY_TIME, "TemporalHourglassStartCap", ConfigValues.TEMPORAL_HOURGLASS_START, "The starting cap for the temporal hourglass.").getInt();
            ConfigValues.TEMPORAL_HOURGLASS_HARD_CAP = config.get(CATEGORY_TIME, "TemporalHourglassHardCap", ConfigValues.TEMPORAL_HOURGLASS_HARD_CAP, "The hard cap for the temporal hourglass.").getInt();
            ConfigValues.ETHEREAL_HOURGLASS_START = config.get(CATEGORY_TIME, "EtherealHourglassStartCap", ConfigValues.ETHEREAL_HOURGLASS_START).getInt();
            ConfigValues.ETHEREAL_HOURGLASS_HARD_CAP = config.get(CATEGORY_TIME, "EtherealHourglassHardCap", ConfigValues.ETHEREAL_HOURGLASS_HARD_CAP).getInt();

            ConfigValues.BASE_ETHEREAL_TIMEFRAME_KEY_DROP_RATE = config.get(CATEGORY_TIME, "BaseTimeframeKeyDropRateEthereal", ConfigValues.BASE_ETHEREAL_TIMEFRAME_KEY_DROP_RATE, "The base chance for a timeframe key to drop from ethereal mobs. Chance is 1*lootingLevelOfSword in (config value).").getInt();
            ConfigValues.BASE_PHASIC_TIMEFRAME_KEY_DROP_RATE = config.get(CATEGORY_TIME, "BaseTimeframeKeyDropRatePhasic", ConfigValues.BASE_PHASIC_TIMEFRAME_KEY_DROP_RATE).getInt();
            ConfigValues.BASE_ETERNAL_TIMEFRAME_KEY_DROP_RATE = config.get(CATEGORY_TIME, "BaseTimeframeKeyDropRateEternal", ConfigValues.BASE_ETERNAL_TIMEFRAME_KEY_DROP_RATE).getInt();
        }
        catch(Exception ex)
        {
            Logger.error("Exception caught in ConfigurationHandler:");
            ex.printStackTrace();
        }
        finally
        {
            config.save();
        }
    }

    public static void initBlacklist(File directory)
    {
        directory = new File(directory, Reference.MOD_ID + "/achievement_blacklist" + ".txt");
        try {
            if(!Files.exists(Paths.get(directory.toURI()).getParent()))
                Files.createDirectories(Paths.get(directory.toURI()).getParent());
            if(!Files.exists(Paths.get(directory.toURI())))
                Files.createFile(Paths.get(directory.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader input = new BufferedReader(new FileReader(directory));
            String line;

            while((line = input.readLine()) != null)
            {
                if(line.startsWith("//"))
                    continue;
                while(line.startsWith(" "))
                    line = line.substring(1);
                while(line.endsWith(" "))
                    line = line.substring(0, line.length() - 1);
                if(line.length() <= 0)
                    continue;
                AchievementTiering.blacklistedAchievements.add(line);
                Logger.info("Blacklisted achievement: " + line);
            }

            input.close();
        } catch (FileNotFoundException e) {
            Logger.error("Failed to find achievement_blacklist.txt file.");
            e.printStackTrace();
        } catch (IOException e) {
            Logger.error("IOException while reading the achievement blacklist.");
            e.printStackTrace();
        }

    }
}
