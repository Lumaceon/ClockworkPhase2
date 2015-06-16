package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidExporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockTimezoneFluidExporter extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneFluidExporter(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f0, float f1, float f2)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(x, y, z);
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
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
