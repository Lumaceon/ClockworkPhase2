package lumaceon.mods.clockworkphase2.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.time.ITimeSupplierItem;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemTemporalHourglass extends ItemClockworkPhase implements ITimeSupplierItem
{
    public int capacity;

    public ItemTemporalHourglass(int maxStack, int maxDamage, int capacity, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
        this.capacity = capacity;
    }

    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if(is.getItem() instanceof ItemTemporalHourglass && entity.isSneaking())
            ((ItemTemporalHourglass) is.getItem()).receiveTime(is, 20, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        list.add("Time Stored: " + Colors.AQUA + TimeConverter.parseNumber(NBTHelper.INT.get(is, NBTTags.TIME), 3));
    }

    @Override
    public int receiveTime(ItemStack timeItem, int maxReceive, boolean simulate)
    {
        int currentTime = NBTHelper.INT.get(timeItem, NBTTags.TIME);
        int timeReceived = Math.min(getEmptySpace(timeItem), maxReceive);
        if(!simulate)
            NBTHelper.INT.set(timeItem, NBTTags.TIME, currentTime + timeReceived);
        return timeReceived;
    }

    @Override
    public int extractTime(ItemStack timeItem, int maxExtract, boolean simulate)
    {
        int currentTime = NBTHelper.INT.get(timeItem, NBTTags.TIME);
        int timeExtracted = Math.min(currentTime, maxExtract);
        if(!simulate)
            NBTHelper.INT.set(timeItem, NBTTags.TIME, currentTime - timeExtracted);
        return timeExtracted;
    }

    @Override
    public int getMaxCapacity(ItemStack timeItem) {
        return capacity;
    }

    @Override
    public int getTimeStored(ItemStack timeItem) {
        return NBTHelper.INT.get(timeItem, NBTTags.TIME);
    }

    public int getEmptySpace(ItemStack timeItem) {
        return getMaxCapacity(timeItem) - getTimeStored(timeItem);
    }
}
