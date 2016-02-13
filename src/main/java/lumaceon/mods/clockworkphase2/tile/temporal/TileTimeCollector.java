package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileTimeCollector extends TileClockworkPhase implements ITimeProvider, ITickable
{
    public TimeStorage timeStorage;

    public TileTimeCollector() {
        timeStorage = new TimeStorage(TimeConverter.MINUTE);
    }

    @Override
    public void update()
    {
        if(!worldObj.isRemote)
            timeStorage.receiveTime(1, false);
        else
            timeStorage.receiveTime(1, false);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(timeStorage != null)
            timeStorage.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(timeStorage != null)
            timeStorage.readFromNBT(nbt);
    }

    @Override
    public int extractTime(int maxExtract, boolean simulate) {
        return timeStorage.extractTime(maxExtract, simulate);
    }

    @Override
    public int getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public int getTimeStored() {
        return timeStorage.getTimeStored();
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    @Override
    public boolean canConnectFrom(EnumFacing from) {
        return false;
    }
}
