package lumaceon.mods.clockworkphase2.container;

import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.inventory.InventoryUpdated;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerPAC extends Container
{
    protected World world;
    protected EntityPAC pac;

    protected InventoryUpdated mainInventory = new InventoryUpdated(this, 1, 1);

    public ContainerPAC(InventoryPlayer ip, World world, EntityPAC pac)
    {
        this.world = world;
        this.pac = pac;

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 48 + x * 18 , 232));
        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 48 + x * 18, 174 + y * 18));

        this.addSlotToContainer(new Slot(mainInventory, 0, 120, 76));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return pac.owner.equals(player) && ExtendedPlayerProperties.get(player).playerPAC.equals(pac);
    }
}
