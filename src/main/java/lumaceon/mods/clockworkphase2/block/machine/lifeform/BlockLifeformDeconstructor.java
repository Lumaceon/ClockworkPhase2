package lumaceon.mods.clockworkphase2.block.machine.lifeform;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.block.machine.BlockClockworkMachine;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformDeconstructor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLifeformDeconstructor extends BlockClockworkMachine
{
    public BlockLifeformDeconstructor(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null)
            {
                player.openGui(ClockworkPhase2.instance, GUIs.LIFEFORM_DECONSTRUCTOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLifeformDeconstructor();
    }
}
