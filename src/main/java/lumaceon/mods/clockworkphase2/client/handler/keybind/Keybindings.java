package lumaceon.mods.clockworkphase2.client.handler.keybind;

import lumaceon.mods.clockworkphase2.lib.KeyLib;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybindings
{
    public static KeyBinding activate = new KeyBinding(KeyLib.ACTIVATE, Keyboard.KEY_R, KeyLib.CATEGORY);
    public static KeyBinding toolbelt = new KeyBinding(KeyLib.ACTIVATE, Keyboard.KEY_LCONTROL, KeyLib.CATEGORY);
}
