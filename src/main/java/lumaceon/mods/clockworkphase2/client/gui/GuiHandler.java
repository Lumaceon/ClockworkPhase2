package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.client.gui.guidebook.GuiGuidebook;
import lumaceon.mods.clockworkphase2.clockworknetwork.gui.ContainerClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.gui.GuiClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import lumaceon.mods.clockworkphase2.container.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.container.ContainerClockworkItemStorage;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalFurnace;
import lumaceon.mods.clockworkphase2.container.ContainerTimeWell;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(ClockworkPhase2.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
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
            //SKIP 4 as it is client only.
            case 5:
                return new ContainerClockworkController(player.inventory, (TileClockworkController) te, world, 0, 0);
            case 6:
                return new ContainerClockworkItemStorage(player.inventory, (TileClockworkItemStorage) te, world);
            //SKIP 7 as well.
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
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
                ItemStack[] componentInventory = NBTHelper.INVENTORY.get(player.getHeldItem(), NBTTags.COMPONENT_INVENTORY);
                ItemStack[] items = new ItemStack[componentInventory.length - 2];
                for(int i = 0; i < items.length; i++)
                    items[i] = componentInventory[i + 2];
                return new GuiClockworkTool(items);
            case 5:
                return new GuiClockworkController(player.inventory, (TileClockworkController) te, world);
            case 6:
                return new GuiClockworkItemStorage(player.inventory, (TileClockworkItemStorage) te, world);
            case 7:
                return new GuiGuidebook(player);
        }
        return null;
    }
}
