package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.MemoryItemRegistry;
import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

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
                event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(ModItems.gearElysianBroken)));
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

    @SubscribeEvent
    public void onEntityClicked(EntityInteractEvent event) //Player right-clicked an entity.
    {
        if(event.target != null && event.target instanceof EntityPAC) //The entity was a PAC.
            ((EntityPAC) event.target).onRightClicked(event.entityPlayer);
    }

    /*@SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent event)
    {
        if(!event.isCanceled() && event instanceof LivingSpawnEvent.CheckSpawn)
        {
            ITimezone timezone = TimezoneHandler.getTimeZone(event.x, event.y, event.z, event.world);
            if(timezone != null)
            {
                ItemStack timestream;
                for(int n = 0; n < 8; n++)
                {
                    timestream = timezone.getTimezoneModule(n);
                    if(timestream != null && timestream.getItem() instanceof ItemTimestreamMobMagnet)
                    {
                        event.entity.setPosition(timezone.getX(), timezone.getY(), timezone.getZ());
                        break;
                    }
                }
            }
        }
    }*/
}
