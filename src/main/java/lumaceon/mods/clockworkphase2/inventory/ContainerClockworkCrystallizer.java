package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrystallizer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerClockworkCrystallizer extends ContainerClockworkMachine
{
    EntityPlayer player;
    TileClockworkCrystallizer tile;

    public ContainerClockworkCrystallizer(EntityPlayer player, TileClockworkCrystallizer tile)
   {
        super(tile, 8, 83);

        this.tile = tile;
        this.player = player;

        initializeSlots(player.inventory, false);
    }
}
