package lumaceon.mods.clockworkphase2.item.armillary;

import lumaceon.mods.clockworkphase2.capabilities.FluidHandlerItemStackMultitank;
import lumaceon.mods.clockworkphase2.capabilities.mode.IModeHandler;
import lumaceon.mods.clockworkphase2.capabilities.mode.ModeHandler;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class ItemAquaticScepter extends ItemClockworkPhase
{
    @CapabilityInject(IFluidHandlerItem.class)
    public static Capability<IFluidHandlerItem> FLUID_HANDLER_ITEM_CAPABILITY = null;
    @CapabilityInject(IModeHandler.class)
    public static Capability<IModeHandler> MODE_CAPABILITY = null;

    public ItemAquaticScepter(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedName(stack));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(player.isSneaking())
        {
            if(stack != null)
            {
                IFluidHandler fluidHandler = stack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN);
                if(fluidHandler != null)
                {
                    IFluidTankProperties[] props = fluidHandler.getTankProperties();
                    int number = 1;
                    for(IFluidTankProperties prop : props)
                    {
                        FluidStack contents = prop.getContents();
                        if(contents == null)
                            LogHelper.info("Tank " + number + ": EMPTY");
                        else
                            LogHelper.info("Tank " + number + ": " + contents.getFluid().getName() + " (" + contents.amount + ")");
                        number++;
                    }
                }
            }
        }
        else
        {
            IModeHandler modeHandler = stack.getCapability(MODE_CAPABILITY, EnumFacing.DOWN);
            if(modeHandler != null)
            {
                int mode = modeHandler.nextMode();
                player.sendMessage(new TextComponentString("Current Mode: " + MODE.values()[mode].name()));
            }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack itemstack = player.getHeldItem(handIn);

        IModeHandler modeHandler = itemstack.getCapability(MODE_CAPABILITY, EnumFacing.DOWN);
        if(modeHandler != null)
        {
            int mode = modeHandler.getMode();
            if(mode == MODE.VACUUM.ordinal())
            {
                RayTraceResult raytraceresult = this.rayTrace(worldIn, player, true);

                if(raytraceresult == null)
                {
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);
                }
                else if(raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
                {
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);
                }
                else
                {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    if(!worldIn.isBlockModifiable(player, blockpos))
                        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
                    if(!player.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
                        return new ActionResult<>(EnumActionResult.FAIL, itemstack);


                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();

                    if(material == Material.WATER && iblockstate.getValue(BlockLiquid.LEVEL) == 0)
                    {
                        IFluidHandler fluidHandler = itemstack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN);
                        if(fluidHandler != null && fluidHandler.fill(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), false) > 0)
                        {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                            fluidHandler.fill(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), true);
                            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                        }
                    }
                    else if(material == Material.LAVA && iblockstate.getValue(BlockLiquid.LEVEL) == 0)
                    {
                        IFluidHandler fluidHandler = itemstack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN);
                        if(fluidHandler != null && fluidHandler.fill(new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME), false) > 0)
                        {
                            player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            fluidHandler.fill(new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME), true);
                            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                        }
                    }
                    else if(iblockstate.getBlock() instanceof IFluidBlock)
                    {
                        IFluidBlock fluid = (IFluidBlock) iblockstate.getBlock();
                        if(fluid.canDrain(worldIn, blockpos) && fluid.getFilledPercentage(worldIn, blockpos) == 1)
                        {
                            IFluidHandler fluidHandler = itemstack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN);
                            if(fluidHandler != null)
                            {
                                if(fluidHandler.fill(fluid.drain(worldIn, blockpos, false), false) > 0)
                                {
                                    player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                                    fluidHandler.fill(fluid.drain(worldIn, blockpos, true), true);
                                    return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                                }
                            }
                        }
                    }
                }
            }
            else if(mode == MODE.FAUCET.ordinal())
            {
                RayTraceResult raytraceresult = this.rayTrace(worldIn, player, false);

                if(raytraceresult == null)
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);
                else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);

                BlockPos blockpos = raytraceresult.getBlockPos();
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if(!player.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack))
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);

                IFluidHandler fluidHandler = itemstack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN);
                if(fluidHandler != null)
                {
                    FluidStack fs = fluidHandler.drain(Fluid.BUCKET_VOLUME, false);
                    if(fs == null || fs.amount < Fluid.BUCKET_VOLUME)
                        return new ActionResult<>(EnumActionResult.FAIL, itemstack);

                    if(this.tryPlaceContainedLiquid(player, worldIn, blockpos1, fs.getFluid()))
                    {
                        if(player instanceof EntityPlayerMP)
                        {
                            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, blockpos1, itemstack);
                        }

                        fluidHandler.drain(Fluid.BUCKET_VOLUME, true);
                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                    }
                }
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }

    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn, Fluid fluidToPlace)
    {
        IBlockState iblockstate = worldIn.getBlockState(posIn);
        Material material = iblockstate.getMaterial();
        boolean flag = !material.isSolid();
        boolean flag1 = iblockstate.getBlock().isReplaceable(worldIn, posIn);

        if(!worldIn.isAirBlock(posIn) && !flag && !flag1)
        {
            return false;
        }
        else if(fluidToPlace.getBlock() == null)
        {
            return false;
        }
        else
        {
            if(worldIn.provider.doesWaterVaporize() && fluidToPlace.getBlock() == Blocks.FLOWING_WATER)
            {
                int l = posIn.getX();
                int i = posIn.getY();
                int j = posIn.getZ();
                worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                for(int k = 0; k < 8; ++k)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                if(!worldIn.isRemote && (flag || flag1) && !material.isLiquid())
                {
                    worldIn.destroyBlock(posIn, true);
                }

                SoundEvent soundevent = fluidToPlace.getBlock() == Blocks.FLOWING_LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                worldIn.playSound(player, posIn, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(posIn, fluidToPlace.getBlock().getDefaultState(), 11);
            }

            return true;
        }
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new CapabilityProvider(stack);
    }

    private static class CapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        FluidHandlerItemStackMultitank fluidHandler;
        IModeHandler mode;

        public CapabilityProvider(ItemStack is)
        {
            //Although this implies fill all 5 with new FluidTanks, it fills all 5 with references to the one FluidTank.
            NonNullList<FluidTank> tanks = NonNullList.withSize(5, new FluidTank(100000));

            //So we set the other 4 to new ones as well.
            tanks.set(1, new FluidTank(100000));
            tanks.set(2, new FluidTank(100000));
            tanks.set(3, new FluidTank(100000));
            tanks.set(4, new FluidTank(100000));
            fluidHandler = new FluidHandlerItemStackMultitank(is, tanks);

            mode = new ModeHandler(MODE.VACUUM.ordinal(), MODE.values().length);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            if(capability == FLUID_HANDLER_ITEM_CAPABILITY || capability == MODE_CAPABILITY)
                return true;
            return false;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == FLUID_HANDLER_ITEM_CAPABILITY)
                return FLUID_HANDLER_ITEM_CAPABILITY.cast(fluidHandler);
            else if(capability == MODE_CAPABILITY)
                return MODE_CAPABILITY.cast(mode);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("fluid", fluidHandler.serializeNBT());
            nbt.setInteger("mode", mode.getMode());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            fluidHandler.deserializeNBT((NBTTagCompound)nbt.getTag("fluid"));
            mode.setMode(nbt.getInteger("mode"));
        }
    }

    static enum MODE
    {
        VACUUM, FAUCET, CANNON, SPRINKLE
    }
}
