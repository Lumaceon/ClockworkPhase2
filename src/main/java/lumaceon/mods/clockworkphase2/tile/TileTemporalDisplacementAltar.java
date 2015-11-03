package lumaceon.mods.clockworkphase2.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTileStateChange;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileTemporalDisplacementAltar extends TileClockworkPhase
{
    private int blocksToPlace = 96;
    private int machineState = 0; //0 - not assembled, 1 - Assembled no gears, 2 through 5 - assembled with gears.
    private boolean rendererSetup = false;

    public boolean isAvailable() {
        return machineState > 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);
        nbt.setInteger("machine_state", this.machineState);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");
        machineState = nbt.getInteger("machine_state");
    }

    @Override
    public void updateEntity()
    {
        if(!isAvailable())
        {
            handleMultiblockPlacement();
            return;
        }

        if(worldObj.isRemote && !rendererSetup)
        {
            ClockworkPhase2.proxy.addWorldRenderer(worldObj, xCoord, yCoord, zCoord, 1);
            rendererSetup = true;
        }
    }

    private void handleMultiblockPlacement()
    {
        if(!this.worldObj.isRemote)
        {
            if(blocksToPlace == 0)
            {
                for(int n = 0; n < BlockPatterns.CELESTIAL_COMPASS.length; n++)
                {
                    int x, y, z;
                    x = this.xCoord + BlockPatterns.CELESTIAL_COMPASS[n].x;
                    y = this.yCoord + BlockPatterns.CELESTIAL_COMPASS[n].y;
                    z = this.zCoord + BlockPatterns.CELESTIAL_COMPASS[n].z;
                    this.worldObj.markBlockForUpdate(x, y, z);
                    blocksToPlace--;
                }
                setStateAndUpdate(1);
            }
            else if(blocksToPlace > 0)
            {
                int x, y, z, meta;
                x = this.xCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].x;
                y = this.yCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].y;
                z = this.zCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].z;
                meta = BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].meta;

                if(worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z).equals(ModBlocks.temporalDisplacementAltarSB) || worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z))
                {
                    this.getWorldObj().setBlock(x, y, z, ModBlocks.temporalDisplacementAltarSB, meta, 2);
                    blocksToPlace--;
                }
                else
                {
                    //PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 48));
                }
            }
        }
    }

    @Override
    public void setState(int state) {
        machineState = state;
    }

    @Override
    public void setStateAndUpdate(int state)
    {
        if(!worldObj.isRemote)
        {
            setState(state);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTileStateChange(xCoord, yCoord, zCoord, state), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 200));
            markDirty();
        }
    }

    public static void destroyMultiblock(World world, int x, int y, int z)
    {
        for(int n = 0; n < 96; n++)
        {
            int currentX, currentY, currentZ;
            currentX = x + BlockPatterns.CELESTIAL_COMPASS[n].x;
            currentY = y + BlockPatterns.CELESTIAL_COMPASS[n].y;
            currentZ = z + BlockPatterns.CELESTIAL_COMPASS[n].z;

            if(world.getBlock(currentX, currentY, currentZ).equals(ModBlocks.temporalDisplacementAltarSB))
                world.setBlock(currentX, currentY, currentZ, Blocks.air);
        }
        ClockworkPhase2.proxy.clearWorldRenderers(world, x, y, z);
    }
}
