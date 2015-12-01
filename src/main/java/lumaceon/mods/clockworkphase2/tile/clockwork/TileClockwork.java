package lumaceon.mods.clockworkphase2.tile.clockwork;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkDestination;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileClockwork extends TileClockworkPhase implements IClockworkDestination
{
    public ItemStack itemBlock;
    public int quality; //Tension efficiency; higher = less tension used per operation.
    public int speed; //Work speed; higher = faster machines.
    public int maxTension;
    public int currentTension;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagCompound tag;
        if(itemBlock != null)
        {
            tag = new NBTTagCompound();
            itemBlock.writeToNBT(tag);
            nbt.setTag("itemBlock", tag);
        }

        nbt.setInteger(NBTTags.QUALITY, quality);
        nbt.setInteger(NBTTags.SPEED, speed);
        nbt.setInteger(NBTTags.MAX_TENSION, maxTension);
        nbt.setInteger(NBTTags.CURRENT_TENSION, currentTension);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagCompound tagCompound;
        tagCompound = nbt.getCompoundTag("itemBlock");
        itemBlock = ItemStack.loadItemStackFromNBT(tagCompound);

        quality = nbt.getInteger(NBTTags.QUALITY);
        speed = nbt.getInteger(NBTTags.SPEED);
        maxTension = nbt.getInteger(NBTTags.MAX_TENSION);
        currentTension = nbt.getInteger(NBTTags.CURRENT_TENSION);
    }

    @Override
    public boolean canReceiveFrom(ForgeDirection direction) {
        return ForgeDirection.getOrientation(blockMetadata).ordinal() != direction.getOpposite().ordinal();
    }

    @Override
    public int receiveClockworkEnergy(ForgeDirection from, int maxReception) {
        int energyTaken = canReceiveFrom(from) ? Math.min(maxTension - currentTension, maxReception) : 0;
        currentTension += energyTaken;
        return energyTaken;
    }

    @Override
    public int getMaxCapacity() { return maxTension; }
    @Override
    public int getEnergyStored() { return currentTension; }

    @Override
    public int wind(int tension) {
        int amountToAdd = Math.min(this.maxTension - this.currentTension, tension);
        currentTension += amountToAdd;
        return amountToAdd;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
