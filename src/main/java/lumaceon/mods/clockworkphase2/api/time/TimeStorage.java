package lumaceon.mods.clockworkphase2.api.time;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reference implementation of ITimeStorage.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public class TimeStorage implements ITimeStorage
{
    protected Map<Time, TimeInfo> timesStored = new ConcurrentHashMap<Time, TimeInfo>();
    protected long capacity;

    public TimeStorage(long capacity) {
        this.capacity = capacity;
    }

    public TimeStorage writeToNBT(NBTTagCompound nbt)
    {
        if(timesStored != null && !timesStored.isEmpty())
        {
            NBTTagList list = new NBTTagList();
            Collection<TimeInfo> times = timesStored.values();
            Iterator<TimeInfo> iterator = times.iterator();
            while(iterator.hasNext())
            {
                TimeInfo timeInfo = iterator.next();
                if(timeInfo == null)
                    continue;

                NBTTagCompound timeNBT = new NBTTagCompound();
                timeNBT.setString("time_name", timeInfo.time.getName());
                timeNBT.setLong("time_value", timeInfo.amount);
                list.appendTag(timeNBT);
            }
            nbt.setTag("time_storage", list);
        }
        else
            nbt.removeTag("time_storage");

        return this;
    }

    public NBTTagCompound readFromNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("time_storage"))
        {
            NBTTagList list = (NBTTagList) nbt.getTag("time_storage");
            if(list != null)
            {
                for(int n = 0; n < list.tagCount(); n++)
                {
                    NBTTagCompound timeNBT = list.getCompoundTagAt(n);
                    if(timeNBT == null)
                        continue;

                    String name = timeNBT.getString("time_name");
                    Time time = TimeRegistry.getTime(name);
                    if(time == null)
                        continue;

                    timesStored.put(time, new TimeInfo(time, timeNBT.getLong("time_value")));
                }
            }
        }

        if(getTotalTimeStored() > getMaxTotalTimeStorage())
            condenseTimeStorage();

        return nbt;
    }

    public void setCapacity(long capacity)
    {
        this.capacity = capacity;
        if(getTotalTimeStored() > getMaxTotalTimeStorage())
            condenseTimeStorage();
    }

    @Override
    public long receiveTime(Time time, long maxReceive, boolean simulate)
    {
        if(time == null)
            return 0;

        long timeReceived = Math.min(getEmptySpace(time), maxReceive);
        if(!simulate)
        {
            if(timesStored.containsKey(time))
            {
                TimeInfo ti = timesStored.get(time);
                ti.setAmount(ti.getAmount() + timeReceived);
            }
            else
            {
                TimeInfo ti = new TimeInfo(time, timeReceived);
                timesStored.put(time, ti);
            }
        }
        return timeReceived;
    }

    @Override
    public long extractTime(Time time, long maxExtract, boolean simulate)
    {
        if(timesStored == null || !timesStored.containsKey(time))
            return 0;

        long timeExtracted = Math.min(timesStored.get(time).amount, maxExtract);

        if(!simulate)
            timesStored.get(time).amount -= timeExtracted;

        return timeExtracted;
    }

    @Override
    public long getTotalTimeStored()
    {
        if(timesStored == null || timesStored.isEmpty())
            return 0;

        long currentTime = 0;
        Collection<TimeInfo> times = timesStored.values();
        Iterator<TimeInfo> iterator = times.iterator();
        while(iterator.hasNext())
        {
            TimeInfo timeInfo = iterator.next();
            currentTime += timeInfo.amount * timeInfo.time.getMagnitude();
        }

        return currentTime;
    }

    @Override
    public long getMaxTotalTimeStorage() {
        return capacity;
    }

    @Override
    public long getTimeStored(Time time)
    {
        if(timesStored == null || timesStored.isEmpty() || !timesStored.containsKey(time))
            return 0;
        return timesStored.get(time).amount;
    }

    @Override
    public long getEmptySpace(Time time) {
        return (getMaxTotalTimeStorage() - getTotalTimeStored()) / time.getMagnitude();
    }

    /**
     * Handles the loss of time, which occurs if the capacity is reduced below the amount of time stored within.
     */
    protected void condenseTimeStorage()
    {

    }
}
