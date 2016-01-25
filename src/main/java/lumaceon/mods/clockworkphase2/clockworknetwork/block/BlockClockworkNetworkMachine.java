package lumaceon.mods.clockworkphase2.clockworknetwork.block;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class BlockClockworkNetworkMachine extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockClockworkNetworkMachine(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }
}
