package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneModulationItem;
import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTimezoneModulator extends BlockClockworkPhase
{
    public static final PropertyEnum<BlockBed.EnumPartType> PART = PropertyEnum.create("part", BlockBed.EnumPartType.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockTimezoneModulator(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(state.getValue(PART).equals(BlockBed.EnumPartType.FOOT))
            return false;
        if(!player.isSneaking())
        {
            ItemStack is = player.getHeldItem();
            if(is != null)
            {
                if(is.getItem() instanceof ITimezoneModulationItem)
                {
                    TileEntity te = world.getTileEntity(pos);
                    if(te != null && te instanceof TileTimezoneModulator)
                        return ((TileTimezoneModulator) te).onRightClickWithModulatorStack(player, is);
                }
            }
            else
            {
                TileEntity te = world.getTileEntity(pos);
                if(te != null && te instanceof TileTimezoneModulator)
                    return ((TileTimezoneModulator) te).onRightClickWithEmptyHand(player);
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing) : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : Items.bed;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if(state.getValue(PART) == BlockBed.EnumPartType.FOOT)
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
    }

    @Override
    public int getRenderType() {
        return 2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | (state.getValue(FACING)).getHorizontalIndex();
        if(state.getValue(PART) == BlockBed.EnumPartType.HEAD)
            i |= 8;
        return i;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING, PART);
    }
}
