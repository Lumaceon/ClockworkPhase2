package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBasicWindingBox extends BlockClockworkPhase
{
    public BlockBasicWindingBox(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IClockworkConstruct)
            {
                ItemStack is = player.getHeldItem();
                if(NBTHelper.hasTag(is, NBTTags.MAX_TENSION))
                {
                    ((IClockworkConstruct) is.getItem()).addTension(is, 1000);
                    return true;
                }
            }
        }
        return false;
    }
}
