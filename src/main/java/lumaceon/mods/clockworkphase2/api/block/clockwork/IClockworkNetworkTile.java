package lumaceon.mods.clockworkphase2.api.block.clockwork;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;

/**
 * Tiles implementing this are marked as being part of a clockwork network.
 */
public interface IClockworkNetworkTile
{
    public ClockworkNetwork getClockworkNetwork();
}
