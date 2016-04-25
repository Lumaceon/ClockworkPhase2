package lumaceon.mods.clockworkphase2.fluid;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidTemporium extends Fluid
{
    public FluidTemporium(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
        this.setViscosity(500);
        this.setDensity(500);
        this.setRarity(EnumRarity.EPIC);
    }
}
