package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTimezoneFluidExporter extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneFluidExporter(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null && te instanceof TileTimezoneFluidExporter)
            {
                String fluidName = ((TileTimezoneFluidExporter) te).setNextTargetFluid();
                if(fluidName.equals(""))
                    fluidName = "(No Liquids Found)";

                if(!world.isRemote)
                    player.addChatComponentMessage(new ChatComponentText("Exporting: " + fluidName));
                return true;
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTimezoneFluidExporter();
    }

    @Override
    public int getRenderType() {
        return 2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
