package lumaceon.mods.clockworkphase2.api.time;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * The base class for ITimeReceiver and ITimeProvider. You will probably want one (or both) of those instead.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeConnection
{
    /**
     * Returns true if a connection from the given side is valid. Usually used for temporal conduits and similar tiles.
     */
    public boolean canConnectFrom(ForgeDirection from);
}
