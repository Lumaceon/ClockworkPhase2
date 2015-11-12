package lumaceon.mods.clockworkphase2.api.assembly;

import java.util.List;

public interface IAssemblableButtons extends IAssemblable
{
    /**
     * Provides a method to initialize buttons to the GUI. You should use a proxy to make sure no client-only classes
     * are imported into your universal item class (such as GuiButton).
     * @param buttonList The list of buttons from the GUI to be added on to.
     */
    public void initButtons(List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop);

    public void onButtonClicked(int buttonID, List buttonList);
}
