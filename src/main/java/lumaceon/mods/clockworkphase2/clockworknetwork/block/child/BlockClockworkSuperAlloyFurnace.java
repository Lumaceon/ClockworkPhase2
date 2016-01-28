package lumaceon.mods.clockworkphase2.clockworknetwork.block.child;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkSuperAlloyFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockClockworkSuperAlloyFurnace extends BlockCN
{
    public BlockClockworkSuperAlloyFurnace(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileClockworkSuperAlloyFurnace();
    }
}
