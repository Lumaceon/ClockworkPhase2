package lumaceon.mods.clockworkphase2.item.timeframekey;

import lumaceon.mods.clockworkphase2.api.item.ITimeframeKeyItem;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTimeframeKey extends ItemClockworkPhase implements ITimeframeKeyItem
{
    public ItemTimeframeKey(int maxStack, int maxDamage, String registryName) {
        super(maxStack, maxDamage, registryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        list.add("WIP Item: coming \'soon\'");
    }

    @Override
    public int getTickLength() {
        return 1200;
    }

    @Override
    public boolean onCraftTick(World world, TileEntity tile, BlockPos tilePosition, ItemStack[] craftingItems, int tickNumber) {
        return false;
    }
}
