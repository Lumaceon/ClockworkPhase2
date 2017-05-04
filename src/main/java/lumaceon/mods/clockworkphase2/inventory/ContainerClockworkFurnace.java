package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerClockworkFurnace extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkFurnace tile;

    public ContainerClockworkFurnace(EntityPlayer player, TileClockworkFurnace tile) {
        super(tile, 8, 83);

        this.player = player;
        this.tile = tile;

        initializeSlots(player.inventory, false);
    }
}
