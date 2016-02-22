package lumaceon.mods.clockworkphase2.container;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerClockworkFurnace extends Container
{
    private World world;
    private TileClockworkNetworkMachine te;

    private int previousCookTime;

    public ContainerClockworkFurnace(InventoryPlayer ip, TileClockworkFurnace te, World world)
    {
        this.world = world;
        this.te = te;

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 70 + x * 18 , 205));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 70 + x * 18, 147 + y * 18));

        this.addSlotToContainer(new SlotInventoryValid(te, 0, 70, 72));
        this.addSlotToContainer(new SlotInventoryValid(te, 1, 214, 72));
    }

    @Override
    public void onCraftGuiOpened(ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, 0, te.getProgress());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        ICrafting crafting;
        for(Object i : this.crafters)
        {
            if(i instanceof ICrafting)
            {
                crafting = (ICrafting) i;
                if(this.previousCookTime != te.getProgress())
                    crafting.sendProgressBarUpdate(this, 0, te.getProgress());
            }
        }

        this.previousCookTime = te.getProgress();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        switch(id)
        {
            case 0:
                te.setProgress(value);
                break;
        }
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
