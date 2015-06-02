package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.ItemStack;

public class TimestreamHelper
{
    public static boolean isEnabled(ItemStack item)
    {
        if(!NBTHelper.hasTag(item, NBTTags.ACTIVE))
        {
            NBTHelper.BOOLEAN.set(item, NBTTags.ACTIVE, true);
            return true;
        }
        return NBTHelper.BOOLEAN.get(item, NBTTags.ACTIVE);
    }

    public static int getMagnitude(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.MAGNITUDE) ? NBTHelper.INT.get(item, NBTTags.MAGNITUDE) : 0;
    }

    public static void setMagnitude(ItemStack item, int magnitude) {
        NBTHelper.INT.set(item, NBTTags.MAGNITUDE, magnitude);
    }
}
