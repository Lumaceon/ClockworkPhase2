package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.nbt.NBTTagCompound;

public class TileAssemblyTable extends TileClockworkPhase
{
    private int blocksToPlace = 1;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");
    }

    @Override
    public void updateEntity()
    {
        handleMultiblockPlacement();
    }

    private void handleMultiblockPlacement()
    {
        if(!worldObj.isRemote)
        {
            if(blocksToPlace == 0)
                blocksToPlace = -1;
            else if(blocksToPlace > 0)
            {
                if(worldObj.isAirBlock(xCoord, yCoord + 1, zCoord))
                {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, ModBlocks.assemblyTableSB, blockMetadata, 2);
                    blocksToPlace--;
                }
                else
                {
                    //TODO particley stuff.
                }
            }
        }
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}
}
