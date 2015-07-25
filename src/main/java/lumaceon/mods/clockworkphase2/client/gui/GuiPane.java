package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.client.gui.pane.Pane;
import lumaceon.mods.clockworkphase2.client.gui.pane.PaneComponent;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public abstract class GuiPane extends GuiScreen
{
    public Pane basePane = new Pane(mc, this);

    private int lastMouseX, lastMouseY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        if(mouseX != lastMouseX || mouseY != lastMouseY)
            basePane.onMouseMoved((float) mouseX/width, (float) mouseY/height);
        basePane.draw(width, height, zLevel);
        GL11.glPopMatrix();

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        basePane.update(width, height);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int buttonClicked)
    {
        super.mouseClicked(mouseX, mouseY, buttonClicked);
        basePane.onMouseClicked((float) mouseX/width, (float) mouseY/height, buttonClicked);
    }

    /**
     * Called from each PaneComponent when it receives a click. Note that sometimes this method will be ignored in favor
     * of the pane's action itself (see PaneButtonWheel).
     * @param component The PaneComponent that was clicked on.
     */
    public abstract void onComponentClicked(PaneComponent component, int buttonClicked);
}
