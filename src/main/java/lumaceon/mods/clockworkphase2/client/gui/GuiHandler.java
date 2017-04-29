package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkFurnace;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkMachine;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
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
        if(ID == GUIs.ASSEMBLY_TABLE.ordinal())
        {
            return new ContainerAssemblyTable(player.inventory, world);
        }
        else if(ID == GUIs.CLOCKWORK_FURNACE.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkFurnace)
                return new ContainerClockworkFurnace(player, (TileClockworkFurnace) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GUIs.ASSEMBLY_TABLE.ordinal())
        {
            return new GuiAssemblyTable(player.inventory, world, x, y, z);
        }
        else if(ID == GUIs.TEMPORAL_EXCAVATOR.ordinal())
        {
            if (player == null)
                return false;
            ItemStack stack = player.inventory.getCurrentItem();
            if (stack == null)
                return null;

            IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if (inventory == null)
                return null;

            ItemStack[] items = new ItemStack[inventory.getSlots() - 3];
            for (int i = 3; i < inventory.getSlots(); i++)
                items[i - 3] = inventory.getStackInSlot(i);

            return new GuiTemporalExcavatorUpgrades(items);
        }
        else if(ID == GUIs.GUIDEBOOK.ordinal())
        {
            return new GuiGuidebook(player);
        }
        else if(ID == GUIs.CLOCKWORK_FURNACE.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkFurnace)
                return new GuiClockworkFurnace(player, (TileClockworkFurnace) te);
        }
        return null;
    }
}
