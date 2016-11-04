package lumaceon.mods.clockworkphase2.network.message.handler.dummy;

import lumaceon.mods.clockworkphase2.network.message.MessageCelestialCompassItemGet;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyHandlerCelestialCompassItemGet implements IMessageHandler<MessageCelestialCompassItemGet, IMessage>
{
    @Override
    public IMessage onMessage(MessageCelestialCompassItemGet message, MessageContext ctx) {
        return null;
    }
}
