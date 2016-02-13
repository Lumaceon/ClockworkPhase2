package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockTimeSand extends BlockFluidClassic
{
    public BlockTimeSand(Fluid fluid, Material material, String unlocalizedName)
    {
        super(fluid, material);
        this.setLightLevel(15);
        this.setLightOpacity(0);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
