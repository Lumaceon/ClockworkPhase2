package lumaceon.mods.clockworkphase2.network.message.handler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemMainspring;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerMainspringButton implements IMessageHandler<MessageMainspringButton, IMessage>
{
    @Override
    public IMessage onMessage(final MessageMainspringButton message, final MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageToolUpgradeActivate received on wrong side:" + ctx.side);
            return null;
        }

        final EntityPlayerMP sendingPlayer = ctx.getServerHandler().playerEntity;
        if(sendingPlayer == null)
        {
            System.err.println("MessageToolUpgradeActivate received with null player.");
            return null;
        }

        final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
        playerWorldServer.addScheduledTask(new Runnable() {
            public void run() {
                processMessage(message, ctx, sendingPlayer);
            }
        });
        return null;
    }

    private void processMessage(MessageMainspringButton message, MessageContext ctx, EntityPlayerMP player)
    {
        Container container = player.openContainer;
        if(container != null && container instanceof ContainerAssemblyTable)
            ItemMainspring.onButtonClickedServer((ContainerAssemblyTable) container);
    }
}
