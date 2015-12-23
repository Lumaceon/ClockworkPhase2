package lumaceon.mods.clockworkphase2.client.gui.components;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import lumaceon.mods.clockworkphase2.client.gui.GuiClockworkController;
import lumaceon.mods.clockworkphase2.util.ChildGuiData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiCNGuiElement extends GuiButton
{
    public ChildGuiData guiData;

    public GuiCNGuiElement(ChildGuiData guiData, int id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "");
        this.guiData = guiData;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if(this.visible)
        {
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.field_146123_n);
            if(guiData != null && guiData.gui != null && guiData.gui instanceof ClockworkNetworkGuiClient)
            {
                GL11.glEnable(GL11.GL_BLEND);
                if(hoverState == 2)
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
                else
                    GL11.glColor4f(0.9F, 0.9F, 0.9F, 0.9F);

                ((ClockworkNetworkGuiClient) guiData.gui).drawBackground(this.xPosition, this.yPosition, this.zLevel);
                ((ClockworkNetworkGuiClient) guiData.gui).drawForeground(this.xPosition, this.yPosition, this.zLevel);
            }
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }

    public void moveGui(int newX, int newY, int guiSizeX, int guiSizeY)
    {
        if(guiData != null)
        {
            this.xPosition = newX;
            this.yPosition = newY;
            guiData.setLocation(newX, newY, guiSizeX, guiSizeY);
        }
    }
}
