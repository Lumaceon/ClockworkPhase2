package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("ALL")
public class BlockBugSwatter extends BlockClockworkPhase
{
    final static PropertyInteger TEST_PROP = PropertyInteger.create("test", 0, 2);

    public BlockBugSwatter(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TEST_PROP, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TEST_PROP, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TEST_PROP);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { TEST_PROP });
    }
}
