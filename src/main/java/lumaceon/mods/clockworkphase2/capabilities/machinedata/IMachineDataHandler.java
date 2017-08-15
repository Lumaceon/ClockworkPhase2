package lumaceon.mods.clockworkphase2.capabilities.machinedata;

import lumaceon.mods.clockworkphase2.util.FluidTankSided;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public interface IMachineDataHandler
{
    public void setSlotsForDirection(int[] slots, EnumFacing direction);
    public int[] getSlotsForDirection(EnumFacing direction);

    public void setFluidTanks(FluidTankSided[] fluidTanks);
    public FluidTankSided[] getFluidTanks();

    public void setIsTemporal(boolean isTemporal);
    public boolean getIsTemporal();

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound nbt);
}
