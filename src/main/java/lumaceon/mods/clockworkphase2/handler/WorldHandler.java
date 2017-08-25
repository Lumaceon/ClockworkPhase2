package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.capabilities.coordinate.ICoordinateHandler;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModBiomes;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeFurnace;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeRelocate;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Random;

public class WorldHandler
{
    @CapabilityInject(ICoordinateHandler.class)
    public static final Capability<ICoordinateHandler> COORDINATE = null;

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

        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        IToolUpgrade smelt = null;

        if(!heldItem.isEmpty())
        {
            IItemHandler inventory = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if(inventory != null)
            {
                ItemStack item;
                for(int i = 3; i < inventory.getSlots(); i++)
                {
                    item = inventory.getStackInSlot(i);

                    if(!item.isEmpty() && item.getItem().equals(ModItems.toolUpgradeFurnace) && ((ItemToolUpgradeFurnace) item.getItem()).getActive(item, heldItem))
                        smelt = (IToolUpgrade) item.getItem();
                }
            }

            if(!event.isSilkTouching())
            {
                if(smelt != null && !drops.isEmpty())
                    for(int n = 0; n < drops.size(); n++)
                    {
                        ItemStack smeltedOutput = FurnaceRecipes.instance().getSmeltingResult(drops.get(n));

                        //Fortune code from BlockOre\\
                        int j = world.rand.nextInt(event.getFortuneLevel() + 2) - 1;
                        if (j < 0) { j = 0; }
                        int size = j + 1; //Modified to ignore quantity dropped, already handled by iterating through drops.
                        //Fortune code from BlockOre\\

                        if(!smeltedOutput.isEmpty())
                        {
                            smeltedOutput = smeltedOutput.copy();
                            //Only drop 1 if the smelted item is a block or the same as the block broken
                            if(Block.getBlockFromItem(smeltedOutput.getItem()) != null || Item.getItemFromBlock(state.getBlock()).equals(smeltedOutput.getItem()))
                            {
                                size = 1;
                            }
                            smeltedOutput.setCount(size);
                            drops.remove(n);
                            drops.add(n, smeltedOutput);
                        }
                    }
            }
        }

        //"RELOCATE ITEMS TO INVENTORY" UPGRADE
        if(!heldItem.isEmpty())
        {
            IItemHandler inventory = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if(inventory == null)
                return;

            ItemStack item;
            for(int i = 3; i < inventory.getSlots(); i++)
            {
                item = inventory.getStackInSlot(i);
                if(!item.isEmpty() && item.getItem().equals(ModItems.toolUpgradeRelocate) && ((ItemToolUpgradeRelocate) item.getItem()).getActive(item, heldItem))
                {
                    ICoordinateHandler coordinateHandler = item.getCapability(COORDINATE, EnumFacing.DOWN);
                    BlockPos targetPosition = coordinateHandler.getCoordinate();
                    EnumFacing side = coordinateHandler.getSide();
                    if(side == null)
                    {
                        side = EnumFacing.DOWN;
                    }

                    TileEntity te = world.getTileEntity(targetPosition);
                    if(te != null && te instanceof IInventory)
                    {
                        if(te instanceof ISidedInventory) //Inventory is side-specific.
                        {
                            ISidedInventory sidedInventory = (ISidedInventory) te;
                            for(int n = 0; n < drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = drops.get(n);
                                int[] validSlots = sidedInventory.getSlotsForFace(side);
                                for(int currentSlot : validSlots)
                                {
                                    if(sidedInventory.isItemValidForSlot(currentSlot, drop) && sidedInventory.canInsertItem(currentSlot, drop, side))
                                    {
                                        ItemStack inventorySlotItem = sidedInventory.getStackInSlot(currentSlot);
                                        if(!inventorySlotItem.isEmpty())
                                        {
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.getCount() + drop.getCount())
                                            {
                                                inventorySlotItem.grow(drop.getCount());
                                                sidedInventory.setInventorySlotContents(currentSlot, inventorySlotItem);
                                                drops.remove(n);
                                                --n;
                                                PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            sidedInventory.setInventorySlotContents(currentSlot, drop.copy());
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
                            IInventory tileInventory = (IInventory) te;
                            for(int n = 0; n < drops.size(); n++) //Each drop.
                            {
                                ItemStack drop = drops.get(n);
                                boolean escapeFlag = false;
                                for(int n2 = 0; n2 < tileInventory.getSizeInventory() && !escapeFlag; n2++)
                                {
                                    if(tileInventory.isItemValidForSlot(n2, drop))
                                    {
                                        ItemStack inventorySlotItem = tileInventory.getStackInSlot(n2);
                                        if(!inventorySlotItem.isEmpty())
                                        {
                                            if(drop.getItem().equals(inventorySlotItem.getItem()) && drop.getItemDamage() == inventorySlotItem.getItemDamage() && inventorySlotItem.getMaxStackSize() >= inventorySlotItem.getCount() + drop.getCount())
                                            {
                                                inventorySlotItem.grow(drop.getCount());
                                                tileInventory.setInventorySlotContents(n2, inventorySlotItem);
                                                drops.remove(n);
                                                --n;
                                                escapeFlag = true;
                                                PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(0, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                                            }
                                        }
                                        else
                                        {
                                            tileInventory.setInventorySlotContents(n2, drop.copy());
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

    /**
     * Mostly copied from WorldServer. Used to find a spawn point away from the center of the world, which is now
     * dangerous because of the crater of death.
     */
    @SubscribeEvent
    public void onWorldFindSpawn(WorldEvent.CreateSpawnPosition event)
    {
        if(ConfigValues.SPAWN_WORLD_CRATER)
        {
            World world = event.getWorld();
            if(world != null && !world.isRemote && world.provider.getDimension() == 0)
            {
                event.setCanceled(true);
                BiomeProvider biomeprovider = world.provider.getBiomeProvider();
                List<Biome> list = biomeprovider.getBiomesToSpawnIn();
                Random random = new Random(world.getSeed());
                BlockPos blockpos = biomeprovider.findBiomePosition(700, 700, 256, list, random);
                int i = 8;
                int j = world.provider.getAverageGroundLevel();
                int k = 8;

                if (blockpos != null)
                {
                    i = blockpos.getX();
                    k = blockpos.getZ();
                }
                else
                {
                    LogHelper.info("Unable to find spawn biome");
                }

                int l = 0;

                while (!world.provider.canCoordinateBeSpawn(i, k) || world.getBiome(new BlockPos(i, 0, k)) == ModBiomes.temporalFallout)
                {
                    i += random.nextInt(64) - random.nextInt(64);
                    k += random.nextInt(64) - random.nextInt(64);
                    ++l;

                    if (l == 1000)
                    {
                        break;
                    }
                }

                world.getWorldInfo().setSpawn(new BlockPos(i, j, k));

                if(event.getSettings().isBonusChestEnabled())
                {
                    createBonusChest(world);
                }
            }
        }
    }

    /**
     * Creates the bonus chest in the specified world.
     */
    protected void createBonusChest(World world)
    {
        WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest();

        for (int i = 0; i < 10; ++i)
        {
            int j = world.getWorldInfo().getSpawnX() + world.rand.nextInt(6) - world.rand.nextInt(6);
            int k = world.getWorldInfo().getSpawnZ() + world.rand.nextInt(6) - world.rand.nextInt(6);
            BlockPos blockpos = world.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)).up();

            if(worldgeneratorbonuschest.generate(world, world.rand, blockpos))
            {
                break;
            }
        }
    }
}
