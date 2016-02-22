package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import lumaceon.mods.clockworkphase2.container.ContainerClockworkItemStorage;
import lumaceon.mods.clockworkphase2.network.message.MessageItemStorageResize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerItemStorageResize implements IMessageHandler<MessageItemStorageResize, IMessage>
{
    @Override
    public IMessage onMessage(MessageItemStorageResize message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if(player != null)
        {
            World world = player.worldObj;
            if(world != null && message.pos != null)
            {
                TileEntity te = world.getTileEntity(message.pos);
                if(te != null && te instanceof TileClockworkItemStorage && ((TileClockworkItemStorage) te).isUseableByPlayer(player))
                    if(     ((TileClockworkItemStorage) te).canResizeInventory(message.xSlots, message.ySlots)
                            && player.openContainer != null
                            && player.openContainer instanceof ContainerClockworkItemStorage       )
                    {
                        ((ContainerClockworkItemStorage) player.openContainer).resizeSlotsInContainer(message.xSlots, message.ySlots);
                        ((TileClockworkItemStorage) te).resizeInventory(message.xSlots, message.ySlots);
                    }
            }
        }
        return null;
    }
}
