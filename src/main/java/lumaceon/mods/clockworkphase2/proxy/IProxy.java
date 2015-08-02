package lumaceon.mods.clockworkphase2.proxy;

import net.minecraft.world.World;

public interface IProxy
{
    public void registerTESR();
    public void registerModels();
    public void registerKeybindings();
    public void initSideHandlers();
    public void addWorldRenderer(World world, int x, int y, int z, int ID);
    public void clearWorldRenderers(World world, int x, int y, int z);
}
