package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedMapData;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.components.ItemTemporalItemStorageMatrix;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeFurnace;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeRelocate;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeSilk;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeStorage;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldHandler
{
    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        if(event.world != null)
        {
            ExtendedMapData worldData = ExtendedMapData.get(event.world);
            if(!worldData.isRuinMapGenerated() && !event.world.isRemote)
                worldData.generateRuinMap();
            if(!worldData.areExperimentalAlloysRegistered() && !event.world.isRemote)
                worldData.registerExperimentalAlloys();
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
            IToolUpgrade silk = null;
            ItemStack silkStack = null; //TODO - looking back at this code: is this ItemStack even necessary?
            IToolUpgrade smelt = null;
            ItemToolUpgradeStorage massStorage = null;
            ItemStack massStorageStack = null;
            int massStorageIndex = 0;

            if(heldItem != null)
            {
                if(NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
                {
                    ItemStack[] inventory = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
                    for(ItemStack item : inventory)
                    {
                        if(item != null && item.getItem().equals(ModItems.toolUpgradeSilk.getItem()) && ((ItemToolUpgradeSilk) item.getItem()).getActive(item, heldItem))
                        {
                            silk = (IToolUpgrade) item.getItem();
                            silkStack = item;
                        }

                        if(item != null && item.getItem().equals(ModItems.toolUpgradeFurnace.getItem()) && ((ItemToolUpgradeFurnace) item.getItem()).getActive(item, heldItem))
                            smelt = (IToolUpgrade) item.getItem();

                        if(item != null && item.getItem().equals(ModItems.toolUpgradeMassStorage.getItem()) && ((ItemToolUpgradeStorage) item.getItem()).getActive(item, heldItem))
                        {
                            massStorage = (ItemToolUpgradeStorage) item.getItem();
                            massStorageStack = item;
                        }
                        else if(massStorageStack == null)
                            ++massStorageIndex;
                    }
                }

                if(!event.isSilkTouching)
                {
                    if(smelt != null && !event.drops.isEmpty())
                        for(int n = 0; n < event.drops.size(); n++)
                        {
                            ItemStack smeltedOutput = FurnaceRecipes.instance().getSmeltingResult(event.drops.get(n));

                            //Fortune code from BlockOre\\
                            int j = event.world.rand.nextInt(event.fortuneLevel + 2) - 1;
                            if (j < 0) { j = 0; }
                            int size = j + 1; //Modified to ignore quantity dropped, already handled by iterating through drops.
                            //Fortune code from BlockOre\\

                            if(smeltedOutput != null)
                            {
                                smeltedOutput = smeltedOutput.copy();
                                //Only drop 1 if the smelted item is a block or the same as the block broken
                                if(Block.getBlockFromItem(smeltedOutput.getItem()) != null || Item.getItemFromBlock(event.state.getBlock()).equals(smeltedOutput.getItem()))
                                {
                                    size = 1;
                                }
                                smeltedOutput.stackSize = size;
                                event.drops.remove(n);
                                event.drops.add(n, smeltedOutput);
                            }
                        }
                }
            }

        //"STORE THE USELESS CRAP" UPGRADE
        if(massStorage != null && !event.drops.isEmpty())
        {
            if(massStorageStack != null && NBTHelper.hasTag(massStorageStack, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(massStorageStack, NBTTags.COMPONENT_INVENTORY);
                for(int n = 0; n < event.drops.size(); n++)
                {
                    ItemStack drop = event.drops.get(n);
                    if(drop != null && drop.isStackable())
                    {
                        for(int i = 0; i < items.length; i++)
                        {
                            ItemStack item = items[i];
                            if(item != null && item.getItem() instanceof ItemTemporalItemStorageMatrix)
                            {
                                if(NBTHelper.hasTag(item, "ul_name") && NBTHelper.STRING.get(item, "ul_name").equals(drop.getUnlocalizedName()))
                                {
                                    if(NBTHelper.hasTag(item, "item_count"))
                                        NBTHelper.INT.set(item, "item_count", NBTHelper.INT.get(item, "item_count") + drop.stackSize);
                                    else
                                        NBTHelper.INT.set(item, "item_count", drop.stackSize);
                                    event.drops.remove(n);
                                    --n;
                                    break;
                                }
                            }
                        }
                    }
                }
                NBTHelper.INVENTORY.set(massStorageStack, NBTTags.COMPONENT_INVENTORY, items);
            }
        }

        //"RELOCATE ITEMS TO INVENTORY" UPGRADE
        if(heldItem != null && NBTHelper.hasTag(heldItem, NBTTags.COMPONENT_INVENTORY))
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(heldItem, NBTTags.COMPONENT_INVENTORY);
            if(items == null)
                return;

            for(ItemStack item : items)
            {
                if(item != null && item.getItem().equals(ModItems.toolUpgradeRelocate.getItem()) && ((ItemToolUpgradeRelocate) item.getItem()).getActive(item, heldItem))
                {
                    int x = NBTHelper.INT.get(item, "cp_x");
                    int y = NBTHelper.INT.get(item, "cp_y");
                    int z = NBTHelper.INT.get(item, "cp_z");
                    EnumFacing side = EnumFacing.VALUES[NBTHelper.INT.get(item, "cp_side")];

                    TileEntity te = event.world.getTileEntity(new BlockPos(x, y, z));
                    if(te != null && te instanceof IInventory)
                    {
                        if(te instanceof ISidedInventory) //Inventory is side-specific.
                        {
                            ISidedInventory inventory = (ISidedInventory) te;
                            for(int n = 0; n < event.drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = event.drops.get(n);
                                int[] validSlots = inventory.getSlotsForFace(side);
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
