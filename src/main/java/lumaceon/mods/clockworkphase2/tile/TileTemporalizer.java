package lumaceon.mods.clockworkphase2.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.api.ITemporalMaterial;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTileStateChange;
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
        nbt.setInteger("cp_state", STATE.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        setState(nbt.getInteger("cp_state"));
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
            {
                progressToGo--;
                if(STATE != TemporalizerState.ACTIVE)
                    setStateAndUpdate(TemporalizerState.ACTIVE.ordinal());
            }

            else
            {
                if(!requestTimeSandFromTimezone(worldObj, xCoord, yCoord, zCoord, timeSandRequestAmount))
                {
                    if(STATE != TemporalizerState.NO_TIMEZONE)
                        setStateAndUpdate(TemporalizerState.NO_TIMEZONE.ordinal());
                }
                amountConsumed =+ consumeTimeSand(30 - amountConsumed);
                if(amountConsumed == 30)
                {
                    progressToGo--;
                    if(STATE != TemporalizerState.ACTIVE)
                        setStateAndUpdate(TemporalizerState.ACTIVE.ordinal());
                }
                else if(STATE != TemporalizerState.NO_ENERGY)
                    setStateAndUpdate(TemporalizerState.NO_ENERGY.ordinal());

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
        {
            progressToGo = progressPerAction;
            if(STATE != TemporalizerState.IDLE)
                setStateAndUpdate(TemporalizerState.IDLE.ordinal());
        }

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot == 0 && !(item.getItem() instanceof ITemporalMaterial);
    }

    @Override
    public void setState(int state) {
        STATE = TemporalizerState.values()[state];
    }

    @Override
    public void setStateAndUpdate(int state)
    {
        if(worldObj != null && !worldObj.isRemote)
        {
            STATE = TemporalizerState.values()[state];
            PacketHandler.INSTANCE.sendToAllAround(new MessageTileStateChange(xCoord, yCoord, zCoord, state), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 200));
        }
    }

    public enum TemporalizerState { IDLE, ACTIVE, NO_ENERGY, NO_TIMEZONE }
}
