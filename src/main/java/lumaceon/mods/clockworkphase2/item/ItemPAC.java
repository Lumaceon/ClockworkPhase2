package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPAC extends ItemClockworkPhase
{
    public ItemPAC(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
        if(!world.isRemote)
            world.spawnEntityInWorld(new EntityPAC(world, player));
        return is;
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        if(!world.isRemote)
            world.spawnEntityInWorld(new EntityPAC(world, player));
        return true;
    }
}