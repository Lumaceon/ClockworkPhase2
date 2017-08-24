package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.entity.EntityTemporalFishHook;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ItemTemporalFishingRod extends ItemFishingRod implements ISimpleNamed
{
    String simpleName;

    public ItemTemporalFishingRod(String name)
    {
        super();
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.simpleName = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            itemstack.damageItem(i, playerIn);
            playerIn.swingArm(handIn);
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
            if(playerIn.isSneaking())
            {
                playerIn.openGui(ClockworkPhase2.instance, GUIs.TEMPORAL_FISHING_ROD.ordinal(), worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            }
            else
            {
                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                if (!worldIn.isRemote)
                {
                    EntityTemporalFishHook entityfishhook = new EntityTemporalFishHook(worldIn, playerIn);
                    int j = EnchantmentHelper.getFishingSpeedBonus(itemstack);

                    if (j > 0)
                    {
                        entityfishhook.setLureSpeed(j);
                    }

                    int k = EnchantmentHelper.getFishingLuckBonus(itemstack);

                    if (k > 0)
                    {
                        entityfishhook.setLuck(k);
                    }

                    worldIn.spawnEntity(entityfishhook);
                }

                playerIn.swingArm(handIn);
                playerIn.addStat(StatList.getObjectUseStats(this));
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new Provider();
    }

    @CapabilityInject(IItemHandler.class)
    public static final Capability<IItemHandler> ITEM_HANDLER = null;

    private static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        ItemStackHandler itemHandler;

        public Provider() {
            itemHandler = new ItemStackHandler(1);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == ITEM_HANDLER;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == ITEM_HANDLER)
                return ITEM_HANDLER.cast(itemHandler);

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("inv", itemHandler.serializeNBT());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if(nbt.hasKey("inv"))
            {
                itemHandler.deserializeNBT((NBTTagCompound) nbt.getTag("inv"));
            }
        }
    }
}
