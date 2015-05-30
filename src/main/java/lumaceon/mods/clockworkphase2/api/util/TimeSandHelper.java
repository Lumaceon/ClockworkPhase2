package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import lumaceon.mods.clockworkphase2.util.Logger;
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
     * @return Overspill (the amount that couldn't be added).
     */
    public static long addTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long overspill;
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
                            amount = addTimeSand(is, player, amount);
                            NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                            if(amount <= 0)
                                return 0;
                        }
                    }
                }
                if(current + amount >= max)
                {
                    timeContainer.setTimeSand(item, player, max);
                    overspill = max - current;
                }
                else
                {
                    timeContainer.setTimeSand(item, player, current + amount);
                    overspill = 0;
                }
            }
            else
            {
                timeContainer.setTimeSand(item, player, current + amount);
                overspill = 0;
            }
            return overspill;
        }
        return amount;
    }

    public static long consumeTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        if(item.getItem() instanceof IClockworkConstruct)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long overspill;
            long currentTension = timeContainer.getTimeSand(item);

            if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                for(ItemStack is : items)
                {
                    if(is != null && is.getItem() instanceof ITemporalCore)
                    {
                        amount = consumeTimeSand(is, player, amount);
                        NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                        if(amount <= 0)
                            return 0;
                    }
                }
            }

            if(currentTension - amount <= 0)
            {
                timeContainer.setTimeSand(item, player, 0);
                overspill = currentTension;
            }
            else
            {
                timeContainer.setTimeSand(item, player, currentTension - amount);
                overspill = amount;
            }
            return overspill;
        }
        return amount;
    }

    public static int getTimeSandChance(int playerLevel)
    {
        if(playerLevel < 1)
            return 1000000;
        return (int) Math.ceil(10000.0 / ((double) playerLevel * playerLevel));
    }
}
