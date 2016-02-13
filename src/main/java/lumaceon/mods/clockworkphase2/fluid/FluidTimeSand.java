package lumaceon.mods.clockworkphase2.fluid;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;

public class FluidTimeSand extends Fluid
{
    public FluidTimeSand(String fluidName)
    {
        super(fluidName, null, null);
        this.setLuminosity(10);
        this.setViscosity(250);
        this.setDensity(100);
        this.setRarity(EnumRarity.EPIC);
    }
}
