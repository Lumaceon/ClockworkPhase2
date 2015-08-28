package lumaceon.mods.clockworkphase2.api.time;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.Iterator;

/**
 * Reference implementation of ITimeStorage.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public class TimeStorage implements ITimeStorage
{
    protected long time, capacity;

    public TimeStorage(long capacity) {
        this.capacity = capacity;
    }

    public TimeStorage writeToNBT(NBTTagCompound nbt)
    {
        nbt.setLong("time_stored", time);
        nbt.setLong("storage_cap", capacity);
        return this;
    }

    public NBTTagCompound readFromNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("time_stored"))
            time = nbt.getLong("time_stored");
        if(nbt.hasKey("storage_cap"))
            capacity = nbt.getLong("storage_cap");
        return nbt;
    }

    public void setCapacity(long capacity)
    {
        this.capacity = capacity;
        if(getTimeStored() > getMaxCapacity())
            time = capacity;
    }

    @Override
    public long receiveTime(long maxReceive, boolean simulate)
    {
        long timeReceived = Math.min(getEmptySpace(), maxReceive);
        if(!simulate)
            time += timeReceived;
        return timeReceived;
    }

    @Override
    public long extractTime(long maxExtract, boolean simulate)
    {
        long timeExtracted = Math.min(time, maxExtract);
        if(!simulate)
            time -= timeExtracted;
        return timeExtracted;
    }

    @Override
    public long getMaxCapacity() {
        return capacity;
    }

    @Override
    public long getTimeStored() {
        return time;
    }

    public long getEmptySpace() {
        return getMaxCapacity() - getTimeStored();
    }
}
