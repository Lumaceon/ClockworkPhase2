package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.TileTDA;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTDA extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTDA(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setResistance(1000000.0F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, item);
        if(entity.rotationPitch < -50 || entity.rotationPitch > 50)
        {
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.DOWN.ordinal(), 2);
            return;
        }
        int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(direction == 0)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.NORTH.ordinal(), 2);
        else if(direction == 1)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.EAST.ordinal(), 2);
        else if(direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.SOUTH.ordinal(), 2);
        else if(direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.WEST.ordinal(), 2);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileTDA.destroyMultiblock(world, x, y, z, meta);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTDA();
    }
}
