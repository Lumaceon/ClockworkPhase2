package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.modulations.TimezoneModulationTank;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

public class TileTimezoneFluidImporter extends TileTemporal implements IFluidHandler
{
    public List<TimezoneModulation> getTanks()
    {
        ITimezoneProvider timezoneProvider = getTimezoneProvider();
        if(timezoneProvider == null)
            return null;

        Timezone timezone = timezoneProvider.getTimezone();
        if(timezone != null)
            return timezone.getTimezoneModulations(TimezoneModulationTank.class);

        return null;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        List<TimezoneModulation> tanks = getTanks();
        if(tanks == null || tanks.isEmpty())
            return 0;

        for(TimezoneModulation tank : tanks)
            if(tank != null && tank instanceof TimezoneModulationTank)
                if(((TimezoneModulationTank) tank).getFluid().getName().equals(resource.getFluid().getName()))
                    return ((TimezoneModulationTank) tank).fill(resource, doFill);
        for(TimezoneModulation tank : tanks)
            if(tank != null && tank instanceof TimezoneModulationTank)
                if(((TimezoneModulationTank) tank).getFluidStack().amount <= 0)
                    return ((TimezoneModulationTank) tank).fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[0];
    }
}
