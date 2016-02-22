package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

import java.util.ArrayList;

public class TileClockworkController extends TileClockworkPhase implements IClockworkNetworkTile, ITickable
{
    protected ClockworkNetwork clockworkNetwork;
    protected ArrayList<SetupComponent> config = new ArrayList<SetupComponent>();

    protected long uniqueID = -1;
    private boolean setup = false;

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if(uniqueID != -1)
            nbt.setLong("uniqueID_CP", uniqueID);

        NBTTagList list = new NBTTagList();
        NBTTagCompound temp;
        for(SetupComponent data : config)
            if(data != null && data.tile != null)
            {
                temp = new NBTTagCompound();
                temp.setLong("cn_UID", data.tile.getUniqueID());
                if(data.tile instanceof IClockworkNetworkMachine && ((IClockworkNetworkMachine) data.tile).getTargetInventory() != null)
                    temp.setLong("cn_target_UID", ((IClockworkNetworkMachine) data.tile).getTargetInventory().getUniqueID());
                temp.setInteger("x", data.x);
                temp.setInteger("y", data.y);
            }
        nbt.setTag("components", list);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        if(nbt.hasKey("uniqueID_CP"))
            uniqueID = nbt.getLong("uniqueID_CP");

        if(nbt.hasKey("components"))
            newSettings(nbt);
    }

    public void newSettings(NBTTagCompound nbt)
    {
        NBTBase maybeList = nbt.getTag("components");
        if(maybeList != null && maybeList instanceof NBTTagList)
        {
            config.clear(); //Clear the old config, we're building the new one from scratch.
            NBTTagList list = (NBTTagList) maybeList;
            for(int n = 0; n < list.tagCount(); n++) //Go through the list
            {
                NBTBase t = list.get(n);
                if(t != null && t instanceof NBTTagCompound)
                {
                    NBTTagCompound temp = (NBTTagCompound) t;
                    long uid = temp.getLong("cn_UID");
                    IClockworkNetworkTile tempTile = clockworkNetwork.getTile(uid); //Grab the tile we're working with.
                    if(tempTile != null)
                    {
                        //Set the target if one is specified and valid.
                        if(tempTile instanceof IClockworkNetworkMachine && temp.hasKey("cn_target_UID"))
                        {
                            uid = temp.getLong("cn_target_UID");
                            IClockworkNetworkTile tempTarget = clockworkNetwork.getTile(uid);
                            if(tempTarget != null)
                                ((IClockworkNetworkMachine) tempTile).setTargetInventory(tempTarget);
                        }
                        int x = temp.getInteger("x");
                        int y = temp.getInteger("y");
                        SetupComponent comp = new SetupComponent(tempTile, x ,y); //Finish creating the component...
                        this.config.add(comp); //...and add it to the list for the controller.
                    }
                }
            }
        }
    }

    @Override
    public void update()
    {
        if(uniqueID == -1)
        {
            uniqueID = System.currentTimeMillis();
            markDirty();
            worldObj.markBlockForUpdate(getPos()); //TODO 'efficientize' this.
        }

        if(!setup)
        {
            if(clockworkNetwork == null)
            {
                clockworkNetwork = new ClockworkNetwork();
                clockworkNetwork.addNetworkTile(this);
                clockworkNetwork.loadNetwork(worldObj, false);
            }
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

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()) <= 8;
    }

    public static class SetupComponent
    {
        public IClockworkNetworkTile tile;
        public int x, y;
        public SetupComponent(IClockworkNetworkTile tile, int x, int y)
        {
            this.tile = tile;
            this.x = x;
            this.y = y;
        }
    }
}
