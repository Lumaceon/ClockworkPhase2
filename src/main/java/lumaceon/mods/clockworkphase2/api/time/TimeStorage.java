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
    protected int time, capacity;

    public TimeStorage(int capacity) {
        this.capacity = capacity;
    }

    public TimeStorage writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("time_stored", time);
        nbt.setInteger("storage_cap", capacity);
        return this;
    }

    public NBTTagCompound readFromNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("time_stored"))
            time = nbt.getInteger("time_stored");
        if(nbt.hasKey("storage_cap"))
            capacity = nbt.getInteger("storage_cap");
        return nbt;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        if(getTimeStored() > getMaxCapacity())
            time = capacity;
    }

    @Override
    public int receiveTime(int maxReceive, boolean simulate)
    {
        int timeReceived = Math.min(getEmptySpace(), maxReceive);
        if(!simulate)
            time += timeReceived;
        return timeReceived;
    }

    @Override
    public int extractTime(int maxExtract, boolean simulate)
    {
        int timeExtracted = Math.min(time, maxExtract);
        if(!simulate)
            time -= timeExtracted;
        return timeExtracted;
    }

    @Override
    public int getMaxCapacity() {
        return capacity;
    }

    @Override
    public int getTimeStored() {
        return time;
    }

    public int getEmptySpace() {
        return getMaxCapacity() - getTimeStored();
    }
}
