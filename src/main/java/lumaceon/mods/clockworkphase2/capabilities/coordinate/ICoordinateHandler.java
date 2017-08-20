package lumaceon.mods.clockworkphase2.capabilities.coordinate;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public interface ICoordinateHandler
{
    public BlockPos getCoordinate();

    public void setCoordinate(BlockPos pos);

    @Nullable
    public EnumFacing getSide();

    public void setSide(EnumFacing facing);
}
