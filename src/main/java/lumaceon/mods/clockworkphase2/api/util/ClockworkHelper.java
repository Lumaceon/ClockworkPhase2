package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockwork;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class ClockworkHelper
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    /**
     * Only really relevant for client-side, as it uses the nbt data.
     * @param stack An item stack with energy in the nbt data.
     * @return The damage for the stack.
     */
    public static int getDamageFromEnergyForClient(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null && nbt.hasKey("energy_max") && nbt.hasKey("energy"))
        {
            int maxEnergy = nbt.getInteger("energy_max");
            int energy = nbt.getInteger("energy");
            int damage = stack.getMaxDamage() - (int) ( ((double) energy / (double) maxEnergy) * stack.getMaxDamage() );
            if(damage <= 0)
            {
                damage = 1;
            }
            return damage;
        }
        return stack.getMaxDamage();
    }

    private static ItemStackHandlerClockwork getHandler(ItemStack item)
    {
        if(item == null)
            return null;

        IItemHandler itemHandler = item.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(itemHandler != null && itemHandler instanceof ItemStackHandlerClockwork)
            return (ItemStackHandlerClockwork) itemHandler;

        return null;
    }

    public static int getQuality(ItemStack item, boolean isServer)
    {
        if(isServer)
        {
            ItemStackHandlerClockwork handler = getHandler(item);
            return handler == null ? 0 : handler.getQuality();
        }
        else
        {
            return NBTHelper.INT.get(item, "cw_quality");
        }
    }

    public static int getSpeed(ItemStack item, boolean isServer)
    {
        if(isServer)
        {
            ItemStackHandlerClockwork handler = getHandler(item);
            return handler == null ? 0 : handler.getSpeed();
        }
        else
        {
            return NBTHelper.INT.get(item, "cw_speed");
        }
    }

    public static int getTier(ItemStack item, boolean isServer)
    {
        if(isServer)
        {
            ItemStackHandlerClockwork handler = getHandler(item);
            return handler == null ? 0 : handler.getTier();
        }
        else
        {
            return NBTHelper.INT.get(item, "cw_tier");
        }
    }

    /**
     * Used primarily by tools, where machines tend to have a slightly smaller exponent to them.
     */
    public static int getTensionCostFromStats(int baseCost, int quality, int speed) {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 4));
    }

    /**
     * Machines are a little more friendly with their exponents, since they also grow more costly from the speed itself.
     */
    public static int getTensionCostFromStatsMachine(int baseCost, int quality, int speed) {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 1.5));
    }

    /**
     * Used by machines (or item) that wish to have work speed increase exponentially with the speed stat. This method
     * provides a standard most tiles machines follow, which assumes a 'par' speed of 250.
     * @param speed The speed of the tiles.
     * @return A multiplier to be applied to speed, which can be less than 1 in cases where speed is lower (<250).
     */
    public static double getStandardExponentialSpeedMultiplier(int speed)
    {
        if(speed <= 0)
            return 0;
        double d = (double) speed;
        return Math.pow(d/250.0, 3);
    }
}
