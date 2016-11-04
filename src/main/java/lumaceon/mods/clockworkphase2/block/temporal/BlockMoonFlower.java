package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockMoonFlower extends BlockClockworkPhase implements IPlantable, IGrowable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);
    public static final AxisAlignedBB MOON_FLOWER_BB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);

    public BlockMoonFlower(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        this.setTickRandomly(true);
        this.fullBlock = false;
        this.setHardness(0.0F);
        this.setSoundType(SoundType.GROUND);
        this.disableStats();
        this.setCreativeTab(null);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);
        if(worldIn.canBlockSeeSky(pos))
        {
            int meta = state.getValue(AGE);
            if(!worldIn.isDaytime())
            {
                if(meta < 4 && rand.nextInt(8) == 0)
                     worldIn.setBlockState(pos, state.withProperty(AGE, meta + 1), 2);
            }
            else if(meta > 0 && rand.nextInt(2) == 0)
                worldIn.setBlockState(pos, state.withProperty(AGE, meta - 1), 2);
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(state, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    protected Item getSeed() { return ModItems.moonFlowerSeeds.getItem(); }
    protected Item getCrop() { return ModItems.matterTemporal.getItem(); }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(AGE) == 4 ? this.getCrop() : this.getSeed();
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(AGE) < 4;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this.getSeed());
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public List<ItemStack> getDrops(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return MOON_FLOWER_BB;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return MOON_FLOWER_BB.offset(pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return getDefaultState();
        return state;
    }
}
