package lumaceon.mods.clockworkphase2.block.machine.lifeform;

import lumaceon.mods.clockworkphase2.block.machine.BlockClockworkMachine;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLifeformConstructor extends BlockClockworkMachine
{
    public BlockLifeformConstructor(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLifeformConstructor();
    }
}
