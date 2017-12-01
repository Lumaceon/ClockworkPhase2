package lumaceon.mods.clockworkphase2.tile.temporal;

import com.google.common.collect.ImmutableSet;
import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        }

        if(!hasRegistedTimezone)
        {
            hasRegistedTimezone = true;
            TimezoneHandler.INTERNAL.registerTimezone(this);
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
