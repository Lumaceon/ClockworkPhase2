package lumaceon.mods.clockworkphase2.block.clockwork;

import lumaceon.mods.clockworkphase2.api.omnimachine.IOmnimachineItem;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.TileClockworkOmnimachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockClockworkOmnimachine extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockClockworkOmnimachine(Material blockMaterial, String registryName) {
        super(blockMaterial, registryName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack is = player.inventory.getCurrentItem();
            if(is != null && is.getItem() instanceof IOmnimachineItem)
            {
                TileEntity te = world.getTileEntity(pos);
                if(te != null && te instanceof TileClockworkOmnimachine)
                    ((TileClockworkOmnimachine) te).addModule(((IOmnimachineItem)is.getItem()).getOmnimachineModule());
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClockworkOmnimachine();
    }
}
