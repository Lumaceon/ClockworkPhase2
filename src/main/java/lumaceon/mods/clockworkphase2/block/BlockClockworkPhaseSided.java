package lumaceon.mods.clockworkphase2.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockClockworkPhaseSided extends BlockClockworkPhase
{
    protected IIcon topIcon;
    protected IIcon bottomIcon;
    protected IIcon frontIcon;

    public BlockClockworkPhaseSided(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        switch(side)
        {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            default:
                return side == meta ? frontIcon : blockIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry)
    {
        this.blockIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_side");
        this.frontIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_front");
        this.topIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_top");
        this.bottomIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_bottom");
    }

    //Copied from BlockFurnace.
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, item);
        int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(direction == 0)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.NORTH.ordinal(), 2);
        else if(direction == 1)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.EAST.ordinal(), 2);
        else if(direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.SOUTH.ordinal(), 2);
        else if(direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.WEST.ordinal(), 2);
    }
}
