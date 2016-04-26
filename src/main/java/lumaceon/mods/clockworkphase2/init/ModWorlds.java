package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.lib.Configs;
import lumaceon.mods.clockworkphase2.timetravel.first.world.WorldProviderFirstAge;
import lumaceon.mods.clockworkphase2.timetravel.second.world.WorldProviderSecondAge;
import lumaceon.mods.clockworkphase2.timetravel.third.world.WorldProviderThirdAge;
import lumaceon.mods.clockworkphase2.timetravel.zeroth.world.WorldProviderZerothAge;
import net.minecraftforge.common.DimensionManager;

public class ModWorlds
{
    public static void init()
    {
        Configs.DIM_ID.THIRD_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Configs.DIM_ID.THIRD_AGE, WorldProviderThirdAge.class, false);
        DimensionManager.registerDimension(Configs.DIM_ID.THIRD_AGE, Configs.DIM_ID.THIRD_AGE);

        Configs.DIM_ID.SECOND_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Configs.DIM_ID.SECOND_AGE, WorldProviderSecondAge.class, false);
        DimensionManager.registerDimension(Configs.DIM_ID.SECOND_AGE, Configs.DIM_ID.SECOND_AGE);

        Configs.DIM_ID.FIRST_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Configs.DIM_ID.FIRST_AGE, WorldProviderFirstAge.class, false);
        DimensionManager.registerDimension(Configs.DIM_ID.FIRST_AGE, Configs.DIM_ID.FIRST_AGE);

        Configs.DIM_ID.ZEROTH_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Configs.DIM_ID.ZEROTH_AGE, WorldProviderZerothAge.class, false);
        DimensionManager.registerDimension(Configs.DIM_ID.ZEROTH_AGE, Configs.DIM_ID.ZEROTH_AGE);
    }
}
