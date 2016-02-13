package lumaceon.mods.clockworkphase2.api.time;


/**
 * Implemented on tile entity classes which should receive time.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeReceiver extends ITimeConnection
{
    /**
     * Called to add time to this tile entity.
     *
     * @param maxReceive The amount of time trying to be added to storage.
     * @param simulate If true, a simulation will occur, but no time will actually be added.
     * @return The amount of time that was added successfully (or would have been added if simulated).
     */
    public int receiveTime(int maxReceive, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public int getMaxCapacity();

    /**
     * Returns the amount of time that's stored.
     */
    public int getTimeStored();
}
