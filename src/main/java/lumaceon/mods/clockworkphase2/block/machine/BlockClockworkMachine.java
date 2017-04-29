package lumaceon.mods.clockworkphase2.block.machine;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class BlockClockworkMachine extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockClockworkMachine(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }
}
