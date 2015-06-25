package lumaceon.mods.clockworkphase2.network.message.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lumaceon.mods.clockworkphase2.item.construct.weapon.ItemLightningSword;
import lumaceon.mods.clockworkphase2.network.message.MessageLightningSwordActivate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HandlerLightningSwordActivate implements IMessageHandler<MessageLightningSwordActivate, IMessage>
{
    @Override
    public IMessage onMessage(MessageLightningSwordActivate message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if(player != null)
        {
            ItemStack heldItem = player.inventory.getCurrentItem();
            if(heldItem != null && heldItem.getItem() instanceof ItemLightningSword)
                ((ItemLightningSword) heldItem.getItem()).lightningTeleport(heldItem, player.worldObj, player, message.charge, message.pos, message.look);
        }
        return null;
    }
}
