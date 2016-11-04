package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import lumaceon.mods.clockworkphase2.api.IPhaseEntity;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.item.ITimeCompressor;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHourglass extends ItemClockworkPhase implements IHourglass
{
    protected long capacity;
    protected EnumExpTier tier;

    public ItemHourglass(int maxStack, int maxDamage, String unlocalizedName, long capacity, EnumExpTier tier) {
        super(maxStack, maxDamage, unlocalizedName);
        this.capacity = capacity;
        this.tier = tier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        list.add("Compressed Time Energy: " + Colors.AQUA + TimeConverter.parseNumber(getTimeStored(is), 3));
        list.add("Max Compression: " + Colors.AQUA + TimeConverter.parseNumber(getMaxCapacity(is), 3));
        list.add("");
        list.add((int) (NBTHelper.INT.get(is, NBTTags.TOTAL_TIME_GENERATION) * 0.05) + " seconds of compression");
        list.add(TimeConverter.parseNumber(NBTHelper.INT.get(is, NBTTags.TIME_GENERATION_PER_TICK) * 20, 2).toLowerCase() + " per second of compression");

        int minXP = NBTHelper.INT.get(is, NBTTags.MIN_XP);
        String color = player.experienceLevel < minXP ? Colors.RED : Colors.GREEN;
        list.add(color + "Required experience level: " + minXP);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(stack == null || !isActive(stack) || entityIn == null || !(entityIn instanceof EntityPlayer))
            return;

        int generationPerTick = NBTHelper.INT.get(stack, NBTTags.TIME_GENERATION_PER_TICK);
        int remainingTicks = NBTHelper.INT.get(stack, NBTTags.TOTAL_TIME_GENERATION);
        int minimumXP = NBTHelper.INT.get(stack, NBTTags.MIN_XP);

        if(generationPerTick <= 0 || remainingTicks <= 0 || ((EntityPlayer) entityIn).experienceLevel < minimumXP)
            return;

        if(receiveTime(stack, generationPerTick, true) == generationPerTick)
        {
            receiveTime(stack, generationPerTick, false);
            if(remainingTicks <= 1)
            {
                NBTHelper.INT.set(stack, NBTTags.TIME_GENERATION_PER_TICK, 0);
                NBTHelper.INT.set(stack, NBTTags.TOTAL_TIME_GENERATION, 0);
                NBTHelper.INT.set(stack, NBTTags.MIN_XP, 0);
            }
            else
                NBTHelper.INT.set(stack, NBTTags.TOTAL_TIME_GENERATION, remainingTicks - 1);
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(playerIn.isSneaking())
            return EnumActionResult.FAIL;
        NBTHelper.BOOLEAN.set(stack, NBTTags.ACTIVE, !NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE));
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World world, EntityPlayer player, EnumHand hand) {
        if(!player.isSneaking())
            NBTHelper.BOOLEAN.set(is, NBTTags.ACTIVE, !NBTHelper.BOOLEAN.get(is, NBTTags.ACTIVE));
        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE);
    }

    @Override
    public long receiveTime(ItemStack timeItem, long maxReceive, boolean simulate)
    {
        long currentTime = NBTHelper.INT.get(timeItem, NBTTags.TIME);
        long timeReceived = Math.min(getEmptySpace(timeItem), maxReceive);
        if(!simulate)
            NBTHelper.LONG.set(timeItem, NBTTags.TIME, currentTime + timeReceived);
        return timeReceived;
    }

    @Override
    public long extractTime(ItemStack timeItem, long maxExtract, boolean simulate)
    {
        long currentTime = NBTHelper.LONG.get(timeItem, NBTTags.TIME);
        long timeExtracted = Math.min(currentTime, maxExtract);
        if(!simulate)
            NBTHelper.LONG.set(timeItem, NBTTags.TIME, currentTime - timeExtracted);
        return timeExtracted;
    }

    @Override
    public long getMaxCapacity(ItemStack timeItem) {
        return capacity;
    }

    @Override
    public long getTimeStored(ItemStack timeItem) {
        return NBTHelper.LONG.get(timeItem, NBTTags.TIME);
    }

    @Override
    public boolean addTimeCompressor(ItemStack hourglassStack, ItemStack compressorStack)
    {
        if(hourglassStack == null || compressorStack == null || !(compressorStack.getItem() instanceof ITimeCompressor))
            return false;
        int generationPerTick = NBTHelper.INT.get(hourglassStack, NBTTags.TIME_GENERATION_PER_TICK);
        int generationRemaining = NBTHelper.INT.get(hourglassStack, NBTTags.TOTAL_TIME_GENERATION);
        if(generationPerTick == ((ITimeCompressor) compressorStack.getItem()).getCompressionRate() || generationPerTick <= 0 || generationRemaining <= 0)
        {
            generationRemaining += ((ITimeCompressor) compressorStack.getItem()).getTotalTicks();
            NBTHelper.INT.set(hourglassStack, NBTTags.TOTAL_TIME_GENERATION, generationRemaining);
            NBTHelper.INT.set(hourglassStack, NBTTags.TIME_GENERATION_PER_TICK, ((ITimeCompressor) compressorStack.getItem()).getCompressionRate());
            NBTHelper.INT.set(hourglassStack, NBTTags.MIN_XP, ((ITimeCompressor) compressorStack.getItem()).getTier().minimumXP);
            return true;
        }
        return false;
    }

    public long getEmptySpace(ItemStack timeItem) {
        return getMaxCapacity(timeItem) - getTimeStored(timeItem);
    }

    @Override
    public EnumExpTier getTier(ItemStack stack) {
        return tier;
    }

    @Override
    public boolean isActive(ItemStack stack) {
        return NBTHelper.BOOLEAN.get(stack, NBTTags.ACTIVE);
    }

    @Override
    public boolean isSpawningPhaseEntities(ItemStack stack, int xpLevel, EnumExpTier tier) {
        return isActive(stack) && getTier(stack) != null && getTier(stack).tierIndex <= tier.tierIndex + 1 && xpLevel >= getTier(stack).minimumXP;
    }

    @Override
    public boolean isAcceptingTime(ItemStack stack, int xpLevel, EnumExpTier tier) {
        return isActive(stack) && getTier(stack) != null && getTier(stack).tierIndex >= tier.tierIndex && xpLevel >= getTier(stack).minimumXP;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }
}
