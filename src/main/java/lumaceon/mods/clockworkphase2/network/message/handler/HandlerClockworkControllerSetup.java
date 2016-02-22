package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.network.message.MessageClockworkControllerSetup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClockworkControllerSetup implements IMessageHandler<MessageClockworkControllerSetup, IMessage>
{
    @Override
    public IMessage onMessage(MessageClockworkControllerSetup message, MessageContext ctx)
    {
        if(message.settings == null || message.controllerPos == null)
            return null;

        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if(player == null)
            return null;

        World world = player.worldObj;
        if(world == null || world.isRemote)
            return null;

        TileEntity te = world.getTileEntity(message.controllerPos);
        if(te != null && te instanceof TileClockworkController)
        {
            TileClockworkController controller = (TileClockworkController) te;
            controller.newSettings(message.settings);
            controller.markDirty();
        }

        return null;
    }
}
