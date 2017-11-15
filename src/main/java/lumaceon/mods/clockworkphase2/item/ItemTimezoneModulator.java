package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTimezoneModulator extends ItemClockworkPhase
{
    public ItemTimezoneModulator(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        ItemStack stack = player.getHeldItem(hand);
        if(!worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing); //Block is not replaceable, so offset based on the face we clicked on.
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        boolean replaceable = block.isReplaceable(worldIn, pos);

        EnumFacing enumfacing = player.getHorizontalFacing().getOpposite();
        BlockPos blockpos = pos.up();

        if(player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(blockpos, facing, stack))
        {
            boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
            boolean flag2 = replaceable || worldIn.isAirBlock(pos);
            boolean flag3 = flag1 || worldIn.isAirBlock(blockpos);

            if(flag2 && flag3)
            {
                IBlockState iblockstate1 = ModBlocks.timezoneModulatorBottom.getDefaultState().withProperty(BlockHorizontal.FACING, enumfacing);

                if(worldIn.setBlockState(pos, iblockstate1, 3))
                {
                    IBlockState iblockstate2 = ModBlocks.timezoneModulator.getDefaultState().withProperty(BlockHorizontal.FACING, enumfacing);
                    worldIn.setBlockState(pos.up(), iblockstate2, 3);
                }

                stack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else
                return EnumActionResult.FAIL;
        }
        else
            return EnumActionResult.FAIL;
    }
}
