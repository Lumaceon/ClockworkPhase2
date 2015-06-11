package lumaceon.mods.clockworkphase2.tile.timezone;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.item.components.timestream.ItemTimestreamExtradimensionalTank;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTileStateChange;
import lumaceon.mods.clockworkphase2.tile.generic.TileTimezone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileTimezoneFluidExporter extends TileTimezone implements IFluidHandler
{
    public String targetFluid = "";
    public FluidStack renderStack;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setString("cp_state", targetFluid);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("cp_state"))
        {
            String state = nbt.getString("cp_state");
            if(!state.equals("") && FluidRegistry.isFluidRegistered(state))
                setState(FluidRegistry.getFluidID(nbt.getString("cp_state")));
            else
                setState(-1);
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
            FluidTankInfo[] tanks = getTankInfo(ForgeDirection.DOWN);
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

                    if(tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && FluidRegistry.getFluidID(targetFluid) == tank.fluid.fluidID)
                    {
                        found = true;
                        indexFound = n;
                    }
                    else if(found && tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && FluidRegistry.getFluidID(targetFluid) != tank.fluid.fluidID)
                    {
                        targetFluid = tank.fluid.getFluid().getName();
                        setStateAndUpdate(FluidRegistry.getFluidID(targetFluid));
                        return targetFluid;
                    }
                }

                if(found)
                {
                    for(int n = 0; n < Math.min(tanks.length, indexFound); n++)
                    {
                        tank = tanks[n];
                        if(tank != null && tank.fluid != null && FluidRegistry.isFluidRegistered(targetFluid) && FluidRegistry.getFluidID(targetFluid) != tank.fluid.fluidID)
                        {
                            targetFluid = tank.fluid.getFluid().getName();
                            setStateAndUpdate(FluidRegistry.getFluidID(targetFluid));
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
                            setStateAndUpdate(FluidRegistry.getFluidID(targetFluid));
                            return targetFluid;
                        }
                    }
                }
            }
            if(!anyFound)
            {
                targetFluid = "";
                setStateAndUpdate(-1);
            }
        }
        return targetFluid;
    }

    public ItemStack getTimestream()
    {
        ITimezone timezone = getTimezone();
        ItemStack timestream;
        if(timezone != null)
        {
            for(int n = 0; n < 8; n++)
            {
                timestream = timezone.getTimestream(n);
                if(timestream != null && timestream.getItem() instanceof ItemTimestreamExtradimensionalTank)
                    return timestream;
            }
        }
        return null;
    }

    @Override
    public void setState(int state)
    {
        Fluid fluid = FluidRegistry.getFluid(state);
        if(fluid != null)
        {
            targetFluid = FluidRegistry.getFluidName(state);
            renderStack = new FluidStack(fluid, 1000);
        }
        else
        {
            targetFluid = "";
            renderStack = null;
        }
    }

    @Override
    public void setStateAndUpdate(int state)
    {
        if(!worldObj.isRemote)
        {
            setState(state);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTileStateChange(xCoord, yCoord, zCoord, state), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 200));
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if(resource == null)
            return null;
        ItemStack timestream = getTimestream();
        if(timestream != null)
            return ((ItemTimestreamExtradimensionalTank) timestream.getItem()).drain(timestream, resource.amount, doDrain, targetFluid);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        ItemStack timestream = getTimestream();
        if(timestream != null)
            return ((ItemTimestreamExtradimensionalTank) timestream.getItem()).drain(timestream, maxDrain, doDrain, targetFluid);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        ItemStack timestream = getTimestream();
        if(timestream != null)
        {
            ItemTimestreamExtradimensionalTank tank = ((ItemTimestreamExtradimensionalTank) timestream.getItem());
            return tank.getTankInfo(timestream);
        }
        return new FluidTankInfo[] { new FluidTankInfo(null, 0) };
    }
}
