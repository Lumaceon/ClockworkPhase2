package lumaceon.mods.clockworkphase2.capabilities.coordinate;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CoordinateHandler implements ICoordinateHandler
{
    public BlockPos position = new BlockPos(0, 0, 0);
    public EnumFacing facing = null;

    @Override
    public BlockPos getCoordinate() {
        return position;
    }

    @Override
    public void setCoordinate(BlockPos pos) {
        position = pos;
    }

    @Override
    public EnumFacing getSide() {
        return facing;
    }

    @Override
    public void setSide(EnumFacing facing) {
        this.facing = facing;
    }
}
