package lumaceon.mods.clockworkphase2.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.phase.Phases;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class BlockMoonFlower extends BlockClockworkPhase implements IPlantable, IGrowable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] blockIcons;

    public BlockMoonFlower(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setHardness(0.0F);
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.farmland;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        super.onNeighborBlockChange(world, x, y, z, block);
        this.checkAndDropBlock(world, x, y, z);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        this.checkAndDropBlock(world, x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if(meta < 3)
        {
            if(world.canBlockSeeTheSky(x, y, z) && !world.isDaytime() && random.nextInt(2) == 0)
            {
                ++meta;
                world.setBlockMetadataWithNotify(x, y, z, meta, 2);
            }
        }
        else if(meta == 3)
        {
            if(world.canBlockSeeTheSky(x, y, z) && !world.isDaytime())
            {
                if(!world.getBlock(x, y+1, z).equals(ModBlocks.telescope))
                    return;
                else if(!world.getBlock(x, y+2, z).equals(ModBlocks.telescope) && random.nextInt(3) != 0)
                    return;
                else if(!world.getBlock(x, y+3, z).equals(ModBlocks.telescope) && random.nextInt(3) != 0)
                    return;

                if(Phases.isPhaseActive(world, x, y, z, Phases.elysianComet))
                    world.setBlockMetadataWithNotify(x, y, z, 5, 2); //Set meta to 5 (temporal material)
                else
                    world.setBlockMetadataWithNotify(x, y, z, 4, 2); //Set meta to 4 (moon pearl)
            }
        }
    }

    protected void checkAndDropBlock(World world, int x, int y, int z)
    {
        if(!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, getBlockById(0), 0, 2);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z).canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
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
    public int getRenderType() {
        return 1;
    }

    protected Item getSeeds() {
        return ModItems.moonFlowerSeeds;
    }

    protected Item getProduce(int metadata) {
        switch(metadata)
        {
            case 4:
                return ModItems.moonPearl;
            case 5:
                return ModItems.elysianGem;
        }
        return null;
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(world, x, y, z, p_149690_5_, p_149690_6_, 0);
    }

    @Override
    public Item getItemDropped(int metadata, Random p_149650_2_, int p_149650_3_) {
        return metadata > 3 ? this.getProduce(metadata) : this.getSeeds();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int metadata) {
        return this.blockIcons[metadata];
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_) {
        return world.getBlockMetadata(x, y, z) < 3;
    }

    @Override
    public boolean func_149852_a(World world, Random p_149852_2_, int x, int y, int z) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return this.getSeeds();
    }

    /**
     * When fertilized (typically with bonemeal).
     */
    @Override
    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {}

    //TODO - Possibly add a condition for duplicating seeds?
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        return super.getDrops(world, x, y, z, metadata, fortune);
        /*ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if(metadata >= 3)
        {
            Do some stuff with special drops.
        }

        return ret;*/
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry)
    {
        blockIcons = new IIcon[6];
        for(int n = 0; n < blockIcons.length; n++)
            this.blockIcons[n] = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + n);
    }
}
