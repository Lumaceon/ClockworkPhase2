package lumaceon.mods.clockworkphase2.tile.temporal;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalMachineSync;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporalInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTemporalFurnace extends TileTemporalInventory implements ITimeReceiver, ITimeProvider
{
    public int ticksPerAction = 200; //10 seconds, the time of a normal furnace.

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
        while(canSmelt() && (timeStorage.getTimeStored() >= ticksPerAction || (getTimezone() != null && getTimezone().getTimeSand() >= ticksPerAction - timeStorage.getTimeStored())))
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
    private boolean consumeTime()
    {
        int timeConsumed = Math.min(timeStorage.getTimeStored(), ticksPerAction);
        if(timeConsumed < ticksPerAction) //Could not consume all time required, take additional time from timezone.
        {
            timeStorage.extractTime(timeStorage.getTimeStored(), false);
            return getTimezone().consumeTimeSand(ticksPerAction - timeConsumed) == ticksPerAction - timeConsumed;
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
    }
}
