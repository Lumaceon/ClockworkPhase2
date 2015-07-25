package lumaceon.mods.clockworkphase2.client.gui.pane;

import lumaceon.mods.clockworkphase2.client.gui.GuiPane;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

/**
 * Used to group gui components for rendering in a more intuitive layout. Extends pane component so panes themselves can
 * be components in other panes. PANECEPTION!
 */
public class Pane extends PaneComponent
{
    protected ArrayList<PaneComponent> components = new ArrayList<PaneComponent>();

    public float spacingTop = 0;
    public float spacingBottom = 0;
    public float spacingLeft = 0;
    public float spacingRight = 0;

    public float componentSpacingTop = 0;
    public float componentSpacingBottom = 0;
    public float componentSpacingLeft = 0;
    public float componentSpacingRight = 0;

    public Pane(Minecraft mc, GuiPane guiPane) {
        super(mc, guiPane);
    }

    public void addComponent(PaneComponent component, int screenWidth, int screenHeight)
    {
        components.add(component);
        update(screenWidth, screenHeight);
    }

    public PaneComponent getComponent(int id) {
        return components.size() > id ? components.get(id) : null;
    }

    public void setPaneSpacing(float top, float bottom, float left, float right)
    {
        spacingTop = top;
        spacingBottom = bottom;
        spacingLeft = left;
        spacingRight = right;
    }

    public void setComponentSpacing(float top, float bottom, float left, float right)
    {
        componentSpacingTop = top;
        componentSpacingBottom = bottom;
        componentSpacingLeft = left;
        componentSpacingRight = right;
    }

    @Override
    public void update(int screenWidth, int screenHeight)
    {
        super.update(screenWidth, screenHeight);
        for(PaneComponent component : components)
        {
            if(component != null)
            {
                component.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                component.yCenter = this.yCenter + (spacingTop / 2) - (spacingBottom / 2);
                component.xSize = this.xSize - spacingLeft - spacingRight;
                component.ySize = this.ySize - spacingTop - spacingBottom;
                component.update(screenWidth, screenHeight);
            }
        }
    }

    @Override
    public void onMouseMoved(float mouseX, float mouseY)
    {
        for(PaneComponent component : components)
            if(component != null)
                component.onMouseMoved(mouseX, mouseY);
    }

    @Override
    public void onMouseClicked(float mouseX, float mouseY, int buttonClicked)
    {
        for(PaneComponent component : components)
            if(component != null)
                component.onMouseClicked(mouseX, mouseY, buttonClicked);
        if(mouseX > xCenter - xSize / 2.0F && mouseX < xCenter + xSize / 2.0F && mouseY > yCenter - ySize / 2.0F && mouseY < yCenter + ySize / 2.0F)
            parentGui.onComponentClicked(this, buttonClicked);
    }

    @Override
    public void draw(int screenWidth, int screenHeight, double zLevel)
    {
        for(PaneComponent component : components)
            if(component != null)
                component.draw(screenWidth, screenHeight, zLevel);
    }
}
