package lumaceon.mods.clockworkphase2.clockworknetwork.block.child;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockCN extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockCN(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileClockworkNetworkMachine)
        {
            TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
            cnMachine.setTileDataFromItemStack(stack);
            cnMachine.markDirty();
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
        if(!world.isRemote && !player.capabilities.isCreativeMode)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileClockworkNetworkMachine)
            {
                TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
                ItemStack result = cnMachine.itemBlock.copy();

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
            if(te != null && te instanceof TileClockworkNetworkMachine)
            {
                TileClockworkNetworkMachine cnMachine = (TileClockworkNetworkMachine) te;
                ItemStack result = cnMachine.itemBlock.copy();
                results.add(result);
            }
        }
        return results;
    }
}
