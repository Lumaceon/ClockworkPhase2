package lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Super class to be extended by tile entities wishing to be used in a clockwork network.
 */
public abstract class TileClockworkNetworkMachine extends TileEntity implements IClockworkNetworkMachine, ISidedInventory, ITickable
{
    protected long uniqueID = -1;

    protected ItemStack[] inventory;
    public BlockPos targetInventory = null; //The location of the clockwork network inventory this will export to.

    protected ClockworkNetwork clockworkNetwork;
    public ItemStack itemBlock;
    protected int quality; //Tension efficiency; higher = less tension used per operation.
    protected int speed; //Work speed; higher = faster machines.

    protected int workProgress = 0;

    public TileClockworkNetworkMachine() {
        inventory = new ItemStack[0];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) //TODO make syncs more efficient.
    {
        super.writeToNBT(nbt);

        if(uniqueID != -1)
            nbt.setLong("uniqueID_CP", uniqueID);

        NBTTagCompound tag;
        if(itemBlock != null)
        {
            tag = new NBTTagCompound();
            itemBlock.writeToNBT(tag);
            nbt.setTag("itemBlock", tag);
        }

        nbt.setInteger(NBTTags.QUALITY, quality);
        nbt.setInteger(NBTTags.SPEED, speed);

        NBTTagList nbtList = new NBTTagList();
        for(int index = 0; index < inventory.length; index++)
        {
            if(inventory[index] != null)
            {
                tag = new NBTTagCompound();
                tag.setByte("slot_index", (byte)index);
                inventory[index].writeToNBT(tag);
                nbtList.appendTag(tag);
            }
        }
        nbt.setTag("inventory_items", nbtList);

        if(targetInventory != null)
            nbt.setIntArray("target_inventory", new int[] {targetInventory.getX(), targetInventory.getY(), targetInventory.getZ()});
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if(nbt.hasKey("uniqueID_CP"))
            uniqueID = nbt.getLong("uniqueID_CP");

        NBTTagCompound tagCompound;
        tagCompound = nbt.getCompoundTag("itemBlock");
        itemBlock = ItemStack.loadItemStackFromNBT(tagCompound);

        quality = nbt.getInteger(NBTTags.QUALITY);
        speed = nbt.getInteger(NBTTags.SPEED);

        NBTTagList tagList = nbt.getTagList("inventory_items", 10);
        inventory = new ItemStack[getSizeInventory()];
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("slot_index");
            if(slotIndex >= 0 && slotIndex < inventory.length)
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
        }

