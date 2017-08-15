package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkAlloyFurnace;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerClockworkAlloyFurnace extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkAlloyFurnace tile;

    public ContainerClockworkAlloyFurnace(EntityPlayer player, TileClockworkAlloyFurnace tile) {
        super(tile, 8, 83);

        this.player = player;
        this.tile = tile;

        initializeSlots(player.inventory, false);
    }
}
