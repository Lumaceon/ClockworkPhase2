package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCelestialCompass extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockCelestialCompass(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float localX, float localY, float localZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && te instanceof TileCelestialCompass && ((TileCelestialCompass) te).onMainBlockClicked(player);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileCelestialCompass.destroyMultiblock(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileCelestialCompass();
    }
}
