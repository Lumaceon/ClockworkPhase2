package lumaceon.mods.clockworkphase2.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.container.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalizer;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTemporalizer;
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
                return new ContainerTemporalizer(player.inventory, (TileTemporalizer) te, world);
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
                return new GuiTemporalizer(player.inventory, (TileTemporalizer) te, world);
        }
        return null;
    }
}
