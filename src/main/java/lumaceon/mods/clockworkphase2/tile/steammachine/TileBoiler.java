package lumaceon.mods.clockworkphase2.tile.steammachine;

import lumaceon.mods.clockworkphase2.api.steammachine.ISteamProvider;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBoiler extends TileClockworkPhase implements ISteamProvider
{
    private int steam = 0;

    @Override
    public void setState(int state) {

    }

    @Override
    public void setStateAndUpdate(int state) {

    }

    @Override
    public int drain(ForgeDirection drainingFrom, int amountToDrain)
    {
        if(!canDrainFrom(drainingFrom) || amountToDrain <= 0)
            return 0;

        return Math.min(steam, amountToDrain);
    }

    @Override
    public int getSteamAvailable(ForgeDirection drainingFrom) {
        return steam;
    }

    @Override
    public boolean canDrainFrom(ForgeDirection direction) {
        return true;
    }
}
