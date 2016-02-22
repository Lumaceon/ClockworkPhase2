package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageTileStateChange;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerTileStateChange implements IMessageHandler<MessageTileStateChange, IMessage>
{
    @Override
    public IMessage onMessage(MessageTileStateChange message, MessageContext ctx)
    {
        if(Minecraft.getMinecraft().theWorld != null)
        {
            TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(new BlockPos(message.x, message.y, message.z));
            //if(te != null && te instanceof TileClockworkPhase)
                ///((TileClockworkPhase) te).setState(message.state);
        }
        return null;
    }
}
