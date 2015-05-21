package lumaceon.mods.clockworkphase2.config;

import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static void init(File configurationFile)
    {
        Configuration config = new Configuration(configurationFile);
        try
        {
            config.load();
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
}
