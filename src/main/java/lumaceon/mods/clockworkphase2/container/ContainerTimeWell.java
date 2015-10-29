package lumaceon.mods.clockworkphase2.container;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerTimeWell extends Container
{
    private TileTimeWell te;

    public ContainerTimeWell(InventoryPlayer ip, TileTimeWell te, World world)
    {
        this.te = te;

        for(int x = 0; x < 9; x++)
           this.addSlotToContainer(new Slot(ip, x, 70 + x * 18 , 205));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 70 + x * 18, 147 + y * 18));

        this.addSlotToContainer(new SlotInventoryValid(te, 0, 106, 81));
        this.addSlotToContainer(new SlotInventoryValid(te, 1, 178, 81));
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
