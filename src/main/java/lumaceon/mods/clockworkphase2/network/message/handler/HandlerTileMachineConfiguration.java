package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageTileMachineConfiguration;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerTileMachineConfiguration implements IMessageHandler<MessageTileMachineConfiguration, IMessage>
{
    @Override
    public IMessage onMessage(final MessageTileMachineConfiguration message, final MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageTileMachineConfiguration received on wrong side:" + ctx.side);
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

    private void processMessage(MessageTileMachineConfiguration message, MessageContext ctx, WorldServer world)
    {
        TileEntity te = world.getTileEntity(message.tilePosition);
        if(te != null && te instanceof TileClockworkMachine)
        {
            TileClockworkMachine machine = (TileClockworkMachine) te;
            machine.changeIO(message.direction, message.slotID, message.activate);
        }
    }
}
