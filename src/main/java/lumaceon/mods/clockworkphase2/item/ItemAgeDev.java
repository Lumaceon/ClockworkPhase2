package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAgeDev extends ItemClockworkPhase
{
    public ItemAgeDev(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        if(world.provider.dimensionId == 0)
        {
            if(player.isSneaking())
                player.travelToDimension(Defaults.DIM_ID.ZEROTH_AGE);
            else
                player.travelToDimension(Defaults.DIM_ID.FIRST_AGE);
        }
        else
            player.travelToDimension(0);

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
        if(world.provider.dimensionId == 0)
        {
            if(player.isSneaking())
                player.travelToDimension(Defaults.DIM_ID.SECOND_AGE);
            else
                player.travelToDimension(Defaults.DIM_ID.THIRD_AGE);
        }
        else
            player.travelToDimension(0);
        return is;
    }
}
