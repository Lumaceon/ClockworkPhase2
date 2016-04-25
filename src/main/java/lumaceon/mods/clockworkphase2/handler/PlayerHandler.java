package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.util.TimeHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemTemporalHourglass;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemTemporalExcavator;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
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
                                if(TimeHelper.getTimeInInventory(event.entityPlayer.inventory) < TimeHelper.getTimeToBreakBlock(event.entity.worldObj, event.pos, event.state.getBlock(), event.entityPlayer, currentItem))
                                    event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerPickupXP(PlayerPickupXpEvent event)
    {
        if(!event.isCanceled() && event.entityPlayer != null && event.entityPlayer.inventory != null)
        {
            for(int n = 0; n < event.entityPlayer.inventory.getSizeInventory(); n++)
            {
                ItemStack item = event.entityPlayer.inventory.getStackInSlot(n);
                if(item != null && item.getItem() instanceof ItemTemporalHourglass && NBTHelper.BOOLEAN.get(item, NBTTags.ACTIVE))
                {
                    ((ItemTemporalHourglass) item.getItem()).receiveTime(item, (int) (event.orb.getXpValue() * Defaults.TIME.perXP * TimeHelper.getXPToTimeMultiplier(event.entityPlayer)), false);
                    event.orb.setDead();
                    event.setCanceled(true);
                }
            }
        }
    }
}
