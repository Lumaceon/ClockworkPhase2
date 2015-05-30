package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalToolFunction;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalableTool;
import lumaceon.mods.clockworkphase2.api.util.TemporalToolHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;

public class WorldHandler
{
    @SubscribeEvent
    public void onBlockHarvested(BlockEvent.HarvestDropsEvent event)
    {
        if(event.world.isRemote || event.harvester == null)
            return;
        if(event.harvester.inventory == null || event.drops == null || event.drops.isEmpty())
            return;

        ItemStack heldItem = event.harvester.inventory.getStackInSlot(event.harvester.inventory.currentItem);
        if(!event.isSilkTouching)
        {
            if(event.harvester.inventory.getStackInSlot(0) != null && event.harvester.inventory.getStackInSlot(0).getItem() instanceof ITemporalableTool)
            {
                ITemporalToolFunction silk = null;
                ItemStack silkStack = null;
                ITemporalToolFunction smelt = null;
                ItemStack smeltStack = null;

                if(heldItem != null && heldItem.getItem() instanceof ITemporalableTool && ((ITemporalableTool) heldItem.getItem()).isTemporal(heldItem) && heldItem.getItem() instanceof ITimeSand)
                {
                    ITimeSand timeContainer = (ITimeSand) heldItem.getItem();
                    long timeSand = TemporalToolHelper.getTimeSand(heldItem);
                    if(NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
                    {
                        ItemStack[] inventory = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
                        for(ItemStack item : inventory)
                        {
                            if(item != null && item.getItem().equals(ModItems.temporalFunctionSilkyHarvest))
                            {
                                silk = (ITemporalToolFunction) item.getItem();
                                silkStack = item;
                            }

                            if(item != null && item.getItem().equals(ModItems.temporalFunctionSmelt))
                            {
                                smelt = (ITemporalToolFunction) item.getItem();
                                smeltStack = item;
                            }
                        }
                    }

                    if(silk != null && silkStack != null && !event.drops.isEmpty())
                    {
                        if(event.block.canSilkHarvest(event.world, event.harvester, event.x, event.y, event.z, event.blockMetadata))
                        {
                            if(timeSand - silk.getTimeSandCostPerApplication(silkStack) >= 0)
                            {
                                timeContainer.consumeTimeSand(heldItem, event.harvester, silk.getTimeSandCostPerApplication(silkStack));
                                ItemStack result = new ItemStack(event.block, 1, event.blockMetadata);
                                event.drops.set(0, result);
                            }
                        }
                    }
                    else if(smelt != null && !event.drops.isEmpty())
                    {
                        for(int n = 0; n < event.drops.size(); n++)
                        {
                            ItemStack smeltedOutput = FurnaceRecipes.smelting().getSmeltingResult(event.drops.get(n).copy());

                            //Fortune code from BlockOre\\
                            int j = event.world.rand.nextInt(event.fortuneLevel + 2) - 1;
                            if (j < 0) { j = 0; }
                            int size = j + 1; //Modified to ignore quantity dropped, already handled by iterating through drops.
                            //Fortune code from BlockOre\\

                            if(smeltedOutput != null && timeSand - smelt.getTimeSandCostPerApplication(smeltStack) >= 0)
                            {
                                timeContainer.consumeTimeSand(heldItem, event.harvester, smelt.getTimeSandCostPerApplication(smeltStack));
                                //Only drop 1 if the smelted item is a block or the same as the block broken
                                if(Item.getItemFromBlock(event.block).equals(smeltedOutput.getItem()))
                                    size = 1;
                                smeltedOutput.stackSize = size;
                                event.drops.remove(n);
                                event.drops.add(n, smeltedOutput);
                            }
                        }
                    }
                }
            }
        }

        if(heldItem != null && NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
            if(items == null)
                return;

            for(ItemStack item : items)
            {
                if(item != null && item.getItem().equals(ModItems.temporalFunctionRelocation))
                {
                    int x = NBTHelper.INT.get(item, "cp_x");
                    int y = NBTHelper.INT.get(item, "cp_y");
                    int z = NBTHelper.INT.get(item, "cp_z");
                    int side = NBTHelper.INT.get(item, "cp_side");

                    TileEntity te = event.world.getTileEntity(x, y, z);
                    if(te != null && te instanceof IInventory)
                    {
                        if(te instanceof ISidedInventory) //Inventory is side-specific.
                        {
                            ISidedInventory inventory = (ISidedInventory) te;

                            for(int n = 0; n < event.drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = event.drops.get(n);
                                int[] validSlots = inventory.getAccessibleSlotsFromSide(side);
                                for(int currentSlot : validSlots)
                                {
                                    if(inventory.isItemValidForSlot(currentSlot, drop) && inventory.canInsertItem(currentSlot, drop, side))
                                    {
                                        ItemStack inventorySlotItem = inventory.getStackInSlot(currentSlot);
                                        if(inventorySlotItem != null)
                                        {
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.stackSize + 1)
                                            {
                                                inventorySlotItem.stackSize++;
                                                inventory.setInventorySlotContents(currentSlot, inventorySlotItem);
                                                event.drops.remove(n);
                                                --n;
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            inventory.setInventorySlotContents(currentSlot, drop.copy());
                                            event.drops.remove(n);
                                            --n;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else //Inventory is not side specific, loop through all slots instead.
                        {
                            IInventory inventory = (IInventory) te;
                            for(int n = 0; n < event.drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = event.drops.get(n);
                                boolean escapeFlag = false;
                                for(int i = 0; i < inventory.getSizeInventory() && !escapeFlag; i++)
                                {
                                    if(inventory.isItemValidForSlot(i, drop))
                                    {
                                        ItemStack inventorySlotItem = inventory.getStackInSlot(i);
                                        if(inventorySlotItem != null)
                                        {
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.stackSize + 1)
                                            {
                                                inventorySlotItem.stackSize++;
                                                inventory.setInventorySlotContents(i, inventorySlotItem);
                                                event.drops.remove(n);
                                                --n;
                                                escapeFlag = true;
                                            }
                                        }
                                        else
                                        {
                                            inventory.setInventorySlotContents(i, drop.copy());
                                            event.drops.remove(n);
                                            --n;
                                            escapeFlag = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
