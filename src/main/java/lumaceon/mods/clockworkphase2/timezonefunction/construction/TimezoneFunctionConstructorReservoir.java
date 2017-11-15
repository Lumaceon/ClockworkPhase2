package lumaceon.mods.clockworkphase2.timezonefunction.construction;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.timezonefunction.TimezoneFunctionReservoir;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimezoneFunctionConstructorReservoir extends TimezoneFunctionConstructor
{
    private int emptySpace = 0;
    private int glassBlocks = 0;

    public TimezoneFunctionConstructorReservoir(TimezoneFunctionType type) {
        super(type, 2);
    }

    public void setEmptySpace(int emptySpace) {
        this.emptySpace = emptySpace;
    }

    public int getEmptySpace() {
        return this.emptySpace;
    }

    @Override
    public long getMaxProgressIndexForLayer(ITimezone timezone, int layerIndex) {
        if(layerIndex == 0)
            return 65024;
        return getEmptySpace();
    }

    @Override
    public void onUpdate(ITimezone timezone)
    {
        if(layer == 0)
        {
            int xPos = (int) (progress % 255);
            xPos = xPos - 127;
            int zPos = (int) (progress / 255);
            zPos = zPos - 127;

            World world = timezone.getTile().getWorld();
            if(world != null)
            {
                BlockPos pos = timezone.getPosition();
                BlockPos translatedPosition = new BlockPos(pos.getX() + xPos, 0, pos.getZ() + zPos);

                for(int yPos = 0; yPos < 256; yPos++)
                {
                    if(world.isAirBlock(translatedPosition.up(yPos)))
                        emptySpace++;
                }

                progress++;
            }
        }
    }

    @Override
    public ItemStack insertStack(ITimezone timezone, ItemStack stackToInsert)
    {
        if(layer == 1)
        {
            if(!stackToInsert.isEmpty() && stackToInsert.getItem().equals(Item.getItemFromBlock(Blocks.GLASS)))
            {
                int maxAcceptable = emptySpace - glassBlocks;
                if(maxAcceptable > 0)
                {
                    int actuallyAccepted = Math.min(stackToInsert.getCount(), maxAcceptable);
                    stackToInsert.setCount(stackToInsert.getCount() - actuallyAccepted);
                    if(stackToInsert.getCount() <= 0)
                    {
                        return ItemStack.EMPTY;
                    }
                    return stackToInsert;
                }
            }
        }
        return stackToInsert;
    }

    @Override
    public boolean canComplete(ITimezone timezone) {
        return layer > 0 && glassBlocks > 0;
    }

    @Override
    public TimezoneFunction createTimezoneFunction(ITimezone timezone) {
        return new TimezoneFunctionReservoir(type, (long) glassBlocks * 1000L);
    }

    @Override
    public String getLayerDisplayName(ITimezone timezone, int layerIndex, boolean detailed)
    {
        if(detailed)
            return layerIndex == 0 ? "Scans each block within the timezone's influence for empty space." : "Accepts glass blocks, expanding total fluid storage by 1000mb per glass.";
        else
            return layerIndex == 0 ? "Locate Empty Space" : "Expand Reservoir Capacity";
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("space", this.emptySpace);
        nbt.setInteger("glass_blocks", this.glassBlocks);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("space"))
            this.emptySpace = nbt.getInteger("space");
        if(nbt.hasKey("glass_blocks"))
            this.glassBlocks = nbt.getInteger("glass_blocks");
    }
}
