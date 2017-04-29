package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemGuidebook extends ItemClockworkPhase
{
    public ItemGuidebook(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        resolveContents(stack, playerIn);
        playerIn.openGui(ClockworkPhase2.instance, 7, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World world, EntityPlayer player, EnumHand hand)
    {
        resolveContents(is, player);
        player.openGui(ClockworkPhase2.instance, GUIs.GUIDEBOOK.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }

    private void resolveContents(ItemStack stack, EntityPlayer player)
    {
        if (stack != null && stack.getTagCompound() != null)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if(!nbttagcompound.getBoolean("resolved"))
            {
                nbttagcompound.setBoolean("resolved", true);

                if(ItemWritableBook.isNBTValid(nbttagcompound))
                {
                    NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);

                    for (int i = 0; i < nbttaglist.tagCount(); ++i)
                    {
                        String s = nbttaglist.getStringTagAt(i);
                        ITextComponent textComponent;

                        try
                        {
                            textComponent = TextComponentString.Serializer.jsonToComponent(s);
                            //textComponent = ChatComponentProcessor.processComponent(player, textComponent, player);
                            //TODO fix
                        }
                        catch (Exception var9)
                        {
                            textComponent = new TextComponentString(s);
                        }

                        nbttaglist.set(i, new NBTTagString(TextComponentString.Serializer.componentToJson(textComponent)));
                    }

                    nbttagcompound.setTag("pages", nbttaglist);

                    if (player instanceof EntityPlayerMP && player.getActiveItemStack() == stack)
                    {
                        Slot slot = player.openContainer.getSlotFromInventory(player.inventory, player.inventory.currentItem);
                        //((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new SPacketSetSlot(0, slot.slotNumber, stack));
                        //TODO fix
                    }
                }
            }
        }
    }
}
