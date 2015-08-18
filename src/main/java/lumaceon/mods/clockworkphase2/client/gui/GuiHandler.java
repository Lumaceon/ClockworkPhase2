package lumaceon.mods.clockworkphase2.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.container.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.container.ContainerPAC;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalFurnace;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import lumaceon.mods.clockworkphase2.tile.machine.TileTemporalFurnace;
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
            case 3:
                return new ContainerPAC(player.inventory, world, ExtendedPlayerProperties.get(player).playerPAC);
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
            case 2:
                return new GuiTimestreamExtractionChamber(te);
            case 3:
                return new GuiPAC(player, ExtendedPlayerProperties.get(player).playerPAC);
            case 4:
                return new GuiTemporalFurnace(player.inventory, (TileTemporalFurnace) te, world);
        }
        return null;
    }
}
