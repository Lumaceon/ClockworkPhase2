package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.activatable.ActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.activatable.IActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.timestorage.TimeStorage;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.util.ExperienceHelper;
import lumaceon.mods.clockworkphase2.util.SideHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHourglass extends ItemClockworkPhase implements IHourglass
{
    @CapabilityInject(IActivatableHandler.class)
    public static final Capability<IActivatableHandler> ACTIVATABLE = null;
    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME = null;

    protected long capacity;

    public ItemHourglass(int maxStack, int maxDamage, String unlocalizedName, long capacity) {
        super(maxStack, maxDamage, unlocalizedName);
        this.capacity = capacity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null && nbt.hasKey("time"))
        {
            tooltip.add("Time: " + TimeConverter.parseNumber(nbt.getLong("time"), 2));
        }
    }

    @Override
    @Nullable
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt == null)
        {
            nbt = new NBTTagCompound();
        }

        IActivatableHandler activatableHandler = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        if(activatableHandler != null)
        {
            nbt.setBoolean("is_active", activatableHandler.getActive());
        }

        ITimeStorage timeStorage = stack.getCapability(TIME, EnumFacing.DOWN);
        if(timeStorage != null)
        {
            nbt.setLong("time_max", timeStorage.getMaxCapacity());
            nbt.setLong("time", timeStorage.getTimeInTicks());
        }

        return nbt;
    }

    @Override
    public int getDamage(ItemStack stack)
    {
        return getDamageFromTime(stack);
    }

    @Override
    public int getMetadata(ItemStack stack) {
        return getDamageFromTime(stack);
    }

    private int getDamageFromTime(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null && nbt.hasKey("time_max") && nbt.hasKey("time"))
        {
            long maxTime = nbt.getLong("time_max");
            long time = nbt.getLong("time");
            int damage = stack.getMaxDamage() - (int) ( ((double) time / (double) maxTime) * stack.getMaxDamage() );
            if(damage <= 0)
            {
                damage = 1;
            }
            return damage;
        }
        return stack.getMaxDamage();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
            return EnumActionResult.FAIL;

        if(!worldIn.isRemote)
        {
            ItemStack stack = player.getHeldItem(hand);
            IActivatableHandler cap = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
            if(cap != null)
                cap.setActive();
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack is = player.getHeldItem(handIn);
        if(!player.isSneaking())
        {
            IActivatableHandler cap = is.getCapability(ACTIVATABLE, EnumFacing.DOWN);
            if(cap != null)
                cap.setActive();
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        return nbt != null && nbt.hasKey("is_active") && nbt.getBoolean("is_active");
    }

    @Override
    public boolean isActive(ItemStack stack)
    {
        if(SideHelper.isServerSide())
        {
            IActivatableHandler cap = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
            return cap == null || cap.getActive();
        }
        else
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt != null && nbt.hasKey("is_active"))
            {
                return nbt.getBoolean("is_active");
            }
        }
        return false;
    }

    @Override
    public boolean generateTime(ItemStack stack, EntityPlayer player)
    {
        int xpLevel = player.experienceLevel;
        int xpTier = 1;

        if(xpLevel >= ConfigValues.HOURGLASS_XP_LEVEL_TIER_5)
            xpTier = 5;
        else if(xpLevel >= ConfigValues.HOURGLASS_XP_LEVEL_TIER_4)
            xpTier = 4;
        else if(xpLevel >= ConfigValues.HOURGLASS_XP_LEVEL_TIER_3)
            xpTier = 3;
        else if(xpLevel >= ConfigValues.HOURGLASS_XP_LEVEL_TIER_2)
            xpTier = 2;

        ITimeStorage timeStorage = stack.getCapability(TIME, EnumFacing.DOWN);
        if(timeStorage != null)
        {
            if(timeStorage.getTimeInTicks() >= timeStorage.getMaxCapacity())
            {
                return false;
            }

            int timeToAdd = 0;
            int percentageChance = 100;
            switch(xpTier)
            {
                case 1:
                    timeToAdd = 1;
                    percentageChance = 5;
                    break;
                case 2:
                    timeToAdd = 1;
                    percentageChance = 20;
                    break;
                case 3:
                    timeToAdd = 4;
                    break;
                case 4:
                    timeToAdd = 12;
                    break;
                case 5:
                    timeToAdd = 32;
                    break;
            }

            if(percentageChance > itemRand.nextInt(100))
            {
                long amountAccepted = timeStorage.insertTime(timeToAdd);
                if(amountAccepted > 0 && ConfigValues.TEMPORAL_HOURGLASS_CONSUMES_XP)
                {
                    int xpToConsume = timeToAdd/10;

                    if(xpToConsume <= 0)
                        xpToConsume = 1;

                    ExperienceHelper.addPlayerXP(player, -xpToConsume);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem()) || oldStack.hasEffect() != newStack.hasEffect();
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new HourglassCapabilityProvider(capacity, stack);
    }

    public static class HourglassCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        ActivatableHandler activatableHandler = new ActivatableHandler();
        TimeStorage timeStorage;

        public HourglassCapabilityProvider(long capacity, ItemStack stack) {
            timeStorage = new TimeStorage(capacity, stack);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == ACTIVATABLE || capability == TIME;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == ACTIVATABLE)
                return ACTIVATABLE.cast(activatableHandler);
            else if(capability == TIME)
                return TIME.cast(timeStorage);

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean("is_active", activatableHandler.getActive());
            nbt.setLong("ticks_stored", timeStorage.getTimeInTicks());
            nbt.setLong("max_capacity", timeStorage.getMaxCapacity());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            activatableHandler.setActive(nbt.getBoolean("is_active"));
            timeStorage.setMaxCapacity(nbt.getLong("max_capacity"));
            timeStorage.insertTime(nbt.getLong("ticks_stored"));
        }
    }
}
