package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTimezoneControllerSB extends BlockClockworkPhase
{
    public BlockTimezoneControllerSB(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setCreativeTab(null);
        this.setBlockUnbreakable();
        this.setResistance(1000000F);
        this.setLightLevel(1.0F);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        /*if(localY != 1.0F)
        {
            return false;
        }

        //Coordinates to be passed into the TextureHelper.
        int xOffset = 0;
        int zOffset = 0;

        meta = world.getBlockMetadata(x, y, z);
        ForgeDirection direction = ForgeDirection.getOrientation(meta);

        x += direction.offsetX;
        z += direction.offsetZ;

        xOffset += -direction.offsetX;
        zOffset += -direction.offsetZ;

        for(int n = 0; n < 10; n++)
        {
            if(world.getBlock(x, y, z) == null)
            {
                return false;
            }
            else if(world.getBlock(x, y, z).equals(ModBlocks.timezoneController))
            {
                TileEntity te = world.getTileEntity(x, y, z);
                if(te != null && te instanceof TileCelestialCompass)
                {
                    return handleBlockClick(player, (TileCelestialCompass) te, xOffset + localX, zOffset + localZ);
                }
            }
            else
            {
                meta = world.getBlockMetadata(x, y, z);
                direction = ForgeDirection.getOrientation(meta);

                x += direction.offsetX;
                z += direction.offsetZ;

                xOffset += -direction.offsetX;
                zOffset += -direction.offsetZ;
            }
        }*/
        return false;
    }

    private static int getTextureIndexFromCoordinates(int x, int z)
    {
        int index;

        //[0-4]
        if(x == -5)
        {
            z += 2;
            index = z;
        }
        //[5-11]
        else if(x == -4)
        {
            z += 3;
            index = z + 5;
        }
        //[12-20]
        else if(x == -3)
        {
            z += 4;
            index = z + 12;
        }
        //[21-75]
        else if(x >= -2 && x <= 2)
        {
            int indexOffset = x + 2;

            z += 5;
            index = z + 21 + (indexOffset * 11);
        }
        //[76-84]
        else if(x == 3)
        {
            z += 4;
            index = z + 76;
        }
        //[85-91]
        else if(x == 4)
        {
            z += 3;
            index = z + 85;
        }
        //[92-96]
        else if(x == 5)
        {
            z += 2;
            index = z + 92;
        }
        else
        {
            return 0;
        }

        if(index >= 48) { index--; }
        return index;
    }
}