package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemGuidebook extends ItemClockworkPhase
{
    public ItemGuidebook(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        resolveContents(stack, playerIn);
        playerIn.openGui(ClockworkPhase2.instance, 7, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        resolveContents(is, player);
        player.openGui(ClockworkPhase2.instance, 7, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return is;
    }

    private void resolveContents(ItemStack stack, EntityPlayer player)
    {
        if (stack != null && stack.getTagCompound() != null)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (!nbttagcompound.getBoolean("resolved"))
            {
                nbttagcompound.setBoolean("resolved", true);

                if (ItemEditableBook.validBookTagContents(nbttagcompound))
                {
                    NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);

                    for (int i = 0; i < nbttaglist.tagCount(); ++i)
                    {
                        String s = nbttaglist.getStringTagAt(i);
                        IChatComponent ichatcomponent;

                        try
                        {
                            ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
                            ichatcomponent = ChatComponentProcessor.processComponent(player, ichatcomponent, player);
                        }
                        catch (Exception var9)
                        {
                            ichatcomponent = new ChatComponentText(s);
                        }

                        nbttaglist.set(i, new NBTTagString(IChatComponent.Serializer.componentToJson(ichatcomponent)));
                    }

                    nbttagcompound.setTag("pages", nbttaglist);

                    if (player instanceof EntityPlayerMP && player.getCurrentEquippedItem() == stack)
                    {
                        Slot slot = player.openContainer.getSlotFromInventory(player.inventory, player.inventory.currentItem);
                        ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, slot.slotNumber, stack));
                    }
                }
            }
        }
    }
}
