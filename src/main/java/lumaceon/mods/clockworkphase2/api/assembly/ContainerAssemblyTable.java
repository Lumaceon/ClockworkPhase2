package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ContainerAssemblyTable extends Container
{
    public InventoryUpdated mainInventory = new InventoryUpdated(this, 1, 1);
    public InventoryAssemblyTableComponents componentInventory;
    public World world;
    public EntityPlayer player;
    private ItemStack previousMainStack;
    public List buttonList = null;
    public int guiLeft = -1;
    public int guiTop = -1;

    public ContainerAssemblyTable(InventoryPlayer ip, World world)
    {
        this.world = world;
        this.player = ip.player;

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 70 + x * 18 , 205));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 70 + x * 18, 147 + y * 18));

        this.addSlotToContainer(new SlotAssemblable(mainInventory, 0, 142, 68));
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
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
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);

        if(!this.world.isRemote)
        {
            ItemStack itemstack = this.mainInventory.getStackInSlot(0);
            if(itemstack != null)
            {
                p_75134_1_.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return mainInventory.isUseableByPlayer(p_75145_1_);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        return null;
    }

    public void onCraftMatrixComponentChanged()
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        if(item != null && item.getItem() instanceof IAssemblable)
        {
            ((IAssemblable) item.getItem()).onInventoryChange(this);
            if(!world.isRemote)
                ((IAssemblable) item.getItem()).saveComponentInventory(this);
        }
    }

    public void onGUIResize()
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        if(item != null && item.getItem() instanceof IAssemblableButtons && buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            ((IAssemblableButtons) item.getItem()).initButtons(buttonList, this, guiLeft, guiTop);
        }
    }

    private void setupNewContainerAndGUI(ItemStack item)
    {
        IAssemblable constructGUI = (IAssemblable) item.getItem();
        componentInventory = constructGUI.getGUIInventory(this);
        constructGUI.saveComponentInventory(this);
        Slot[] slots = constructGUI.getContainerSlots(componentInventory);
        for(Slot slot : slots)
        {
            this.addSlotToContainer(slot);
        }

        if(item.getItem() instanceof IAssemblableButtons && buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            IAssemblableButtons buttonGUI = (IAssemblableButtons) item.getItem();

            buttonGUI.initButtons(buttonList, this, guiLeft, guiTop);
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
}
