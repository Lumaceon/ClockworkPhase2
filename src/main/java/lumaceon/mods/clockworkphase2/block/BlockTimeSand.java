package lumaceon.mods.clockworkphase2.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockTimeSand extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;

    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockTimeSand(Fluid fluid, Material material, String unlocalizedName)
    {
        super(fluid, material);
        this.setLightLevel(15);
        this.setLightOpacity(0);
        this.setBlockName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        //stillIcon = iconRegister.registerIcon(Textures.STILL_TIME_SAND);
        //flowingIcon = iconRegister.registerIcon(Textures.FLOWING_TIME_SAND);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
