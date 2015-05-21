package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.fluid.FluidTimeSand;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
    public static Fluid timeSand;

    public static void init()
    {
        timeSand = new FluidTimeSand(Names.FLUID.TIME_SAND);
        FluidRegistry.registerFluid(timeSand);
    }

    public static void bindBlocks()
    {
        timeSand.setBlock(ModBlocks.timeSand);
    }
}
