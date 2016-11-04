package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemTemporalExcavator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.network.message.MessageToolUpgradeActivate;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerToolUpgradeActivate implements IMessageHandler<MessageToolUpgradeActivate, IMessage>
{


    @Override
    public IMessage onMessage(final MessageToolUpgradeActivate message, final MessageContext ctx)
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

    private void processMessage(MessageToolUpgradeActivate message, MessageContext ctx, EntityPlayerMP player)
    {
        ItemStack item = player.inventory.getCurrentItem();
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
}
