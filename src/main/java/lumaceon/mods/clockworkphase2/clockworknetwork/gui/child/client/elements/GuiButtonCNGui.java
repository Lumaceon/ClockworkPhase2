package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client.elements;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiButtonCNGui extends GuiButton
{
    private ClockworkNetworkContainer gui;

    public GuiButtonCNGui(ClockworkNetworkContainer gui, int id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "");
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if(this.visible)
        {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.hovered);
            if(gui != null && gui instanceof ClockworkNetworkGuiClient)
            {
                if(hoverState == 2)
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                else if(hoverState == 1)
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                else
                    GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
                ((ClockworkNetworkGuiClient) gui).drawBackground(this.xPosition, this.yPosition, this.zLevel);
                ((ClockworkNetworkGuiClient) gui).drawForeground(this.xPosition, this.yPosition, this.zLevel);
            }
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
