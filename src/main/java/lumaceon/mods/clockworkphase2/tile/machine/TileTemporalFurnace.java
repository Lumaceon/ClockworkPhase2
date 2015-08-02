package lumaceon.mods.clockworkphase2.tile.machine;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalMachineSync;
import lumaceon.mods.clockworkphase2.tile.generic.TileTimezonePoweredInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileTemporalFurnace extends TileTimezonePoweredInventory
{
    private boolean hasRegisteredRenderer = false;

    public long ticksPerAction = 600; //30 seconds, triple the time of a normal furnace.
    public long maxTimeStored = ticksPerAction * 8;

    public TileTemporalFurnace()
    {
        super();
        this.inventory = new ItemStack[2]; //0 - Input, 1 - Output.
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            if(timeStored < maxTimeStored)
                timeStored++;
            smeltAsMuchAsPossible();
        }
        else if(!hasRegisteredRenderer)
        {
            ClockworkPhase2.proxy.addWorldRenderer(worldObj, xCoord, yCoord, zCoord, 0);
            hasRegisteredRenderer = true;
        }
        else if(timeStored < maxTimeStored)
            timeStored++;
    }

    private void smeltAsMuchAsPossible()
    {
        boolean smelted = false;
        while(canSmelt() && (timeStored >= ticksPerAction || getTimezone().getTimeSand() >= ticksPerAction - timeStored))
        {
            if(!consumeTime())
                return;
            smeltItem();
            smelted = true;
        }

        if(smelted)
            PacketHandler.INSTANCE.sendToAllAround(new MessageTemporalMachineSync(timeStored, xCoord, yCoord, zCoord), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 256));
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
        long timeConsumed = Math.min(timeStored, ticksPerAction);
        if(timeConsumed < ticksPerAction) //Could not consume all time required, take additional time from timezone.
        {
            timeStored = 0;
            return getTimezone().consumeTimeSand(ticksPerAction - timeConsumed) == ticksPerAction - timeConsumed;
        }
        else //All time was consumed from this tile entity.
            timeStored -= ticksPerAction;
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}
}
