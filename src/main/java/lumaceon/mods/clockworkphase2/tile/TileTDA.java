package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileTDA extends TileClockworkPhase implements ITickable
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
    public void update() {
        if(!isAvailable())
            handleMultiblockPlacement();

        if(worldObj.isRemote && !rendererSetup)
        {
            ClockworkPhase2.proxy.addWorldRenderer(worldObj, pos.getX(), pos.getY(), pos.getZ(), 2);
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
                    if(getBlockMetadata() == EnumFacing.EAST.ordinal() || getBlockMetadata() == EnumFacing.WEST.ordinal())
                    {
                        x = pos.getX() + BlockPatterns.TDA[n].z;
                        y = pos.getY() + 14 + BlockPatterns.TDA[n].y;
                        z = pos.getZ() + BlockPatterns.TDA[n].x;
                    }
                    else if(getBlockMetadata() == EnumFacing.DOWN.ordinal() || getBlockMetadata() == EnumFacing.UP.ordinal())
                    {
                        x = pos.getX() + BlockPatterns.TDA[n].x;
                        y = pos.getY() + BlockPatterns.TDA[n].z;
                        z = pos.getZ() + 14 + BlockPatterns.TDA[n].y;
                    }
                    else
                    {
                        x = pos.getX() + BlockPatterns.TDA[n].x;
                        y = pos.getY() + 14 + BlockPatterns.TDA[n].y;
                        z = pos.getZ() + BlockPatterns.TDA[n].z;
                    }
                    this.worldObj.markBlockForUpdate(new BlockPos(x, y, z));
                    blocksToPlace--;
                }
            }
            else if(blocksToPlace > 0)
            {
                int x, y, z, meta;
                if(getBlockMetadata() == EnumFacing.EAST.ordinal() || getBlockMetadata() == EnumFacing.WEST.ordinal())
                {
                    x = pos.getX() + BlockPatterns.TDA[blocksToPlace - 1].z;
                    y = pos.getY() + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                    z = pos.getZ() + BlockPatterns.TDA[blocksToPlace - 1].x;
                }
                else if(getBlockMetadata() == EnumFacing.DOWN.ordinal() || getBlockMetadata() == EnumFacing.UP.ordinal())
                {
                    x = pos.getX() + BlockPatterns.TDA[blocksToPlace - 1].x;
                    y = pos.getY() + BlockPatterns.TDA[blocksToPlace - 1].z;
                    z = pos.getZ() + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                }
                else
                {
                    x = pos.getX() + BlockPatterns.TDA[blocksToPlace - 1].x;
                    y = pos.getY() + 14 + BlockPatterns.TDA[blocksToPlace - 1].y;
                    z = pos.getZ() + BlockPatterns.TDA[blocksToPlace - 1].z;
                }

                meta = BlockPatterns.TDA[blocksToPlace - 1].meta;

                blocksToPlace--;
                /*if(worldObj.isAirBlock(new BlockPos(x, y, z)) || worldObj.getBlockState(new BlockPos(x, y, z)).getBlock().equals(ModBlocks.tdaSB) || worldObj.getBlockState(new BlockPos(x, y, z)).getBlock().isReplaceable(worldObj, new BlockPos(x, y, z)))
                {
                    worldObj.setBlockState(new BlockPos(x, y, z), ModBlocks.tdaSB.getDefaultState());
                }
                else
                {
                    //PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 48));
                }*/
            }
        }
    }

    public static void destroyMultiblock(World world, int x, int y, int z, int blockMetadata)
    {
        for(int n = 0; n < 55; n++)
        {
            int currentX, currentY, currentZ;
            if(blockMetadata == EnumFacing.EAST.ordinal() || blockMetadata == EnumFacing.WEST.ordinal())
            {
                currentX = x + BlockPatterns.TDA[n].z;
                currentY = y + 14 + BlockPatterns.TDA[n].y;
                currentZ = z + BlockPatterns.TDA[n].x;
            }
            else if(blockMetadata == EnumFacing.DOWN.ordinal() || blockMetadata == EnumFacing.UP.ordinal())
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


            //if(world.getBlockState(new BlockPos(currentX, currentY, currentZ)).getBlock().equals(ModBlocks.tdaSB))
            //    world.setBlockState(new BlockPos(currentX, currentY, currentZ), Blocks.air.getDefaultState());
        }
        ClockworkPhase2.proxy.clearWorldRenderers(world, x, y, z);
    }
}
