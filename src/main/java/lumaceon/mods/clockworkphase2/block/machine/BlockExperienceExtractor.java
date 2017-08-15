package lumaceon.mods.clockworkphase2.block.machine;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockExperienceExtractor extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockExperienceExtractor(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
