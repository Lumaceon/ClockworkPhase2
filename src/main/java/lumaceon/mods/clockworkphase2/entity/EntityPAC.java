package lumaceon.mods.clockworkphase2.entity;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityPAC extends EntityLiving
{
    public EntityPlayer owner;

    public EntityPAC(World world)
    {
        super(world);
        this.setSize(1, 1);
    }

    public EntityPAC(World world, EntityPlayer owner)
    {
        super(world);
        this.setPosition(owner.posX, owner.posY + 2, owner.posZ);
        this.owner = owner;
    }

    @Override
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
    {
        //moveFlying(p_70612_1_, p_70612_2_, !this.isAIDisabled() ? 0.04F : 0.02F);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(worldObj != null && !worldObj.isRemote)
        {
                this.kill();
        }
    }

    public void onRightClicked(EntityPlayer player)
    {
        if(owner == null || !player.equals(owner))
            return;
            player.openGui(ClockworkPhase2.instance, 3, worldObj, (int) posX, (int) posY, (int) posZ);
    }
}