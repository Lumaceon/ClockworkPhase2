package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockClockworkPhase extends Block implements ISimpleNamed
{
    String simpleName;

    public BlockClockworkPhase(Material blockMaterial, String name) {
        super(blockMaterial);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setHardness(3.0F);
        this.simpleName = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @Override
    public String getUnlocalizedName() {
        return this.getRegistryName().toString();
    }

    @Override
    public String getSimpleName() {
        return this.simpleName;
    }
}
