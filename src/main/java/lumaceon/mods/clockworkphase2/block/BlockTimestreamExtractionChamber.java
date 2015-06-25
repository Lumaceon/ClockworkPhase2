package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.tile.TileTimestreamExtractionChamber;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTimestreamExtractionChamber extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimestreamExtractionChamber(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float localX, float localY, float localZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileTimestreamExtractionChamber)
        {
            TileTimestreamExtractionChamber exChamber = (TileTimestreamExtractionChamber) te;
            if(exChamber.isReady())
            {
                player.openGui(ClockworkPhase2.instance, 2, world, x, y, z);
                return true;
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTimestreamExtractionChamber();
    }
}
