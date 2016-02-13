package lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;

public interface IClockworkNetworkMachine extends IClockworkNetworkTile
{
    /**
     * Go through a proxy to separate client and server code here.
     * @return A Clockwork Network GUI class representing this gui.
     */
    public ClockworkNetworkContainer getGui();
}
