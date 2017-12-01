package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.temporal.ITimeSink;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.block.temporal.BlockTemporalCompressionConduit;
import lumaceon.mods.clockworkphase2.capabilities.timestorage.TimeStorage;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.List;

public class TileTemporalCompressor extends TileMod implements ITickable, ITimeSink
{
    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME_STORAGE_CAPABILITY = null;

    public long lastWorldTimeThisWasUpdated; //We can't initialize this properly in the constructor without a world...
    protected boolean isFirstUpdate = true; //...so we'll do it during the first update tick.

    protected int updateTimer = 0;

    protected ITimeStorage timeStorage = new TimeStorage(TimeConverter.HOUR);

    @Override
    public void update()
    {
        if(isFirstUpdate)
        {
            World world = getWorld();
            if(world != null)
            {
                lastWorldTimeThisWasUpdated = world.getTotalWorldTime();
                isFirstUpdate = false;
                markDirty();
                return;
            }
        }

        ++updateTimer;
        if(updateTimer > 20) //Once per second.
        {
            updateTimer = 0;

            World world = this.getWorld();
            if(world == null || world.isRemote) //There needs to be a world, and we only want to continue server-side.
                return;

            /**CHECK FOR NEARBY CONDUITS AND CALCULATE AS NECESSARY **/
            BlockPos localPos;
            EnumFacing tempFacing;
            for(int i = 0; i < 6; i++) //Each side of this block.
            {
                localPos = this.getPos().offset(EnumFacing.getFront(i));
                IBlockState state = world.getBlockState(localPos);
                int chunkX = localPos.getX() >> 4;
                int chunkY = localPos.getZ() >> 4;
                if(state != null && state.getBlock() instanceof BlockTemporalCompressionConduit)
                {
                    int emergencyEscape = 0;
                    while(emergencyEscape < 1000) //Aside from the max 1000 iterations, this is essentially "while true"
                    {
                        tempFacing = ((BlockTemporalCompressionConduit) state.getBlock()).getConduitOutputDirection();

                        if(tempFacing == null)
                            break;

                        localPos = localPos.offset(tempFacing);
                        if(localPos.getX() >> 4 != chunkX || localPos.getZ() >> 4 != chunkY) //We're on a new chunk.
                        {
                            chunkX = localPos.getX() >> 4;
                            chunkY = localPos.getZ() >> 4;
                            if(!world.isBlockLoaded(localPos))
                            {
                                timeStorage.insertTime(20);
                                break;
                            }
                        }

                        state = world.getBlockState(localPos);
                        if(state == null || !(state.getBlock() instanceof BlockTemporalCompressionConduit))
                            break;

                        ++emergencyEscape;
                    }
                }
            }

            /** CHECK FOR TIMEZONES AND TRANSFER TIME IF POSSIBLE **/
            List<ITimezone> timezones = TimezoneHandler.getTimezonesFromWorldPosition(pos.getX(), pos.getY(), pos.getZ(), world);
            for(ITimezone timezone : timezones)
            {
                timezone.insertTime(timeStorage.extractTime(timeStorage.getTimeInTicks()));
                if(timeStorage.getTimeInTicks() <= 0)
                    break;
            }
        }

        World world = getWorld();
        if(world != null && !world.isRemote)
        {
            long totalWorldTime = world.getTotalWorldTime();
            if(lastWorldTimeThisWasUpdated < totalWorldTime-1)
            {
                long difference = totalWorldTime - lastWorldTimeThisWasUpdated;
                timeStorage.insertTime(difference);
            }
            lastWorldTimeThisWasUpdated = totalWorldTime;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setBoolean("first_update", isFirstUpdate);
        nbt.setLong("last_world_time", lastWorldTimeThisWasUpdated);

        nbt.setLong("max_time", timeStorage.getMaxCapacity());
        nbt.setLong("time_stored", timeStorage.getTimeInTicks());

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if(nbt.hasKey("first_update"))
            isFirstUpdate = nbt.getBoolean("first_update");
        if(nbt.hasKey("last_world_time"))
            lastWorldTimeThisWasUpdated = nbt.getLong("last_world_time");
        if(nbt.hasKey("max_time"))
            timeStorage = new TimeStorage(nbt.getLong("max_time"));
        if(nbt.hasKey("time_stored"))
            timeStorage.setTime(nbt.getLong("time_stored"));
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability != null && capability == TIME_STORAGE_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if(capability != null && capability == TIME_STORAGE_CAPABILITY)
            return TIME_STORAGE_CAPABILITY.cast(timeStorage);
        return super.getCapability(capability, facing);
    }
}
