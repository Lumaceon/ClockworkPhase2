package lumaceon.mods.clockworkphase2.network.message.handler.dummy;

import lumaceon.mods.clockworkphase2.network.message.MessagePlayerDataOnWorldJoin;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyHandlerPlayerDataOnWorldJoin implements IMessageHandler<MessagePlayerDataOnWorldJoin, IMessage>
{
    @Override
    public IMessage onMessage(MessagePlayerDataOnWorldJoin message, MessageContext ctx) {
        return null;
    }
}
