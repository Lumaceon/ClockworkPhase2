package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.capabilities.activatable.ActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.activatable.IActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem.IStasisItemHandler;
import lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem.StasisItemHandler;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPersonalStasisShield extends ItemClockworkPhase
{
    @CapabilityInject(IStasisItemHandler.class)
    public static final Capability<IStasisItemHandler> STASIS_ITEM_CAPABILITY = null;
    @CapabilityInject(IActivatableHandler.class)
    public static final Capability<IActivatableHandler> ACTIVATABLE = null;

    public ItemPersonalStasisShield(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
            return EnumActionResult.FAIL;

        ItemStack stack = player.getHeldItem(hand);
        IActivatableHandler cap = stack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        if(cap != null)
            cap.setActive();

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
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new Provider();
    }

    private static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        IStasisItemHandler stasisItemHandler;
        IActivatableHandler activatableHandler;

        private Provider()
        {
            stasisItemHandler = new StasisItemHandler();
            activatableHandler = new ActivatableHandler();
            activatableHandler.setActive(true);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == STASIS_ITEM_CAPABILITY || capability == ACTIVATABLE;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == STASIS_ITEM_CAPABILITY)
            {
                return STASIS_ITEM_CAPABILITY.cast(stasisItemHandler);
            }
            else if(capability == ACTIVATABLE)
            {
                return ACTIVATABLE.cast(activatableHandler);
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setLong("time_to_offset", stasisItemHandler.getTimeToOffset());
            nbt.setBoolean("is_active", activatableHandler.getActive());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if(nbt.hasKey("time_to_offset"))
                stasisItemHandler.setTimeToOffset(nbt.getLong("time_to_offset"));
            if(nbt.hasKey("is_active"))
                activatableHandler.setActive(nbt.getBoolean("is_active"));
        }
    }
}
