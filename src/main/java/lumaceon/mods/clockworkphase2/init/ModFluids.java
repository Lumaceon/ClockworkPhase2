package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.fluid.FluidSteam;
import lumaceon.mods.clockworkphase2.fluid.FluidTimeSand;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
    public static Fluid timeSand;
    public static Fluid steam;

    public static void init()
    {
        timeSand = new FluidTimeSand(Names.FLUID.TIME_SAND);
        steam = new FluidSteam(Names.FLUID.STEAM);

        FluidRegistry.registerFluid(timeSand);
        FluidRegistry.registerFluid(steam);
    }

    public static void bindBlocks()
    {
        timeSand.setBlock(ModBlocks.timeSand);
    }
}
