package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.client.gui.GuiHelper;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class GuiClockworkMachine extends GuiContainer
{
    protected static ResourceLocation POWER_PEG = new ResourceLocation(Reference.MOD_ID, "textures/gui/power_peg.png");

    protected EntityPlayer player;
    protected TileClockworkMachine tileEntity;
    protected int powerPegX, powerPegY;
    protected ResourceLocation background;
    protected HoverableLocation[] hoverables;

    public GuiClockworkMachine(EntityPlayer player, Container inventorySlotsIn, int powerPegX, int powerPegY, ResourceLocation background, TileClockworkMachine te, HoverableLocation[] hoverables) {
        super(inventorySlotsIn);
        this.player = player;
        this.powerPegX = powerPegX;
        this.powerPegY = powerPegY;
        this.background = background;
        this.tileEntity = te;
        this.hoverables = hoverables;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, this.zLevel, this.xSize, this.ySize);

        float degrees = 0;
        IEnergyStorage energyStorage = tileEntity.energyStorage;
        if(energyStorage != null && energyStorage.getMaxEnergyStored() > 0)
        {
            degrees = ((float) energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored()) * 180.0F;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(POWER_PEG);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.guiLeft + powerPegX + 15, this.guiTop + powerPegY + 2, 0.0F);
        GlStateManager.rotate(degrees, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-15.0F, -2.0F, 0.0F);
        GuiHelper.drawTexturedModalRectStretched(0, 0, this.zLevel, 17, 4);
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(player.inventory.getItemStack() == null)
        {
            for(HoverableLocation h : hoverables)
            {
                if(h != null && mouseX >= this.guiLeft + h.getMinX() && mouseX <= this.guiLeft + h.getMaxX() && mouseY >= this.guiTop + h.getMinY() && mouseY <= this.guiTop + h.getMaxY())
                {
                    this.renderToolTip(h.getTooltip(), mouseX - this.guiLeft, mouseY - this.guiTop);
                    break;
                }
            }
        }
    }

    protected void renderToolTip(List<String> list, int x, int y)
    {
        this.drawHoveringText(list, x, y, fontRendererObj);
    }

    public static class HoverableLocation
    {
        private int minX, maxX, minY, maxY;

        public HoverableLocation(int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        public int getMinX() {
            return minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinY() {
            return minY;
        }

        public int getMaxY() {
            return maxY;
        }

        public List<String> getTooltip() {
            return null;
        }
    }

    public static class HoverableLocationEnergy extends HoverableLocation
    {
        TileClockworkMachine te;

        public HoverableLocationEnergy(int minX, int maxX, int minY, int maxY, TileClockworkMachine te) {
            super(minX, maxX, minY, maxY);
            this.te = te;
        }

        @Override
        public List<String> getTooltip()
        {
            if(te.energyStorage != null)
            {
                ArrayList<String> ret = new ArrayList<String>();
                ret.add("Energy:");
                ret.add(te.energyStorage.getEnergyStored() + " / " + te.energyStorage.getMaxEnergyStored() + " FE");
                return ret;
            }
            else
            {
                ArrayList<String> ret = new ArrayList<String>();
                ret.add(Colors.RED + "Missing Mainspring....");
                return ret;
            }
        }
    }
}
