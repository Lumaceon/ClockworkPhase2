package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.TimezoneHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler
{
    public static int tickTimer = 0;
    public static boolean savingMemory = true;
    public static long startedAtSystemTimeMillis = -1;
    public static int[] memoryX;
    public static int[] memoryY;

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

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ClockworkPhase2.proxy.onClientTick(event);
    }


    /*public static int updates = 0;
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.END))
        {
            if(ItemBugSwatter.debugLong1 == 0)
            {
                ItemBugSwatter.debugLong1 = System.currentTimeMillis();
            }

            if(System.currentTimeMillis() <= ItemBugSwatter.debugLong1 + 1000)
            {
                ItemBugSwatter.debugInteger1++;
            }
            else if(ClockworkPhase2.random.nextInt(500) == 0)
            {
                LogHelper.info("" + ItemBugSwatter.debugInteger1);
            }
        }

        if(startedAtSystemTimeMillis < 0)
        {
            startedAtSystemTimeMillis = System.currentTimeMillis();
        }

        if(memoryX == null)
        {
            memoryX = new int[600];
        }

        if(memoryY == null)
        {
            memoryY = new int[600];
        }

        /*if(savingMemory && sysTime - startedAtSystemTimeMillis < 600)
        {
            PointerInfo mouseLoc = MouseInfo.getPointerInfo();
            Point point = mouseLoc.getLocation();
            memoryX[(int) (sysTime - startedAtSystemTimeMillis)] = point.x;
            memoryY[(int) (sysTime - startedAtSystemTimeMillis)] = point.y;
        }*/

        /*int index = updates % 600;
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) //&& Mouse.isInsideWindow())
        {
            ClientProxy.INPUT_SIMULATOR.mouseMove(memoryX[index], memoryY[index]);
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
        {
            PointerInfo mouseLoc = MouseInfo.getPointerInfo();
            Point point = mouseLoc.getLocation();
            memoryX[index] = point.x;
            memoryY[index] = point.y;
        }
        ++updates;
        if(updates >= 600)
            updates = 0;
    }*/
}
