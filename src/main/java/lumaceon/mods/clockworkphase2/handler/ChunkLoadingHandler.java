package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalZoningMachine;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ChunkLoadingHandler
{
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        TileTemporalZoningMachine.chunkloadingTickets.put(event.getWorld(), ForgeChunkManager.requestTicket(ClockworkPhase2.instance, event.getWorld(), ForgeChunkManager.Type.NORMAL));
    }

    @SubscribeEvent
    public void onWorldUnLoad(WorldEvent.Unload event)
    {
        TileTemporalZoningMachine.chunkloadingTickets.remove(event.getWorld());
    }

    public static class CP2ChunkLoadingCallback implements ForgeChunkManager.OrderedLoadingCallback
    {
        @Override
        public List<ForgeChunkManager.Ticket> ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount) {
            return tickets;
        }

        @Override
        public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {

        }
    }
}
