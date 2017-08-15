package lumaceon.mods.clockworkphase2.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

public class BlockFluidStatic extends BlockFluidClassic
{
    public BlockFluidStatic(Fluid fluid, Material material) {
        super(fluid, material);
        setDefaultState(blockState.getBaseState().withProperty(LEVEL, 0));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {}
}
