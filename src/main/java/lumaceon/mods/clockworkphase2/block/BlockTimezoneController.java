package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.TileTimezoneController;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockTimezoneController extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneController(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
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
}
