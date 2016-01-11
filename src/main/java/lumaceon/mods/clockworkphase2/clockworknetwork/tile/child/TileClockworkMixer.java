package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockwork;

public class TileClockworkMixer extends TileClockwork
{
    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 2);
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
