package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.util.ParadoxiumIngotFlavorText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemParadoxiumIngot extends ItemClockworkPhase
{
    public ItemParadoxiumIngot(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            list.add("\"" + ParadoxiumIngotFlavorText.howDoIFeelYouAsk(is, player) + "\"");
        else
            ParadoxiumIngotFlavorText.index = -1;
    }
}
