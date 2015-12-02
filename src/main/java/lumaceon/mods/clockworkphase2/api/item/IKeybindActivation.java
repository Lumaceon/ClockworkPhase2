package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IKeybindActivation
{
    /**
     * Called CLIENT-SIDE when the activate keybind is pressed with this itemstack in hand.
     */
    public void onKeyPressed(ItemStack item, EntityPlayer player);
}
