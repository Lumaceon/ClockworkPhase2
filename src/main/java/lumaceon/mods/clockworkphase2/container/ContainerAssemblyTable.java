package lumaceon.mods.clockworkphase2.container;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.inventory.InventoryUpdated;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ContainerAssemblyTable extends Container implements IAssemblyContainer
{
    private World world;

    private InventoryUpdated mainInventory = new InventoryUpdated(this, 1, 1);
    private InventoryAssemblyComponents componentInventory;
    private ItemStack previousMainStack;
    public List buttonList = null;
    public int guiLeft = -1;
    public int guiTop = -1;

    public ContainerAssemblyTable(InventoryPlayer ip, World world)
    {
        this.world = world;

        for(int x = 0; x < 9; x++)
        {
            this.addSlotToContainer(new Slot(ip, x, 48 + x * 18 , 232));
        }

        for(int x = 0; x < 9; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 48 + x * 18, 174 + y * 18));
            }
        }

        this.addSlotToContainer(new Slot(mainInventory, 0, 120, 76));
    }

    public void onCraftMatrixChanged(IInventory inventory)
    {
        super.onCraftMatrixChanged(inventory);
        ItemStack item = mainInventory.getStackInSlot(0);
        boolean mainItemChanged = item != previousMainStack;

        //Handle changes to the main item.
        if(mainItemChanged)
        {
            //Remove the component inventory if the change is removal of the main item.
            if((item == null || !(item.getItem() instanceof IAssemblable)) && componentInventory != null)
            {
                cleanContainerAndGUI();
            }

            //Set up the new component inventory if the main item has changed from a separate main item.
            if(item != null && componentInventory != null && item.getItem() instanceof IAssemblable)
            {
                cleanContainerAndGUI();

                setupNewContainerAndGUI(item);
            }

            //Set up a component inventory if the main item has been added from the default screen.
            if(item != null && componentInventory == null && item.getItem() instanceof IAssemblable)
            {
                setupNewContainerAndGUI(item);
            }
        }

        previousMainStack = item;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if(!this.world.isRemote)
        {
            ItemStack itemstack = this.mainInventory.getStackInSlotOnClosing(0);
            if(itemstack != null)
            {
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return mainInventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        return null;
    }

    @Override
    public void onGUIInitialize(List buttonList, int guiLeft, int guiTop)
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        this.buttonList = buttonList;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        if(item != null && item.getItem() instanceof IAssemblable && buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            ((IAssemblable) item.getItem()).initButtons(buttonList, this, guiLeft, guiTop);
        }
    }

    private void cleanContainerAndGUI()
    {
        componentInventory = null;
        while (this.inventorySlots.size() > 37)
        {
            this.inventorySlots.remove(this.inventorySlots.size() - 1); //Remove the slot from container.
            this.inventoryItemStacks.remove(this.inventoryItemStacks.size() - 1); //Remove the itemstack from container.
        }

        if(buttonList != null)
        {
            buttonList.clear();
        }
    }

    private void setupNewContainerAndGUI(ItemStack item)
    {
        IAssemblable template = (IAssemblable) item.getItem();
        componentInventory = template.createComponentInventory(this);
        template.saveComponentInventory(this);
        Slot[] slots = template.getContainerSlots(componentInventory);
        for(Slot slot : slots)
        {
            this.addSlotToContainer(slot);
        }

        if(buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            template.initButtons(buttonList, this, guiLeft, guiTop);
        }
    }

    @Override
    public void onComponentChanged()
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        if(item != null && item.getItem() instanceof IAssemblable)
        {
            ((IAssemblable) item.getItem()).onComponentChange(this);
            if(!world.isRemote)
                ((IAssemblable) item.getItem()).saveComponentInventory(this);
        }
    }

    @Override
    public InventoryAssemblyComponents getComponentInventory()
    {
        return this.componentInventory;
    }

    @Override
    public IInventory getMainInventory()
    {
        return this.mainInventory;
    }
}
