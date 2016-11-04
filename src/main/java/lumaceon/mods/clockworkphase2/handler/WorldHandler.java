package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedMapData;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeRelocate;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeSilk;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageParticleSpawn;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.List;

public class WorldHandler
{
    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        if(event.getWorld() != null)
        {
            ExtendedMapData worldData = ExtendedMapData.get(event.getWorld());
            if(!worldData.areExperimentalAlloysRegistered() && !event.getWorld().isRemote)
                worldData.registerExperimentalAlloys();
        }
    }

    @SubscribeEvent
    public void onBlockHarvested(BlockEvent.HarvestDropsEvent event)
    {
        World world = event.getWorld();
        List<ItemStack> drops = event.getDrops();
        IBlockState state = event.getState();
        EntityPlayer player = event.getHarvester();
        BlockPos pos = event.getPos();
        if(event.getWorld().isRemote || player == null)
            return;
        if(player.inventory == null || drops == null || drops.isEmpty())
            return;

        ItemStack heldItem = player.inventory.getStackInSlot(player.inventory.currentItem);
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
                    if(item != null && item.getItem().equals(ModItems.toolUpgradeSilk.getItem()) && ((ItemToolUpgradeSilk) item.getItem()).getActive(item, heldItem))
                    {
                        silk = (IToolUpgrade) item.getItem();
                        silkStack = item;
                    }

                    //if(item != null && item.getItem().equals(ModItems.toolUpgradeFurnace.getItem()) && ((ItemToolUpgradeFurnace) item.getItem()).getActive(item, heldItem))
                    //    smelt = (IToolUpgrade) item.getItem();
                }
            }

            if(!event.isSilkTouching())
            {
                if(silk != null && silkStack != null && !drops.isEmpty())
                {
                    if(state.getBlock().canSilkHarvest(world, pos, state, player))
                    {
                        ItemStack result = new ItemStack(state.getBlock(), 1, state.getBlock().damageDropped(state));
                        drops.set(0, result);
                    }
                }
                else if(smelt != null && !drops.isEmpty())
                    for(int n = 0; n < drops.size(); n++)
                    {
                        ItemStack smeltedOutput = FurnaceRecipes.instance().getSmeltingResult(drops.get(n));

                        //Fortune code from BlockOre\\
                        int j = world.rand.nextInt(event.getFortuneLevel() + 2) - 1;
                        if (j < 0) { j = 0; }
                        int size = j + 1; //Modified to ignore quantity dropped, already handled by iterating through drops.
                        //Fortune code from BlockOre\\

                        if(smeltedOutput != null)
                        {
                            smeltedOutput = smeltedOutput.copy();
                            //Only drop 1 if the smelted item is a block or the same as the block broken
                            if(Block.getBlockFromItem(smeltedOutput.getItem()) != null || Item.getItemFromBlock(state.getBlock()).equals(smeltedOutput.getItem()))
                            {
                                size = 1;
                            }
                            smeltedOutput.stackSize = size;
                            drops.remove(n);
                            drops.add(n, smeltedOutput);
                        }
                    }
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

                    TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                    if(te != null && te instanceof IInventory)
                    {
                        if(te instanceof ISidedInventory) //Inventory is side-specific.
                        {
                            ISidedInventory inventory = (ISidedInventory) te;
                            for(int n = 0; n < drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = drops.get(n);
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
                                                drops.remove(n);
                                                --n;
                                                PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            inventory.setInventorySlotContents(currentSlot, drop.copy());
                                            drops.remove(n);
                                            --n;
                                            PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else //Inventory is not side specific, loop through all slots instead.
                        {
                            IInventory inventory = (IInventory) te;
                            for(int n = 0; n < drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = drops.get(n);
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
                                                drops.remove(n);
                                                --n;
                                                escapeFlag = true;
                                                PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                                            }
                                        }
                                        else
                                        {
                                            inventory.setInventorySlotContents(i, drop.copy());
                                            drops.remove(n);
                                            --n;
                                            escapeFlag = true;
                                            PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
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
