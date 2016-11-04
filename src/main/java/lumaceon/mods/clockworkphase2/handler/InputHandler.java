package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.temporal.ItemTemporalMultitool;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;


public class InputHandler
{
    @SubscribeEvent
    public void onMouseInput(MouseEvent event)
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
        {
            int wheel = event.getDwheel();
            if(wheel > 0) //To the left.
            {
                if(process(true))
                    event.setCanceled(true);
            }
            else if(wheel < 0) //To the right.
                if(process(false))
                    event.setCanceled(true);
        }
    }

    /**
     * @param left True for left, false for right (mouse wheel up is usually left).
     */
    private boolean process(boolean left)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player != null)
        {
            ItemStack is = player.inventory.getCurrentItem();
            if(is != null && is.getItem() instanceof ItemTemporalMultitool && NBTHelper.hasTag(is, NBTTags.COMPONENT_INVENTORY))
            {
                byte index = left ? (byte) (NBTHelper.BYTE.get(is, "MT_index") - 1) : (byte) (NBTHelper.BYTE.get(is, "MT_index") + 1);
                ItemStack[] items = NBTHelper.INVENTORY.get(is, NBTTags.COMPONENT_INVENTORY);
                if(index >= items.length)
                    index = 0;
                if(index < 0)
                    index = (byte) (items.length - 1);
                //TODO - account for empty slots in the items array? (it contains empty array slots)
                NBTHelper.BYTE.set(is, "MT_index", index);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
                //PacketHandler.INSTANCE.sendToServer(new MessageMultitoolIndexUpdate(index));
                return true;
            }
        }
        return false;
    }
}
