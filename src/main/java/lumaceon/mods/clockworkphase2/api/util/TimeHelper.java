package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.time.ITimeSupplierItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TimeHelper
{
    /**
     * Attempts to consume time from items implementing ITimeSupplierItem in the inventory. If enough time is found
     * within the inventory, it will be consumed and this method will return true. Otherwise, no time will be consumed
     * from any of them, and this method will return false.
     * @return True if all the time was consumed, false if there isn't enough and none was consumed.
     */
    public static boolean consumeTimeAllOrNothing(IInventory inventory, int timeToConsume)
    {
        ArrayList<ItemStack> timeSuppliers = new ArrayList<ItemStack>();
        int timeAvailable = 0;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            ItemStack is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ITimeSupplierItem)
            {
                timeSuppliers.add(is);
                timeAvailable += ((ITimeSupplierItem) is.getItem()).getTimeStored(is);
            }
        }

        if(timeAvailable < timeToConsume)
            return false;
        else
        {
            int timeConsumed = 0;
            for(ItemStack is : timeSuppliers)
            {
                timeConsumed += ((ITimeSupplierItem) is.getItem()).extractTime(is, timeToConsume - timeConsumed, false);
                if(timeConsumed >= timeToConsume)
                    return true;
            }
        }
        return false;
    }

    /**
     * Attempts to consume time from items implementing ITimeSupplierItem in the inventory. If there is less time
     * available than requested, as much as possible will be consumed.
     * @param timeToConsume The amount of time to attempt to consume.
     * @return The amount of time that was successfully consumed.
     */
    public static int consumeTimeMostPossible(IInventory inventory, int timeToConsume)
    {
        int timeConsumed = 0;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            ItemStack is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ITimeSupplierItem)
            {
                timeConsumed += ((ITimeSupplierItem) is.getItem()).extractTime(is, timeToConsume - timeConsumed, false);
                if(timeConsumed >= timeToConsume)
                    return timeConsumed;
            }
        }
        return timeConsumed;
    }

    public static int getTimeInInventory(IInventory inventory)
    {
        int timeFound = 0;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            ItemStack is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ITimeSupplierItem)
                timeFound += ((ITimeSupplierItem) is.getItem()).getTimeStored(is);
        }
        return timeFound;
    }

    /**
     * Gets the time to break the block in seconds (1 = 1 second). This defaults to the speed of diamond tools if there
     * is no override tool in the excavator.
     * @param blockHardness The hardness of the block.
     * @param player The player trying to break the block.
     * @param temporalExcavator The temporal excavator itemstack.
     * @return The time, in ticks, it takes to break the block.
     */
    public static int timeToBreakBlock(float blockHardness, EntityPlayer player, ItemStack temporalExcavator)
    {
        float timeCostInSeconds = blockHardness * 1.5F / 8.0F;
        if(player.isInWater())
            timeCostInSeconds *= 5.0F;
        if(player.isAirBorne)
            timeCostInSeconds *= 5.0F;
        return (int) (timeCostInSeconds * 20);
    }
}
