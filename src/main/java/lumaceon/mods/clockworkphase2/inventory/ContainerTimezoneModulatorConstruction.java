package lumaceon.mods.clockworkphase2.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class ContainerTimezoneModulatorConstruction extends Container
{
    TileEntity tile;

    public ContainerTimezoneModulatorConstruction(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
