package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.fluid.FluidTemporium;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
    public static Fluid liquidTemporium;

    public static void init()
    {
        liquidTemporium = new FluidTemporium(Names.FLUID.LIQUID_TEMPORIUM, Textures.FLUID.TEMPORIUM_STILL, Textures.FLUID.TEMPORIUM_FLOW);

        FluidRegistry.registerFluid(liquidTemporium);
    }

    public static void bindBlocks()
    {
        liquidTemporium.setBlock(ModBlocks.liquidTemporium.getBlock());
    }
}
