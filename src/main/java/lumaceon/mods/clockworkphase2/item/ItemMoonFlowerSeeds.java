package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemMoonFlowerSeeds extends ItemClockworkPhase implements IPlantable
{
    public ItemMoonFlowerSeeds(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        BlockPos above = pos.up();
        if(playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(above, side, stack))
        {
            if(worldIn.getBlockState(pos).getBlock().canSustainPlant(worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(above))
            {
                worldIn.setBlockState(above, ModBlocks.moonFlower.getBlock().getDefaultState());
                --stack.stackSize;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return ModBlocks.moonFlower.getBlock().getDefaultState();
    }
}
