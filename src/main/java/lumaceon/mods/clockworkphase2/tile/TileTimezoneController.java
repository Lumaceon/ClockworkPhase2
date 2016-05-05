package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.time.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import lumaceon.mods.clockworkphase2.lib.Configs;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileTimezoneController extends TileClockworkPhase implements ITimezoneProvider, ITickable
{
    protected Timezone timezone;

    private NBTTagCompound nbtReference;
    private int blocksToPlace = 96;
    private boolean timezoneSetup = false;

    protected TimeStorage timeStorage = new TimeStorage(Configs.TIME.maxTimezoneTime);

    public boolean isAvailable() {
        return blocksToPlace < 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);
        if(timezone != null)
        {
            NBTTagCompound timezoneTag = new NBTTagCompound();
            timezone.writeToNBT(timezoneTag);
            nbt.setTag("timezone", timezoneTag);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");
        this.nbtReference = nbt;
    }

    protected void readTimezoneFromNBT(NBTTagCompound nbt) {
        if(nbt != null && nbt.hasKey("timezone"))
            timezone.readFromNBT((NBTTagCompound) nbt.getTag("timezone"));
    }

    @Override
    public void update()
    {
        if(!isAvailable())
        {
            handleMultiblockPlacement();
            return;
        }

        if(worldObj == null)
            return;

        if(!timezoneSetup)
        {
            timezoneSetup = true;
            timezone = new Timezone(worldObj, this);
            readTimezoneFromNBT(nbtReference);
            TimezoneHandler.INTERNAL.registerTimezone(pos.getX(), pos.getY(), pos.getZ(), worldObj);
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
                    x = pos.getX() + BlockPatterns.CELESTIAL_COMPASS[n].x;
                    y = pos.getY() + BlockPatterns.CELESTIAL_COMPASS[n].y;
                    z = pos.getZ() + BlockPatterns.CELESTIAL_COMPASS[n].z;
                    BlockPos newPos = new BlockPos(x, y, z);
                    this.worldObj.markBlockForUpdate(newPos);
                    blocksToPlace--;
                }
            }
            else if(blocksToPlace > 0)
            {
                int x, y, z, meta;
                x = this.pos.getX() + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].x;
                y = this.pos.getY() + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].y;
                z = this.pos.getZ() + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].z;
                BlockPos newPos = new BlockPos(x, y, z);
                //meta = BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].meta;

                blocksToPlace--;
                if(worldObj.isAirBlock(newPos) || worldObj.getBlockState(newPos).getBlock().equals(ModBlocks.timezoneControllerSB.getBlock()) || worldObj.getBlockState(newPos).getBlock().isReplaceable(worldObj, newPos))
                    worldObj.setBlockState(newPos, ModBlocks.timezoneControllerSB.getBlock().getDefaultState());
                else
                {
                    //PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 48));
                }
            }
        }
    }

    public static void destroyMultiblock(World world, BlockPos pos)
    {
        for(int n = 0; n < 96; n++)
        {
            int currentX, currentY, currentZ;
            currentX = pos.getX() + BlockPatterns.CELESTIAL_COMPASS[n].x;
            currentY = pos.getY() + BlockPatterns.CELESTIAL_COMPASS[n].y;
            currentZ = pos.getZ() + BlockPatterns.CELESTIAL_COMPASS[n].z;
            BlockPos tempPos = new BlockPos(currentX, currentY, currentZ);

            if(world.getBlockState(tempPos).getBlock().equals(ModBlocks.timezoneControllerSB.getBlock()))
                world.setBlockState(tempPos, Blocks.air.getDefaultState());
        }
        TimezoneHandler.INTERNAL.pingAndCleanTimezones();
        ClockworkPhase2.proxy.clearWorldRenderers(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public Timezone getTimezone() {
        return timezone;
    }

    @Override
    public float getRange() { return 128F; }

    @Override
    public int getX() {
        return pos.getX();
    }

    @Override
    public int getY() {
        return pos.getY();
    }

    @Override
    public int getZ() {
        return pos.getZ();
    }

    @Override
    public int getMaxTime() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public int getTime() {
        return timeStorage.getTimeStored();
    }

    @Override
    public int addTime(int time) {
        return timeStorage.receiveTime(time, false);
    }

    @Override
    public int consumeTime(int time) {
        return timeStorage.extractTime(time, false);
    }

    //TODO: for some reason, this has several "grey zones" where the TESR is culled when looking towards negative X and only when the player is negative X relative to the tile entity position.
    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.util.AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 256D * 256D;
    }
}
