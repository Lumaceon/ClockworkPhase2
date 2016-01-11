package lumaceon.mods.clockworkphase2.timetravel.third.world;

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

public class WorldProviderThirdAge extends WorldProviderSurface
{
    private IRenderHandler skyRenderer;

    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Defaults.DIM_ID.THIRD_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderThirdAge(this.worldObj, getSeed(), false);
    }

    @Override
    public String getDimensionName() {
        return "The 3rd Age";
    }

    @Override
    public String getWelcomeMessage() {
        return "Turning time back to the 3rd Age...";
    }

    @Override
    protected void generateLightBrightnessTable()
    {
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = (1.0F - (float)i / 15.0F);
            this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f) * 0.15F;
        }
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

    @Override
    public float getSunBrightnessFactor(float par1) {
        return 0.0F;
    }
}
