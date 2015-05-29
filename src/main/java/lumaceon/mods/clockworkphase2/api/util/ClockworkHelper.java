package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.item.ItemStack;

public class ClockworkHelper
{
    public static final String MAX_TENSION = "max_tension";
    public static final String CURRENT_TENSION = "current_tension";
    public static final String QUALITY = "cp_quality";
    public static final String SPEED = "cp_speed";
    public static final String MEMORY = "cp_memory";

    public static int getMaxTension(ItemStack item)
    {
        return NBTHelper.hasTag(item, MAX_TENSION) ? NBTHelper.INT.get(item, MAX_TENSION) : 0;
    }

    public static int getTension(ItemStack item)
    {
        return NBTHelper.hasTag(item, CURRENT_TENSION) ? NBTHelper.INT.get(item, CURRENT_TENSION) : 0;
    }

    public static int getQuality(ItemStack item)
    {
        return NBTHelper.hasTag(item, QUALITY) ? NBTHelper.INT.get(item, QUALITY) : 0;
    }

    public static int getSpeed(ItemStack item)
    {
        return NBTHelper.hasTag(item, SPEED) ? NBTHelper.INT.get(item, SPEED) : 0;
    }

    public static int getMemory(ItemStack item)
    {
        return NBTHelper.hasTag(item, MEMORY) ? NBTHelper.INT.get(item, MEMORY) : 0;
    }

    public static void setTension(ItemStack item, int tension)
    {
        NBTHelper.INT.set(item, CURRENT_TENSION, tension);
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

    public static int getTensionCostFromStats(int baseCost, int quality, int speed)
    {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 2));
    }

    public static int getTimeSandFromStats(int memory)
    {
        return (int) (3 * (Math.pow(memory, 1.5)));
    }
}
