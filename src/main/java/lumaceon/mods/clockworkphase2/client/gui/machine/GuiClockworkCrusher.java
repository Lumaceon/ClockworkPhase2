package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkCrusher;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrusher;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClockworkCrusher extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_crusher.png");
    static String[] INPUT_TT = new String[] {
            "Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crushed"
    };
    static String[] OUTPUT_TT = new String[]{
            "Crusher Output Slot",
            Colors.GREY + "Does not accept input"
    };

    public GuiClockworkCrusher(EntityPlayer player, TileClockworkCrusher te) {
        super(  player,
                new ContainerClockworkCrusher(player, te),
                176, 165,
                10, 6,
                72, 26,
                152, 2,
                152, 24,
                BG,
                te,
                new HoverableLocation[] { new HoverableLocationEnergy(9, 24, 5, 74, te) },
                new IOConfiguration[] { new IOConfigurationSlot(te.slots[0], te, INPUT_TT), new IOConfigurationSlot(te.slots[1], te, OUTPUT_TT) } );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        Minecraft.getMinecraft().renderEngine.bindTexture(ICONS);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        if(tileEntity.isOperable(tileEntity.getEnergyCostPerTick()) && tileEntity.canWork())
        {
            this.drawTexturedModalRect(i + 81, j + 61, 16, 0, 16, 16);
        }
    }
}
