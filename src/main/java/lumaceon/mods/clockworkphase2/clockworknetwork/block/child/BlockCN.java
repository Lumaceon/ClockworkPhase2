package lumaceon.mods.clockworkphase2.clockworknetwork.block.child;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockCN extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockCN(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof TileClockworkNetworkMachine)
        {
            TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
            cnMachine.setTileDataFromItemStack(stack);
            cnMachine.markDirty();
        }
    }

    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if(!world.isRemote && !player.capabilities.isCreativeMode)
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null && te instanceof TileClockworkNetworkMachine)
            {
                TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
                ItemStack result = cnMachine.itemBlock.copy();

                float f = 0.7F;
                double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, result);

                entityitem.setPickupDelay(10);
                world.spawnEntityInWorld(entityitem);
            }
        }

        return super.removedByPlayer(world, pos, player, willHarvest);
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> results = new ArrayList<ItemStack>(1);
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof TileClockworkNetworkMachine)
        {
            TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
            ItemStack result = cnMachine.itemBlock.copy();
            results.add(result);
        }
        return results;
    }
}
