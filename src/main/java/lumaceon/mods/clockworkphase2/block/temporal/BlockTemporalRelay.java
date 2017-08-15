package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.TimezoneInternalStorage;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalRelay;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.List;

public class BlockTemporalRelay extends BlockClockworkPhase implements ITileEntityProvider
{
    @CapabilityInject(ITimeStorage.class)
    static Capability<ITimeStorage> TIME = null;

    public BlockTemporalRelay(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack currentItem = player.getHeldItem(hand);
            if(currentItem != null)
            {
                ITimeStorage timeStorage = currentItem.getCapability(TIME, EnumFacing.DOWN);
                if(timeStorage != null)
                {
                    TileEntity te = world.getTileEntity(pos);
                    if(te != null && te instanceof TileTemporalRelay)
                    {
                        TileTemporalRelay temporalRelay = (TileTemporalRelay) te;
                        List<ITimezone> timezones = temporalRelay.getTimezones();
                        for(ITimezone timezone : timezones)
                        {
                            if(timezone instanceof TimezoneInternalStorage)
                            {
                                ItemStack internalStack = ((TimezoneInternalStorage) timezone).setTimeStorageStack(currentItem);
                                player.setHeldItem(hand, internalStack);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(BlockDirectional.FACING, facing);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { BlockDirectional.FACING });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(BlockDirectional.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTemporalRelay();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
