package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        if(world.provider.dimensionId == Defaults.DIM_ID.FIRST_AGE)
            player.travelToDimension(0);
        else
            player.travelToDimension(Defaults.DIM_ID.FIRST_AGE);
        return true;
    }
}
