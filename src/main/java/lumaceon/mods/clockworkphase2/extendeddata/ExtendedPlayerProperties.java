package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.api.TemporalAchievementList;
import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.extendeddata.player.PlayerPropertiesPAC;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalInfluence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayerProperties implements IExtendedEntityProperties
{
    public static final String DATA_KEY = "cp2_player_data";
    private final EntityPlayer player;
    public EntityPAC playerPAC;
    public PlayerPropertiesPAC PACProperties;

    //Represents overall progression in the game, as dictated by achievements.
    public int temporalInfluence = 0;

    //Used in rendering to show the increase of temporal influence.
    public int previousTemporalInfluence = 0;

    public int totalAchievementWeight = 0;
    public int totalSpecialAchievementsEarned = 0;


    public ExtendedPlayerProperties(EntityPlayer player)
    {
        this.player = player;
        PACProperties = new PlayerPropertiesPAC();
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(ExtendedPlayerProperties.DATA_KEY, new ExtendedPlayerProperties(player));
    }

    public static ExtendedPlayerProperties get(EntityPlayer player) {
        return (ExtendedPlayerProperties) player.getExtendedProperties(DATA_KEY);
    }

    private static String getSaveKey(EntityPlayer player) {
        return player.getUniqueID().toString() + ":" + DATA_KEY;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("temporalInfluence", temporalInfluence);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        temporalInfluence = compound.getInteger("temporalInfluence");
    }

    @Override
    public void init(Entity entity, World world) {

    }

    /**
     * Calculates temporal influence based on all achievements earned on the server and sends an update to the client.
     * The Achievement passed in can be null. If it isn't null, this method will consider the achievement earned.
     */
    public void calculateTemporalInfluence(Achievement achievement)
    {
        if(player != null && player.worldObj != null && !player.worldObj.isRemote)
        {
            EntityPlayerMP playerMP = ((EntityPlayerMP)player);
            int baseInfluence = 0;
            int pageMultiplier = 1;
            int specialAchievements = 0;
            for(Object object : AchievementList.achievementList)
            {
                if(object instanceof Achievement && TemporalAchievementList.isAchievementListed((Achievement) object) && (playerMP.getStatFile().hasAchievementUnlocked((Achievement) object) || (achievement != null && achievement.equals(object))))
                {
                    baseInfluence += TemporalAchievementList.achievementValues.get(object);
                    if(((Achievement) object).getSpecial())
                        specialAchievements++;
                }
            }

            for(AchievementPage page : AchievementPage.getAchievementPages())
            {
                boolean isComplete = true;
                for(Achievement ach : page.getAchievements())
                {
                    if(TemporalAchievementList.isAchievementListed(ach) && (!playerMP.getStatFile().hasAchievementUnlocked(ach) && (achievement == null || !ach.equals(achievement))))
                        isComplete = false;
                }

                if(isComplete)
                    pageMultiplier += TemporalAchievementList.getPageBonusMultiplier(page);
            }

            previousTemporalInfluence = temporalInfluence;
            temporalInfluence = baseInfluence * pageMultiplier * (int) Math.pow(specialAchievements + 1, TemporalAchievementList.INTERNAL.specialAchievementMultiplierExponent);

            if(previousTemporalInfluence != temporalInfluence)
                PacketHandler.INSTANCE.sendTo(new MessageTemporalInfluence(temporalInfluence), playerMP);
        }
    }
}