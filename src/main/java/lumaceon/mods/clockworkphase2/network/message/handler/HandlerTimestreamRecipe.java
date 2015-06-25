package lumaceon.mods.clockworkphase2.network.message.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.network.message.MessageTimestreamRecipe;
import lumaceon.mods.clockworkphase2.tile.TileTimestreamExtractionChamber;
import net.minecraft.tileentity.TileEntity;

public class HandlerTimestreamRecipe implements IMessageHandler<MessageTimestreamRecipe, IMessage>
{
    @Override
    public IMessage onMessage(MessageTimestreamRecipe message, MessageContext ctx)
    {
        TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if(te != null && te instanceof TileTimestreamExtractionChamber)
            if(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.size() > message.index)
                ((TileTimestreamExtractionChamber) te).setRecipe(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.get(message.index));
        return null;
    }
}
