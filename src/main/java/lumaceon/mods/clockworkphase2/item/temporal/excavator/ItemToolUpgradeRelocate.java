package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.capabilities.activatable.ActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.coordinate.CoordinateHandler;
import lumaceon.mods.clockworkphase2.capabilities.coordinate.ICoordinateHandler;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemToolUpgradeRelocate extends ItemToolUpgrade implements IToolUpgrade
{
    @CapabilityInject(ICoordinateHandler.class)
    public static final Capability<ICoordinateHandler> COORDINATE = null;

    public ItemToolUpgradeRelocate(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        ICoordinateHandler coordinateHandler = stack.getCapability(COORDINATE, EnumFacing.DOWN);
        if(coordinateHandler != null)
        {
            BlockPos pos = coordinateHandler.getCoordinate();
            if(pos != null)
            {
                tooltip.add("Target: [" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]");
            }

            EnumFacing facing = coordinateHandler.getSide();
            if(facing != null)
            {
                tooltip.add("Target side: " + facing.getName());
            }
        }
        else
        {
            tooltip.add("Shift-right-click on an inventory to set the target");
            tooltip.add("Selection is side-sensitive");
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof IInventory)
        {
            ICoordinateHandler coordinateHandler = stack.getCapability(COORDINATE, EnumFacing.DOWN);
            if(coordinateHandler != null)
            {
                coordinateHandler.setCoordinate(pos);
                coordinateHandler.setSide(facing);
            }
            String blockName = te.getBlockType().getLocalizedName();

            if(!worldIn.isRemote)
                player.sendMessage(new TextComponentString(Colors.AQUA + "Inventory location saved: " + blockName));

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new RelocateCapabilityProvider();
    }

    private static class RelocateCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        ActivatableHandler activatableHandler = new ActivatableHandler();
        CoordinateHandler coordinateHandler = new CoordinateHandler();

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && (capability == ACTIVATABLE || capability == COORDINATE);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability != null)
            {
                if(capability == ACTIVATABLE)
                    return ACTIVATABLE.cast(activatableHandler);
                else if(capability == COORDINATE)
                    return COORDINATE.cast(coordinateHandler);
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();

            nbt.setBoolean("active", activatableHandler.getActive());

            BlockPos pos = coordinateHandler.getCoordinate();
            nbt.setInteger("x", pos.getX());
            nbt.setInteger("y", pos.getY());
            nbt.setInteger("z", pos.getZ());
            EnumFacing facing = coordinateHandler.getSide();
            int facingInteger = facing == null ? -1 : facing.getIndex();
            nbt.setInteger("side", facingInteger);
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            activatableHandler.setActive(nbt.getBoolean("active"));

            BlockPos pos;
            if(nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z"))
            {
                pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
            }
            else
            {
                pos = new BlockPos(0, 0, 0);
            }
            coordinateHandler.setCoordinate(pos);

            int facingInteger = nbt.getInteger("side");
            if(facingInteger == -1)
            {
                coordinateHandler.setSide(null);
            }
            else
            {
                coordinateHandler.setSide(EnumFacing.getFront(facingInteger));
            }
        }
    }
}
