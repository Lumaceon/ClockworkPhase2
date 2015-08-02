package lumaceon.mods.clockworkphase2.tile.generic;

import lumaceon.mods.clockworkphase2.init.ModFluids;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSteamTemporal extends TileTimezonePowered implements IFluidHandler
{
    public int steamUsePerTick = 5;
    public int maxSteamBuffer = 1000;
    public int steamBuffer = 0;

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if(canFill(from, resource.getFluid()))
        {
            int amount = Math.min(maxSteamBuffer - steamBuffer, resource.amount);
            if(doFill)
                steamBuffer += amount;
            return amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) { return null; }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) { return null; }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid.equals(ModFluids.steam); //TODO - OreDictionary support
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) { return false; }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {new FluidTankInfo(new FluidStack(ModFluids.steam, 0), steamUsePerTick)};
    }

    @Override
    public void setState(int state) {

    }

    @Override
    public void setStateAndUpdate(int state) {

    }
}
