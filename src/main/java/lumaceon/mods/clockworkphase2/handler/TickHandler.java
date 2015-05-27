package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;

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
}