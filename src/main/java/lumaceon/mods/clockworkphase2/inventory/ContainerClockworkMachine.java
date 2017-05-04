package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerClockworkMachine extends Container
{
    protected TileClockworkMachine tile;
    private int playerInventoryX, playerInventoryY;

    private int progressTimer = 0;
    private int energy = 0;
    private int maxEnergy = 0;

    public ContainerClockworkMachine(TileClockworkMachine tile, int playerInventoryX, int playerInventoryY) {
        this.tile = tile;
        this.playerInventoryX = playerInventoryX;
        this.playerInventoryY = playerInventoryY;
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tile);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if(this.progressTimer != this.tile.getField(0))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
            }

            if(this.energy != this.tile.getField(1))
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
            }

            if(this.maxEnergy != this.tile.getField(2))
            {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
            }
        }

        this.progressTimer = this.tile.getField(0);
        this.energy = this.tile.getField(1);
        this.maxEnergy = this.tile.getField(2);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tile.setField(id, data);
    }

    public void initializeSlots(InventoryPlayer ip, boolean hideSlots)
    {
        this.inventorySlots.clear();

        if(hideSlots) //GUI is being configured, hide all the slots off-screen.
        {
            //Player Inventory
            for(int n = 0; n < 36; n++)
                this.addSlotToContainer(new Slot(ip, n, -1000000, -1000000));

            if(tile != null)
            {
                Slot[] slots = tile.slots;
                if(slots != null)
                {
                    for(Slot slot : slots)
                    {
                        Slot newSlot = new SlotNever(slot.inventory, slot.getSlotIndex(), -1000000, -1000000);
                        this.addSlotToContainer(newSlot);
                    }
                }
            }
        }
        else //GUI is in a usable state, display the slots normally.
        {
            //Player Inventory
            for(int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(ip, x, playerInventoryX + x * 18 , playerInventoryY+58));

            for(int x = 0; x < 9; x++)
                for(int y = 0; y < 3; y++)
                    this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, playerInventoryX + x * 18, playerInventoryY + y * 18));

            if(tile != null)
            {
                Slot[] slots = tile.slots;
                if(slots != null)
                    for(Slot slot : slots)
                        this.addSlotToContainer(slot);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUseableByPlayer(playerIn);
    }
}
