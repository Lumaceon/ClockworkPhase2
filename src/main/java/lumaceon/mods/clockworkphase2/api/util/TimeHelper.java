package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.time.ITimeSupplierItem;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class TimeHelper
{
    /**
     * Attempts to consume time from item implementing ITimeSupplierItem in the inventory. If enough time is found
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
     * Attempts to consume time from item implementing ITimeSupplierItem in the inventory. If there is less time
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

    /**
     * Provides a multiplier for the xp -> time conversion of the temporal hourglass. This is purely beneficial and
     * never falls below x1. This increases with the player's xp level to give them incentive to walk around risking
     * high levels.
     */
    public static float getXPToTimeMultiplier(EntityPlayer player) {
        return (float) Math.max(Math.pow(player.experienceLevel, 1.5) / 100.0F, 1.0F);
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
     * Gets the time to break the block in seconds (1 = 1 first).
     * @param blockToBreak The block to break.
     * @param player The player trying to break the block.
     * @param temporalExcavator The temporal excavator itemstack.
     * @return The time, in ticks, it takes to break the block.
     */
    public static int getTimeToBreakBlock(World world, BlockPos pos, Block blockToBreak, EntityLivingBase player, ItemStack temporalExcavator)
    {
        float strength = 1.0F;
        if(temporalExcavator != null)
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(temporalExcavator, NBTTags.COMPONENT_INVENTORY);
            if(items != null)
            {
                for(int n = 0; n < 3 && n < items.length; n++)
                {
                    ItemStack item = items[n];
                    if(item != null)
                    {
                        int speed = NBTHelper.INT.get(temporalExcavator, NBTTags.SPEED);
                        if(speed > 0)
                            strength = Math.max(item.getItem().getStrVsBlock(item, blockToBreak), strength);
                    }
                }
            }
        }

        float timeCostInSeconds = blockToBreak.getBlockHardness(world, pos) * 1.5F / strength;
        if(player != null)
        {
            if(player.isInWater())
                timeCostInSeconds *= 5.0F;
            if(player.isAirBorne)
                timeCostInSeconds *= 5.0F;
        }
        return Math.max((int) (timeCostInSeconds * 20), 1);
    }
}
