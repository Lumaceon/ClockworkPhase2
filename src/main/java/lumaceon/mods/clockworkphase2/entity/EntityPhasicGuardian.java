package lumaceon.mods.clockworkphase2.entity;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import lumaceon.mods.clockworkphase2.api.IPhaseEntity;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityPhasicGuardian extends EntityMob implements IPhaseEntity
{
    public EntityPhasicGuardian(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && true;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) //Modified from EntityLiving
    {
        Item item = ModItems.matterEthereal.getItem();
        if(item != null)
        {
            int i = this.rand.nextInt(2);

            if(lootingModifier > 1)
                i += (1 + this.rand.nextInt(lootingModifier));
            else if(lootingModifier == 1)
                i++;

            for(int j = 0; j < i; ++j)
                this.dropItem(item, 1);
        }
    }

    @Override
    public EnumExpTier getTier() {
        return EnumExpTier.PHASIC;
    }
}
