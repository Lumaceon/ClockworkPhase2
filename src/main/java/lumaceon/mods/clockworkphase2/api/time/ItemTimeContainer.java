package lumaceon.mods.clockworkphase2.api.time;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTimeContainer extends Item implements ITimeContainerItem
{
    public int capacity;

    public ItemTimeContainer(int maxStack, int maxDamage, int capacity)
    {
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(maxDamage);
        this.capacity = capacity;
    }

    @Override
    public int receiveTime(ItemStack timeItem, int maxReceive, boolean simulate)
    {
        int currentTime = NBTHelper.INT.get(timeItem, NBTTags.TIME);
        int timeReceived = Math.min(getEmptySpace(timeItem), maxReceive);
        if(!simulate)
            NBTHelper.INT.set(timeItem, NBTTags.TIME, currentTime + timeReceived);
        return timeReceived;
    }

    @Override
    public int extractTime(ItemStack timeItem, int maxExtract, boolean simulate)
    {
        int currentTime = NBTHelper.INT.get(timeItem, NBTTags.TIME);
        int timeExtracted = Math.min(currentTime, maxExtract);
        if(!simulate)
            NBTHelper.INT.set(timeItem, NBTTags.TIME, currentTime - timeExtracted);
        return timeExtracted;
    }

    @Override
    public int getMaxCapacity(ItemStack timeItem) {
        return capacity;
    }

    @Override
    public int getTimeStored(ItemStack timeItem) {
        return NBTHelper.INT.get(timeItem, NBTTags.TIME);
    }

    public int getEmptySpace(ItemStack timeItem) {
        return getMaxCapacity(timeItem) - getTimeStored(timeItem);
    }
}
