package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client.elements;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import lumaceon.mods.clockworkphase2.clockworknetwork.gui.ChildGuiData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiCNGuiElement extends GuiButton
{
    public ChildGuiData guiData;
    public List<GuiButton> buttonList;

    public GuiCNGuiElement(ChildGuiData guiData, int id, int x, int y, int width, int height, List<GuiButton> buttons) {
        super(id, x, y, width, height, "");
        this.guiData = guiData;
        this.buttonList = buttons;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if(this.visible)
        {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            if(this.hovered)
                for(int n = buttonList.size() - 1; n > 1; n--) //Loop backwards from draw order to select the top gui.
                {
                    GuiButton button = buttonList.get(n);
                    if(button != null && button instanceof GuiCNGuiElement && !button.equals(this) && mouseX >= button.xPosition && mouseY >= button.yPosition && mouseX < button.xPosition + button.width && mouseY < button.yPosition + button.height)
                        this.hovered = false;
                    if(button != null && button.equals(this))
                        break;
                }

            int hoverState = this.getHoverState(this.hovered);
            if(guiData != null && guiData.gui != null && guiData.gui instanceof ClockworkNetworkGuiClient)
            {
                GL11.glEnable(GL11.GL_BLEND);
                if(hoverState == 2)
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
                else
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);

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
