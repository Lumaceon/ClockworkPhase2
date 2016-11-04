package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemToolUpgradeRelocate extends ItemClockworkPhase implements IToolUpgrade
{
    public ItemToolUpgradeRelocate(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if(NBTHelper.hasTag(stack, "cp_name"))
        {
            tooltip.add("Relocating to [X" + NBTHelper.INT.get(stack, "cp_x") + ", Y" + NBTHelper.INT.get(stack, "cp_y") + ", Z" + NBTHelper.INT.get(stack, "cp_z") + "]");
            tooltip.add("Last seen block at that location: " + NBTHelper.STRING.get(stack, "cp_name"));
            tooltip.add("Inserting into the " + EnumFacing.getFront(NBTHelper.INT.get(stack, "cp_side")).getName().toUpperCase() + " side");
        }
        else
        {
            tooltip.add("Shift-right-click on an inventory to set the target.");
            tooltip.add("Selection is side-sensitive, depending on the inventory.");
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof IInventory)
        {
            NBTHelper.INT.set(stack, "cp_x", pos.getX());
            NBTHelper.INT.set(stack, "cp_y", pos.getY());
            NBTHelper.INT.set(stack, "cp_z", pos.getZ());
            NBTHelper.INT.set(stack, "cp_side", side.ordinal());
            String blockName = te.getBlockType().getLocalizedName();
            NBTHelper.STRING.set(stack, "cp_name", blockName);
            if(!worldIn.isRemote)
                playerIn.addChatComponentMessage(new TextComponentString(Colors.AQUA + "Inventory location saved: " + blockName));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void setActive(ItemStack upgradeStack, ItemStack toolStack, boolean active) {
        NBTHelper.BOOLEAN.set(upgradeStack, NBTTags.ACTIVE, active);
    }

    @Override
    public boolean getActive(ItemStack upgradeStack, ItemStack toolStack) {
        return NBTHelper.BOOLEAN.get(upgradeStack, NBTTags.ACTIVE);
    }
}
