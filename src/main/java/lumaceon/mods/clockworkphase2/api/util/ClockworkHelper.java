package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.ItemStack;

public class ClockworkHelper
{
    public static int getMaxTension(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.MAX_TENSION) ? NBTHelper.INT.get(item, NBTTags.MAX_TENSION) : 0;
    }

    public static int getTension(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.CURRENT_TENSION) ? NBTHelper.INT.get(item, NBTTags.CURRENT_TENSION) : 0;
    }

    public static int getQuality(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.QUALITY) ? NBTHelper.INT.get(item, NBTTags.QUALITY) : 0;
    }

    public static int getSpeed(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.SPEED) ? NBTHelper.INT.get(item, NBTTags.SPEED) : 0;
    }

    public static int getTier(ItemStack item) {
        return NBTHelper.hasTag(item, NBTTags.TIER) ? NBTHelper.INT.get(item, NBTTags.TIER) : 0;
    }

    public static void setTension(ItemStack item, int tension) {
        NBTHelper.INT.set(item, NBTTags.CURRENT_TENSION, tension);
    }

    public static void setTier(ItemStack item, int tier) {
        NBTHelper.INT.set(item, NBTTags.TIER, tier);
    }

    public static int addTension(ItemStack item, int tension)
    {
        if(item.getItem() instanceof IClockworkConstruct)
        {
            IClockworkConstruct construct = (IClockworkConstruct) item.getItem();
            int overspill;
            int maxTension = construct.getMaxTension(item);
            int currentTension = construct.getTension(item);

            if(currentTension + tension >= maxTension)
            {
                construct.setTension(item, maxTension);
                overspill = maxTension - currentTension;
                currentTension = maxTension;
            }
            else
            {
                construct.setTension(item, currentTension + tension);
                overspill = tension;
                currentTension += tension;
            }

            if(maxTension <= 0)
                item.setItemDamage(item.getMaxDamage());
            else
                item.setItemDamage((int) (item.getMaxDamage() - ((float) currentTension / maxTension) * item.getMaxDamage()));
            if(item.getItemDamage() == 0)
                item.setItemDamage(1);
            return overspill;
        }
        return tension;
    }

    public static int consumeTension(ItemStack item, int tension)
    {
        if(item.getItem() instanceof IClockworkConstruct)
        {
            IClockworkConstruct construct = (IClockworkConstruct) item.getItem();
            int overspill;
            int maxTension = construct.getMaxTension(item);
            int currentTension = construct.getTension(item);

            if(currentTension - tension <= 0)
            {
                construct.setTension(item, 0);
                overspill = currentTension;
                currentTension = 0;
            }
            else
            {
                construct.setTension(item, currentTension - tension);
                overspill = tension;
                currentTension -= tension;
            }

            if(maxTension <= 0)
                item.setItemDamage(item.getMaxDamage());
            else
                item.setItemDamage((int) (item.getMaxDamage() - ((float) currentTension / maxTension) * item.getMaxDamage()));
            if(item.getItemDamage() == 0)
                item.setItemDamage(1);
            return overspill;
        }
        return tension;
    }

    public static int getTensionCostFromStats(int baseCost, int quality, int speed) {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 2));
    }

    /**
     * Used by machines (or items) that wish to have work speed increase exponentially with the speed stat. This method
     * provides a standard most clockwork machines follow, which assumes a 'par' speed of 200.
     * @param speed The speed of the clockwork.
     * @return A multiplier for the work done per tick, which can be less than 1 in cases where speed is poor (<200).
     */
    public static double getStandardExponentialSpeedMultiplier(int speed)
    {
        if(speed <= 0)
            return 0;
        double d = (double) speed;
        return Math.pow(d/200.0, 3.5);
    }
}
