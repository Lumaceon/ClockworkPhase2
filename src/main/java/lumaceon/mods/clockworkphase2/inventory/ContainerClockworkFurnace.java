package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerClockworkFurnace extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkFurnace tile;

    public ContainerClockworkFurnace(EntityPlayer player, TileClockworkFurnace tile) {
        super(tile);

        this.player = player;
        this.tile = tile;
        InventoryPlayer ip = player.inventory;

        for(int y = 0; y < 3; y++)
            for(int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 8 + x * 18, 84 + y * 18));

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 8 + x * 18 , 142));

        this.addSlotToContainer(new Slot(tile, 0, 56, 25));
        this.addSlotToContainer(new Slot(tile, 1, 116, 34));
    }
}
