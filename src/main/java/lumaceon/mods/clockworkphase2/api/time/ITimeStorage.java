package lumaceon.mods.clockworkphase2.api.time;

/**
 * An interface for time storage and interaction. Should be implemented on a class which is then used to represent the
 * internal time storage. Do not implement this on a tile entity class. Instead, use the TimeStorage class or implement
 * your own.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeStorage
{
    /**
     * Receive time into this storage.
     * @param maxReceive The amount of time trying to be added to storage.
     * @param simulate If true, a simulation will occur, but no time will actually be added.
     * @return The amount of time that was added successfully (or would have been added if simulated).
     */
    public int receiveTime(int maxReceive, boolean simulate);

    /**
     * Extract time from this storage.
     * @param maxExtract The amount of time trying to be removed from storage.
     * @param simulate If true, a simulation will occur, but no time will actually be removed.
     * @return The amount of time that was removed successfully (or would have been removed if simulated).
     */
    public int extractTime(int maxExtract, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public int getMaxCapacity();

    /**
     * Returns the amount of time that's in this storage.
     */
    public int getTimeStored();
}
