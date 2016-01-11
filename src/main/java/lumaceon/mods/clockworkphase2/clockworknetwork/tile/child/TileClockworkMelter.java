package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockwork;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileClockworkMelter extends TileClockwork implements IFluidHandler
{
    public IFluidTank tank;

    public TileClockworkMelter() {
        tank = new FluidTank(32000);
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 3);
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if(tank != null)
            return tank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if(resource != null && tank != null)
            return tank.drain(tank.getFluidAmount(), doDrain);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if(tank != null)
            return tank.drain(maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }
}
