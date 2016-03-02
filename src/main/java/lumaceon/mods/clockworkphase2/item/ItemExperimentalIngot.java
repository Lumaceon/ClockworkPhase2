package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemExperimentalIngot extends ItemClockworkPhase
{
    public ItemExperimentalIngot(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        list.add(Colors.WHITE + NBTHelper.INT.get(is, "percent_eternium") + "% Eternium");
        list.add(Colors.WHITE + NBTHelper.INT.get(is, "percent_momentium") + "% Momentium");
        list.add(Colors.WHITE + NBTHelper.INT.get(is, "percent_paradoxium") + "% Paradoxium");
        list.add("");
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            for(int n = 0; n < 6; n++)
                list.add(NBTHelper.STRING.get(is, "ingot_" + n));
        else
            list.add(Colors.BLUE + "Shift - Component Metals");
    }
}
