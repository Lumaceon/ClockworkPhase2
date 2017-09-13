package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClockworkFurnace extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_furnace.png");
    static String[] INPUT_TT = new String[] {
            "Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are smelted"
    };
    static String[] OUTPUT_TT = new String[]{
            "Smelting Output Slot",
            Colors.GREY + "Does not accept input"
    };

    public GuiClockworkFurnace(EntityPlayer player, TileClockworkFurnace te) {
        super(  player,
                new ContainerClockworkFurnace(player, te),
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
        Minecraft.getMinecraft().renderEngine.bindTexture(MOD_ICONS);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        if(tileEntity.isOperable(tileEntity.getEnergyCostPerTick()) && tileEntity.canWork())
        {
            int k = 13;
            this.drawTexturedModalRect(i + 81, j + 62 + 12 - k, 0, 0, 14, k + 1);
        }
    }
}
