package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;

public class BlockTDASB extends BlockClockworkPhase
{
    public BlockTDASB(Material blockMaterial, String unlocalizedName) {
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

    /*@Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int meta)
    {
        //if(meta != 0 && meta != 1)
        //    return this.blockIcon;

        boolean flag = true;

        //Coordinates to be passed into the TextureHelper.
        int timesMoved = 0;

        meta = blockAccess.getBlockMetadata(x, y, z);
        ForgeDirection direction = ForgeDirection.getOrientation(meta);

        x += direction.offsetX;
        y += direction.offsetY;
        z += direction.offsetZ;

        timesMoved++;

        for(int n = 0; n < 30 && flag; n++)
        {
            if(blockAccess.getBlock(x, y, z) == null)
                return getIcon(0, 0);
            else if(blockAccess.getBlock(x, y, z).equals(ModBlocks.tda))
                flag = false;
            else
            {
                meta = blockAccess.getBlockMetadata(x, y, z);
                direction = ForgeDirection.getOrientation(meta);

                x += direction.offsetX;
                y += direction.offsetY;
                z += direction.offsetZ;

                timesMoved++;
            }
        }

        int iconIndex = timesMoved - 1;
        if(meta == ForgeDirection.WEST.ordinal() && direction.equals(ForgeDirection.WEST))
            iconIndex += 27;
        if(meta == ForgeDirection.EAST.ordinal())
            return this.icons[0];
        if(iconIndex < 0 || iconIndex > 55)
            return this.blockIcon;
        return this.icons[iconIndex];
    }

    /*private static int getTextureIndex(int timesMoved, ForgeDirection finalIncomingDirection)
    {
        int index;

        if(finalIncomingDirection.equals(ForgeDirection.EAST))
        {

        }
        else if(finalIncomingDirection.equals(ForgeDirection.WEST))
        {

        }
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
    }*/
}
