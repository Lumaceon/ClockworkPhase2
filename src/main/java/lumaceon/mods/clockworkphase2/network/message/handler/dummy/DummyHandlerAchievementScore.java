package lumaceon.mods.clockworkphase2.network.message.handler.dummy;

import lumaceon.mods.clockworkphase2.network.message.MessageAchievementScore;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyHandlerAchievementScore implements IMessageHandler<MessageAchievementScore, IMessage>
{
    @Override
    public IMessage onMessage(MessageAchievementScore message, MessageContext ctx) {
        return null;
    }
}
