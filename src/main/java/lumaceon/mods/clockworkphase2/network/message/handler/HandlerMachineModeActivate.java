package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.tile.ITemporalTile;
import lumaceon.mods.clockworkphase2.network.message.MessageMachineModeActivate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerMachineModeActivate implements IMessageHandler<MessageMachineModeActivate, IMessage>
{
    @Override
    public IMessage onMessage(final MessageMachineModeActivate message, final MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageMachineModeActivate received on wrong side:" + ctx.side);
            return null;
        }

        final WorldServer world = DimensionManager.getWorld(message.dimension);
        world.addScheduledTask(new Runnable() {
            public void run() {
                processMessage(message, ctx, world);
            }
        });
        return null;
    }

    private void processMessage(MessageMachineModeActivate message, MessageContext ctx, WorldServer world)
    {
        TileEntity te = world.getTileEntity(message.pos);
        if(te != null)
        {
            if(te instanceof ITemporalTile)
            {
                ITemporalTile temporalTile = (ITemporalTile) te;
                temporalTile.toggleTemporalMode();
            }
        }
    }
}
