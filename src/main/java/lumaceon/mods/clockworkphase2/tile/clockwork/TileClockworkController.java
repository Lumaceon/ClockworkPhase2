package lumaceon.mods.clockworkphase2.tile.clockwork;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.util.ClockworkNetwork;
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
            TileEntity te = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);

            te = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            te = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
            if(te != null && te instanceof IClockworkNetworkMachine)
                clockworkNetwork.addMachine((IClockworkNetworkMachine) te);
            setup = true;
        }
    }

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return clockworkNetwork;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) xCoord, (double) yCoord, (double) zCoord) <= 8;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
