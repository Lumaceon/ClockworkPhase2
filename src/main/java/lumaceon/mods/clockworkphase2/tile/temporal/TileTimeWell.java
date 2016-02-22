package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeContainerItem;
import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalMachineSync;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileTimeWell extends TileTemporalInventory implements ITimeReceiver, ITimeProvider, ITickable
{
    int maxDrainPerTick = TimeConverter.SECOND * 10;

    public TileTimeWell() {
        super();
        this.inventory = new ItemStack[2]; //0 - item to fill, 1 - item to drain from.
        timeStorage = new TimeStorage(TimeConverter.HOUR * 12);
    }

    @Override
    public void update()
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
                PacketHandler.INSTANCE.sendToAllAround(new MessageTemporalMachineSync(timeStorage, pos.getX(), pos.getY(), pos.getZ()), new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 256));
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
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return item != null && item.getItem() instanceof ITimeContainerItem;
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
    public boolean canConnectFrom(EnumFacing from) {
        return false;
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
}
