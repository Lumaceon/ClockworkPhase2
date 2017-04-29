package lumaceon.mods.clockworkphase2.block.machine;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockClockworkFurnace extends BlockClockworkMachine
{
    public BlockClockworkFurnace(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!playerIn.isSneaking())
        {
            playerIn.openGui(ClockworkPhase2.instance, GUIs.CLOCKWORK_FURNACE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClockworkFurnace();
    }
}
