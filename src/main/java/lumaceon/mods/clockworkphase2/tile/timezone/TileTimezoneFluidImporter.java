package lumaceon.mods.clockworkphase2.tile.timezone;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.item.timezonemodule.ItemTimezoneModuleTank;
import lumaceon.mods.clockworkphase2.tile.generic.TileTimezoneUsage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTimezoneFluidImporter extends TileTimezoneUsage implements IFluidHandler
{
    public ItemStack getTimezoneModule()
    {
        ITimezone timezone = getTimezone();
        ItemStack timezoneModule;
        if(timezone != null)
        {
            for(int n = 0; n < 8; n++)
            {
                timezoneModule = timezone.getTimezoneModule(n);
                if(timezoneModule != null && timezoneModule.getItem() instanceof ItemTimezoneModuleTank)
                    return timezoneModule;
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
        ItemStack timestream = getTimezoneModule();
        if(timestream != null)
            return ((ItemTimezoneModuleTank) timestream.getItem()).fill(timestream, resource, doFill);
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
