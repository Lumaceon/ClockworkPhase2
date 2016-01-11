package lumaceon.mods.clockworkphase2.timetravel;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterPast extends Teleporter
{
    public TeleporterPast(WorldServer p_i1963_1_) {
        super(p_i1963_1_);
    }

    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        par1Entity.setLocationAndAngles(0, 255, 0, par1Entity.rotationYaw, 0.0F);
        par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
    }
}
