package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockTimezoneFluidExporter extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTimezoneFluidExporter(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null && te instanceof TileTimezoneFluidExporter)
            {
                ItemStack container = player.getHeldItem();
                if(container == null || !FluidContainerRegistry.isEmptyContainer(container)) //Holding no item or a not-a-bucket.
                {
                    String fluidName = ((TileTimezoneFluidExporter) te).setNextTargetFluid();
                    if(fluidName.equals(""))
                        fluidName = "(No Liquids Found)";

                    if(!world.isRemote)
                        player.addChatComponentMessage(new ChatComponentText("Exporting: " + fluidName));
                    return true;
                }
                else //Holding an empty container.
                {
                    FluidStack fluidDrained = ((TileTimezoneFluidExporter) te).drain(side, 1000000, false);
                    ItemStack filledItem = FluidContainerRegistry.fillFluidContainer(fluidDrained, container);
                    if(filledItem != null)
                    {
                        if(container.stackSize == 1)
                        {
                            ((TileTimezoneFluidExporter) te).drain(side, FluidContainerRegistry.getContainerCapacity(fluidDrained, filledItem), true);
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, filledItem);
                            return true;
                        }
                        else
                        {
                            for(int n = 0; n < 36; n++)
                            {
                                ItemStack currentItem = player.inventory.getStackInSlot(n);
                                if(currentItem == null)
                                {
                                    ((TileTimezoneFluidExporter) te).drain(side, FluidContainerRegistry.getContainerCapacity(fluidDrained, filledItem), true);
                                    player.inventory.setInventorySlotContents(n, filledItem);
                                    --container.stackSize;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTimezoneFluidExporter();
    }

    @Override
    public int getRenderType() {
        return 2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
