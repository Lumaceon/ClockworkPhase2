package lumaceon.mods.clockworkphase2.api.timezone;

public interface ITimeSandTile
{
    public long getTimeSandMax();
    public long getTimeSand();

    /**
     * Adds time sand to this tile.
     * @param timeSand The amount of time sand to add.
     * @return The amount that was actually added.
     */
    public long addTimeSand(long timeSand);

    /**
     * Removes time sand from this tile.
     * @param timeSand The amount of time sand to remove.
     * @return The amount that was actually removed.
     */
    public long consumeTimeSand(long timeSand);
}
