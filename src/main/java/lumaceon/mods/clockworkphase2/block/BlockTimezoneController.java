package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.TileTimezoneController;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTimezoneController extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneController(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack heldItem = player.getHeldItem();
            if(heldItem != null && Block.getBlockFromItem(heldItem.getItem()) != null && Block.getBlockFromItem(heldItem.getItem()).equals(ModBlocks.constructionBlock.getBlock()))
            {
                TileEntity te = world.getTileEntity(pos);
                if(te != null && te instanceof TileTimezoneController)
                    if(((TileTimezoneController) te).onRightClickWithConstructionBlock())
                        --heldItem.stackSize;
                return true;
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileTimezoneController.destroyMultiblock(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileTimezoneController();
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }
}
