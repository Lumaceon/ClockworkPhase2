package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileClockworkController extends TileClockworkPhase implements IClockworkNetworkTile, ITickable
{
    private ClockworkNetwork clockworkNetwork = new ClockworkNetwork();
    boolean setup = false;

    @Override
    public void update()
    {
        if(!setup)
        {
            clockworkNetwork.addNetworkTile(this);
            clockworkNetwork.loadNetwork(worldObj, false);
            setup = true;
        }
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
    public BlockPos getPosition() {
        return getPos();
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()) <= 8;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
