package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import lumaceon.mods.clockworkphase2.util.NBTTags;
import lumaceon.mods.clockworkphase2.client.gui.guidebook.GuiGuidebook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
            case 4:
                if(player == null)
                    return false;
                ItemStack stack = player.inventory.getCurrentItem();
                if(stack == null)
                    return null;

                IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                if(inventory == null)
                    return null;

                ItemStack[] items = new ItemStack[inventory.getSlots() - 3];
                for(int i = 3; i < inventory.getSlots(); i++)
                    items[i-3] = inventory.getStackInSlot(i);

                return new GuiTemporalExcavatorUpgrades(items);
            case 7:
                return new GuiGuidebook(player);
        }
        return null;
    }
}
