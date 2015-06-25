package lumaceon.mods.clockworkphase2.client.gui.pane;

import net.minecraft.client.Minecraft;

public class PaneBorder extends Pane
{
    public PaneBorder(Minecraft mc) {
        super(mc);
    }

    @Override
    public void addComponent(PaneComponent component, int screenWidth, int screenHeight)
    {
        components.add(component);
        update(screenWidth, screenHeight);
    }

    @Override
    public void update(int screenWidth, int screenHeight)
    {
        super.update(screenWidth, screenHeight);
        for(PaneComponent pc : components)
        {
            switch(pc.ANCHOR)
            {
                case CENTER:
                    pc.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                    pc.yCenter = this.yCenter + (spacingTop / 2) - (spacingBottom / 2);
                    pc.xSize = this.xSize - spacingLeft - spacingRight;
                    pc.ySize = this.ySize - spacingTop - spacingBottom;
                    break;
                case TOP:
                    pc.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                    pc.yCenter = this.yCenter - (this.ySize / 2) + (spacingTop / 2);
                    pc.xSize = this.xSize - spacingLeft - spacingRight;
                    pc.ySize = this.spacingTop;
                    break;
                case BOTTOM:
                    pc.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                    pc.yCenter = this.yCenter + (this.ySize / 2) - (spacingBottom / 2);
                    pc.xSize = this.xSize - spacingLeft - spacingRight;
                    pc.ySize = this.spacingBottom;
                    break;
                case LEFT:
                    pc.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                    pc.yCenter = this.yCenter - (this.xSize / 2) + (spacingLeft / 2);
                    pc.xSize = this.spacingLeft;
                    pc.ySize = this.ySize - spacingTop - spacingBottom;
                    break;
                case RIGHT:
                    pc.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                    pc.yCenter = this.yCenter + (this.xSize / 2) - (spacingRight / 2);
                    pc.xSize = this.spacingRight;
                    pc.ySize = this.ySize - spacingTop - spacingBottom;
                    break;
            }
            pc.update(screenWidth, screenHeight);
        }
    }
}
