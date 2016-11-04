package lumaceon.mods.clockworkphase2.api.capabilities.achievementscore;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityAchievementScore
{
    @CapabilityInject(IAchievementScoreHandler.class)
    public static final Capability<IAchievementScoreHandler> ACHIEVEMENT_HANDLER_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IAchievementScoreHandler.class, new Capability.IStorage<IAchievementScoreHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<IAchievementScoreHandler> capability, IAchievementScoreHandler instance, EnumFacing side) {
                return new NBTTagInt(instance.getAchievementPoints());
            }

            @Override
            public void readNBT(Capability<IAchievementScoreHandler> capability, IAchievementScoreHandler instance, EnumFacing side, NBTBase base) {
                instance.calculateTier(((NBTTagInt)base).getInt());
            }
        }, AchievementScoreHandler.class);
    }
}
