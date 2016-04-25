package lumaceon.mods.clockworkphase2.api.time.timezone;

public interface ITimezoneProvider
{
    public Timezone getTimezone();
    public float getRange();
    public int getX();
    public int getY();
    public int getZ();

    public int getMaxTime();
    public int getTime();

    /**
     * Adds time to this timezone.
     * @param time Amount of time to add to this timezone.
     * @return The amount of time successfully added.
     */
    public int addTime(int time);

    /**
     * Consumes time from this timezone.
     * @param time Amount of time to remove from this timezone.
     * @return The amount of time successfully consumed.
     */
    public int consumeTime(int time);
}