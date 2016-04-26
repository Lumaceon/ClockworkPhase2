package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.modulations.TimezoneModulationTank;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.*;

import java.util.List;

public class TileTimezoneFluidExporter extends TileTemporal implements IFluidHandler, ITickable
{
    public String targetFluid = "";
    public FluidStack renderStack;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setString("target_fluid", targetFluid);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("target_fluid"))
        {
            String fluid = nbt.getString("target_fluid");
            if(!fluid.equals("") && FluidRegistry.isFluidRegistered(fluid))
                targetFluid = nbt.getString("target_fluid");
            else
                targetFluid = "";
        }
    }

    /**
     * Used by BlockTimezoneFluidExporter on a right-click to set this to the next fluid available.
     * @return Name of the fluid that this tile entity is now exporting, or null if there are none.
     */
    public String setNextTargetFluid()
    {
        if(!worldObj.isRemote)
        {
            FluidTankInfo[] tanks = getTankInfo(EnumFacing.DOWN);
            boolean found = false;
            boolean anyFound = false;
            int indexFound = 0;
            if(tanks != null)
            {
                FluidTankInfo tank;
                for(int n = 0; n < tanks.length; n++)
                {
                    tank = tanks[n];
                    if(!anyFound && tank != null && tank.fluid != null)
                        anyFound = true;

                    if(tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && targetFluid.equals(tank.fluid.getFluid().getName()))
                    {
                        found = true;
                        indexFound = n;
                    }
                    else if(found && tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && !targetFluid.equals(tank.fluid.getFluid().getName()))
                    {
                        targetFluid = tank.fluid.getFluid().getName();
                        worldObj.markBlockForUpdate(this.getPos()); //TODO: Check that this actually does update properly.
                        return targetFluid;
                    }
                }

                if(found)
                {
                    for(int n = 0; n < Math.min(tanks.length, indexFound); n++)
                    {
                        tank = tanks[n];
                        if(tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && !targetFluid.equals(tank.fluid.getFluid().getName()))
                        {
                            targetFluid = tank.fluid.getFluid().getName();
                            worldObj.markBlockForUpdate(this.getPos()); //TODO: Check that this actually does update properly.
                            return targetFluid;
                        }
                    }
                }
                else
                {
                    for(FluidTankInfo t : tanks)
                    {
                        if(t != null && t.fluid != null)
                        {
                            targetFluid = t.fluid.getFluid().getName();
                            worldObj.markBlockForUpdate(this.getPos()); //TODO: Check that this actually does update properly.
                            return targetFluid;
                        }
                    }
                }
            }
            if(!anyFound)
            {
                targetFluid = "";
                worldObj.markBlockForUpdate(this.getPos()); //TODO: Check that this actually does update properly.
            }
        }
        return targetFluid;
    }

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
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
    {
        if(resource == null || resource.getFluid() == null || !resource.getFluid().getName().equals(targetFluid))
            return null;
        List<TimezoneModulation> tanks = getTanks();
        for(TimezoneModulation tank : tanks)
            if(tank != null && tank instanceof TimezoneModulationTank)
                if(((TimezoneModulationTank) tank).getFluid().getName().equals(targetFluid))
                    return ((TimezoneModulationTank) tank).drain(((TimezoneModulationTank) tank).getMaxCapacity(), doDrain);
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
    {
        List<TimezoneModulation> tanks = getTanks();
        for(TimezoneModulation tank : tanks)
            if(tank != null && tank instanceof TimezoneModulationTank)
                if(((TimezoneModulationTank) tank).getFluid().getName().equals(targetFluid))
                    return ((TimezoneModulationTank) tank).drain(maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        List<TimezoneModulation> tanks = getTanks();
        for(TimezoneModulation tank : tanks)
            if(tank != null && tank instanceof TimezoneModulationTank)
                if(((TimezoneModulationTank) tank).getFluid().getName().equals(targetFluid))
                    return ((TimezoneModulationTank) tank).getTankInfo();
        return new FluidTankInfo[] { new FluidTankInfo(null, 0) };
    }

    @Override
    public void update() {

    }
}
