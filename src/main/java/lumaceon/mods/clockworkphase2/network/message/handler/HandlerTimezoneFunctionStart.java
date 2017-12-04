package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.init.ModTZFunctions;
import lumaceon.mods.clockworkphase2.network.message.MessageTimezoneFunctionStart;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class HandlerTimezoneFunctionStart implements IMessageHandler<MessageTimezoneFunctionStart, IMessage>
{
    @Override
    public IMessage onMessage(final MessageTimezoneFunctionStart message, final MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageTileMachineConfiguration received on wrong side:" + ctx.side);
            return null;
        }

        final WorldServer world = DimensionManager.getWorld(message.dimension);
        world.addScheduledTask(new Runnable() {
            public void run() {
                processMessage(message, ctx, world);
            }
        });
        return null;
    }

    private void processMessage(MessageTimezoneFunctionStart message, MessageContext ctx, WorldServer world)
    {
        TimezoneFunctionType type = ModTZFunctions.getTypeFromID(message.typeID);
        if(type == null)
            return;

        List<ITimezone> timezones = TimezoneHandler.getTimezonesFromWorldPosition(message.pos.getX(), message.pos.getY(), message.pos.getZ(), message.dimension);
        for(ITimezone timezone : timezones)
        {
            if(timezone != null)
            {
                TimezoneFunction fnc = timezone.getTimezoneFunction(type);
                if(fnc == null)
                {
                    TimezoneFunctionConstructor cons = timezone.getTimezoneFunctionConstructor(type);
                    if(cons == null)
                    {
                        timezone.addTimezoneFunctionConstructor(type.createTimezoneFunctionConstructorInstance());
                    }
                    else if(cons.canComplete(timezone))
                    {
                        timezone.addTimezoneFunction(cons.createTimezoneFunction(timezone));
                    }
                }
            }
        }
    }
}
