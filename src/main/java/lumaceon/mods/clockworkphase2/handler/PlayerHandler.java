package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.util.TimeHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemTemporalExcavator;
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
            if(currentItem != null && currentItem.getItem() instanceof ItemTemporalExcavator)
            {
                ItemStack[] componentInventory = NBTHelper.INVENTORY.get(currentItem, NBTTags.COMPONENT_INVENTORY);
                if(componentInventory != null)
                {
                    for(int n = 3; n < componentInventory.length; n++)
                    {
                        ItemStack component = componentInventory[n];
                        if(component != null && component.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                            if(((ItemToolUpgradeTemporalInfuser) component.getItem()).getActive(component, currentItem))
                                if(TimeHelper.getTimeInInventory(event.entityPlayer.inventory) < TimeHelper.timeToBreakBlock(event.entity.worldObj, event.pos, event.state.getBlock(), event.entityPlayer, currentItem))
                                    event.setCanceled(true);
                    }
                }
            }
        }
    }
}
