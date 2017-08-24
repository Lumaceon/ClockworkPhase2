package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileCelestialCompass extends TileMod implements ITickable
{
    /**
     * Called during destruction of the multiblock, mostly so that destroyed blocks don't try to destroy the multiblock
     * themselves, and only the first will ever call it.
     */
    public boolean isBeingDestroyed = false;

    @Override
    public void update()
    {

    }

    public static void destroyMultiblock(TileCelestialCompass te, World world, BlockPos pos)
    {
        te.isBeingDestroyed = true;
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.multiblockCelestialCompass)));
        ItemStack consBlocks1 = new ItemStack(ModBlocks.constructionBlock);
        consBlocks1.setCount(64);
        ItemStack consBlocks2 = new ItemStack(ModBlocks.constructionBlock);
        consBlocks2.setCount(32);
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), consBlocks1));
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), consBlocks2));

        IMultiblockTemplate template = MultiblockTemplateCelestialCompass.INSTANCE;
        BlockPos currentPosition;
        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            IMultiblockTemplate.BlockData data = template.getBlockForIndex(i);
            currentPosition = pos.add(data.getPosition());
            IBlockState state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock() != null && state.getBlock().equals(data.getBlock()))
                world.setBlockToAir(currentPosition);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 256D * 256D;
    }
}
