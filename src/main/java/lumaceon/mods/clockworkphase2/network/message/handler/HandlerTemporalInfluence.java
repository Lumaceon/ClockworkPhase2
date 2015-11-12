package lumaceon.mods.clockworkphase2.network.message.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalInfluence;
import net.minecraft.client.Minecraft;

public class HandlerTemporalInfluence implements IMessageHandler<MessageTemporalInfluence, IMessage>
{
    @Override
    public IMessage onMessage(MessageTemporalInfluence message, MessageContext ctx)
    {
        ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
        properties.previousTemporalInfluence = properties.temporalInfluence;
        properties.temporalInfluence = message.newTemporalInfluence;

        RenderHandler.overlayInfluence.displayInfluenceIncrease(properties.previousTemporalInfluence, properties.temporalInfluence);
        return null;
    }
}
