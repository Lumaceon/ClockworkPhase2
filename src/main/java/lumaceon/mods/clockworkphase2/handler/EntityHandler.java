package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.MemoryItemRegistry;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EntityHandler
{
    @SubscribeEvent
    public void onEntityItemDrop(LivingDropsEvent event)
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
}
