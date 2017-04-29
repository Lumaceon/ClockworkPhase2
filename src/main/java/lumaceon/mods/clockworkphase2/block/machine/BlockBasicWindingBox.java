package lumaceon.mods.clockworkphase2.block.machine;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class BlockBasicWindingBox extends BlockClockworkPhase
{
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY = null;

    public BlockBasicWindingBox(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if(currentItem != null)
            {
                IEnergyStorage energyStorage = currentItem.getCapability(ENERGY, side);
                if(energyStorage != null)
                {
                    energyStorage.receiveEnergy(101 + world.rand.nextInt(100), false);
                    return true;
                }
            }
        }
        return false;
    }
}
