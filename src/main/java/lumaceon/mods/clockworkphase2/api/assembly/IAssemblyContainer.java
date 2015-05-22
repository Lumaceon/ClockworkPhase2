package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.inventory.IInventory;

import java.util.List;

public interface IAssemblyContainer
{
    public void onComponentChanged();

    public InventoryAssemblyComponents getComponentInventory();

    public IInventory getMainInventory();

    public void onGUIInitialize(List buttonList, int guiLeft, int guiTop);
}
