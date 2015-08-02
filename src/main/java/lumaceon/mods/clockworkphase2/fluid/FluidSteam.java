package lumaceon.mods.clockworkphase2.fluid;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends Fluid
{
    public FluidSteam(String fluidName)
    {
        super(fluidName);
        this.setDensity(-200);
        this.setTemperature(400);
        this.setViscosity(1);
        this.setGaseous(true);
        this.setRarity(EnumRarity.common);
    }
}
