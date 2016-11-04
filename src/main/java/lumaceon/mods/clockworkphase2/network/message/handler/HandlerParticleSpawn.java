package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageParticleSpawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerParticleSpawn implements IMessageHandler<MessageParticleSpawn, IMessage>
{
    @Override
    public IMessage onMessage(final MessageParticleSpawn message, final MessageContext ctx)
    {
        if(ctx.side != Side.CLIENT)
        {
            System.err.println("MessageParticleSpawn received on wrong side:" + ctx.side);
            return null;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.theWorld;
        minecraft.addScheduledTask(new Runnable()
        {
            public void run()
            {
                processMessage(message, ctx, worldClient);
            }
        });
        return null;
    }

    private void processMessage(MessageParticleSpawn message, MessageContext ctx, WorldClient world)
    {
        switch(message.particleIndex)
        {
            case 0: //Relocation.
                for(int i = 0; i < 20; i++)
                    world.spawnParticle(EnumParticleTypes.PORTAL, true, message.x + world.rand.nextFloat(), message.y + (world.rand.nextFloat() -0.5F), message.z + world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), new int[0]);
                break;
        }
    }
}
