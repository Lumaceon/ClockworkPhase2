package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import lumaceon.mods.clockworkphase2.util.NBTTags;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.apache.commons.lang3.ArrayUtils;

public class HourglassHelper
{
    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME = null;

    public static boolean doesPlayerHaveAnActiveHourglass(EntityPlayer player)
    {
        for(ItemStack stack : player.inventory.mainInventory)
            if(stack != null && stack.getItem() instanceof IHourglass && ((IHourglass) stack.getItem()).isActive(stack))
                return true;
        return false;
    }

    /**
     * Used to acquire only the first hourglass in the give player's inventory.
     * @return The first occurrence of an hourglass in the player's inventory.
     */
    public static ItemStack getFirstActiveHourglass(EntityPlayer player)
    {
        for(ItemStack stack : player.inventory.mainInventory)
            if(stack != null && stack.getItem() instanceof IHourglass && ((IHourglass) stack.getItem()).isActive(stack))
                return stack;
        return null;
    }

    /**
     * Used to get an array of all the active hourglasses in the given player's inventory, skipping over inactive ones.
     * @return A newly constructed array of all the active hourglasses found in the player's inventory.
     */
    public static ItemStack[] getActiveHourglasses(EntityPlayer player)
    {
        ItemStack[] ret = new ItemStack[0];
        for(ItemStack stack : player.inventory.mainInventory)
            if(stack != null && stack.getItem() instanceof IHourglass && ((IHourglass) stack.getItem()).isActive(stack))
                ret = ArrayUtils.add(ret, stack);
        return ret;
    }

    /**
     * Used to get an array of all the hourglasses in the given player's inventory,
     * @return A newly constructed array of all the hourglasses found in the player's inventory.
     */
    public static ItemStack[] getHourglasses(EntityPlayer player)
    {
        ItemStack[] ret = new ItemStack[0];
        for(ItemStack stack : player.inventory.mainInventory)
            if(stack != null && stack.getItem() instanceof IHourglass)
                ret = ArrayUtils.add(ret, stack);
        return ret;
    }

    /**
     * Helper method to simplify getting the total time from several hourglasses.
     * @param hourglasses An array of hourglasses to query for their time contents.
     * @return The total sum, in ticks, of all these time in hourglasses.
     */
    public static int getTimeFromHourglasses(ItemStack[] hourglasses)
    {
        int timeFound = 0;
        for(ItemStack is : hourglasses)
        {
            if(is != null && is.getItem() instanceof IHourglass)
            {
                ITimeStorage timeStorage = is.getCapability(TIME, EnumFacing.DOWN);
                if(timeStorage != null)
                {
                    timeFound += timeStorage.getTimeInTicks();
                }
            }
        }
        return timeFound;
    }

    /**
     * Attempts to consume time from the given array of IHourglass itemstacks. If enough time is found, it will be
     * consumed and this method will return true. Otherwise, no time will be consumed from any of them, and this method
     * will return false.
     * @return True if all the time was consumed, false if there isn't enough and none was consumed.
     */
    public static boolean consumeTimeAllOrNothing(ItemStack[] hourglasses, long timeToConsume)
    {
        int timeAvailable = 0;
        for(ItemStack is : hourglasses)
        {
            if(is != null && is.getItem() instanceof IHourglass)
            {
                ITimeStorage timeStorage = is.getCapability(TIME, EnumFacing.DOWN);
                if(timeStorage != null)
                {
                    timeAvailable += timeStorage.getTimeInTicks();
                }
            }
        }

        if(timeAvailable < timeToConsume)
            return false;
        else
        {
            int timeConsumed = 0;
            for(ItemStack is : hourglasses)
            {
                if(is != null && is.getItem() instanceof IHourglass)
                {
                    ITimeStorage timeStorage = is.getCapability(TIME, EnumFacing.DOWN);
                    if(timeStorage != null)
                    {
                        timeConsumed += timeStorage.extractTime(timeToConsume - timeConsumed);
                    }
                }

                if(timeConsumed >= timeToConsume)
                    return true;
            }
        }
        return false;
    }



    /**
     * Attempts to consume time from the supplied hourglasses. If there is less time available than requested, as much
     * as possible will be consumed.
     * @param timeToConsume The amount of time to attempt to consume.
     * @return The amount of time that was successfully consumed.
     */
    public static long consumeTimeMostPossible(ItemStack[] hourglasses, long timeToConsume)
    {
        int timeConsumed = 0;
        for(ItemStack is : hourglasses)
        {
            if(is != null && is.getItem() instanceof IHourglass)
            {
                ITimeStorage timeStorage = is.getCapability(TIME, EnumFacing.DOWN);
                if(timeStorage != null)
                {
                    timeConsumed += timeStorage.extractTime(timeToConsume - timeConsumed);
                }

                if(timeConsumed >= timeToConsume)
                    return timeConsumed;
            }
        }
        return timeConsumed;
    }

    /**
     * Gets the time to break the block in ticks.
     * @param state The state of the block to break.
     * @param player The player trying to break the block.
     * @param temporalExcavator The temporal excavator itemstack.
     * @return The time, in ticks, it takes to break the block.
     */
    public static long getTimeToBreakBlock(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack temporalExcavator)
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
                        long speed = NBTHelper.LONG.get(temporalExcavator, NBTTags.SPEED);
                        if(speed > 0)
                            strength = Math.max(item.getItem().getStrVsBlock(item, state), strength);
                    }
                }
            }
        }

        float timeCostInTicks = state.getBlockHardness(world, pos) * 1.5F / strength;
        if(player != null)
        {
            if(player.isInWater())
                timeCostInTicks *= 5.0F;
            if(player.isAirBorne)
                timeCostInTicks *= 5.0F;
        }
        return Math.max((int) (timeCostInTicks), 1);
    }
}
