package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.IAchievementScoreHandler;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemTemporalExcavator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler
{
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone event)
    {
        if(event.isWasDeath())
        {
            EntityPlayer oldPlayer = event.getOriginal();
            EntityPlayer player = event.getEntityPlayer();
            if(oldPlayer.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN) && player.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN))
            {
                IAchievementScoreHandler oldAchievementScoreHandler = oldPlayer.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
                IAchievementScoreHandler newAchievementScoreHandler = player.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
                newAchievementScoreHandler.calculateTier(oldAchievementScoreHandler.getAchievementPoints());
            }
        }
    }

    @SubscribeEvent
    public void breakSpeedModification(PlayerEvent.BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if(player != null)
        {
            ItemStack currentItem = player.inventory.getCurrentItem();
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
