package lumaceon.mods.clockworkphase2.modulations;

import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.lib.Configs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.*;

public class TimezoneModulationTank extends TimezoneModulation
{
    protected FluidTank tank = new FluidTank(Configs.MISC_VALUES.timezoneFluidModulationCap);

    public TimezoneModulationTank(TileTimezoneModulator tile) {
        super(tile);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        tank.writeToNBT(nbt);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        tank.readFromNBT(nbt);
    }

    public int getMaxCapacity() {
        return tank.getCapacity();
    }

    public int fill(FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    public FluidStack getFluidStack() {
        return tank.getFluid();
    }

    public Fluid getFluid() {
        return tank.getFluid().getFluid();
    }

    public FluidTankInfo[] getTankInfo() {
        return new FluidTankInfo[] {tank.getInfo()};
    }
}
