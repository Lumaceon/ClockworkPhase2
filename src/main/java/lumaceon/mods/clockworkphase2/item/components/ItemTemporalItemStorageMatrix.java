package lumaceon.mods.clockworkphase2.item.components;

import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTemporalItemStorageMatrix extends ItemClockworkPhase
{
    public ItemTemporalItemStorageMatrix(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        if(NBTHelper.hasTag(is, "display_name") && NBTHelper.hasTag(is, "item_count") && NBTHelper.hasTag(is, "ul_name"))
        {
            int count = NBTHelper.INT.get(is, "item_count");
            String displayName = NBTHelper.STRING.get(is, "display_name");
            if(count == 0)
                list.add(Colors.AQUA + "Anchoring " + displayName + "s...");
            else if(count == 1)
                list.add(Colors.AQUA + "Anchoring 1 " + displayName + "...");
            else
                list.add(Colors.AQUA + "Anchoring " + count + " " + displayName + "s...");
        }
        else
            list.add(Colors.RED + "Right-click on a block.");
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        IBlockState state = worldIn.getBlockState(pos);
        if(state != null && state.getBlock() != null)
        {
            //Make sure there's no items inside before changing
            if(!NBTHelper.hasTag(is, "item_count") || NBTHelper.INT.get(is, "item_count") == 0)
            {
                ItemStack temp;
                if(playerIn == null || playerIn.isSneaking())
                    temp = new ItemStack(state.getBlock());
                else
                    temp = state.getBlock().getDrops(worldIn, pos, state, 0).get(0);
                if(temp == null)
                {
                    playerIn.addChatComponentMessage(new ChatComponentText(Colors.RED + "No harvesting drops found for " + state.getBlock().getUnlocalizedName() + "."));
                    return false;
                }
                NBTHelper.STRING.set(is, "ul_name", temp.getUnlocalizedName());
                NBTHelper.STRING.set(is, "display_name", temp.getDisplayName());
                NBTHelper.INT.set(is, "item_count", 0);

                if(playerIn != null && !worldIn.isRemote)
                    playerIn.addChatComponentMessage(new ChatComponentText(Colors.AQUA + "Now anchoring " + temp.getDisplayName() + "..."));
                return true;
            }
            else if(playerIn != null && !worldIn.isRemote && NBTHelper.hasTag(is, "display_name") && NBTHelper.hasTag(is, "ul_name"))
            {
                int count = NBTHelper.INT.get(is, "item_count");
                String displayName = NBTHelper.STRING.get(is, "display_name");
                if(count == 1)
                    playerIn.addChatComponentMessage(new ChatComponentText(Colors.RED + "Failed to set new target block; already contains 1 " + displayName + "."));
                else
                    playerIn.addChatComponentMessage(new ChatComponentText(Colors.RED + "Failed to set new target block; already contains " + count + " " + displayName + "s."));
            }
        }
        return false;
    }
}
