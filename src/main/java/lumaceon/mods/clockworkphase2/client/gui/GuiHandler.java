package lumaceon.mods.clockworkphase2.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.container.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalFurnace;
import lumaceon.mods.clockworkphase2.container.ContainerTimeWell;
import lumaceon.mods.clockworkphase2.tile.clockwork.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(ClockworkPhase2.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        switch(ID)
        {
            case 0:
                return new ContainerAssemblyTable(player.inventory, world);
            case 1:
                return new ContainerTimeWell(player.inventory, (TileTimeWell) te, world);
            case 2:
                return new ContainerClockworkFurnace(player.inventory, (TileClockworkFurnace) te, world);
            case 3:
                return new ContainerTemporalFurnace(player.inventory, (TileTemporalFurnace) te, world);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        switch(ID)
        {
            case 0:
                return new GuiAssemblyTable(player.inventory, world, x, y, z);
            case 1:
                return new GuiTimeWell(player.inventory, (TileTimeWell) te, world);
            case 2:
                return new GuiClockworkFurnace(player.inventory, (TileClockworkFurnace) te, world);
            case 3:
                return new GuiTemporalFurnace(player.inventory, (TileTemporalFurnace) te, world);
            case 4:
                if(player == null || player.getHeldItem() == null || !NBTHelper.hasTag(player.getHeldItem(), NBTTags.COMPONENT_INVENTORY))
                    return null;
                ItemStack[] items = new ItemStack[5];
                ItemStack[] componentInventory = NBTHelper.INVENTORY.get(player.getHeldItem(), NBTTags.COMPONENT_INVENTORY);
                items[0] = componentInventory[2];
                items[1] = componentInventory[3];
                items[2] = componentInventory[4];
                items[3] = componentInventory[5];
                items[4] = componentInventory[6];
                return new GuiClockworkTool(items);
        }
        return null;
    }
}
