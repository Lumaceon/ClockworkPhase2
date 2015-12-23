package lumaceon.mods.clockworkphase2.client.gui.components;

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
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if(this.visible)
        {
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.field_146123_n);
            if(gui != null && gui instanceof ClockworkNetworkGuiClient)
            {
                if(hoverState == 2)
                    GL11.glColor4f(1.5F, 1.5F, 1.5F, 1.0F);
                else if(hoverState == 1)
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                else
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                ((ClockworkNetworkGuiClient) gui).drawBackground(this.xPosition, this.yPosition, this.zLevel);
                ((ClockworkNetworkGuiClient) gui).drawForeground(this.xPosition, this.yPosition, this.zLevel);
            }
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
        }
    }
}
