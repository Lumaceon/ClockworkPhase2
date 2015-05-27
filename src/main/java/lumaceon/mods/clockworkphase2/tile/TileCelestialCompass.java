package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.api.crafting.ITimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileCelestialCompass extends TileEntity implements ITimezone
{
    private int blocksToPlace = 96;
    private boolean registerTimezone = true;

    private ItemStack[] craftingItems = new ItemStack[9];
    private ITimestreamCraftingRecipe currentRecipe;
    private long timeSandLeftToCraft = 0;

    private long timeSand = 0;


    public boolean isAvailable()
    {
        return blocksToPlace < 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);
        nbt.setLong("TZ_timesand", this.timeSand);
        nbt.setLong("TZ_tstg", this.timeSandLeftToCraft);

        if(craftingItems != null)
        {
            NBTTagList nbtList = new NBTTagList();
            for (int index = 0; index < craftingItems.length; index++)
            {
                if(craftingItems[index] != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    craftingItems[index].writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
                else
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    nbtList.appendTag(tag);
                }
            }
            nbt.setTag("CC_craftC", nbtList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");
        this.timeSand = nbt.getLong("TZ_timesand");
        this.timeSandLeftToCraft = nbt.getLong("TZ_tstg");

        if(nbt.hasKey("CC_craftC"))
        {
            NBTTagList list = (NBTTagList) nbt.getTag("CC_craftC");
            ItemStack[] inventory;
            inventory = new ItemStack[list.tagCount()];

            for(int i = 0; i < list.tagCount(); ++i)
            {
                NBTTagCompound tagCompound = list.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("slot_index");
                if(slotIndex >= 0 && slotIndex < inventory.length)
                {
                    inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                }
            }
            this.craftingItems = inventory;
        }
    }

    @Override
    public void updateEntity()
    {
        if(!isAvailable())
        {
            handleMultiblockPlacement();
            return;
        }

        if(currentRecipe != null)
        {
            if(currentRecipe.matches(craftingItems))
            {
                craftingItems[8] = currentRecipe.getCraftingResult(craftingItems).copy();
                currentRecipe = null;
                for(int n = 0; n < craftingItems.length - 1; n++)
                {
                    craftingItems[n] = null;
                }
            }
        }

        if(registerTimezone && worldObj != null)
        {
            registerTimezone = false;
            TimezoneHandler.INTERNAL.registerTimezone(xCoord, yCoord, zCoord, worldObj);
        }
    }

    public ItemStack getCenterItem()
    {
        return this.craftingItems[8];
    }

    /**
     * Returns the item current in one of the outlying circles, starting at the top and moving clockwise.
     * @param index The circle index.
     * @return The item currently in that circle.
     */
    public ItemStack getCraftingItem(int index)
    {
        return this.craftingItems[index];
    }

    public boolean onMainBlockClicked(EntityPlayer player)
    {
        if(player.isSneaking() && player.inventory.getStackInSlot(player.inventory.currentItem) == null)
        {
            for(ITimestreamCraftingRecipe recipe : TimestreamCraftingRegistry.TIMESTREAM_RECIPES)
            {
                if(recipe.matches(this.craftingItems))
                {
                    this.currentRecipe = recipe;
                    return true;
                }
            }
            return false;
        }
        else if(!player.isSneaking())
        {
            return onSubBlockClicked(player, 8);
        }
        return false;
    }

    public boolean onSubBlockClicked(EntityPlayer player, int circleClicked)
    {
        if(circleClicked == -1 || player.isSneaking())
            return false;
        else
        {
            int currentItem = player.inventory.currentItem;
            if(player.inventory.getStackInSlot(currentItem) != null && craftingItems[circleClicked] == null)
            {
                ItemStack item = player.inventory.getStackInSlot(currentItem).copy();
                item.stackSize = 1;
                craftingItems[circleClicked] = item;
                craftingItems[circleClicked].stackSize = 1;
                player.inventory.decrStackSize(currentItem, 1);
                return true;
            }
            else if(craftingItems[circleClicked] != null && player.inventory.getStackInSlot(currentItem) == null)
            {
                player.inventory.setInventorySlotContents(currentItem, craftingItems[circleClicked].copy());
                craftingItems[circleClicked] = null;
                return true;
            }
        }
        return true;
    }

    private void handleMultiblockPlacement()
    {
        if(!this.worldObj.isRemote)
        {
            if(blocksToPlace == 0)
            {
                for(int n = 0; n < BlockPatterns.CELESTIAL_COMPASS.length; n++)
                {
                    int x, y, z;
                    x = this.xCoord + BlockPatterns.CELESTIAL_COMPASS[n].x;
                    y = this.yCoord + BlockPatterns.CELESTIAL_COMPASS[n].y;
                    z = this.zCoord + BlockPatterns.CELESTIAL_COMPASS[n].z;
                    this.worldObj.markBlockForUpdate(x, y, z);
                    blocksToPlace--;
                }
            }
            else if(blocksToPlace > 0)
            {
                int x, y, z, meta;
                x = this.xCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].x;
                y = this.yCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].y;
                z = this.zCoord + BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].z;
                meta = BlockPatterns.CELESTIAL_COMPASS[blocksToPlace - 1].meta;

                if(worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z).equals(ModBlocks.celestialCompassSB))
                {
                    this.getWorldObj().setBlock(x, y, z, ModBlocks.celestialCompassSB, meta, 2);
                    blocksToPlace--;
                }
                else
                {
                    //PacketHandler.INSTANCE.sendToAllAround(new MessageParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 48));
                }
            }
        }
    }

    public static void destroyCompass(World world, int x, int y, int z)
    {
        for(int n = 0; n < 96; n++)
        {
            int currentX, currentY, currentZ;
            currentX = x + BlockPatterns.CELESTIAL_COMPASS[n].x;
            currentY = y + BlockPatterns.CELESTIAL_COMPASS[n].y;
            currentZ = z + BlockPatterns.CELESTIAL_COMPASS[n].z;

            if(world.getBlock(currentX, currentY, currentZ).equals(ModBlocks.celestialCompassSB))
            {
                world.setBlock(currentX, currentY, currentZ, Blocks.air);
            }
        }
        TimezoneHandler.INTERNAL.pingAndCleanTimezones();
    }

    @Override
    public float getRange()
    {
        return 128;
    }
}
