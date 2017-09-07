package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonMainspring;
import lumaceon.mods.clockworkphase2.inventory.ContainerAssemblyTableClient;

import java.util.List;

public class ButtonInitializer
{
    public static void initializeMainspringButtons(List buttonList, ContainerAssemblyTableClient container, int guiLeft, int guiTop) {
        buttonList.add(new GuiButtonMainspring(0, guiLeft + 90, guiTop + 115, 120, 20, container));
    }
}
