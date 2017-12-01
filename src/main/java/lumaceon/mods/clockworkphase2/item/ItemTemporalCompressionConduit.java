package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.temporal.ITimeSink;
import lumaceon.mods.clockworkphase2.block.temporal.BlockTemporalCompressionConduit;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTemporalCompressionConduit extends ItemClockworkPhase
{
    public ItemTemporalCompressionConduit(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        BlockPos originalPosition = pos;
        IBlockState originalBlockState = worldIn.getBlockState(originalPosition);
        if(originalBlockState != null && originalBlockState.getBlock() != null && (originalBlockState.getBlock() instanceof ITimeSink || (originalBlockState.getBlock() instanceof BlockTemporalCompressionConduit && ((BlockTemporalCompressionConduit) originalBlockState.getBlock()).getConduitOutputDirection() == null)))
        {
            //Continue...
        }
        else
        {
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        if(!worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing); //Block is not replaceable, so offset based on the face we clicked on.
        }
        else
        {
            return EnumActionResult.FAIL;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if(player.canPlayerEdit(pos, facing, stack))
        {
            if(block.isReplaceable(worldIn, pos) || worldIn.isAirBlock(pos))
            {
                IBlockState iblockstate1 = ModBlocks.temporalCompressionConduitEnd.getDefaultState();

                if(worldIn.setBlockState(pos, iblockstate1, 3) && originalBlockState.getBlock() instanceof BlockTemporalCompressionConduit)
                {
                    IBlockState iblockstate2 = iblockstate1;
                    if(facing.equals(EnumFacing.UP))
                        iblockstate2 = ModBlocks.temporalCompressionConduitUp.getDefaultState();
                    else if(facing.equals(EnumFacing.DOWN))
                        iblockstate2 = ModBlocks.temporalCompressionConduitDown.getDefaultState();
                    else if(facing.equals(EnumFacing.WEST))
                        iblockstate2 = ModBlocks.temporalCompressionConduitWest.getDefaultState();
                    else if(facing.equals(EnumFacing.EAST))
                        iblockstate2 = ModBlocks.temporalCompressionConduitEast.getDefaultState();
                    else if(facing.equals(EnumFacing.NORTH))
                        iblockstate2 = ModBlocks.temporalCompressionConduitNorth.getDefaultState();
                    else if(facing.equals(EnumFacing.SOUTH))
                        iblockstate2 = ModBlocks.temporalCompressionConduitSouth.getDefaultState();

                    worldIn.setBlockState(originalPosition, iblockstate2, 3);
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
