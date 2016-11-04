package lumaceon.mods.clockworkphase2.network.message.handler.dummy;

import lumaceon.mods.clockworkphase2.network.message.MessageParticleSpawn;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyHandlerParticleSpawn implements IMessageHandler<MessageParticleSpawn, IMessage>
{
    @Override
    public IMessage onMessage(MessageParticleSpawn message, MessageContext ctx) {
        return null;
    }
}
