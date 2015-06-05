package lumaceon.mods.clockworkphase2.api.timezone;

import net.minecraft.item.ItemStack;

public interface ITimezone
{
    public float getRange();

    /**
     * Indices 0-7 are the outer circles, started at the north-side and moving clockwise. Index 8 is the center.
     * @return The timestream is the given index, or null if none exist.
     */
    public ItemStack getTimestream(int index);

    public void setTimestream(int index, ItemStack item);

    public long getMaxTimeSand();
    public long getTimeSand();
    public void setTimeSand(long timeSand);

    /**
     * Adds time sand to this timezone.
     * @param timeSand Amount of time sand to add to this timezone.
     * @return The amount of time sand successfully added.
     */
    public long addTimeSand(long timeSand);

    /**
     * Consumes time sand from this timezone.
     * @param timeSand Amount of time sand to remove from this timezone.
     * @return The amount of time sand successfully consumed.
     */
    public long consumeTimeSand(long timeSand);
}