package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeConnection;
import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import lumaceon.mods.clockworkphase2.util.TemporalConduitNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileTemporalConduit extends TileClockworkPhase implements ITimeConnection
{
    public TemporalConduitNetwork TCN;
    public boolean[] connections = new boolean[6];
    private boolean hasInitialUpdated = false;
    private long extractionRate = TimeConverter.MINUTE;
    private long timeBuffer = 0;

    @Override
    public void updateEntity()
    {
        if(!hasInitialUpdated)
        {
            hasInitialUpdated = true;
            if(TCN == null)
                updateNetwork(worldObj, xCoord, yCoord, zCoord);
            else
                updateConnections(xCoord, yCoord, zCoord);
        }

        if(timeBuffer < extractionRate)
        {
            for(int n = 0; n < 6; n++)
            {
                ForgeDirection direction = ForgeDirection.getOrientation(n);
                TileEntity te = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                if(te != null && te instanceof ITimeProvider)
                    timeBuffer += ((ITimeProvider) te).extractTime(extractionRate - timeBuffer, false);
            }
        }

        if(timeBuffer > 0 && TCN != null)
            timeBuffer -= TCN.distributeTime(timeBuffer);
    }

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    /**
     * Called whenever this or neighboring blocks are changed to set valid connections.
     */
    public void updateConnections(int x, int y, int z)
    {
        TileEntity te = worldObj.getTileEntity(x, y + 1, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.UP))
            connections[ForgeDirection.UP.ordinal()] = true;
        else
            connections[ForgeDirection.UP.ordinal()] = false;

        te = worldObj.getTileEntity(x, y - 1, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.DOWN))
            connections[ForgeDirection.DOWN.ordinal()] = true;
        else
            connections[ForgeDirection.DOWN.ordinal()] = false;

        te = worldObj.getTileEntity(x, y, z - 1);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.NORTH))
            connections[ForgeDirection.NORTH.ordinal()] = true;
        else
            connections[ForgeDirection.NORTH.ordinal()] = false;

        te = worldObj.getTileEntity(x, y, z + 1);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.SOUTH))
            connections[ForgeDirection.SOUTH.ordinal()] = true;
        else
            connections[ForgeDirection.SOUTH.ordinal()] = false;

        te = worldObj.getTileEntity(x - 1, y, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.WEST))
            connections[ForgeDirection.WEST.ordinal()] = true;
        else
            connections[ForgeDirection.WEST.ordinal()] = false;

        te = worldObj.getTileEntity(x + 1, y, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.EAST))
            connections[ForgeDirection.EAST.ordinal()] = true;
        else
            connections[ForgeDirection.EAST.ordinal()] = false;
    }

    public TemporalConduitNetwork updateNetwork(World world, int x, int y, int z)
    {
        updateConnections(x, y, z);
        TileEntity te;
        TileTemporalConduit conduit;
        TemporalConduitNetwork TCN = new TemporalConduitNetwork();
        ArrayList<TileTemporalConduit> conduitsToCheck = new ArrayList<TileTemporalConduit>();
        ArrayList<TileTemporalConduit> conduitsAlreadyChecked = new ArrayList<TileTemporalConduit>();

        te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileTemporalConduit)
            conduitsToCheck.add((TileTemporalConduit) te);

        while(!conduitsToCheck.isEmpty())
        {
            conduit = conduitsToCheck.get(0);
            conduitsToCheck.remove(conduit);
            conduitsAlreadyChecked.add(conduit);
            for(int n = 0; n < 6; n++)
            {
                ForgeDirection direction = ForgeDirection.getOrientation(n);
                te = world.getTileEntity(conduit.xCoord + direction.offsetX, conduit.yCoord + direction.offsetY, conduit.zCoord + direction.offsetZ);
                if(te != null)
                {
                    if(te instanceof ITimeReceiver && ((ITimeReceiver) te).canConnectFrom(direction))
                        TCN.addDestination((ITimeReceiver) te);
                    if(te instanceof TileTemporalConduit)
                    {
                        TCN.addConduit((TileTemporalConduit) te);
                        if(!conduitsAlreadyChecked.contains((TileTemporalConduit) te))
                            conduitsToCheck.add((TileTemporalConduit) te);
                    }
                }
            }
        }

        return TCN;
    }
}
