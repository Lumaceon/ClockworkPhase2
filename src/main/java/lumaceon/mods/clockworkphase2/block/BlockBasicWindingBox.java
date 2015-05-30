package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBasicWindingBox extends BlockClockworkPhase
{
    public BlockBasicWindingBox(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f0, float f1, float f2)
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
