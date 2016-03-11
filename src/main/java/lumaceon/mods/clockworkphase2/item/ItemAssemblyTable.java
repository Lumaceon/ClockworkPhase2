package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.block.BlockAssemblyTable;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemAssemblyTable extends ItemClockworkPhase
{
    public ItemAssemblyTable(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            return true;

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
                enumfacing = EnumFacing.EAST;
                break;
            case 1:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.NORTH;
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
                IBlockState iblockstate1 = ModBlocks.assemblyTable.getBlock().getDefaultState().withProperty(BlockAssemblyTable.FACING, enumfacing).withProperty(BlockAssemblyTable.PART, BlockAssemblyTable.EnumPartType.LEFT);

                if(worldIn.setBlockState(pos, iblockstate1, 3))
                {
                    IBlockState iblockstate2 = iblockstate1.withProperty(BlockAssemblyTable.PART, BlockAssemblyTable.EnumPartType.RIGHT);
                    worldIn.setBlockState(blockpos, iblockstate2, 3);
                }

                --stack.stackSize;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}
