package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.TileTemporalDisplacementAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTemporalDisplacementAltar extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTemporalDisplacementAltar(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
    }

    /*@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileTemporalDisplacementAltar.destroyMultiblock(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }*/

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTemporalDisplacementAltar();
    }
}
