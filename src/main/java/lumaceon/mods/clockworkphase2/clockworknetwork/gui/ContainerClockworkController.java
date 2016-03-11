package lumaceon.mods.clockworkphase2.clockworknetwork.gui;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotClockworkControllerGhost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;

public class ContainerClockworkController extends Container
{
    public World world;
    public TileClockworkController te;
    public ClockworkNetwork clockworkNetwork;

    public ContainerClockworkController(InventoryPlayer ip, TileClockworkController te, World world, int playerInventoryX, int playerInventoryY)
    {
        this.world = world;
        this.te = te;
        this.clockworkNetwork = te.getClockworkNetwork();

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, playerInventoryX + x * 18 , playerInventoryY));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, playerInventoryX + x * 18, playerInventoryY + y * 18));

        if(clockworkNetwork != null)
        {
            Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
            if(!machines.isEmpty())
                for(IClockworkNetworkMachine machine : machines)
                    if(machine != null)
                    {
                        ClockworkNetworkContainer container = machine.getGui();
                        if(container == null)
                            continue;

                        Slot[] slots = container.getSlots();
                        if(slots != null)
                            for(Slot slot : slots)
                                if(slot != null)
                                    this.addSlotToContainer(slot);
                    }
        }
    }

    @Override
    public void onCraftGuiOpened(ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        int startingIndex = 0;
        if(clockworkNetwork != null)
        {
            Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
            if(!machines.isEmpty())
                for (IClockworkNetworkMachine machine : machines)
                    if(machine != null)
                    {
                        ClockworkNetworkContainer container = machine.getGui();
                        if(container == null)
                            continue;
                        container.initialCraftingUpdate(crafting, startingIndex, this);
                        startingIndex += container.getUpdateCount();
                    }

        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        ICrafting crafting;
        int startingIndex = 0;
        if(clockworkNetwork != null)
        {
            Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
            if(!machines.isEmpty())
                for(IClockworkNetworkMachine machine : machines)
                    if(machine != null)
                    {
                        ClockworkNetworkContainer container = machine.getGui();
                        if(container == null)
                            continue;

                        for(Object i : this.crafters)
                            if(i instanceof ICrafting)
                            {
                                crafting = (ICrafting) i;
                                container.detectAndSendChanges(crafting, startingIndex, this);
                            }
                        startingIndex += container.getUpdateCount();
                    }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        int startingIndex = 0;
        if(clockworkNetwork != null)
        {
            Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
            if(!machines.isEmpty())
                for(IClockworkNetworkMachine machine : machines)
                    if(machine != null)
                    {
                        ClockworkNetworkContainer container = machine.getGui();
                        if(container == null)
                            continue;

                        if(startingIndex >= id && startingIndex <= startingIndex + container.getUpdateCount() - 1)
                            container.updateProgressBar(id - startingIndex, value);

                        startingIndex += container.getUpdateCount();
                    }
        }
    }

    public void clearSlots() {
        this.inventorySlots.clear();
    }

    public void reinitializeSlots(InventoryPlayer ip, ArrayList<ChildGuiData> childGUIs, int playerInventoryX, int playerInventoryY, int guiSizeX, int guiSizeY, boolean hideSlots)
    {
        clearSlots();

        if(hideSlots) //GUI is being configured, hide all the slots off-screen.
        {
            //Player Inventory
            for(int n = 0; n < 36; n++)
                this.addSlotToContainer(new Slot(ip, n, -10000, -10000));

            if(clockworkNetwork != null)
            {
                Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
                if(!machines.isEmpty())
                    for(IClockworkNetworkMachine machine : machines)
                        if(machine != null)
                        {
                            ClockworkNetworkContainer container = machine.getGui();
                            if(container == null)
                                continue;

                            Slot[] slots = container.getSlots();
                            if(slots != null)
                                for(Slot slot : slots)
                                    if(slot != null)
                                    {
                                        Slot newSlot = new SlotClockworkControllerGhost(slot.inventory, slot.getSlotIndex(), -10000, -10000, slot);
                                        this.addSlotToContainer(newSlot);
                                    }
                        }
            }
        }
        else //GUI is in a usable state, display the slots normally.
        {
            //Player Inventory
            for(int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(ip, x, playerInventoryX+6 + x * 18 , playerInventoryY+63));

            for(int x = 0; x < 9; x++)
                for(int y = 0; y < 3; y++)
                    this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, playerInventoryX+6 + x * 18, playerInventoryY+5 + y * 18));

            if(clockworkNetwork != null)
            {
                Collection<IClockworkNetworkMachine> machines = clockworkNetwork.getMachines().values();
                if(!machines.isEmpty())
                    for(IClockworkNetworkMachine machine : machines)
                        if(machine != null)
                        {
                            ClockworkNetworkContainer container = machine.getGui();
                            if(container == null)
                                continue;

                            ChildGuiData child = null;
                            for(ChildGuiData child1 : childGUIs)
                                if(child1 != null && child1.gui != null && child1.machine != null && child1.machine.equals(machine))
                                    child = child1;

                            Slot[] slots = container.getSlots();
                            if(slots != null)
                                for(Slot slot : slots)
                                    if(slot != null)
                                    {
                                        Slot newSlot;
                                        if(child == null)
                                            newSlot = new SlotClockworkControllerGhost(slot.inventory, slot.getSlotIndex(), -10000, -10000, slot);
                                        else
                                            newSlot = new SlotClockworkControllerGhost(slot.inventory, slot.getSlotIndex(), child.getX(guiSizeX) + slot.xDisplayPosition, child.getY(guiSizeY) + slot.yDisplayPosition, slot);
                                        this.addSlotToContainer(newSlot);
                                    }
                        }
            }
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        return null;
    }
}
