package lumaceon.mods.clockworkphase2.entity;

import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityPAC extends EntityLiving
{
    protected EntityPlayer owner;

    public EntityPAC(World world)
    {
        super(world);
        this.setSize(1, 1);
    }

    public EntityPAC(World world, EntityPlayer owner)
    {
        super(world);
        this.setPosition(owner.posX, owner.posY + 2, owner.posZ);
        ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(owner);
        if(properties.playerPAC != null && properties.playerPAC != this)
            properties.playerPAC.kill();
        properties.playerPAC = this;
        this.owner = owner;
    }

    @Override
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
    {
        moveFlying(p_70612_1_, p_70612_2_, this.isAIEnabled() ? 0.04F : 0.02F);
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
            if(owner == null || ExtendedPlayerProperties.get(owner).playerPAC == null || ExtendedPlayerProperties.get(owner).playerPAC != this)
                this.kill();
        }
    }
}