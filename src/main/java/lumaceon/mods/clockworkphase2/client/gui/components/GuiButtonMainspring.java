package lumaceon.mods.clockworkphase2.client.gui.components;

import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class GuiButtonMainspring extends GuiButton
{
    private ContainerAssemblyTable containerAssemblyTable;

    public GuiButtonMainspring(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, ContainerAssemblyTable container) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, "+");
        this.containerAssemblyTable = container;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.hovered)
            {
                l = 16777120;
            }

            int metalAddition = 0;
            for(int n = 0; n < containerAssemblyTable.componentInventory.getSizeInventory(); n++)
                metalAddition += MainspringMetalRegistry.getValue(containerAssemblyTable.componentInventory.getStackInSlot(n));

            this.drawCenteredString(fontrenderer, this.displayString + metalAddition + " Tension", this.x + this.width / 2, this.y + (this.height - 8) / 2, l);
        }
    }
}
