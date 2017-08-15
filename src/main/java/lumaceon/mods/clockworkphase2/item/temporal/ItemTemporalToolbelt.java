package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTemporalToolbelt extends ItemClockworkPhase
{
    public ItemTemporalToolbelt(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
        if(toolbelt != null && toolbelt.getRowCount() < 5)
        {
            if(!worldIn.isRemote)
            {
                toolbelt.setRowCount(toolbelt.getRowCount() + 1); //Because onItemRightClick is also called (only for the client), make this server-only.
                stack.shrink(1);
                if(stack.getCount() <= 0)
                {
                    player.inventory.deleteStack(stack);
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack stack = player.getHeldItem(handIn);
        ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
        if(toolbelt != null && toolbelt.getRowCount() < 5)
        {
            toolbelt.setRowCount(toolbelt.getRowCount() + 1);
            if(!worldIn.isRemote)
            {
                stack.shrink(1);
                if(stack.getCount() <= 0)
                    return ActionResult.newResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }
}
