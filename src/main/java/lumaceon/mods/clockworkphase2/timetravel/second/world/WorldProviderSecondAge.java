package lumaceon.mods.clockworkphase2.timetravel.second.world;

import lumaceon.mods.clockworkphase2.lib.Configs;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSecondAge extends WorldProviderSurface
{
    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Configs.DIM_ID.SECOND_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT, "SecondAge");
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderSecondAge(this.worldObj, getSeed(), false, worldObj.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public String getDimensionName() {
        return "SecondAge";
    }

    @Override
    public String getWelcomeMessage() {
        return "Turning time back to the 2nd Age...";
    }
}
