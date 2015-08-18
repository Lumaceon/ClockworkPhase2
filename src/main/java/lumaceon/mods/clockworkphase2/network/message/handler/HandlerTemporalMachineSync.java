package lumaceon.mods.clockworkphase2.network.message.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalMachineSync;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class HandlerTemporalMachineSync implements IMessageHandler<MessageTemporalMachineSync, IMessage>
{
    @Override
    public IMessage onMessage(MessageTemporalMachineSync message, MessageContext ctx)
    {
        TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        if(te != null && te instanceof TileTemporal)
            ((TileTemporal)te).updateClientTime(message.nbt);
        return null;
    }
}
