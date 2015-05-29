package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;

import java.util.List;

public interface IProxy
{
    public void registerTESR();
    public void registerModels();
    public void registerKeybindings();
    public void initSideHandlers();
    public void initButtons(int id, List buttonList, IAssemblyContainer container, int guiLeft, int guiTop);
}
