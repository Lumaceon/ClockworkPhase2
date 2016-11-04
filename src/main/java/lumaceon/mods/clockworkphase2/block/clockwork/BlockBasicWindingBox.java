package lumaceon.mods.clockworkphase2.block.clockwork;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBasicWindingBox extends BlockClockworkPhase
{
    public BlockBasicWindingBox(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IClockworkConstruct)
            {
                ItemStack is = player.getHeldItemMainhand();
                ((IClockworkConstruct) is.getItem()).addTension(is, 1000);
                return true;
            }
        }
        return false;
    }
}
