package lumaceon.mods.clockworkphase2.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof IFluidHandler)
        {
            FluidStack fluid = ((IFluidHandler) te).getTankInfo(ForgeDirection.DOWN)[0].fluid;
            if(fluid != null)
                player.addChatComponentMessage(new ChatComponentText("Fluid: " + fluid.getFluid().getUnlocalizedName()));
        }
        return true;
    }
}
