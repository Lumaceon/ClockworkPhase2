package lumaceon.mods.clockworkphase2.api.block;

import net.minecraft.item.ItemStack;

public interface ITimezoneProvider
{
    public float getRange();
    public int getX();
    public int getY();
    public int getZ();

    /**
     * Indices 0-7 are the outer circles, started at the north-side and moving clockwise. Index 8 is the center.
     * @return The timestream is the given index, or null if none exist.
     */
    public ItemStack getTimezoneModule(int index);

    public void setTimestream(int index, ItemStack item);

    public int getMaxTimeSand();
    public int getTimeSand();
    public void setTimeSand(int timeSand);

    /**
     * Adds time sand to this timezone.
     * @param timeSand Amount of time sand to add to this timezone.
     * @return The amount of time sand successfully added.
     */
    public int addTimeSand(int timeSand);

    /**
     * Consumes time sand from this timezone.
     * @param timeSand Amount of time sand to remove from this timezone.
     * @return The amount of time sand successfully consumed.
     */
    public int consumeTimeSand(int timeSand);
}