package lumaceon.mods.clockworkphase2.world.provider.forthage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderForthAge extends WorldProviderSurface
{
    private IRenderHandler skyRenderer;

    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Defaults.DIM_ID.FORTH_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderForthAge(this.worldObj, getSeed(), false);
    }

    @Override
    public String getDimensionName() {
        return "The Forth Age";
    }

    @Override
    public String getWelcomeMessage() {
        return "Entering the 4th Age";
    }


    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
        if(this.skyRenderer == null)
            skyRenderer = ClockworkPhase2.proxy.getSkyRendererForWorld(this);
        return this.skyRenderer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
        Vec3 vec = super.getFogColor(p_76562_1_, p_76562_2_);
        vec.xCoord *= 0.2;
        vec.yCoord *= 0.2;
        vec.zCoord *= 0.2;
        return vec;
    }
}
