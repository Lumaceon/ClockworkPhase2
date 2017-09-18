package lumaceon.mods.clockworkphase2.inventory.lifeform;

import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkMachine;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLifeformDeconstructor extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkMachine tile;

    public ContainerLifeformDeconstructor(EntityPlayer player, TileClockworkMachine tile)
    {
        super(tile, 28, 103);

        this.tile = tile;
        this.player = player;

        initializeSlots(player.inventory, false);
    }
}