        if(nbt.hasKey("target_inventory"))
        {
            int[] pos = nbt.getIntArray("target_inventory");
            targetInventory = new BlockPos(pos[0], pos[1], pos[2]);
        }
    }

    @Override
    public void update()
    {
        int tensionCost = getTensionCostPerTick();
        boolean working = isWorking(tensionCost);
        boolean isChanged = false;

        if(uniqueID == -1)
        {
            uniqueID = System.currentTimeMillis();
            isChanged = true;
            worldObj.markBlockForUpdate(getPos());
        }

        if(clockworkNetwork == null)
            return;

        if(!this.worldObj.isRemote)
        {
            if(working)
            {
                if(this.canWork())
                {
                    if(getClockworkNetwork().consumeTension(tensionCost) >= tensionCost)
                    {
                        this.workProgress += (int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50);
                        if(this.workProgress >= 10000)
                        {
                            this.workProgress -= 10000;
                            this.work();
                            isChanged = true;
                        }
                    }
                }
                else
                    this.workProgress = 0;
            }

            if(working != isWorking(tensionCost))
                isChanged = true;
        }

        if(isChanged)
            this.markDirty();
    }

    public int getTensionCostPerTick() {
        return ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50;
    }

    protected int getBaseProgressSpeed() {
        return 10000;
    }

    /**
     * @param tensionCost The tension it would cost to work this tick.
     * @return Whether or not the internal machine's stats allow for this to work this tick.
     */
    public boolean isWorking(int tensionCost) {
        return clockworkNetwork != null && clockworkNetwork.getCurrentTension() > tensionCost && speed > 0 && quality > 0;
    }

    /**
     * Whether or not this inventory can process something this tick.
     */
    public abstract boolean canWork();

    /**
     * Handle whatever processing this machine does (like smelting an ingot out of an ore).
     * This is called once every time the workProgress value passes getBaseProgressSpeed().
     */
    public abstract void work();

    /**
     * Called to test if this tile's exports can successfully transfer to the target inventory. If this returns false,
     * most machines cease to run, as they rarely have an export slot of their own.
     *
     * @param exports An array of item stacks containing the result items you wish to export.
     * @return True if all the exports can be inserted into this tile's targeted inventory; false if one or more cannot.
     */
    public boolean canExportAll(ItemStack[] exports)
    {
        if(targetInventory == null)
        {
            //System.out.println("failedTI");
            return false;
        }

        TileEntity te = worldObj.getTileEntity(targetInventory);
        if(te == null || !(te instanceof IInventory))
            return false;

        if(exports == null || exports.length <= 0)
            return true; //Technically, all inventories can successfully accept nothing...

        IInventory inventory = (IInventory) te;
        ItemStack inventoryItem;

        //Simulate changing the exports by stack sizes.
        int[] simulatedExportStackSizes = new int[exports.length];
        for(int n = 0; n < exports.length; n++)
            simulatedExportStackSizes[n] = exports[n].stackSize;

        //Simulate changing the targeted inventory by stack sizes and virtual stack changes.
        int[] simulatedInventoryStackSizes = new int[inventory.getSizeInventory()];
        ItemStack[] simulatedInventoryChanges = new ItemStack[inventory.getSizeInventory()];
        for(int n = 0; n < inventory.getSizeInventory(); n++)
            if(inventory.getStackInSlot(n) != null)
                simulatedInventoryStackSizes[n] = inventory.getStackInSlot(n).stackSize;

        ItemStack item;
        for(int n = 0; n < exports.length; n++) //For every export.
        {
            boolean exportIsGone = false;
            item = exports[n];
            if(item == null)
                continue;

            for(int invIndex = 0; invIndex < inventory.getSizeInventory(); invIndex++) //For every inventory slot.
            {
                inventoryItem = inventory.getStackInSlot(invIndex);

                //Was empty (technically still is), but we simulated a change here. Use simulation stack instead.
                if(simulatedInventoryChanges[invIndex] != null)
                    inventoryItem = simulatedInventoryChanges[invIndex];

                if(inventoryItem == null || !inventoryItem.isStackable() || simulatedInventoryStackSizes[invIndex] >= inventoryItem.getMaxStackSize() || !inventory.isItemValidForSlot(invIndex, item))
                    continue; //Skip this slot, as it's impossible to increase the stack size for potentially 4 reasons.

                //Check if the item is the same as the export and, if necessary, that the metadata is also the same.
                if(inventoryItem.getItem().equals(item.getItem()) && (!inventoryItem.getHasSubtypes() || inventoryItem.getMetadata() == item.getMetadata()))
                {
                    int numberToMove = Math.min(inventoryItem.getMaxStackSize() - simulatedInventoryStackSizes[invIndex], simulatedExportStackSizes[n]);
                    simulatedExportStackSizes[n] -= numberToMove; //Simulate the changing of stack sizes.
                    simulatedInventoryStackSizes[invIndex] += numberToMove;

                    if(simulatedExportStackSizes[n] == 0)
                    {
                        exportIsGone = true; //We've simulated a complete drain of an export, so reflect it's removal.
                        break; //...and continue to the next export.
                    }
                }
            }

            if(!exportIsGone) //Failed to completely remove the item, so try to find an empty slot.
            {
                boolean found = false;
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    inventoryItem = inventory.getStackInSlot(i);
                    if(inventoryItem == null && simulatedInventoryChanges[i] == null && inventory.isItemValidForSlot(i, item))
                    {
                        simulatedInventoryChanges[i] = item;
                        simulatedInventoryStackSizes[i] = item.stackSize;
                        //exportIsGone = true;
                        found = true;
                        break;
                    }
                }
                if(!found)
                    return false;
            }
        }
        return true;
    }

    /**
     * Attempts to export the passed in item stacks to the tile's target inventory. This is essentially the same thing
     * as canExportAll, except that it actually tries to export the item stacks instead of simulating the export. One
     * should, however, test with canExportAll if they want all-or-nothing, as this will export as much as possible.
     *
     * @param exports An array of item stacks containing the result items you wish to export.
     * @return True if all the exports were inserted into this tile's targeted inventory; false if one ore more couldn't.
     */
    public boolean exportAll(ItemStack[] exports)
    {
        TileEntity te = worldObj.getTileEntity(targetInventory);
        if(te == null || !(te instanceof IInventory))
            return false;

        if(exports == null || exports.length <= 0)
            return true; //Technically, all inventories can successfully accept nothing...

        IInventory inventory = (IInventory) te;

        boolean shouldReturnFalse = false; //Well that's ironic...
        ItemStack item;
        ItemStack inventoryItem;
        for(int n = 0; n < exports.length; n++) //For every export.
        {
            item = exports[n];
            if(item == null)
                continue;

            for(int invIndex = 0; invIndex < inventory.getSizeInventory(); invIndex++) //For every inventory slot.
            {
                inventoryItem = inventory.getStackInSlot(invIndex);
                if(inventoryItem == null || !inventoryItem.isStackable() || inventoryItem.stackSize >= inventoryItem.getMaxStackSize() || !inventory.isItemValidForSlot(invIndex, item))
                    continue; //Skip this slot, as it's impossible to increase the stack size for potentially 4 reasons.

                //Check if the item is the same as the export and, if necessary, that the metadata is also the same.
                if(inventoryItem.getItem().equals(item.getItem()) && (!inventoryItem.getHasSubtypes() || inventoryItem.getMetadata() == item.getMetadata()))
                {
                    int numberToMove = Math.min(inventoryItem.getMaxStackSize() - inventoryItem.stackSize, item.stackSize);
                    item.stackSize -= numberToMove;
                    inventoryItem.stackSize += numberToMove;

                    if(item.stackSize == 0)
                    {
                        exports[n] = null; //We've completely drained an export, so remove it.
                        item = null;
                        break; //...and continue to the next export.
                    }
                }
            }

            //We didn't manage to stack this away, so find an empty slot if possible.
            if(item != null)
            {
                boolean found = false;
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    inventoryItem = inventory.getStackInSlot(i);
                    if(inventoryItem == null && inventory.isItemValidForSlot(i, item))
                    {
                        inventory.setInventorySlotContents(i, item);
                        found = true;
                        break;
                    }
                }
                if(!found)
                    shouldReturnFalse = true; //This stack didn't find a home. This is bad if you want all-or-nothing.
            }
        }
        markDirty();
        return !shouldReturnFalse;
    }

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int maxValue) {
        return this.workProgress * maxValue / getBaseProgressSpeed();
    }

    public int getProgress() {
        return workProgress;
    }

    public void setProgress(int newProgress) {
        workProgress = newProgress;
    }

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return clockworkNetwork;
    }

    @Override
    public void setClockworkNetwork(ClockworkNetwork cn) {
        clockworkNetwork = cn;
    }

    @Override
    public BlockPos getPosition() {
        return getPos();
    }

    @Override
    public long getUniqueID() {
        return uniqueID;
    }

    @Override
    public void setUniqueID(long uniqueID) {
        this.uniqueID = uniqueID;
        markDirty();
        worldObj.markBlockForUpdate(getPos());
    }

    @Override
    public abstract ClockworkNetworkContainer getGui();

    @Override
    public boolean canExportToTargetInventory() {
        return true;
    }

    @Override
    public boolean isValidTargetInventory() {
        return true;
    }

    @Override
    public IClockworkNetworkTile getTargetInventory() {
        if(targetInventory == null)
            return null;
        TileEntity te = worldObj.getTileEntity(targetInventory);
        if(te != null && te instanceof IClockworkNetworkTile)
            return (IClockworkNetworkTile) te;
        return null;
    }

    @Override
    public void setTargetInventory(IClockworkNetworkTile targetInventory) {
        this.targetInventory = targetInventory == null ? null : targetInventory.getPosition();
    }

    // ********* SIDED INVENTORY IMPLEMENTATION START ********* \\

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amountToDecrease)
    {
        ItemStack is = getStackInSlot(slot);
        if(is != null)
        {
            if(amountToDecrease >= is.stackSize)
                setInventorySlotContents(slot, null);
            else
            {
                is = is.splitStack(amountToDecrease);
                if(is.stackSize == 0)
                    setInventorySlotContents(slot, null);
            }
        }
        return is;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        inventory[slot] = item;

        if(item != null && item.stackSize > this.getInventoryStackLimit())
            item.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()) <= 8;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack item = inventory[index];
        inventory[index] = null;
        return item;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for(int n = 0; n < inventory.length; n++)
            inventory[n] = null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

    // ********* SIDED INVENTORY IMPLEMENTATION END ********* \\

    public void setTileDataFromItemStack(ItemStack item)
    {
        this.itemBlock = item.copy();
        ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
        if(items == null)
            return;
        if(items[0] != null && items[0].getItem() instanceof IClockwork)
        {
            this.quality = ((IClockwork) items[0].getItem()).getQuality(items[0]);
            this.speed = ((IClockwork) items[0].getItem()).getSpeed(items[0]);
        }
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        writeToNBT(nbt);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeCustomNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.getPos(), -999, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readCustomNBT(pkt.getNbtCompound());
    }
}
