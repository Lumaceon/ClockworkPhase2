package lumaceon.mods.clockworkphase2.util;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SideHelper
{
    public static boolean isServerSide() {
        return FMLCommonHandler.instance().getSide().isServer();
    }

    public static boolean isServerSide(World world) {
        return world == null ? isServerSide() : !world.isRemote;
    }
}
