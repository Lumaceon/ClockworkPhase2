package lumaceon.mods.clockworkphase2.client.gui.pane;

import net.minecraft.client.Minecraft;

public class PaneButtonWheel extends Pane
{
    public int targetIndex = 0;
    public float wheelLocation = 0; //Starting at 0 and moving a component to the right/down per increment of 1.
    public int componentFullDimRadius = 3;

    public PaneButtonWheel(Minecraft mc) {
        super(mc);
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
