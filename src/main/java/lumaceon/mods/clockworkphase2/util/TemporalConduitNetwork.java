package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalConduit;

import java.util.ArrayList;

/**
 * Used to represent a network of conduits, allowing a conduit to easily find out where it's sending it's stuff to.
 */
public class TemporalConduitNetwork
{
    public ArrayList<TileTemporalConduit> conduits = new ArrayList<TileTemporalConduit>(20);
    public ArrayList<ITimeReceiver> destinations = new ArrayList<ITimeReceiver>(5);

    /** Gets set to 100 every tick this network is active, and goes down once per render tick. **/
    public int visualTimer = 0;
    public boolean hasVisualTimerUpdated = false;

    public void addConduit(TileTemporalConduit conduit) {
        conduit.TCN = this;
        if(!conduits.contains(conduit))
            conduits.add(conduit);
    }

    public void addDestination(ITimeReceiver timeReceiver) {
        if(!destinations.contains(timeReceiver))
            destinations.add(timeReceiver);
    }

    /**
     * Distributes time semi-equally among the destinations.
     * @param timeToDistribute The amount of time to distribute.
     * @return The amount of time that was consumed by this process.
     */
    public long distributeTime(long timeToDistribute)
    {
        long initialTimeToDistribute = timeToDistribute;
        int divisor = destinations.size();
        if(divisor <= 0)
            return 0;

        long amountPerDestination = timeToDistribute / divisor;
        if(amountPerDestination <= 0)
            return 0;

        for(ITimeReceiver receiver : destinations)
            timeToDistribute -= receiver.receiveTime(amountPerDestination, false);

        if(timeToDistribute > 0)
        {
            for(ITimeReceiver receiver : destinations)
            {
                timeToDistribute -= receiver.receiveTime(timeToDistribute, false);
                if(timeToDistribute <= 0)
                    break;
            }
        }
        return initialTimeToDistribute - timeToDistribute;
    }
}
