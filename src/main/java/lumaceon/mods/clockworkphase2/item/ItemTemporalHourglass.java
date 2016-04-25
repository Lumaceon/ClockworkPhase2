package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.time.ITimeSupplierItem;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTemporalHourglass extends ItemClockworkPhase implements ITimeSupplierItem
{
    public int capacity;

    public ItemTemporalHourglass(int maxStack, int maxDamage, int capacity, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
        this.capacity = capacity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        list.add("Time Storage: " + Colors.AQUA + TimeConverter.parseNumber(NBTHelper.INT.get(is, NBTTags.TIME), 3));
        if(NBTHelper.BOOLEAN.get(is, NBTTags.ACTIVE))
            list.add("Converting XP to time energy.");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(player.isSneaking())
            return false;
        NBTHelper.BOOLEAN.set(stack, NBTTags.ACTIVE, !NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE));
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!player.isSneaking())
            NBTHelper.BOOLEAN.set(stack, NBTTags.ACTIVE, !NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE));
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE);
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
