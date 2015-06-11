package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.extendeddata.player.PlayerPropertiesPAC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayerProperties implements IExtendedEntityProperties
{
    public static final String DATA_KEY = "cp2_player_data";
    private final EntityPlayer player;
    public EntityPAC playerPAC;

    public PlayerPropertiesPAC PACProperties;

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

    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {

    }

    @Override
    public void init(Entity entity, World world) {

    }
}