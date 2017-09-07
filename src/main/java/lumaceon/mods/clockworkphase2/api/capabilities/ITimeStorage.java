package lumaceon.mods.clockworkphase2.api.capabilities;

public interface ITimeStorage
{
    /**
     * Inserts the given amount of time into this storage.
     * @param ticksToInsert The time, in ticks, to insert into this storage.
     * @return The amount of ticks accepted into this storage.
     */
    long insertTime(long ticksToInsert);

    /**
     * Extracts the given amount of time out of this storage.
     * @param ticksToExtract The time, in ticks, to extract out of this storage.
     * @return The amount of ticks removed from this storage.
     */
    long extractTime(long ticksToExtract);

    /**
     * Returns the time stored.
     * @return How much time is currently stored.
     */
    long getTimeInTicks();

    /**
     * How much time can currently be stored in here?
     * @return The max capacity of this storage.
     */
    long getMaxCapacity();

    /**
     * Sets a new maximum capacity for this storage. If downsizing occurs, this returns the amount of time removed.
     * @param maxCapacity New max capacity to set this storage to.
     * @return The amount of time that was removed due to downsizing, or 0 if no time was lost.
     */
    long setMaxCapacity(long maxCapacity);

    void setTime(long newTime);
}
