package lumaceon.mods.clockworkphase2.timetravel.first.world;

import lumaceon.mods.clockworkphase2.lib.Configs;
import lumaceon.mods.clockworkphase2.timetravel.third.world.ChunkProviderThirdAge;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderFirstAge extends WorldProviderSurface
{
    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Configs.DIM_ID.FIRST_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT, "FirstAge");
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderThirdAge(this.worldObj, getSeed(), false, worldObj.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public String getDimensionName() {
        return "FirstAge";
    }

    @Override
    public String getWelcomeMessage() {
        return "Turning time back to the 1st Age...";
    }
}
