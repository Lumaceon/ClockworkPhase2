package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.lib.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemAgeDev extends ItemClockworkPhase
{
    public ItemAgeDev(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.provider.getDimensionId() == 0)
        {
            if(playerIn.isSneaking())
                playerIn.travelToDimension(Configs.DIM_ID.ZEROTH_AGE);
            else
                playerIn.travelToDimension(Configs.DIM_ID.FIRST_AGE);
        }
        else
            playerIn.travelToDimension(0);

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
        if(world.provider.getDimensionId() == 0)
        {
            if(player.isSneaking())
                player.travelToDimension(Configs.DIM_ID.SECOND_AGE);
            else
                player.travelToDimension(Configs.DIM_ID.THIRD_AGE);
        }
        else
            player.travelToDimension(0);
        return is;
    }
}
