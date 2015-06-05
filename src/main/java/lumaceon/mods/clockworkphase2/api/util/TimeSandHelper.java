package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Contains standard implementations for the ITimeSand interface.
 * Temporal tool users should also take a look at TemporalToolHelper.
 */
public class TimeSandHelper
{
    public static final String TIME_SAND = "time_sand";

    public static long getTimeSand(ItemStack item) {
        return NBTHelper.hasTag(item, TIME_SAND) ? NBTHelper.LONG.get(item, TIME_SAND) : 0;
    }

    public static void setTimeSand(ItemStack item, long timeSand) {
        NBTHelper.LONG.set(item, TIME_SAND, timeSand);
    }

    /**
     * Automatically handles inner temporal cores.
     * @return The amount that could be added.
     */
    public static long addTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long startingAmount = amount;
            long timeAdded;
            long max = timeContainer.getMaxTimeSand(item);
            long current = timeContainer.getTimeSand(item);
            if(current + amount >= max) //Exceeds the tool's internal maximum.
            {
                if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
                {
                    ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                    for(ItemStack is : items)
                    {
                        if(is != null && is.getItem() instanceof ITemporalCore)
                        {
                            amount -= addTimeSand(is, player, amount);
                            NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                            if(amount <= 0)
                                return startingAmount;
                        }
                    }
                }
                current = timeContainer.getTimeSand(item);
                if(current + amount >= max)
                {
                    timeContainer.setTimeSand(item, player, max);
                    timeAdded = max - current;
                }
                else
                {
                    timeContainer.setTimeSand(item, player, current + amount);
                    timeAdded = startingAmount;
                }
            }
            else
            {
                timeContainer.setTimeSand(item, player, current + amount);
                timeAdded = startingAmount;
            }
            return timeAdded;
        }
        return 0;
    }

    /**
     * Automatically handles inner temporal cores.
     * @return The amount that could be added.
     */
    public static long addTimeSand(ItemStack item, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long startingAmount = amount;
            long timeAdded;
            long max = timeContainer.getMaxTimeSand(item);
            long current = timeContainer.getTimeSand(item);
            if(current + amount >= max) //Exceeds the tool's internal maximum.
            {
                if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
                {
                    ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                    for(ItemStack is : items)
                    {
                        if(is != null && is.getItem() instanceof ITemporalCore)
                        {
                            amount -= addTimeSand(is, amount);
                            NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                            if(amount <= 0)
                                return startingAmount;
                        }
                    }
                }
                current = timeContainer.getTimeSand(item);
                if(current + amount >= max)
                {
                    timeContainer.setTimeSand(item, max);
                    timeAdded = max - current;
                }
                else
                {
                    timeContainer.setTimeSand(item, current + amount);
                    timeAdded = startingAmount;
                }
            }
            else
            {
                timeContainer.setTimeSand(item, current + amount);
                timeAdded = startingAmount;
            }
            return timeAdded;
        }
        return 0;
    }

    public static long consumeTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long startingAmount = amount;
            long amountConsumed;
            long currentTime = timeContainer.getTimeSand(item);

            if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                for(ItemStack is : items)
                {
                    if(is != null && is.getItem() instanceof ITemporalCore)
                    {
                        amount -= consumeTimeSand(is, player, amount);
                        NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                        if(amount <= 0)
                            return startingAmount;
                    }
                }
            }
            currentTime = timeContainer.getTimeSand(item);

            if(currentTime - amount <= 0)
            {
                timeContainer.setTimeSand(item, player, 0);
                amountConsumed = currentTime;
            }
            else
            {
                timeContainer.setTimeSand(item, player, currentTime - amount);
                amountConsumed = amount;
            }
            return amountConsumed;
        }
        return 0;
    }

    public static long consumeTimeSand(ItemStack item, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long startingAmount = amount;
            long amountConsumed;
            long currentTime = timeContainer.getTimeSand(item);

            if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                for(ItemStack is : items)
                {
                    if(is != null && is.getItem() instanceof ITemporalCore)
                    {
                        amount -= consumeTimeSand(is, amount);
                        NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                        if(amount <= 0)
                            return startingAmount;
                    }
                }
            }
            currentTime = timeContainer.getTimeSand(item);

            if(currentTime - amount <= 0)
            {
                timeContainer.setTimeSand(item, 0);
                amountConsumed = currentTime;
            }
            else
            {
                timeContainer.setTimeSand(item, currentTime - amount);
                amountConsumed = amount;
            }
            return amountConsumed;
        }
        return 0;
    }

    public static int getTimeSandChance(int playerLevel)
    {
        if(playerLevel < 1)
            return 1000000;
        return (int) Math.ceil(10000.0 / ((double) playerLevel * playerLevel));
    }
}
