package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.item.IMultiblockTemplateItem;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.TileMultiblockAssembler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMultiblockAssembler extends BlockClockworkPhase implements ITileEntityProvider
{
    public static final PropertyInteger METADATA = PropertyInteger.create("meta", 0, 15);

    public BlockMultiblockAssembler(Material blockMaterial, String registryName) {
        super(blockMaterial, registryName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof TileMultiblockAssembler && ((TileMultiblockAssembler) te).templateStack != null && !((TileMultiblockAssembler) te).isAssemblingMultiblock)
        {
            //If the tile has a template stack, and this block isn't destroying itself to create the multiblock, drop the template.
            //Multiblocks are responsible for dropping a template stack when destroyed. All we care about here is premature breakage.
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileMultiblockAssembler) te).templateStack));
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
            return true;
        TileEntity te = world.getTileEntity(pos);
        if(te !=  null && te instanceof TileMultiblockAssembler)
        {
            TileMultiblockAssembler multiblockAssembler = (TileMultiblockAssembler) te;
            ItemStack itemInHand = player.inventory.getCurrentItem();
            if(itemInHand.isEmpty())
                return multiblockAssembler.onRightClickWithEmptyHand(player);

            boolean consumeItem = false;
            if(itemInHand.getItem().equals(Item.getItemFromBlock(ModBlocks.constructionBlock)))
                consumeItem = multiblockAssembler.onRightClickWithConstructionBlock();

            if(itemInHand.getItem() instanceof IMultiblockTemplateItem)
                consumeItem = multiblockAssembler.onRightClickWithMultiblockTemplate(itemInHand);

            if(consumeItem)
            {
                itemInHand.shrink(1);
                if(itemInHand.getCount() <= 0)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(METADATA, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(METADATA);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {METADATA});
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMultiblockAssembler();
    }
}
