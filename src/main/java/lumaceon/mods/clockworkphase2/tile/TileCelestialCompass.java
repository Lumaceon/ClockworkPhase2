package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.item.ITimezoneModule;
import lumaceon.mods.clockworkphase2.api.block.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.TimezoneHandler;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.BlockPatterns;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileCelestialCompass extends TileClockworkPhase implements ITimezoneProvider
{
    private int blocksToPlace = 96;
    private boolean registerTimezone = true;

    private ItemStack[] timestreamItems = new ItemStack[9];


    public boolean isAvailable() {
        return blocksToPlace < 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("internal_block_count", this.blocksToPlace);

        if(timestreamItems != null)
        {
            NBTTagList nbtList = new NBTTagList();
            for (int index = 0; index < timestreamItems.length; index++)
            {
                if(timestreamItems[index] != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    timestreamItems[index].writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
                else
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    nbtList.appendTag(tag);
                }
            }
            nbt.setTag("CC_timestreams", nbtList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.blocksToPlace = nbt.getInteger("internal_block_count");

        if(nbt.hasKey("CC_timestreams"))
        {
            NBTTagList list = (NBTTagList) nbt.getTag("CC_timestreams");
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
            this.timestreamItems = inventory;
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

        if(registerTimezone && worldObj != null)
        {
            registerTimezone = false;
            TimezoneHandler.INTERNAL.registerTimezone(xCoord, yCoord, zCoord, worldObj);
        }
    }

    public boolean onMainBlockClicked(EntityPlayer player)
    {
        return onSubBlockClicked(player, 8);
    }

    public boolean onSubBlockClicked(EntityPlayer player, int circleClicked)
    {
        if(circleClicked == -1)
            return false;
        else
        {
            int currentSlot = player.inventory.currentItem;
            ItemStack heldItem = player.inventory.getCurrentItem();
            if(heldItem != null && heldItem.getItem() instanceof ITimeSand && circleClicked == 8) //Put held ITimeSand in center.
            {
                if(timestreamItems[circleClicked] == null)
                {
                    ItemStack item = heldItem.copy();
                    item.stackSize = 1;
                    timestreamItems[circleClicked] = item;
                    timestreamItems[circleClicked].stackSize = 1;
                    player.inventory.decrStackSize(currentSlot, 1);
                    return true;
                }
            }
            else if(heldItem != null && heldItem.getItem() instanceof ITimezoneModule) //Put held timestream in outlying circle.
            {
                if(timestreamItems[circleClicked] == null)
                {
                    for(ItemStack ts : this.timestreamItems)
                    {
                        if(ts != null && ts.getItem().equals(heldItem.getItem()))
                            return false;
                    }
                    ItemStack item = heldItem.copy();
                    item.stackSize = 1;
                    timestreamItems[circleClicked] = item;
                    timestreamItems[circleClicked].stackSize = 1;
                    player.inventory.decrStackSize(currentSlot, 1);
                    return true;
                }
            }
            else if(timestreamItems[circleClicked] != null && heldItem == null) //Remove timestream from circle, put in hand (only if no crafting item exists).
            {
                player.inventory.setInventorySlotContents(currentSlot, timestreamItems[circleClicked].copy());
                timestreamItems[circleClicked] = null;
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

                if(worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z).equals(ModBlocks.celestialCompassSB) || worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z))
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

    public static void destroyMultiblock(World world, int x, int y, int z)
    {
        for(int n = 0; n < 96; n++)
        {
            int currentX, currentY, currentZ;
            currentX = x + BlockPatterns.CELESTIAL_COMPASS[n].x;
            currentY = y + BlockPatterns.CELESTIAL_COMPASS[n].y;
            currentZ = z + BlockPatterns.CELESTIAL_COMPASS[n].z;

            if(world.getBlock(currentX, currentY, currentZ).equals(ModBlocks.celestialCompassSB))
                world.setBlock(currentX, currentY, currentZ, Blocks.air);
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileCelestialCompass)
            for(ItemStack item : ((TileCelestialCompass) te).timestreamItems)
                if(item != null)
                    world.spawnEntityInWorld(new EntityItem(world, x, y, z, item));
        TimezoneHandler.INTERNAL.pingAndCleanTimezones();
    }

    /*@Override
    public float getRange() {
        return timestreamItems[8] != null ? timestreamItems[8].getItem() instanceof ITimeSand ? timestreamItems[8].getItem() instanceof ITemporalCore ? 128 : 12 : 0 : 0; //Temporal core - 128. ITimeSand - 12.
    }*/

    @Override
    public float getRange() { return 128F; }

    @Override
    public int getX() {
        return xCoord;
    }

    @Override
    public int getY() {
        return yCoord;
    }

    @Override
    public int getZ() {
        return zCoord;
    }

    @Override
    public ItemStack getTimezoneModule(int index) {
        return timestreamItems[index];
    }

    @Override
    public void setTimestream(int index, ItemStack item) {
        timestreamItems[index] = item;
        markDirty();
    }

    @Override
    public int getMaxTimeSand()
    {
        if(timestreamItems[8] != null && timestreamItems[8].getItem() instanceof ITimeSand)
            return ((ITimeSand) timestreamItems[8].getItem()).getMaxTimeSand(timestreamItems[8]);
        return 0;
    }

    @Override
    public int getTimeSand()
    {
        if(timestreamItems[8] != null && timestreamItems[8].getItem() instanceof ITimeSand)
            return ((ITimeSand) timestreamItems[8].getItem()).getTimeSand(timestreamItems[8]);
        return 0;
    }

    @Override
    public void setTimeSand(int timeSand)
    {
        if(timestreamItems[8] != null && timestreamItems[8].getItem() instanceof ITimeSand)
        {
            markDirty();
            ((ITimeSand) timestreamItems[8].getItem()).setTimeSand(timestreamItems[8], timeSand);
        }
    }

    @Override
    public int addTimeSand(int timeSand)
    {
        if(timestreamItems[8] != null && timestreamItems[8].getItem() instanceof ITimeSand)
        {
            markDirty();
            return ((ITimeSand) timestreamItems[8].getItem()).addTimeSand(timestreamItems[8], timeSand);
        }
        return 0;
    }

    @Override
    public int consumeTimeSand(int timeSand)
    {
        if(timestreamItems[8] != null && timestreamItems[8].getItem() instanceof ITimeSand)
        {
            markDirty();
            return ((ITimeSand) timestreamItems[8].getItem()).consumeTimeSand(timestreamItems[8], timeSand);
        }
        return 0;
    }

    @Override
    public void setState(int state) {} //NOOP

    @Override
    public void setStateAndUpdate(int state) {} //NOOP
}
