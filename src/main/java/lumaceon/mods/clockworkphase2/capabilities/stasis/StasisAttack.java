package lumaceon.mods.clockworkphase2.capabilities.stasis;

import lumaceon.mods.clockworkphase2.util.ExperienceHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class StasisAttack
{
    public boolean unblockable, fire, projectile, magic, explosion = false;
    public float amount = 1.0f;
    public long timeToAttack = 0;
    public int experienceToReturn = 0;

    public StasisAttack(boolean unblockable, boolean fire, boolean projectile, boolean magic, boolean explosion, float amount, long timeToAttack, int experienceToReturn) {
        this.unblockable = unblockable;
        this.fire = fire;
        this.projectile = projectile;
        this.magic = magic;
        this.explosion = explosion;
        this.amount = amount;
        this.timeToAttack = timeToAttack;
        this.experienceToReturn = experienceToReturn;
    }

    public void hurtEntity(Entity entity)
    {
        DamageSource ds = new DamageSource("stasis");
        if(unblockable) ds.setDamageBypassesArmor();
        if(fire) ds.setFireDamage();
        if(projectile) ds.setProjectile();
        if(magic) ds.setMagicDamage();
        if(explosion) ds.setExplosion();

        entity.attackEntityFrom(ds, amount);
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            ExperienceHelper.addPlayerXP(player, experienceToReturn);
        }
    }
}
