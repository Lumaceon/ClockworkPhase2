package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerCelestialCompassItemGet implements IMessageHandler<MessageCelestialCompassItemGet, IMessage>
{
    @Override
    public IMessage onMessage(final MessageCelestialCompassItemGet message, final MessageContext ctx)
    {
        if(ctx.side != Side.CLIENT)
        {
            System.err.println("MessageCelestialCompassItem received on wrong side:" + ctx.side);
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

    private void processMessage(MessageCelestialCompassItemGet message, MessageContext ctx, WorldClient worldClient)
    {
        World world = Minecraft.getMinecraft().theWorld;
        if(world != null)
        {
            TileEntity te = world.getTileEntity(message.pos);
            if(te != null && te instanceof TileCelestialCompass)
            {
                TileCelestialCompass controller = (TileCelestialCompass) te;
                controller.setCraftingItem(message.is, message.index);
            }
        }
    }
}
