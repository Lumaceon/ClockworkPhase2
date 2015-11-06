package lumaceon.mods.clockworkphase2.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.container.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalFurnace;
import lumaceon.mods.clockworkphase2.container.ContainerTimeWell;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import net.minecraft.entity.player.EntityPlayer;
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
            case 4:
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
                return new GuiAssemblyTable(player.inventory, world);
            case 1:
                return new GuiTimeWell(player.inventory, (TileTimeWell) te, world);
            case 4:
                return new GuiTemporalFurnace(player.inventory, (TileTemporalFurnace) te, world);
        }
        return null;
    }
}
