package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lumaceon.mods.clockworkphase2.api.Timezones;

public class TickHandler
{
    public static int tickTimer = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(tickTimer >= 600) //Once every 30 seconds.
        {
            tickTimer = 0;
            Timezones.pingAndCleanTimezones();
        }
        else
        {
            tickTimer++;
        }
    }
}
