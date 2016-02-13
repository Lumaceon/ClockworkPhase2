package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileClockworkNetworkConnector extends TileEntity implements IClockworkNetworkTile
{
    public ClockworkNetwork clockworkNetwork;

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return this.clockworkNetwork;
    }

    @Override
    public void setClockworkNetwork(ClockworkNetwork clockworkNetwork) {
        this.clockworkNetwork = clockworkNetwork;
    }

    @Override
    public BlockPos getPosition() {
        return getPos();
    }
}
