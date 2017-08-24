package lumaceon.mods.clockworkphase2.client.handler.keybind;

import lumaceon.mods.clockworkphase2.api.item.IKeybindActivation;
import lumaceon.mods.clockworkphase2.lib.KeyLib;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler
{
    public static KeyLib.Keys getKeyPressed()
    {
        if(Keybindings.activate.isPressed())
            return KeyLib.Keys.ACTIVATE;
        else if(Keybindings.toolbelt.isPressed())
            return KeyLib.Keys.TOOLBELT;
        else
            return KeyLib.Keys.IRRELEVANT;
    }

    @SubscribeEvent
    public void handleKeyPress(InputEvent.KeyInputEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        KeyLib.Keys keyPressed = getKeyPressed();
        if(keyPressed.equals(KeyLib.Keys.ACTIVATE))
        {
            ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
            if(!item.isEmpty() && item.getItem() instanceof IKeybindActivation)
                ((IKeybindActivation) item.getItem()).onKeyPressed(item, player);
        }
    }
}
