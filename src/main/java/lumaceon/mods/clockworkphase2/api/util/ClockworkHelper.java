package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockwork;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

public class ClockworkHelper
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    /**
     * @param stack An item stack with energy either in NBT or capabilities.
     * @return The damage for the stack.
     */
    public static int getDamageFromEnergyForClient(ItemStack stack)
    {
        int maxEnergy = 1;
        int energy = 0;

        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null && nbt.hasKey("energy_max") && nbt.hasKey("energy"))
        {
            maxEnergy = nbt.getInteger("energy_max");
            energy = nbt.getInteger("energy");
        }
        else
        {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(energyStorage != null)
            {
                maxEnergy = energyStorage.getMaxEnergyStored();
                energy = energyStorage.getEnergyStored();
            }
        }

        if(maxEnergy < 1)
        {
            return stack.getMaxDamage();
        }

        int damage = stack.getMaxDamage() - (int) ( ((double) energy / (double) maxEnergy) * stack.getMaxDamage() );
        if(damage <= 0)
        {
            damage = 1;
        }
        return damage;
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

    public static int getQuality(ItemStack item)
    {
        if(NBTHelper.hasTag(item, "cw_quality"))
        {
            int quality = NBTHelper.INT.get(item, "cw_quality");
            if(quality > 0)
                return quality;
        }

        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getQuality();
    }

    public static int getSpeed(ItemStack item)
    {
        if(NBTHelper.hasTag(item, "cw_speed"))
        {
            int speed = NBTHelper.INT.get(item, "cw_speed");
            if(speed > 0)
                return speed;
        }
        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getSpeed();
    }

    public static int getTier(ItemStack item)
    {
        if(NBTHelper.hasTag(item, "cw_tier"))
        {
            int tier = NBTHelper.INT.get(item, "cw_tier");
            if(tier > 0)
                return tier;
        }
        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getTier();
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
