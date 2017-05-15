package lumaceon.mods.clockworkphase2.client.gui.machine;

import com.sun.javafx.collections.ImmutableObservableList;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        Minecraft.getMinecraft().renderEngine.bindTexture(ICONS);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        if(tileEntity.isOperable(tileEntity.getEnergyCostPerTick()) && tileEntity.canWork(tileEntity.isInAntiMode()))
        {
            int k = 13;
            this.drawTexturedModalRect(i + 57, j + 48 + 12 - k, 0, 0, 14, k + 1);
        }

        int l = tileEntity.getProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 0, 14, l + 1, 16);
    }


}
