package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

public class TileClockworkMelter extends TileClockworkNetworkMachine implements IFluidHandler
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
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        if(tank != null)
            return tank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        if(resource != null && tank != null)
            return tank.drain(tank.getFluidAmount(), doDrain);
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        if(tank != null)
            return tank.drain(maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[] {tank.getInfo()};
    }

    @Override
    public boolean canWork() {
        return false;
    }

    @Override
    public void work() {

    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, EnumFacing side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, EnumFacing side) {
        return false;
    }
}
