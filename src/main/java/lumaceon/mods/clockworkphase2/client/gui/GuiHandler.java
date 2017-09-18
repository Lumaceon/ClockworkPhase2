package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkAlloyFurnace;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkCrusher;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkCrystallizer;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkFurnace;
import lumaceon.mods.clockworkphase2.client.gui.machine.lifeform.GuiLifeformConstructor;
import lumaceon.mods.clockworkphase2.client.gui.machine.lifeform.GuiLifeformDeconstructor;
import lumaceon.mods.clockworkphase2.inventory.*;
import lumaceon.mods.clockworkphase2.inventory.lifeform.ContainerLifeformConstructor;
import lumaceon.mods.clockworkphase2.inventory.lifeform.ContainerLifeformDeconstructor;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkAlloyFurnace;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrusher;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrystallizer;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformDeconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
        else if(ID == GUIs.CLOCKWORK_ALLOY_FURNACE.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkAlloyFurnace)
                return new ContainerClockworkAlloyFurnace(player, (TileClockworkAlloyFurnace) te);
        }
        else if(ID == GUIs.CLOCKWORK_CRUSHER.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkCrusher)
                return new ContainerClockworkCrusher(player, (TileClockworkCrusher) te);
        }
        else if(ID == GUIs.CLOCKWORK_CRYSTALLIZER.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkCrystallizer)
                return new ContainerClockworkCrystallizer(player, (TileClockworkCrystallizer) te);
        }
        else if(ID == GUIs.LIFEFORM_CONSTRUCTOR.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileLifeformConstructor)
                return new ContainerLifeformConstructor(player, (TileLifeformConstructor) te);
        }
        else if(ID == GUIs.LIFEFORM_DECONSTRUCTOR.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileLifeformDeconstructor)
                return new ContainerLifeformDeconstructor(player, (TileLifeformDeconstructor) te);
        }
        else if(ID == GUIs.TEMPORAL_FISHING_ROD.ordinal())
        {
            ItemStack stackInHand = player.getHeldItem(EnumHand.MAIN_HAND);
            if(!stackInHand.isEmpty() && stackInHand.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
            {
                return new ContainerTemporalFishingRod(player, stackInHand);
            }
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
            if(player == null)
                return false;

            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            if(stack.isEmpty())
                return null;

            IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if(inventory == null)
                return null;

            ItemStack[] items = new ItemStack[inventory.getSlots() - 3];
            for(int i = 3; i < inventory.getSlots(); i++)
                items[i - 3] = inventory.getStackInSlot(i);

            return new GuiTemporalExcavatorUpgrades(items);
        }
        else if(ID == GUIs.CLOCKWORK_FURNACE.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkFurnace)
                return new GuiClockworkFurnace(player, (TileClockworkFurnace) te);
        }
        else if(ID == GUIs.CLOCKWORK_ALLOY_FURNACE.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkAlloyFurnace)
                return new GuiClockworkAlloyFurnace(player, (TileClockworkAlloyFurnace) te);
        }
        else if(ID == GUIs.CLOCKWORK_CRUSHER.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkCrusher)
                return new GuiClockworkCrusher(player, (TileClockworkCrusher) te);
        }
        else if(ID == GUIs.CLOCKWORK_CRYSTALLIZER.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileClockworkCrystallizer)
                return new GuiClockworkCrystallizer(player, (TileClockworkCrystallizer) te);
        }
        else if(ID == GUIs.LIFEFORM_CONSTRUCTOR.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileLifeformConstructor)
                return new GuiLifeformConstructor(player, (TileLifeformConstructor) te);
        }
        else if(ID == GUIs.LIFEFORM_DECONSTRUCTOR.ordinal())
        {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if(te != null && te instanceof TileLifeformDeconstructor)
                return new GuiLifeformDeconstructor(player, (TileLifeformDeconstructor) te);
        }
        else if(ID == GUIs.TEMPORAL_FISHING_ROD.ordinal())
        {
            ItemStack stackInHand = player.getHeldItem(EnumHand.MAIN_HAND);
            if(!stackInHand.isEmpty() && stackInHand.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN))
            {
                return new GuiTemporalFishingRod(player, stackInHand);
            }
        }
        return null;
    }
}
