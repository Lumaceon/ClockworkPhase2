package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.item.ItemStack;

/**
 * Contains standard implementations for the ITimeSand interface.
 */
public class TimeSandHelper
{
    public static final String TIME_SAND = "time_sand";

    public static long getTimeSand(ItemStack item)
    {
        return NBTHelper.hasTag(item, TIME_SAND) ? NBTHelper.LONG.get(item, TIME_SAND) : 0;
    }

    public static void setTimeSand(ItemStack item, long timeSand)
    {
        NBTHelper.LONG.set(item, TIME_SAND, timeSand);
    }

    public static long addTimeSand(ItemStack item, long amount)
    {
        if(item.getItem() instanceof ITimeSand)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long overspill;
            long max = timeContainer.getMaxTimeSand(item);
            long current = timeContainer.getTimeSand(item);
            if(current + amount >= max)
            {
                timeContainer.setTimeSand(item, max);
                overspill = max - current;
            }
            else
            {
                timeContainer.setTimeSand(item, current + amount);
                overspill = amount;
            }
            return overspill;
        }
        return amount;
    }

    public static long consumeTimeSand(ItemStack item, long amount)
    {
        if(item.getItem() instanceof IClockworkConstruct)
        {
            ITimeSand timeContainer = (ITimeSand) item.getItem();
            long overspill;
            long currentTension = timeContainer.getTimeSand(item);

            if(currentTension - amount <= 0)
            {
                timeContainer.setTimeSand(item, 0);
                overspill = currentTension;
            }
            else
            {
                timeContainer.setTimeSand(item, currentTension - amount);
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
