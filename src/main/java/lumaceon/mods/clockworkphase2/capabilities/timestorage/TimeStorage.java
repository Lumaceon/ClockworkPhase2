package lumaceon.mods.clockworkphase2.capabilities.timestorage;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import net.minecraft.item.ItemStack;

public class TimeStorage implements ITimeStorage
{
    long capacity;
    long timeStored;
    ItemStack updateStack;

    public TimeStorage() {
        capacity = TimeConverter.ETERNITY;
    }

    public TimeStorage(long capacity) {
        this.capacity = capacity;
    }

    public TimeStorage(long capacity, ItemStack updateableStack) {
        this.capacity = capacity;
        this.updateStack = updateableStack;
    }

    @Override
    public long insertTime(long ticksToInsert)
    {
        long timeAccepted = Math.min(capacity - timeStored, ticksToInsert); //Remaining space vs amount to add.
        timeStored += timeAccepted;
        updateItemMetadata();
        return timeAccepted;
    }

    @Override
    public long extractTime(long ticksToExtract)
    {
        long timeRemoved = Math.min(timeStored, ticksToExtract); //Current time vs amount to remove.
        timeStored -= timeRemoved;
        updateItemMetadata();
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
            updateItemMetadata();
            return timeLost;
        }
        updateItemMetadata();
        return 0;
    }

    public void setTimeStored(long time) {
        timeStored = time;
        updateItemMetadata();
    }

    protected void updateItemMetadata()
    {
        if(this.updateStack != null)
        {
            if(this.getMaxCapacity() <= 0)
            {
                this.updateStack.setItemDamage(updateStack.getMaxDamage());
            }
            else
            {
                int damage = this.updateStack.getMaxDamage() - (int) ( ((double) getTimeInTicks() / (double) getMaxCapacity()) * updateStack.getMaxDamage() );
                if(damage <= 0)
                {
                    damage = 1;
                }

                this.updateStack.setItemDamage(damage);
            }
        }
    }
}
