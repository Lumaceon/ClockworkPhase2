package lumaceon.mods.clockworkphase2.api.time;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTimeContainer extends Item implements ITimeContainerItem
{
    public long capacity;

    public ItemTimeContainer(int maxStack, int maxDamage, long capacity)
    {
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(maxDamage);
        this.capacity = capacity;
    }

    @Override
    public long receiveTime(ItemStack timeItem, long maxReceive, boolean simulate)
    {
        long currentTime = NBTHelper.LONG.get(timeItem, NBTTags.TIME);
        long timeReceived = Math.min(getEmptySpace(timeItem), maxReceive);
        if(!simulate)
            NBTHelper.LONG.set(timeItem, NBTTags.TIME, currentTime + timeReceived);
        return timeReceived;
    }

    @Override
    public long extractTime(ItemStack timeItem, long maxExtract, boolean simulate)
    {
        long currentTime = NBTHelper.LONG.get(timeItem, NBTTags.TIME);
        long timeExtracted = Math.min(currentTime, maxExtract);
        if(!simulate)
            NBTHelper.LONG.set(timeItem, NBTTags.TIME, currentTime - timeExtracted);
        return timeExtracted;
    }

    @Override
    public long getMaxCapacity(ItemStack timeItem) {
        return capacity;
    }

    @Override
    public long getTimeStored(ItemStack timeItem) {
        return NBTHelper.LONG.get(timeItem, NBTTags.TIME);
    }

    public long getEmptySpace(ItemStack timeItem) {
        return getMaxCapacity(timeItem) - getTimeStored(timeItem);
    }
}
