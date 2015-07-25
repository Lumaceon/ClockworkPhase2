package lumaceon.mods.clockworkphase2.client.gui.pane;

import lumaceon.mods.clockworkphase2.client.gui.GuiPane;
import net.minecraft.client.Minecraft;

public class PaneButtonWheel extends Pane
{
    public int targetIndex = 0;
    public float wheelLocation = 0; //Starting at 0 and moving a component to the right/down per increment of 1.
    public int componentFullDimRadius = 3;

    public PaneButtonWheel(Minecraft mc, GuiPane guiPane) {
        super(mc, guiPane);
    }

    @Override
    public void update(int screenWidth, int screenHeight)
    {
        super.update(screenWidth, screenHeight);
        wheelLocation += (targetIndex - wheelLocation) * 0.2F;
        PaneComponent component;
        double paneRatio = this.xSize / this.ySize;
        float centerDistance;
        if(paneRatio >= 1) //WIDE, left to right.
        {
            for(int n = 0; n < components.size(); n++)
            {
                component = components.get(n);
                if(component != null)
                {
                    centerDistance = 1 - Math.abs(wheelLocation - n) * (1.0F / (componentFullDimRadius + 1));
                    component.alpha = centerDistance;
                    component.xCenter = (this.xCenter + (n - wheelLocation) * this.xSize * (0.5F / (componentFullDimRadius + 1)));
                    component.yCenter = this.yCenter;

                    float internalScale = centerDistance;

                    component.xSize = (this.ySize * ((float) screenHeight / (float) (screenWidth == 0 ? 1 : screenWidth))) * internalScale;
                    component.ySize = this.ySize * internalScale;
                    if(component instanceof PaneComponentTitled)
                        ((PaneComponentTitled) component).setShouldRenderString(n == targetIndex);
                }
            }
        }
    }

    @Override
    public void onMouseClicked(float mouseX, float mouseY, int buttonClicked)
    {
        for(int n = 0; n < components.size(); n++)
        {
            PaneComponent component = components.get(n);
            if(component.mouseIsHoveringOverThis)
            {
                if(n == targetIndex)
                    component.onMouseClicked(mouseX, mouseY, buttonClicked);
                else
                    targetIndex = n;
                break;
            }
        }
        if(mouseX > xCenter - xSize / 2.0F && mouseX < xCenter + xSize / 2.0F && mouseY > yCenter - ySize / 2.0F && mouseY < yCenter + ySize / 2.0F)
            parentGui.onComponentClicked(this, buttonClicked);
    }

    public void selectNext()
    {
        if(targetIndex + 1 < components.size())
            targetIndex++;
    }

    public void selectPrevious()
    {
        if(targetIndex > 0)
            targetIndex--;
    }

    public int getSelectedIndex() {
        return targetIndex;
    }
}
