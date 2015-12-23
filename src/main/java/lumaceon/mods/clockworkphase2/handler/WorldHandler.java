package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedMapData;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeFurnace;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeRelocate;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeSilk;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

public class WorldHandler
{
    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        if(event.world != null && event.world.provider.dimensionId == 0) //Is this the main world?
        {
            ExtendedMapData worldData = ExtendedMapData.get(event.world);
            if(!worldData.isRuinMapGenerated() && !event.world.isRemote)
                worldData.generateRuinMap();
        }
    }

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
            IToolUpgrade silk = null;
            ItemStack silkStack = null;
            IToolUpgrade smelt = null;

            if(heldItem != null)
            {
                if(NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
                {
                    ItemStack[] inventory = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
                    for(ItemStack item : inventory)
                    {
                        if(item != null && item.getItem().equals(ModItems.toolUpgradeSilk) && ((ItemToolUpgradeSilk) item.getItem()).getActive(item, heldItem))
                        {
                            silk = (IToolUpgrade) item.getItem();
                            silkStack = item;
                        }

                        if(item != null && item.getItem().equals(ModItems.toolUpgradeFurnace) && ((ItemToolUpgradeFurnace) item.getItem()).getActive(item, heldItem))
                            smelt = (IToolUpgrade) item.getItem();
                    }
                }

                if(silk != null && silkStack != null && !event.drops.isEmpty())
                {
                    if(event.block.canSilkHarvest(event.world, event.harvester, event.x, event.y, event.z, event.blockMetadata))
                    {
                        ItemStack result = new ItemStack(event.block, 1, event.block.damageDropped(event.blockMetadata));
                        event.drops.set(0, result);
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

                        if(smeltedOutput != null)
                        {
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

        if(heldItem != null && NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
            if(items == null)
                return;

            for(ItemStack item : items)
            {
                if(item != null && item.getItem().equals(ModItems.toolUpgradeRelocate) && ((ItemToolUpgradeRelocate) item.getItem()).getActive(item, heldItem))
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
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.stackSize + drop.stackSize)
                                            {
                                                inventorySlotItem.stackSize += drop.stackSize;
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
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.stackSize + drop.stackSize)
                                            {
                                                inventorySlotItem.stackSize += drop.stackSize;
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
