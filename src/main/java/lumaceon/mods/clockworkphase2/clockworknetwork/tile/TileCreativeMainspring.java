package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IMainspringTile;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileCreativeMainspring extends TileClockworkPhase implements ITickable, IMainspringTile
{
    protected long uniqueID = -1;

    protected ClockworkNetwork clockworkNetwork;
    boolean hasSetup = false;

    @Override
    public void update()
    {
        if(uniqueID == -1)
        {
            uniqueID = System.currentTimeMillis();
            markDirty();
            worldObj.markBlockForUpdate(getPos());
        }

        if(!hasSetup)
        {
            if(clockworkNetwork == null)
            {
                clockworkNetwork = new ClockworkNetwork();
                clockworkNetwork.addNetworkTile(this);
                clockworkNetwork.loadNetwork(worldObj, false);
            }
            hasSetup = true;
        }
    }

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
    public int addTension(int tensionToAdd) {
        return 0;
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

    @Override
    public long getUniqueID() {
        return uniqueID;
    }

    @Override
    public void setUniqueID(long uniqueID) {
        this.uniqueID = uniqueID;
        markDirty();
        worldObj.markBlockForUpdate(getPos());
    }
}
