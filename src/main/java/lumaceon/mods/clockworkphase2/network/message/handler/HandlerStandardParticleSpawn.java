package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageStandardParticleSpawn;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerStandardParticleSpawn implements IMessageHandler<MessageStandardParticleSpawn, IMessage>
{
    @Override
    public IMessage onMessage(MessageStandardParticleSpawn message, MessageContext ctx)
    {
        switch(message.ID)
        {
            case 0:
                for(int n = 0; n < message.particleNumber; n++)
                    //ParticleSequence.INSTANCE.spawnParticle(new EntityTimeSandExtractionFX(Minecraft.getMinecraft().theWorld, message.x, message.y, message.z), 64);
                break;
        }
        return null;
    }
}
