package lumaceon.mods.clockworkphase2.tile.timezone;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.item.components.timestream.ItemTimestreamExtradimensionalTank;
import lumaceon.mods.clockworkphase2.tile.generic.TileTimezone;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTimezoneFluidImporter extends TileTimezone implements IFluidHandler
{
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
    public void setState(int state) {

    }

    @Override
    public void setStateAndUpdate(int state) {

    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        ItemStack timestream = getTimestream();
        if(timestream != null)
            return ((ItemTimestreamExtradimensionalTank) timestream.getItem()).fill(timestream, resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }
}
