package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTDA extends TileClockworkPhase
{
    private int blocksToPlace = 55;
    private boolean rendererSetup = false;

    public boolean isAvailable() {
        return blocksToPlace < 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");
    }

    @Override
    public void updateEntity() {
        if(!isAvailable())
            handleMultiblockPlacement();

        if(worldObj.isRemote && !rendererSetup)
        {
            ClockworkPhase2.proxy.addWorldRenderer(worldObj, xCoord, yCoord, zCoord, 2);
            rendererSetup = true;
        }
    }

    private void handleMultiblockPlacement()
    {
        if(!this.worldObj.isRemote)
        {
            if(blocksToPlace == 0)
            {
                for(int n = 0; n < BlockPatterns.TDA.length; n++)
                {
                    int x, y, z;
                    if(blockMetadata == ForgeDirection.EAST.ordinal() || blockMetadata == ForgeDirection.WEST.ordinal())
                    {
                        x = this.xCoord + BlockPatterns.TDA[n].z;
                        y = this.yCoord + 14 + BlockPatterns.TDA[n].y;
                        z = this.zCoord + BlockPatterns.TDA[n].x;
                    }
                    else if(blockMetadata == ForgeDirection.DOWN.ordinal() || blockMetadata == ForgeDirection.UP.ordinal())
                    {
                        x = this.xCoord + BlockPatterns.TDA[n].x;
                        y = this.yCoord + BlockPatterns.TDA[n].z;
                        z = this.zCoord + 14 + BlockPatterns.TDA[n].y;
                    }
                    else
                    {
                        x = this.xCoord + BlockPatterns.TDA[n].x;
                        y = this.yCoord + 14 + BlockPatterns.TDA[n].y;
                        z = this.zCoord + BlockPatterns.TDA[n].z;
                    }
                    this.worldObj.markBlockForUpdate(x, y, z);
                    blocksToPlace--;
                }
            }
            else if(blocksToPlace > 0)
            {
                int x, y, z, meta;
                if(blockMetadata == ForgeDirection.EAST.ordinal() || blockMetadata == ForgeDirection.WEST.ordinal())
                {
                    x = this.xCoord + BlockPatterns.TDA[blocksToPlace - 1].z;
                    y = this.yCoord + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                    z = this.zCoord + BlockPatterns.TDA[blocksToPlace - 1].x;
                }
                else if(blockMetadata == ForgeDirection.DOWN.ordinal() || blockMetadata == ForgeDirection.UP.ordinal())
                {
                    x = this.xCoord + BlockPatterns.TDA[blocksToPlace - 1].x;
                    y = this.yCoord + BlockPatterns.TDA[blocksToPlace - 1].z;
                    z = this.zCoord + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                }
                else
                {
                    x = this.xCoord + BlockPatterns.TDA[blocksToPlace - 1].x;
                    y = this.yCoord + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                    z = this.zCoord + BlockPatterns.TDA[blocksToPlace - 1].z;
                }

                meta = BlockPatterns.TDA[blocksToPlace - 1].meta;

                if(worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z).equals(ModBlocks.tdaSB) || worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z))
                {
                    this.getWorldObj().setBlock(x, y, z, ModBlocks.tdaSB, meta, 2);
                    blocksToPlace--;
                }
                else
                {
                    //PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 48));
                }
            }
        }
    }

    public static void destroyMultiblock(World world, int x, int y, int z, int blockMetadata)
    {
        for(int n = 0; n < 55; n++)
        {
            int currentX, currentY, currentZ;
            if(blockMetadata == ForgeDirection.EAST.ordinal() || blockMetadata == ForgeDirection.WEST.ordinal())
            {
                currentX = x + BlockPatterns.TDA[n].z;
                currentY = y + 14 + BlockPatterns.TDA[n].y;
                currentZ = z + BlockPatterns.TDA[n].x;
            }
            else if(blockMetadata == ForgeDirection.DOWN.ordinal() || blockMetadata == ForgeDirection.UP.ordinal())
            {
                currentX = x + BlockPatterns.TDA[n].x;
                currentY = y + BlockPatterns.TDA[n].z;
                currentZ = z + 14 + BlockPatterns.TDA[n].y;
            }
            else
            {
                currentX = x + BlockPatterns.TDA[n].x;
                currentY = y + 14 + BlockPatterns.TDA[n].y;
                currentZ = z + BlockPatterns.TDA[n].z;
            }


            if(world.getBlock(currentX, currentY, currentZ).equals(ModBlocks.tdaSB))
                world.setBlock(currentX, currentY, currentZ, Blocks.air);
        }
        ClockworkPhase2.proxy.clearWorldRenderers(world, x, y, z);
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
