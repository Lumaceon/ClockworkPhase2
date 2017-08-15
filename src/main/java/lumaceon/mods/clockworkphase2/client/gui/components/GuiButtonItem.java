package lumaceon.mods.clockworkphase2.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiButtonItem extends GuiButton
{
    public boolean active;
    public ItemStack item;
    public RenderItem itemRender;
    public FontRenderer fontRenderer;
    public Minecraft mc;

    public GuiButtonItem(ItemStack is, int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_, RenderItem renderItem, FontRenderer fontRenderer, boolean active) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, 20, 20, p_i1020_4_);
        this.item = is;
        this.itemRender = renderItem;
        this.mc = Minecraft.getMinecraft();
        this.active = active;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(this.visible)
        {
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if(packedFGColour != 0)
                l = packedFGColour;
            else if(!this.enabled)
                l = 10526880;
            else if(this.hovered)
                l = 16777120;

            if(item != null)
                this.drawItemStack(item, this.x + 2, this.y + 2, this.displayString);
        }
    }

    private void drawItemStack(ItemStack is, int x, int y, String name)
    {
        if(!active)
            GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        float zLevelOrigin = this.zLevel;
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(is, x, y);
        this.zLevel = zLevelOrigin;
        itemRender.zLevel = 0.0F;
    }
}
