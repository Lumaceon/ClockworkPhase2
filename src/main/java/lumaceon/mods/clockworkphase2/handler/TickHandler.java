package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler
{
    public static int tickTimer = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(tickTimer >= 1200) //Once every minute.
        {
            tickTimer = 0;
            TimezoneHandler.INTERNAL.timezoneCleanup();
        }
        else
        {
            tickTimer++;
        }
    }
}
