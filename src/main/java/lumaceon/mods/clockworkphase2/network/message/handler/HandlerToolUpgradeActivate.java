package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.item.construct.tool.ItemTemporalExcavator;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemClockworkTool;
import lumaceon.mods.clockworkphase2.network.message.MessageToolUpgradeActivate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerToolUpgradeActivate implements IMessageHandler<MessageToolUpgradeActivate, IMessage>
{
    @Override
    public IMessage onMessage(MessageToolUpgradeActivate message, MessageContext ctx)
    {
        if(ctx.side.isServer() && ctx.getServerHandler().playerEntity != null)
        {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack item = player.getHeldItem();
            if(item != null && item.getItem() instanceof ItemTemporalExcavator && NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                if(items != null && items.length > 3 + message.buttonID)
                {
                    ItemStack upgrade = items[message.buttonID + 3];
                    if(upgrade != null && upgrade.getItem() instanceof IToolUpgrade)
                    {
                        IToolUpgrade upgradeItem = (IToolUpgrade) upgrade.getItem();
                        upgradeItem.setActive(upgrade, item, !upgradeItem.getActive(upgrade, item));
                        NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, items);
                    }
                }
            }
        }
        return null;
    }
}
