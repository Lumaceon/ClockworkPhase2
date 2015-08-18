package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import lumaceon.mods.clockworkphase2.api.phase.Phase;
import lumaceon.mods.clockworkphase2.api.phase.Phases;
import lumaceon.mods.clockworkphase2.api.time.TimezoneHandler;

public class TickHandler
{
    public static int tickTimer = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(tickTimer >= 1200) //Once every minute.
        {
            tickTimer = 0;
            TimezoneHandler.INTERNAL.pingAndCleanTimezones();
        }
        else
            tickTimer++;
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(event.world != null && !event.world.isRemote)
        {
            Phase[] phases = Phases.getPhases(event.world);
            for(Phase phase : phases)
                phase.update(Side.SERVER);
        }
    }
}