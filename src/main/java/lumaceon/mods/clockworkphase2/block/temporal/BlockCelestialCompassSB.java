package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCelestialCompassSB extends BlockClockworkPhase
{
    public BlockCelestialCompassSB(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setCreativeTab(null);
        this.setBlockUnbreakable();
        this.setResistance(1000000F);
        this.setLightLevel(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH));
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.IGNORE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(BlockFurnace.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(BlockFurnace.FACING)).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {BlockFurnace.FACING});
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        Block block = state.getBlock();
        EnumFacing direction = EnumFacing.getFront(getMetaFromState(state));
        int iterations = 0;
        while(block != null && block.equals(this) && iterations < 12)
        {
            pos = pos.offset(direction);
            state = worldIn.getBlockState(pos);
            if(state != null)
            {
                block = state.getBlock();
                if(block.equals(this))
                    direction = EnumFacing.getFront(getMetaFromState(state));
            }
            else
                block = null;
            ++iterations;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if(block != null && block.equals(ModBlocks.celestialCompass) && te != null && te instanceof TileCelestialCompass && !((TileCelestialCompass) te).isBeingDestroyed)
            TileCelestialCompass.destroyMultiblock((TileCelestialCompass) te, worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(hitY != 1.0F)
            return false;

        //Coordinates to be passed into the TextureHelper.
        int xOffset = 0;
        int zOffset = 0;

        EnumFacing direction = EnumFacing.getFront(getMetaFromState(state));

        int x = direction.getFrontOffsetX() + pos.getX();
        int z = direction.getFrontOffsetZ() + pos.getZ();

        xOffset += -direction.getFrontOffsetX();
        zOffset += -direction.getFrontOffsetZ();

        for(int n = 0; n < 10; n++)
        {
            if(world.getBlockState(new BlockPos(x, pos.getY(), z)) == null)
                return false;
            else if(world.getBlockState(new BlockPos(x, pos.getY(), z)).getBlock().equals(ModBlocks.celestialCompass))
            {
                TileEntity te = world.getTileEntity(new BlockPos(x, pos.getY(), z));
                if(te != null && te instanceof TileCelestialCompass)
                    return handleBlockClick(player, (TileCelestialCompass) te, xOffset + hitX, zOffset + hitZ);
            }
            else
            {
                direction = EnumFacing.getFront(getMetaFromState(world.getBlockState(new BlockPos(x, pos.getY(), z))));

                x += direction.getFrontOffsetX();
                z += direction.getFrontOffsetZ();

                xOffset += -direction.getFrontOffsetX();
                zOffset += -direction.getFrontOffsetZ();
            }
        }

        return false;
    }

    private boolean handleBlockClick(EntityPlayer player, TileCelestialCompass te, double xOffset, double zOffset)
    {
        //if(player.worldObj.isRemote)
        //    return false;
        if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.CENTER_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.CENTER_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 8);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 0);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 1);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 2);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 3);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 4);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 5);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 6);
        else if(Math.sqrt(Math.pow(xOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_X, 2) + Math.pow(zOffset - Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_Z, 2)) < 0.75F)
            return te.onSubBlockClicked(player, 7);
        else
            return te.onSubBlockClicked(player, -1);
    }

    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public static class Ranges
    {
        public static class CELESTIAL_COMPASS_COORDINATES
        {
            public static float CENTER_X = 0.50F;
            public static float CENTER_Z = 0.50F;

            public static float LIFE_X = 0.50F;
            public static float LIFE_Z = -3.45F;

            public static float LIGHT_X = 3.25F;
            public static float LIGHT_Z = -2.30F;

            public static float WATER_X = 4.40F;
            public static float WATER_Z = 0.50F;

            public static float EARTH_X = 3.25F;
            public static float EARTH_Z = 3.30F;

            public static float AIR_X = 0.50F;
            public static float AIR_Z = 4.35F;

            public static float FIRE_X = -2.25F;
            public static float FIRE_Z = 3.30F;

            public static float CELESTIAL_X = -3.40F;
            public static float CELESTIAL_Z = 0.50F;

            public static float DEATH_X = -2.25F;
            public static float DEATH_Z = -2.30F;

        }
    }
}