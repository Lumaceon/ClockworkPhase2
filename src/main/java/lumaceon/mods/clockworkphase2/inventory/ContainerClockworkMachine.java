package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerClockworkMachine extends Container
{
    protected TileClockworkMachine tile;

    public ContainerClockworkMachine(TileClockworkMachine tile) {
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUseableByPlayer(playerIn);
    }
}
