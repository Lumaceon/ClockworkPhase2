package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.util.TimeHelper;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler
{
    @SubscribeEvent
    public void breakSpeedModification(PlayerEvent.BreakSpeed event)
    {
        if(event.entityPlayer != null)
        {
            ItemStack currentItem = event.entityPlayer.inventory.getCurrentItem();
            if(currentItem != null && currentItem.getItem().equals(ModItems.temporalExcavator))
                if(TimeHelper.getTimeInInventory(event.entityPlayer.inventory) < TimeHelper.timeToBreakBlock(event.state.getBlock().getBlockHardness(event.entityPlayer.worldObj, event.pos), event.entityPlayer, currentItem))
                    event.setCanceled(true);
        }
    }
}
