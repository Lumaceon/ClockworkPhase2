package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.MemoryItemRegistry;
import lumaceon.mods.clockworkphase2.api.block.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.TimezoneHandler;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.item.timezonemodule.ItemTimezoneModuleMobRepellent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler
{
    @SubscribeEvent
    public void onEntityItemDrop(LivingDropsEvent event) //Drop Memory Items.
    {
        if(event.entityLiving.worldObj != null)
        {
            if(event.entityLiving.worldObj.rand.nextInt(99) == 0)
            {
                if(MemoryItemRegistry.MEMORY_ITEMS.size() == 1)
                    event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(MemoryItemRegistry.MEMORY_ITEMS.get(0))));
                else if(MemoryItemRegistry.MEMORY_ITEMS.size() <= 0)
                    return;
                else
                    event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(MemoryItemRegistry.MEMORY_ITEMS.get(event.entity.worldObj.rand.nextInt(MemoryItemRegistry.MEMORY_ITEMS.size())))));
            }
            if(event.entityLiving.worldObj.rand.nextInt(100000) == 0)
                event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(ModItems.gearSecondAge.getItem())));
        }
    }

    @SubscribeEvent
    public void onEntityConstruction(EntityEvent.EntityConstructing event) //Register ExtendedPlayerProperties on join.
    {
        if(event.entity instanceof EntityPlayer && ExtendedPlayerProperties.get((EntityPlayer) event.entity) == null)
        {
            ExtendedPlayerProperties.register((EntityPlayer) event.entity);
        }
    }

    /*@SubscribeEvent
    public void onEntityClicked(EntityInteractEvent event) //Player right-clicked an entity.
    {
        if(event.target != null && event.target instanceof EntityPAC) //The entity was a PAC.
            ((EntityPAC) event.target).onRightClicked(event.entityPlayer);
    }*/

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.CheckSpawn event)
    {
        if(!event.isCanceled())
        {
            ITimezoneProvider timezone = TimezoneHandler.getTimeZone(event.x, event.y, event.z, event.world);
            if(timezone != null)
            {
                ItemStack timestream;
                for(int n = 0; n < 8; n++)
                {
                    timestream = timezone.getTimezoneModule(n);
                    if(timestream != null && timestream.getItem() instanceof ItemTimezoneModuleMobRepellent)
                    {
                        event.setResult(Event.Result.DENY);
                        return;
                    }
                }
            }
        }
    }
}
