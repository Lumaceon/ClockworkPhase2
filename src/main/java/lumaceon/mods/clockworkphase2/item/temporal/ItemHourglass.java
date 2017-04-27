package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.ActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.IActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.TimeStorage;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
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
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        ITimeStorage timeStorage = is.getCapability(TIME, EnumFacing.DOWN);
        if(timeStorage != null)
        {
            list.add("Time: " + TimeConverter.parseNumber(timeStorage.getTimeInTicks(), 2));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(playerIn.isSneaking())
            return EnumActionResult.FAIL;

        IActivatableHandler cap = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        if(cap != null)
            cap.setActive();

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World world, EntityPlayer player, EnumHand hand)
    {
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
    public boolean hasEffect(ItemStack stack) {
        return this.isActive(stack);
    }

    @Override
    public boolean isActive(ItemStack stack) {
        IActivatableHandler cap = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        return cap == null || cap.getActive();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new HourglassCapabilityProvider(capacity);
    }

    public static class HourglassCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        @CapabilityInject(IActivatableHandler.class)
        public static final Capability<IActivatableHandler> ACTIVATABLE = null;
        @CapabilityInject(ITimeStorage.class)
        public static final Capability<ITimeStorage> TIME = null;

        ActivatableHandler activatableHandler = new ActivatableHandler();
        TimeStorage timeStorage;

        public HourglassCapabilityProvider(long capacity) {
            timeStorage = new TimeStorage(capacity);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && capability == ACTIVATABLE || capability == TIME;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability != null)
            {
                if(capability == ACTIVATABLE)
                    return ACTIVATABLE.cast(activatableHandler);
                else if(capability == TIME)
                    return TIME.cast(timeStorage);
            }
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
