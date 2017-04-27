package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.util.Colors;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import lumaceon.mods.clockworkphase2.util.NBTTags;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemTemporalExcavator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler
{
    @SubscribeEvent
    public void breakSpeedModification(PlayerEvent.BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if(player != null)
        {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if(currentItem != null && currentItem.getItem() instanceof ItemTemporalExcavator)
            {
                ItemTemporalExcavator.ItemStackHandlerTemporalExcavator inventory = ((ItemTemporalExcavator) currentItem.getItem()).getInventoryHandler(currentItem);
                if(inventory != null)
                {
                    for(int n = 3; n < inventory.getSlots(); n++)
                    {
                        ItemStack component = inventory.getStackInSlot(n);
                        if(component != null && component.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                            if(((ItemToolUpgradeTemporalInfuser) component.getItem()).getActive(component, currentItem))
                            {
                                ItemStack[] hourglasses = HourglassHelper.getActiveHourglasses(player);
                                if(HourglassHelper.getTimeFromHourglasses(hourglasses) < HourglassHelper.getTimeToBreakBlock(event.getEntity().worldObj, event.getPos(), event.getState(), event.getEntityPlayer(), currentItem))
                                    event.setCanceled(true);
                            }

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTooltipGet(ItemTooltipEvent event) {
        if(!event.isCanceled() && event.getItemStack() != null && event.getItemStack().getItem() instanceof IAssemblable)
            event.getToolTip().add(Colors.AQUA + "~Assembly Item~");
    }
}
