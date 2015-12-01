package lumaceon.mods.clockworkphase2.block.clockwork;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.clockwork.TileClockworkFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockClockworkFurnace extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockClockworkFurnace(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f0, float f1, float f2)
    {
        if(player.isSneaking())
            return false;
        if(!world.isRemote)
            player.openGui(ClockworkPhase2.instance, 2, world, x, y, z);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileClockworkFurnace)
        {
            TileClockworkFurnace CWFurnace = (TileClockworkFurnace) te;
            CWFurnace.setTileDataFromItemStack(stack);
            CWFurnace.markDirty();
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
        if(!world.isRemote && !player.capabilities.isCreativeMode)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileClockworkFurnace)
            {
                TileClockworkFurnace CWFurnace = (TileClockworkFurnace) te;
                ItemStack result = CWFurnace.itemBlock.copy();

                float f = 0.7F;
                double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, result);

                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);
            }
        }

        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> results = new ArrayList<ItemStack>(1);
        if(!world.isRemote)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileClockworkFurnace)
            {
                TileClockworkFurnace CWFurnace = (TileClockworkFurnace) te;
                ItemStack result = CWFurnace.itemBlock.copy();
                results.add(result);
            }
        }
        return results;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileClockworkFurnace();
    }
}
