package lumaceon.mods.clockworkphase2.network.message.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lumaceon.mods.clockworkphase2.client.particle.ParticleGenerator;
import lumaceon.mods.clockworkphase2.client.particle.fx.EntityTimeSandExtractionFX;
import lumaceon.mods.clockworkphase2.network.message.MessageStandardParticleSpawn;
import net.minecraft.client.Minecraft;

public class HandlerStandardParticleSpawn implements IMessageHandler<MessageStandardParticleSpawn, IMessage>
{
    @Override
    public IMessage onMessage(MessageStandardParticleSpawn message, MessageContext ctx)
    {
        switch(message.ID)
        {
            case 0:
                for(int n = 0; n < message.particleNumber; n++)
                    ParticleGenerator.INSTANCE.spawnParticle(new EntityTimeSandExtractionFX(Minecraft.getMinecraft().theWorld, message.x, message.y, message.z), 64);
                break;
        }
        return null;
    }
}
