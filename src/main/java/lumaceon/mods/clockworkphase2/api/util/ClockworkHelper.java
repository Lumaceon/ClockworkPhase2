package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockwork;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class ClockworkHelper
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    private static ItemStackHandlerClockwork getHandler(ItemStack item)
    {
        if(item == null)
            return null;

        IItemHandler itemHandler = item.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(itemHandler != null && itemHandler instanceof ItemStackHandlerClockwork)
            return (ItemStackHandlerClockwork) itemHandler;

        return null;
    }

    public static int getQuality(ItemStack item) {
        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getQuality();
    }

    public static int getSpeed(ItemStack item) {
        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getSpeed();
    }

    public static int getTier(ItemStack item) {
        ItemStackHandlerClockwork handler = getHandler(item);
        return handler == null ? 0 : handler.getTier();
    }

    public static int getTensionCostFromStats(int baseCost, int quality, int speed) {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 4));
    }

    /**
     * Machines are a little more friendly with their exponents, since they also grow more costly from the speed itself.
     */
    public static int getTensionCostFromStatsMachine(int baseCost, int quality, int speed) {
        float efficiency = (float) speed / quality;
        return (int) Math.round(baseCost * Math.pow(efficiency, 1.7));
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
        return Math.pow(d/250.0, 4);
    }
}
