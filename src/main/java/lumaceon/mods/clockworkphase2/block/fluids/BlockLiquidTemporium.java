package lumaceon.mods.clockworkphase2.block.fluids;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidTemporium extends BlockFluidClassic
{
    public BlockLiquidTemporium(Material material, String unlocalizedName, Fluid fluid)
    {
        super(fluid, material);
        this.setLightLevel(15);
        this.setLightOpacity(0);
        this.setUnlocalizedName(Reference.MOD_ID + ":" + unlocalizedName);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
