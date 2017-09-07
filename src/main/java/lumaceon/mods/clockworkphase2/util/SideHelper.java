package lumaceon.mods.clockworkphase2.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public class SideHelper
{
    public static boolean isServerSide() {
        return FMLCommonHandler.instance().getSide().isServer();
    }

    public static boolean isServerSide(World world) {
        return world == null ? isServerSide() : !world.isRemote;
    }

    public static boolean isServerSide(@Nullable EntityPlayer player) {
        return player == null ? isServerSide() : player.isServerWorld();
    }
}
