package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.network.message.MessagePlayerDataOnWorldJoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerPlayerDataOnWorldJoin implements IMessageHandler<MessagePlayerDataOnWorldJoin, IMessage>
{
    @Override
    public IMessage onMessage(MessagePlayerDataOnWorldJoin message, MessageContext ctx)
    {
        if(ctx.side != Side.CLIENT)
        {
            System.err.println("MessagePlayerDataOnWorldJoin received on wrong side:" + ctx.side);
            return null;
        }

        ClockworkPhase2.proxy.getThreadListener(ctx).addScheduledTask(() ->
            {
                EntityPlayer player = ClockworkPhase2.proxy.getClientPlayer();
                if(player != null && player.hasCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN))
                {
                    ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
                    if(toolbelt != null)
                    {
                        toolbelt.setRowCount(message.temporalToolbeltItems.size());
                        for(int i = 0; i < toolbelt.getRowCount(); i++)
                        {
                            toolbelt.setRow(i, message.temporalToolbeltItems.get(i));
                        }
                    }
                }
            }
        );

        return null;
    }
}
