package lumaceon.mods.clockworkphase2.api.temporal.timezone;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A capability interface to be applied to items. Items implementing this as a capability will be treated as a timezone
 * function item and will be accepted into the Temporal Zoning Machine.
 *
 * Generally, implementations of this tell a timezone how to do something, or allow the storing of something inside a
 * timezone, such as liquid.
 */
public interface ITimezoneFunction
{
    /**
     * Called each tick during the timezone provider's TileEntity's update function.
     * @param timezone The timezone this timezone function is a part of, or null if called from elsewhere.
     */
    public void onUpdate(ITimezone timezone);

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound nbt);
}
