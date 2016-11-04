package lumaceon.mods.clockworkphase2.api.guidebook.renderers;

import net.minecraft.item.ItemStack;

/**
 * Can be used by the guidebook to render custom recipes and similar.
 */
public class GuidebookCustomRender
{
    public String key;
    public int yPadding = 100;

    public GuidebookCustomRender(String key) {
        this.key = key;
    }

    /**
     * Called to draw this page. Drawing is relative the the top-left of the page plus yPadding, so "0,0" will be
     * relative to whatever page this is drawing in. yPadding is offset so things like titles and images translate these
     * downward. If you want your rendering to center between the top and bottom, you'll need to take this into account.
     * @param mouseX The relative x coordinate of the mouse to the top-left of the page.
     * @param mouseY The relative y coordinate of the mouse to the top-left of the page plus yPadding.
     * @param yPadding A value representing how far down the page this should draw. Used to offset for titles and etc.
     * @return An ItemStack representing a hovered item. This will (probably) display the item's tooltip.
     */
    public ItemStack draw(int mouseX, int mouseY, int yPadding) {
        return null;
    }

    /**
     * Gets the distance objects under this should be rendered from it's top. Can be safely ignored if you only intend
     * to render one custom object in a page.
     * @param currentYPadding The yPadding of all objects above this.
     * @return The yPadding of all objects above this plus this.
     */
    public int getAdditiveYPadding(int currentYPadding) {
        return currentYPadding + this.yPadding;
    }
}
