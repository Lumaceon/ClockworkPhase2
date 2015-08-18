package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.extendeddata.ExtendedWorldData;
import lumaceon.mods.clockworkphase2.init.Times;
import lumaceon.mods.clockworkphase2.tile.machine.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileTemporalFurnace)
        {
            Logger.info(((TileTemporalFurnace) te).getTimeStored(ForgeDirection.DOWN, Times.raw));
            Logger.info(((TileTemporalFurnace) te).getTimeStored(ForgeDirection.DOWN, Times.smelting));
        }
        Logger.info(ExtendedWorldData.get(world).isRuinMapGenerated(world));
        return true;
    }
}
