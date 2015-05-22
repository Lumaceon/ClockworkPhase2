package lumaceon.mods.clockworkphase2.item.construct;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.client.gui.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

public class ItemClockworkTool extends ItemTool implements IAssemblable
{
    public ItemClockworkTool(float var1, ToolMaterial toolMaterial, Set set, String unlocalizedName)
    {
        super(var1, toolMaterial, set);
        this.setMaxStackSize(1);
        this.setMaxDamage(20);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container)
    {
        InventoryAssemblyComponents inventory = new InventoryAssemblyComponents(container, 2, 1);
        AssemblyHelper.CREATE_COMPONENT_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
            {
                    new SlotItemSpecific(inventory, 0, 0, 0, ModItems.mainspring),
                    new SlotItemSpecific(inventory, 1, 0, 18, ModItems.clockwork)
            };
    }

    @Override
    public void onComponentChange(IAssemblyContainer container)
    {
        AssemblyHelper.COMPONENT_CHANGE.assembleClockworkConstruct(container, 0, 1);
    }

    @Override
    public void saveComponentInventory(IAssemblyContainer container)
    {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveNewComponentInventory(container);
    }

    @Override
    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop) {}

    @Override
    public void onButtonActivated(int buttonID, List buttonList) {}

    @Override
    public ResourceLocation getBackgroundTexture(IAssemblyContainer container)
    {
        return Textures.GUI.DEFAULT_ASSEMBLY_TABLE;
    }
}
