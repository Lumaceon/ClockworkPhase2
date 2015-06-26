package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.ItemStack;

/**
 * Provides methods for simplifying temporal tools.
 */
public class TemporalToolHelper
{
    public static boolean hasTemporalCore(ItemStack item)
    {
        if(item != null)
        {
            ItemStack[] components = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
            if(components == null)
                return false;

            for(ItemStack is : components)
            {
                if(is != null && is.getItem() instanceof ITemporalCore)
                    return true;
            }
        }
        return false;
    }

    public static long getTimeSand(ItemStack item)
    {
        long returnValue = NBTHelper.hasTag(item, TimeSandHelper.TIME_SAND) ? NBTHelper.LONG.get(item, TimeSandHelper.TIME_SAND) : 0;
        if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
            for(ItemStack is : items)
            {
                if(is != null && is.getItem() instanceof ITemporalCore)
                {
                    returnValue += ((ITemporalCore) is.getItem()).getTimeSand(is);
                }
            }
        }
        return returnValue;
    }

    public static long getTimeSandCapacity(ItemStack item)
    {
        long capacity = 0;
        if(item != null)
        {
            if(NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] components = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);

                for(ItemStack is : components)
                {
                    if(is != null && is.getItem() instanceof ITemporalCore)
                        capacity += ((ITemporalCore) is.getItem()).getTimeSand(is);
                }
            }

            if(item.getItem() instanceof ITimeSand)
            {
                capacity += ((ITimeSand) item.getItem()).getTimeSand(item);
            }
        }
        return capacity;
    }
}
