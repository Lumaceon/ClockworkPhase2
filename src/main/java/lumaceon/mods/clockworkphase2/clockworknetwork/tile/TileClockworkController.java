package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.block.clockwork.IMainspringTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileClockworkController extends TileClockworkPhase implements IClockworkNetworkTile
{
    private ClockworkNetwork clockworkNetwork = new ClockworkNetwork();
    boolean setup = false;

    @Override
    public void updateEntity()
    {
        if(!setup)
        {
            addNetworkTileAtLocation(xCoord + 1, yCoord, zCoord);
            addNetworkTileAtLocation(xCoord - 1, yCoord, zCoord);
            addNetworkTileAtLocation(xCoord, yCoord, zCoord + 1);
            addNetworkTileAtLocation(xCoord, yCoord, zCoord - 1);
            addNetworkTileAtLocation(xCoord + 2, yCoord, zCoord);
            addNetworkTileAtLocation(xCoord - 2, yCoord, zCoord);
            addNetworkTileAtLocation(xCoord, yCoord, zCoord + 2);
            addNetworkTileAtLocation(xCoord, yCoord, zCoord - 2);
            setup = true;
        }
    }

    private void addNetworkTileAtLocation(int x, int y, int z) {
        TileEntity te = worldObj.getTileEntity(x, y, z);
        if(te != null)
        {
            if(te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            if(te instanceof IMainspringTile)
                clockworkNetwork.addMainspring((IMainspringTile) te);
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

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) xCoord, (double) yCoord, (double) zCoord) <= 8;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
