package lumaceon.mods.clockworkphase2.tile.timezone;

import lumaceon.mods.clockworkphase2.api.ITemporalMaterial;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.tile.generic.TileTimeSandInventoryMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileTemporalizer extends TileTimeSandInventoryMachine
{
    public TemporalizerState STATE;

    private int progressPerAction = 200;
    private int progressToGo = progressPerAction;

    public TileTemporalizer()
    {
        inventory = new ItemStack[2];
        timeSandRequestAmount = TimeConverter.HOUR * 2;
        STATE = TemporalizerState.IDLE;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setByte("cp_state", (byte) STATE.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        setState(nbt.getByte("cp_state"));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        ItemStack itemToTemporalize = getStackInSlot(0);
        ItemStack resultStack = getStackInSlot(1);
        if(itemToTemporalize != null
        &&!(itemToTemporalize.getItem() instanceof ITemporalMaterial)
        &&(resultStack == null || resultStack.stackSize < resultStack.getMaxStackSize()))
        {
            long amountConsumed = consumeTimeSand(30);
            if(amountConsumed == 30)
                progressToGo--;
            else
            {
                requestTimeSandFromTimezone(worldObj, xCoord, yCoord, zCoord, timeSandRequestAmount);
                amountConsumed =+ consumeTimeSand(30 - amountConsumed);
                if(amountConsumed == 30)
                    progressToGo--;
            }
            if(progressToGo <= 0)
            {
                progressToGo = progressPerAction;
                decrStackSize(0, 1);
                if(resultStack == null)
                    setInventorySlotContents(1, new ItemStack(ModItems.ingotTemporal));
                else
                    resultStack.stackSize++;
            }
        }
        if(itemToTemporalize == null || itemToTemporalize.getItem() instanceof ITemporalMaterial)
            progressToGo = progressPerAction;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot == 0 && !(item.getItem() instanceof ITemporalMaterial);
    }

    @Override
    public void setState(byte state) {
        STATE = TemporalizerState.values()[state];
    }

    public enum TemporalizerState
    {
        IDLE, ACTIVE, NO_ENERGY, NO_TIMEZONE
    }
}
