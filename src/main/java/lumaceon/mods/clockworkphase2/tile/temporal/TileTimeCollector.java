package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTimeCollector extends TileClockworkPhase implements ITimeProvider
{
    public TimeStorage timeStorage;

    public TileTimeCollector() {
        timeStorage = new TimeStorage(TimeConverter.MINUTE);
    }

    @Override
    public void updateEntity()
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
    public long extractTime(long maxExtract, boolean simulate) {
        return timeStorage.extractTime(maxExtract, simulate);
    }

    @Override
    public long getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public long getTimeStored() {
        return timeStorage.getTimeStored();
    }

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}
}
