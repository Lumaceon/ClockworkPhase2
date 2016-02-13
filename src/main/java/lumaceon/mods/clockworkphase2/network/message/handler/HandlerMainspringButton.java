package lumaceon.mods.clockworkphase2.network.message.handler;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.item.components.ItemMainspring;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerMainspringButton implements IMessageHandler<MessageMainspringButton, IMessage>
{
    @Override
    public IMessage onMessage(MessageMainspringButton message, MessageContext ctx)
    {
        if(ctx.side.isServer() && ctx.getServerHandler().playerEntity != null)
        {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            Container container = player.openContainer;
            if(container != null && container instanceof ContainerAssemblyTable)
                ItemMainspring.onButtonClickedServer((ContainerAssemblyTable) container);
        }
        return null;
    }
}
