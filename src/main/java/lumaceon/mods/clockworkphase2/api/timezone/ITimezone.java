package lumaceon.mods.clockworkphase2.api.timezone;

import net.minecraft.item.ItemStack;

public interface ITimezone
{
    public float getRange();
    public ItemStack getTimezoneModule(int index);

    public long getMaxTimeSand();
    public long getTimeSand();
    public void setTimeSand(long timeSand);

    /**
     * Adds time sand to this timezone.
     * @param timeSand Amount of time sand to add to this timezone.
     * @return Amount of time sand that was actually added to this timezone.
     */
    public long addTimeSand(long timeSand);

    /**
     * Consumes time sand from this timezone.
     * @param timeSand Amount of time sand to remove from this timezone.
     * @return Amount of time sand that was actually consumed.
     */
    public long consumeTimeSand(long timeSand);
}