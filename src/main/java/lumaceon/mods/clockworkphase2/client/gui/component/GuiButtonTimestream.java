package lumaceon.mods.clockworkphase2.client.gui.component;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonTimestream extends GuiButton
{
    public ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, "textures/glyph/base.png");
    public float xLoc; //0.0 is left, 1.0 is right, 0.5 is middle.
    public float yLoc; //0.0 is top, 1.0 is bottom, 0.5 is middle.

    public GuiButtonTimestream(int id, float x, float y)
    {
        super(id, 0, 0, "");
        xLoc = x;
        yLoc = y;
    }

    @Override
    public void drawButton(Minecraft minecraft, int p_146112_2_, int p_146112_3_)
    {
        if(this.visible)
        {
            minecraft.getTextureManager().bindTexture(loc);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean mouseIsOverThis =
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
        }
    }
}
