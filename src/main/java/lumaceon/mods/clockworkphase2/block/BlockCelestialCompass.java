package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCelestialCompass extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockCelestialCompass(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        return te != null && te instanceof TileCelestialCompass && ((TileCelestialCompass) te).onMainBlockClicked(player);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileCelestialCompass.destroyMultiblock(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileCelestialCompass();
    }
}
