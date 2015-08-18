package lumaceon.mods.clockworkphase2.api.time;

/**
 * An interface for time storage and interaction. Should be implemented on a class which is then used to interact with
 * the internal time storage. Do not implement this on a tile entity class itself.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeStorage
{
    /**
     * Recieve time into this storage.
     * @param time The kind of time this storage is receiving.
     * @param maxReceive The amount of time trying to be added to storage.
     * @param simulate If true, a simulation will occur, but no time will actually be added.
     * @return The amount of time that was added successfully (or would have been added if simulated).
     */
    public long receiveTime(Time time, long maxReceive, boolean simulate);

    /**
     * Extract time from this storage.
     * @param time The kind of time to be extracted.
     * @param maxExtract The amount of time trying to be removed from storage.
     * @param simulate If true, a simulation will occur, but no time will actually be removed.
     * @return The amount of time that was removed successfully (or would have been removed if simulated).
     */
    public long extractTime(Time time, long maxExtract, boolean simulate);

    /**
     * Returns the sum of all times stored, appropriately weighted based on their magnitude.
     * For example, 3 seconds of "magnitude 1" and 2 seconds of "magnitude 3" would return 9 * (TimeConverter.SECOND).
     */
    public long getTotalTimeStored();

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public long getMaxTotalTimeStorage();

    /**
     * Returns the amount of the specified type of time that's in this storage.
     */
    public long getTimeStored(Time time);

    /**
     * Returns the maximum amount of time of the specified type that can still be added.
     * This is translated to the magnitude internally, so if there are 2 seconds of empty space, this should return
     * 1 * (TimeConverter.SECOND) when called with a time of magnitude 2 passed in for time.
     */
    public long getEmptySpace(Time time);
}
