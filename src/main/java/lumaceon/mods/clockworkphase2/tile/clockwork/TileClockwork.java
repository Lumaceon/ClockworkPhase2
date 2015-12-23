package lumaceon.mods.clockworkphase2.tile.clockwork;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.util.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileClockwork extends TileClockworkPhase implements IClockworkNetworkMachine
{
    private ClockworkNetwork clockworkNetwork;
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
    public ClockworkNetwork getClockworkNetwork() {
        return clockworkNetwork;
    }

    @Override
    public abstract ClockworkNetworkContainer getGui();
}
