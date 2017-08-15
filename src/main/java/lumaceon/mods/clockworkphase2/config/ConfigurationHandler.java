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

            ConfigValues.SPAWN_COPPER = config.get(CATEGORY_WORLDGEN, "SpawnCopper", ConfigValues.SPAWN_COPPER, "False to cancel copper worldgen.").getBoolean();
            ConfigValues.SPAWN_ZINC = config.get(CATEGORY_WORLDGEN, "SpawnZinc", ConfigValues.SPAWN_ZINC, "False to cancel zinc worldgen.").getBoolean();
            ConfigValues.SPAWN_ALUMINUM = config.get(CATEGORY_WORLDGEN, "SpawnAluminum", ConfigValues.SPAWN_ALUMINUM, "False to cancel aluminum worldgen.").getBoolean();
            ConfigValues.SPAWN_MOON_FLOWER_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnMoonFlowerRelic", ConfigValues.SPAWN_MOON_FLOWER_RELIC, "False to cancel moon flower relic worldgen.").getBoolean();
            ConfigValues.SPAWN_UNKNOWN_RELIC = config.get(CATEGORY_WORLDGEN, "SpawnUnknownRelic", ConfigValues.SPAWN_UNKNOWN_RELIC, "False to cancel unknown relic worldgen.").getBoolean();

            ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostBlockBreak", ConfigValues.BASE_TENSION_COST_PER_BLOCK_BREAK, "The cost in tension to break a block if speed and quality are the same.").getInt();
            ConfigValues.BASE_TENSION_COST_PER_ATTACK = config.get(CATEGORY_CLOCKWORK, "BaseTensionCostAttack", ConfigValues.BASE_TENSION_COST_PER_ATTACK, "The cost in tension to attack an entity if speed and quality are the same.").getInt();
            ConfigValues.MAX_MAINSPRING_TENSION = config.get(CATEGORY_CLOCKWORK, "MaxMainspringTension", ConfigValues.MAX_MAINSPRING_TENSION, "The maximum tension a mainspring can hold.").getInt();

            ConfigValues.TEMPORAL_HOURGLASS_START = config.get(CATEGORY_TIME, "TemporalHourglassStartCap", ConfigValues.TEMPORAL_HOURGLASS_START, "The starting cap for the temporal hourglass.").getInt();
            ConfigValues.TEMPORAL_HOURGLASS_HARD_CAP = config.get(CATEGORY_TIME, "TemporalHourglassHardCap", ConfigValues.TEMPORAL_HOURGLASS_HARD_CAP, "The hard cap for the temporal hourglass.").getInt();
            ConfigValues.ETHEREAL_HOURGLASS_START = config.get(CATEGORY_TIME, "EtherealHourglassStartCap", ConfigValues.ETHEREAL_HOURGLASS_START).getInt();
            ConfigValues.ETHEREAL_HOURGLASS_HARD_CAP = config.get(CATEGORY_TIME, "EtherealHourglassHardCap", ConfigValues.ETHEREAL_HOURGLASS_HARD_CAP).getInt();
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
