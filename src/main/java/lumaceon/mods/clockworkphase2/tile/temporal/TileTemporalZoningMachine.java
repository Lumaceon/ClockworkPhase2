package lumaceon.mods.clockworkphase2.tile.temporal;

import com.google.common.collect.ImmutableSet;
import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.init.ModTZFunctions;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.HashMap;

public class TileTemporalZoningMachine extends TileMod implements ITickable
{
    @CapabilityInject(ITimezone.class)
    public static final Capability<ITimezone> TIMEZONE = null;
    public static HashMap<World, ForgeChunkManager.Ticket> chunkloadingTickets = new HashMap<World, ForgeChunkManager.Ticket>();

    boolean hasRegistedTimezone = false;

    ITimezone timezone = new Timezone(this);

    @Override
    public void update()
    {
        if(!world.isRemote)
        {
            /** Chunkloading... stuff **/
            ForgeChunkManager.Ticket ticket = chunkloadingTickets.get(world);
            if(ticket != null)
            {
                boolean chunkAlreadyLoaded = false;
                ImmutableSet<ChunkPos> loadedChunks = ticket.getChunkList();
                ChunkPos thisChunkPos = new ChunkPos(this.getPos());
                if(loadedChunks != null)
                {
                    for(ChunkPos c : loadedChunks)
                    {
                        if(c.x == thisChunkPos.x && c.z == thisChunkPos.z)
                        {
                            chunkAlreadyLoaded = true;
                        }
                    }
                }

                if(!chunkAlreadyLoaded)
                {
                    ForgeChunkManager.forceChunk(ticket, thisChunkPos);
                }
            }

            /** Update constructors for the timezone functions **/
            Collection<TimezoneFunctionConstructor> timezoneFunctionConstructors = timezone.getTimezoneFunctionConstructors();
            for(TimezoneFunctionConstructor tfc : timezoneFunctionConstructors)
            {
                tfc.onUpdate(timezone);
            }

            /** Update actual timezone functions **/
            Collection<TimezoneFunction> timezoneFunctiona = timezone.getTimezoneFunctions();
            for(TimezoneFunction function : timezoneFunctiona)
            {
                long consumed = timezone.extractTime(function.timeCostPerTick(timezone));
                if(consumed != function.timeCostPerTick(timezone))
                {
                    function.instabilityUpdate(timezone);
                }
                else
                {
                    function.onUpdate(timezone);
                }
            }
        }

        if(!hasRegistedTimezone)
        {
            hasRegistedTimezone = true;
            TimezoneHandler.INTERNAL.registerTimezone(this);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setLong("max_time", timezone.getMaxCapacity());
        nbt.setLong("time_stored", timezone.getTimeInTicks());

        NBTTagList constructorList = new NBTTagList();
        Collection<TimezoneFunctionConstructor> timezoneFunctionConstructors = timezone.getTimezoneFunctionConstructors();
        for(TimezoneFunctionConstructor tfc : timezoneFunctionConstructors)
        {
            constructorList.appendTag(tfc.serializeNBT());
        }
        nbt.setTag("constructor_list", constructorList);

        NBTTagList functionList = new NBTTagList();
        Collection<TimezoneFunction> timezoneFunctions = timezone.getTimezoneFunctions();
        for(TimezoneFunction tf : timezoneFunctions)
        {
            functionList.appendTag(tf.serializeNBT());
        }
        nbt.setTag("function_list", functionList);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("max_time"))
            timezone.setMaxCapacity(nbt.getLong("max_time"));
        if(nbt.hasKey("time_stored"))
        {
            timezone.extractTime(Long.MAX_VALUE);
            timezone.insertTime(nbt.getLong("time_stored"));
        }

        if(nbt.hasKey("constructor_list"))
        {
            NBTTagList constructorList = nbt.getTagList("constructor_list", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < constructorList.tagCount(); i++)
            {
                NBTTagCompound compound = constructorList.getCompoundTagAt(i);
                TimezoneFunctionType type = ModTZFunctions.getTypeFromID(compound.getString("type_id"));
                if(type != null)
                {
                    TimezoneFunctionConstructor constructor = type.createTimezoneFunctionConstructorInstance();
                    if(constructor != null)
                    {
                        constructor.deserializeNBT(compound);
                        timezone.addTimezoneFunctionConstructor(constructor);
                    }
                }
            }
        }

        if(nbt.hasKey("function_list"))
        {
            NBTTagList funcList = nbt.getTagList("function_list", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < funcList.tagCount(); i++)
            {
                NBTTagCompound compound = funcList.getCompoundTagAt(i);
                TimezoneFunctionType type = ModTZFunctions.getTypeFromID(compound.getString("type_id"));
                if(type != null)
                {
                    TimezoneFunctionConstructor constructor = type.createTimezoneFunctionConstructorInstance();
                    if(constructor != null)
                    {
                        constructor.deserializeNBT(compound);
                        timezone.addTimezoneFunctionConstructor(constructor);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability != null && capability == TIMEZONE || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if(capability != null && capability == TIMEZONE)
            return TIMEZONE.cast(timezone);
        return super.getCapability(capability, facing);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 356D * 356D;
    }
}
