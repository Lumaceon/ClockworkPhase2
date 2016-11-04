package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.block.CustomProperties;
import lumaceon.mods.clockworkphase2.block.BlockAssemblyTable;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAssemblyTable extends ItemClockworkPhase
{
    public ItemAssemblyTable(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        boolean replaceable = block.isReplaceable(worldIn, pos);

        if(!replaceable)
            pos = pos.up();

        int i = MathHelper.floor_double((double) (playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing enumfacing = EnumFacing.NORTH;
        switch(i)
        {
            case 0:
                enumfacing = EnumFacing.WEST;
                break;
            case 1:
                enumfacing = EnumFacing.NORTH;
                break;
            case 2:
                enumfacing = EnumFacing.EAST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
        }
        BlockPos blockpos = pos.offset(enumfacing);

        if(playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(blockpos, side, stack))
        {
            boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
            boolean flag2 = replaceable || worldIn.isAirBlock(pos);
            boolean flag3 = flag1 || worldIn.isAirBlock(blockpos);

            if(flag2 && flag3)
            {
                IBlockState iblockstate1 = ModBlocks.assemblyTable.getBlock().getDefaultState().withProperty(CustomProperties.FACING_HORIZONTAL, enumfacing.getOpposite()).withProperty(BlockAssemblyTable.PART, BlockAssemblyTable.EnumPartType.LEFT);

                if(worldIn.setBlockState(pos, iblockstate1, 3))
                {
                    IBlockState iblockstate2 = iblockstate1.withProperty(BlockAssemblyTable.PART, BlockAssemblyTable.EnumPartType.RIGHT);
                    worldIn.setBlockState(blockpos, iblockstate2, 3);
                }

                --stack.stackSize;
                return EnumActionResult.SUCCESS;
            }
            else
                return EnumActionResult.FAIL;
        }
        else
            return EnumActionResult.FAIL;
    }
}
