package lumaceon.mods.clockworkphase2.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTableSB;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockAssemblyTableSB extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockAssemblyTableSB(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setBlockBounds(0, 0, 0, 2F/16F, 1, 1);
    }

    @Override
    public int getMobilityFlag()
    {
        return 2;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
    {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(ModBlocks.assemblyTable);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        if(!world.isRemote)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileAssemblyTableSB && ((TileAssemblyTableSB) te).getWorkItem() != null)
                world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, ((TileAssemblyTableSB) te).getWorkItem()));
        }
        if(world.getBlock(x, y - 1, z).equals(ModBlocks.assemblyTable))
            world.setBlock(x, y - 1, z, Blocks.air);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        TileEntity te = iBlockAccess.getTileEntity(x, y, z);
        if(te != null && te instanceof TileAssemblyTableSB)
        {
            ItemStack workItem = ((TileAssemblyTableSB) te).getWorkItem();
            if(workItem != null)
            {
                switch (ForgeDirection.getOrientation(((TileAssemblyTableSB) te).blockMetadata))
                {
                    case EAST:
                        this.setBlockBounds(0, 0, 0, 2.8F / 16F, 1, 1);
                        break;
                    case WEST:
                        this.setBlockBounds(1, 0, 0, 1 - 2.8F / 16F, 1, 1);
                        break;
                    case NORTH:
                        this.setBlockBounds(0, 0, 1, 1, 1, 1 - 2.8F / 16F);
                        break;
                    case SOUTH:
                        this.setBlockBounds(0, 0, 0, 1, 1, 2.8F / 16F);
                        break;
                }
                return;
            }
            switch (ForgeDirection.getOrientation(((TileAssemblyTableSB) te).blockMetadata))
            {
                case EAST:
                    this.setBlockBounds(0, 0, 0, 2F / 16F, 1, 1);
                    break;
                case WEST:
                    this.setBlockBounds(1, 0, 0, 1 - 2F / 16F, 1, 1);
                    break;
                case NORTH:
                    this.setBlockBounds(0, 0, 1, 1, 1, 1 - 2F / 16F);
                    break;
                case SOUTH:
                    this.setBlockBounds(0, 0, 0, 1, 1, 2F / 16F);
                    break;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f0, float f1, float f2)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileAssemblyTableSB)
            {
                TileAssemblyTableSB table = (TileAssemblyTableSB) te;
                ItemStack currentItem = player.inventory.getCurrentItem();
                ItemStack workItem = table.getWorkItem();
                if(workItem != null && workItem.getItem() instanceof IAssemblable)
                {
                    AssemblySlot[] slots = table.getAssemblySlots();
                    if(slots != null)
                        for(AssemblySlot slot : slots)
                            if(slot != null && slot.isEnabled && isIntersecting((double) f0, (double) f1, (double) f2, ForgeDirection.getOrientation(te.blockMetadata), slot))
                            {
                                Logger.info("x " + f0);
                                Logger.info("y " + f1);
                                Logger.info("z " + f2);
                                slot.onClick(world, x, y, z, player, currentItem, workItem, slots);
                                return true;
                            }
                }
                if(currentItem != null && currentItem.getItem() instanceof IAssemblable)
                {
                    if(workItem == null)
                    {
                        ((TileAssemblyTableSB) te).setWorkItem(currentItem);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }
                else if(currentItem == null)
                {
                    if(table.getWorkItem() != null)
                    {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, table.getWorkItem());
                        table.setWorkItem(null);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAssemblyTableSB();
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

    private boolean isIntersecting(double x, double y, double z, ForgeDirection meta, AssemblySlot slot)
    {
        if(x < 0 || z < 0)
            return false;
        switch(meta)
        {
            case EAST:
                if(z > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || z < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case WEST:
                double newZ = 1 - z;
                if(newZ > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || newZ < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case NORTH:
                if(x > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || x < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case SOUTH:
                x = 1 - x;
                if(x > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || x < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
        }
        if(y > (1 - slot.centerY) + slot.sizeY * 0.5 * 1F/16F || y < (1 - slot.centerY) - slot.sizeY * 0.5 * 1F/16F)
            return false;
        return true;
    }
}
