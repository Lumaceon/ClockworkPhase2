package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public class TileTemporalFurnace extends TileTemporalInventory implements ITimeReceiver, ITimeProvider
{
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public int extractTime(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int receiveTime(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int getMaxCapacity() {
        return 0;
    }

    @Override
    public int getTimeStored() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

    @Override
    public boolean canConnectFrom(EnumFacing from) {
        return false;
    }
    /*public int ticksPerAction = 200; //10 seconds, the time of a normal furnace.

    public TileTemporalFurnace() {
        super();
        this.inventory = new ItemStack[2]; //0 - Input, 1 - Output.
        timeStorage = new TimeStorage(ticksPerAction * 32);
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            timeStorage.receiveTime(1, false);
            smeltAsMuchAsPossible();
        }
        else
            timeStorage.receiveTime(1, false);
    }

    private void smeltAsMuchAsPossible()
    {
        boolean smelted = false;
        while(canSmelt() && (timeStorage.getTimeStored() >= ticksPerAction || (getTimezoneProvider() != null && getTimezoneProvider().getTime() >= ticksPerAction - timeStorage.getTimeStored())))
        {
            if(!consumeTime())
                return;
            smeltItem();
            smelted = true;
        }

        if(smelted)
            PacketHandler.INSTANCE.sendToAllAround(new MessageTemporalMachineSync(timeStorage, xCoord, yCoord, zCoord), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 256));
    }

    public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);

        if(this.inventory[1] == null)
            this.inventory[1] = itemstack.copy();
        else if(this.inventory[1].getItem() == itemstack.getItem())
            this.inventory[1].stackSize += itemstack.stackSize;

        --this.inventory[0].stackSize;
        if(this.inventory[0].stackSize <= 0)
            this.inventory[0] = null;
    }

    private boolean canSmelt()
    {
        if(this.inventory[0] == null)
            return false;
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
            if(itemstack == null) return false;
            if(this.inventory[1] == null) return true;
            if(!this.inventory[1].isItemEqual(itemstack)) return false;
            int result = inventory[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize();
        }
    }

    /**
     * Consumes time from this tile as well as a timezone if necessary. Does not check if enough can be consumed.
     * @return Whether or not enough time for one action was consumed.
     */
    /*private boolean consumeTime()
    {
        int timeConsumed = Math.min(timeStorage.getTimeStored(), ticksPerAction);
        if(timeConsumed < ticksPerAction) //Could not consume all time required, take additional time from timezone.
        {
            timeStorage.extractTime(timeStorage.getTimeStored(), false);
            return getTimezoneProvider().consumeTime(ticksPerAction - timeConsumed) == ticksPerAction - timeConsumed;
        }
        else //All time was consumed from this tile entity.
            timeStorage.extractTime(ticksPerAction, false);
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    @Override
    public int extractTime(int maxExtract, boolean simulate) {
        return timeStorage.extractTime(maxExtract, simulate);
    }

    @Override
    public int receiveTime(int maxReceive, boolean simulate) {
        return timeStorage.receiveTime(maxReceive, simulate);
    }

    @Override
    public int getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public int getTimeStored() {
        return timeStorage.getTimeStored();
    }

    public int getEmptySpace() {
        return timeStorage.getEmptySpace();
    }

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }*/
}
