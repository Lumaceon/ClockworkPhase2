package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalToolbeltSwap;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerTemporalToolbeltSwap implements IMessageHandler<MessageTemporalToolbeltSwap, IMessage>
{
    @Override
    public IMessage onMessage(final MessageTemporalToolbeltSwap message, final MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageTemporalToolbeltSwap received on wrong side:" + ctx.side);
            return null;
        }

        if(ctx.getServerHandler().player == null)
        {
            LogHelper.info("MessageTemporalToolbeltSwap received with no player");
            return null;
        }

        final WorldServer world = DimensionManager.getWorld(0);
        world.addScheduledTask(() -> processMessage(message, ctx));
        return null;
    }

    private void processMessage(MessageTemporalToolbeltSwap message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().player;
        ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
        if(toolbelt != null && toolbelt.getRowCount() > message.toolbeltRowIndex)
        {
            NonNullList<ItemStack> newHotbar = toolbelt.getRow(message.toolbeltRowIndex);
            NonNullList<ItemStack> currentHotbar = NonNullList.withSize(9, ItemStack.EMPTY);
            for(int i = 0; i < 9; i++)
            {
                currentHotbar.set(i, player.inventory.getStackInSlot(i));
            }

            for(int i = 0; i < 9; i++)
            {
                player.inventory.setInventorySlotContents(i, newHotbar.get(i));
            }

            toolbelt.setRow(message.toolbeltRowIndex, currentHotbar);
        }
    }
}
