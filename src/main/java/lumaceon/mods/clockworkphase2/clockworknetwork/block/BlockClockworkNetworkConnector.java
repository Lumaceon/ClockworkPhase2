package lumaceon.mods.clockworkphase2.clockworknetwork.block;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkConnector;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockClockworkNetworkConnector extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockClockworkNetworkConnector(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClockworkNetworkConnector();
    }
}
