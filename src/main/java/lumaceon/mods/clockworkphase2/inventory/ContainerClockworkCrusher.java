package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrusher;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerClockworkCrusher extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkMachine tile;

    public ContainerClockworkCrusher(EntityPlayer player, TileClockworkCrusher tile)
    {
        super(tile, 8, 83);

        this.tile = tile;
        this.player = player;

        initializeSlots(player.inventory, false);
    }
}
