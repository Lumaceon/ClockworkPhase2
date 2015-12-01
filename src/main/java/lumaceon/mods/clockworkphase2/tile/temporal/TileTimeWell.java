package lumaceon.mods.clockworkphase2.tile.temporal;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.api.time.ITimeContainerItem;
import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalMachineSync;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTimeWell extends TileTemporalInventory implements ITimeReceiver, ITimeProvider, IInventory
{
    int maxDrainPerTick = TimeConverter.SECOND * 10;

    public TileTimeWell() {
        super();
        this.inventory = new ItemStack[2]; //0 - item to fill, 1 - item to drain from.
        timeStorage = new TimeStorage(TimeConverter.HOUR * 12);
    }

    @Override
    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            ItemStack itemToFill = getStackInSlot(0);
            ItemStack itemToDrain = getStackInSlot(1);
            boolean changeMade = false;

            if(itemToFill != null && itemToFill.getItem() instanceof ITimeContainerItem)
                if(extractTime(((ITimeContainerItem) itemToFill.getItem()).receiveTime(itemToFill, Math.min(maxDrainPerTick, timeStorage.getTimeStored()), false), false) != 0)
                    changeMade = true;

            if(timeStorage.getEmptySpace() > 0)
                if(itemToDrain != null && itemToDrain.getItem() instanceof ITimeContainerItem)
                    if(receiveTime(((ITimeContainerItem) itemToDrain.getItem()).extractTime(itemToDrain, Math.min(timeStorage.getEmptySpace(), maxDrainPerTick), false), false) != 0)
                        changeMade = true;

            if(changeMade)
                PacketHandler.INSTANCE.sendToAllAround(new MessageTemporalMachineSync(timeStorage, xCoord, yCoord, zCoord), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 256));
        }
    }

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

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return item != null && item.getItem() instanceof ITimeContainerItem;
    }
}
