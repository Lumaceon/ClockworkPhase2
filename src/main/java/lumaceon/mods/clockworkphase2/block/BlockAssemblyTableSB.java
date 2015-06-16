package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTableSB;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAssemblyTableSB extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockAssemblyTableSB(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setBlockBounds(0, 0, 0, 2F/16F, 1, 1);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f0, float f1, float f2)
    {
        if(!player.isSneaking())
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileAssemblyTableSB)
            {
                TileAssemblyTableSB table = (TileAssemblyTableSB) te;
                ItemStack currentItem = player.inventory.getCurrentItem();
                ItemStack workItem = table.getWorkItem();
                if(workItem != null && workItem.getItem() instanceof IAssemblable)
                {
                    AssemblySlot[] slots = table.getAssemblySlots();
                    if(slots != null)
                    {
                        for(AssemblySlot slot : slots)
                        {
                            if(slot != null && slot.isEnabled && isIntersecting(f0, f1, f2, slot))
                            {
                                ItemStack slotItem = slot.getItemStack();
                                if(slotItem != null && currentItem == null)
                                {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, slotItem);
                                    slot.setItemStack(null);
                                    ((IAssemblable) workItem.getItem()).onComponentChange(workItem, slots);
                                    ((IAssemblable) workItem.getItem()).saveComponentInventory(workItem, slots);
                                    return true;
                                }
                                else if(slotItem == null && currentItem != null && slot.isItemValid(currentItem))
                                {
                                    slot.setItemStack(currentItem);
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                    ((IAssemblable) workItem.getItem()).onComponentChange(workItem, slots);
                                    ((IAssemblable) workItem.getItem()).saveComponentInventory(workItem, slots);
                                    return true;
                                }
                            }
                        }
                    }
                }
                if(currentItem != null && currentItem.getItem() instanceof IAssemblable)
                {
                    if(workItem == null)
                    {
                        ((TileAssemblyTableSB) te).setWorkItem(currentItem);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }
                else if(currentItem == null)
                {
                    if(table.getWorkItem() != null)
                    {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, table.getWorkItem());
                        table.setWorkItem(null);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAssemblyTableSB();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    private boolean isIntersecting(float x, float y, float z, AssemblySlot slot)
    {
        float pixel = 1F/16F;
        if(z > (1 - slot.centerX) + slot.sizeX * 0.5 * pixel || z < (1 - slot.centerX) - slot.sizeX * 0.5 * pixel)
            return false;
        if(y > (1 - slot.centerY) + slot.sizeY * 0.5 * pixel || y < (1 - slot.centerY) - slot.sizeY * 0.5 * pixel)
            return false;
        return true;
    }
}
