package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.modulations.TimezoneModulationTank;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidImporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.List;

public class BlockTimezoneFluidImporter extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneFluidImporter(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack container = player.getHeldItem();
            if(container != null && FluidContainerRegistry.isFilledContainer(player.getHeldItem()))
            {
                TileEntity te = world.getTileEntity(pos);
                if(te != null && te instanceof TileTimezoneFluidImporter)
                {
                    List<TimezoneModulation> tanks = ((TileTimezoneFluidImporter) te).getTanks();
                    if(tanks != null && !tanks.isEmpty())
                    {
                        TimezoneModulationTank firstEmptyTank = null;
                        for(TimezoneModulation t : tanks)
                            if(t != null && t instanceof TimezoneModulationTank)
                            {
                                TimezoneModulationTank tank = (TimezoneModulationTank) t;
                                if(tank.getFluidStack() == null || tank.getFluidStack().amount <= 0)
                                {
                                    if(firstEmptyTank == null)
                                        firstEmptyTank = tank; //If we haven't found an empty tank yet, set it here.
                                    continue; //Regardless, this is empty, so continue.
                                }

                                Fluid fluid = tank.getFluid();
                                if(fluid != null && fluid.equals(FluidContainerRegistry.getFluidForFilledItem(container).getFluid())) //TODO is this oreDictionary compatible?
                                {
                                    tank.fill(FluidContainerRegistry.getFluidForFilledItem(container), true);
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(container));
                                    return true;
                                }
                            }

                        if(firstEmptyTank != null) //We didn't find a tank with this liquid, so go for an empty one.
                        {
                            firstEmptyTank.fill(FluidContainerRegistry.getFluidForFilledItem(container), true);
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(container));
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTimezoneFluidImporter();
    }
}
