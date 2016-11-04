package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockConstruction extends BlockClockworkPhase
{
    public BlockConstruction(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockMultiblockAssembler.METADATA, 0));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockMultiblockAssembler.METADATA, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockMultiblockAssembler.METADATA);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {BlockMultiblockAssembler.METADATA});
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return true; }

    @Override
    public boolean isVisuallyOpaque() { return false; }
}
