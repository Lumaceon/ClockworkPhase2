package lumaceon.mods.clockworkphase2.client.gui.machine;

import com.sun.javafx.collections.ImmutableObservableList;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiClockworkFurnace extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_furnace.png");
    static List<String> INPUT_TT = new ImmutableObservableList<String> (
            "Furnace Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are smelted"
    );
    static List<String> OUTPUT_TT = new ImmutableObservableList<String> (
            "Furnace Output Slot",
            Colors.GREY + "Does not accept input"
    );

    public GuiClockworkFurnace(EntityPlayer player, TileClockworkFurnace te) {
        super(  player,
                new ContainerClockworkFurnace(player, te),
                176, 165, 12, 50, 152, 2,
                BG,
                te,
                new HoverableLocation[] { new HoverableLocationEnergy(7, 47, 20, 62, te) },
                new IOConfiguration[] { new IOConfigurationSlot(te.slots[0], te, INPUT_TT), new IOConfigurationSlot(te.slots[1], te, OUTPUT_TT) } );
    }
}
