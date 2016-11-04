package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public static int x;
    public static int y;
    public static int z;
    public static int index = 0;

    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof TileCelestialCompass)
        {
            for(int i = 0; i < 9; i++)
                if(((TileCelestialCompass) te).getCraftingItem(i) != null)
                    Logger.info(((TileCelestialCompass) te).getCraftingItem(i).getDisplayName());
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World world, EntityPlayer player, EnumHand hand)
    {
        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }
}
