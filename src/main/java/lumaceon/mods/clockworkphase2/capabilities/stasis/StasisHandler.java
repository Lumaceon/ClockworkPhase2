package lumaceon.mods.clockworkphase2.capabilities.stasis;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class StasisHandler implements IStasis
{
    ArrayList<StasisAttack> stasisAttacks = new ArrayList<>(10);

    long stasisTime = 0;

    @Override
    public void setStasisTime(long stasisTime) {
        this.stasisTime = stasisTime;
    }

    @Override
    public long getStasisTime() {
        return this.stasisTime;
    }

    @Override
    public void onUpdate(Entity entity)
    {
        ++stasisTime;
        for(int i = 0; i < stasisAttacks.size(); i++)
        {
            StasisAttack atk = stasisAttacks.get(i);
            if(atk.timeToAttack <= stasisTime)
            {
                atk.hurtEntity(entity);
                stasisAttacks.remove(atk);
                --i;
            }
        }
    }

    @Override
    public void addStasisAttack(DamageSource source, float amount, long timeToHit, int experienceToReturn) {
        stasisAttacks.add(new StasisAttack
                (
                        source.isUnblockable(),
                        source.isFireDamage(),
                        source.isProjectile(),
                        source.isMagicDamage(),
                        source.isExplosion(),
                        amount,
                        timeToHit,
                        experienceToReturn
                ));
    }

    @Override
    public List<StasisAttack> getStasisAttacks() {
        return stasisAttacks;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        NBTTagList stasisList = new NBTTagList();
        for(StasisAttack attack : stasisAttacks)
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("unblockable", attack.unblockable);
            tag.setBoolean("fire", attack.fire);
            tag.setBoolean("projectile", attack.projectile);
            tag.setBoolean("magic", attack.magic);
            tag.setBoolean("explosion", attack.explosion);
            tag.setFloat("amount", attack.amount);
            tag.setLong("target_time", attack.timeToAttack);
            tag.setInteger("xp", attack.experienceToReturn);
            stasisList.appendTag(tag);
        }
        nbt.setTag("attacks", stasisList);

        nbt.setLong("timer", stasisTime);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("timer"))
            stasisTime = nbt.getLong("timer");

        if(nbt.hasKey("attacks"))
        {
            stasisAttacks.clear();
            NBTTagList stasisList = nbt.getTagList("attacks", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < stasisList.tagCount(); i++)
            {
                NBTTagCompound tag = stasisList.getCompoundTagAt(i);
                stasisAttacks.add(new StasisAttack(
                            tag.getBoolean("unblockable"),
                            tag.getBoolean("fire"),
                            tag.getBoolean("projectile"),
                            tag.getBoolean("magic"),
                            tag.getBoolean("explosion"),
                            tag.getFloat("amount"),
                            tag.getLong("target_time"),
                            tag.getInteger("xp")
                        ));
            }
        }
    }
}
