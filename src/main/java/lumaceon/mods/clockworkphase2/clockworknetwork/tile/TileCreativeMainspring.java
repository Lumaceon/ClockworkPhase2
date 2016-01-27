package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IMainspringTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;

public class TileCreativeMainspring extends TileClockworkPhase implements IMainspringTile
{
    protected ClockworkNetwork clockworkNetwork;

    @Override
    public int getTension() {
        return 10000000;
    }

    @Override
    public int getMaxTension() {
        return 10000000;
    }

    @Override
    public int consumeTension(int tensionToConsume) {
        return tensionToConsume;
    }

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return clockworkNetwork;
    }

    @Override
    public void setClockworkNetwork(ClockworkNetwork clockworkNetwork) {
        this.clockworkNetwork = clockworkNetwork;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
