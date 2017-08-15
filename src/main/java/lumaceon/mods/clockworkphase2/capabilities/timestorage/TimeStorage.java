package lumaceon.mods.clockworkphase2.capabilities.timestorage;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;

public class TimeStorage implements ITimeStorage
{
    long capacity;
    long timeStored;

    public TimeStorage() {
        capacity = TimeConverter.ETERNITY;
    }

    public TimeStorage(long capacity) {
        this.capacity = capacity;
    }

    @Override
    public long insertTime(long ticksToInsert)
    {
        long timeAccepted = Math.min(capacity - timeStored, ticksToInsert); //Remaining space vs amount to add.
        timeStored += timeAccepted;
        return timeAccepted;
    }

    @Override
    public long extractTime(long ticksToExtract)
    {
        long timeRemoved = Math.min(timeStored, ticksToExtract); //Current time vs amount to remove.
        timeStored -= timeRemoved;
        return timeRemoved;
    }

    @Override
    public long getTimeInTicks() {
        return timeStored;
    }

    @Override
    public long getMaxCapacity() {
        return capacity;
    }

    @Override
    public long setMaxCapacity(long maxCapacity)
    {
        if(maxCapacity >= this.capacity)
            this.capacity = maxCapacity;
        else if(maxCapacity < this.timeStored)
        {
            long timeLost = this.timeStored - maxCapacity;
            this.capacity = maxCapacity;
            this.timeStored = maxCapacity;
            return timeLost;
        }
        return 0;
    }

    public void setTimeStored(long time) {
        timeStored = time;
    }
}
