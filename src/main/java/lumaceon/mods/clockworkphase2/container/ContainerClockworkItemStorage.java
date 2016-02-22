package lumaceon.mods.clockworkphase2.container;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerClockworkItemStorage extends Container
{
    private InventoryPlayer ip;
    private TileClockworkItemStorage te;
    private World world;

    public ContainerClockworkItemStorage(InventoryPlayer ip, TileClockworkItemStorage te, World world)
    {
        this.ip = ip;
        this.te = te;
        this.world = world;

        setupSlots(te.xSlots, te.ySlots);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.isUseableByPlayer(playerIn);
    }

    public void setupSlots(int newX, int newY)
    {
        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 70 + x * 18 , 205));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 70 + x * 18, 147 + y * 18));

        for(int x = 0; x < newX; x++)
            for(int y = 0; y < newY; y++)
                this.addSlotToContainer(new Slot(te, x + (y * newX), x * 18, y * 18));
    }

    public void resizeSlotsInContainer(int newX, int newY)
    {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        setupSlots(newX, newY);
    }
}
