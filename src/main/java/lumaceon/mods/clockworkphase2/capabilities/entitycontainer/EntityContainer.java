package lumaceon.mods.clockworkphase2.capabilities.entitycontainer;

import lumaceon.mods.clockworkphase2.api.EntityStack;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EntityContainer implements IEntityContainer
{
    protected int currentEntity = 0;
    protected int capacity;
    protected ArrayList<EntityStack> entities = new ArrayList<>();

    public EntityContainer() {
        this.capacity = 10;
    }

    public EntityContainer(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setCapacity(int capacity)
    {
        while(capacity < entities.size())
            entities.remove(entities.size()-1);

        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getCurrentEntityIndex() {
        return currentEntity;
    }

    @Override
    public void setCurrentEntityIndex(int index) {
        this.currentEntity = index;
    }

    @Override
    public int getEntityCount() {
        return entities.size();
    }

    @Override
    public boolean addEntity(EntityStack entity)
    {
        if(entities.size() >= capacity)
            return false;
        entities.add(entity);
        return true;
    }

    @Override
    public EntityStack getCurrentEntity()
    {
        if(entities.size() > currentEntity)
            return entities.get(currentEntity);
        return null;
    }

    @Override
    public EntityStack getEntity(int index)
    {
        if(entities.size() <= index)
            return null;
        else
            return entities.get(index);
    }

    @Override
    public EntityStack getFirstEntityOfType(Class<? extends Entity> entityClass)
    {
        for(EntityStack e : entities)
            if(e != null)
            {
                Class<? extends Entity> c = e.getEntityClass();
                if(entityClass.isAssignableFrom(c))
                    return e;
            }
        return null;
    }

    @Override
    public EntityStack[] getEntities() {
        return entities.toArray(new EntityStack[entities.size()]);
    }

    @Override
    public EntityStack[] getEntitiesOfType(Class<? extends Entity> entityClass) {
        ArrayList<EntityStack> stacks = new ArrayList<>();
        for(EntityStack e : entities)
            if(e != null)
            {
                Class<? extends Entity> c = e.getEntityClass();
                if(entityClass.isAssignableFrom(c))
                    stacks.add(e);
            }
        if(stacks.isEmpty())
            return new EntityStack[0];
        return stacks.toArray(new EntityStack[stacks.size()]);
    }

    @Override
    public EntityStack extractEntity(int index) {
        return entities.remove(index);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("capacity", capacity);

        NBTTagList list = new NBTTagList();
        NBTTagCompound temp;
        for(EntityStack e : entities)
            if(e != null)
            {
                temp = e.writeToNBT();
                temp.setString("stack_class_ID", e.getEntityStackClassID());
                list.appendTag(temp);
            }
        nbt.setTag("entity_list", list);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        if(tag.hasKey("capacity"))
            this.capacity = tag.getInteger("capacity");

        if(tag.hasKey("entity_list"))
        {
            NBTTagList list = tag.getTagList("entity_list", 10);
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound nbt = list.getCompoundTagAt(i);
                Class<? extends EntityStack> c = EntityStack.getEntityStackClass(nbt.getString("stack_class_ID"));
                EntityStack entityStack = null;

                try {
                    Constructor cons = c.getConstructor(NBTTagCompound.class);
                    entityStack = (EntityStack) cons.newInstance(nbt);
                } catch (NoSuchMethodException e) {
                    System.err.println("Custom EntityStack class '" + c.getName() + "' lacks a constructor with an NBTTagCompound as it's sole parameter.");
                    e.printStackTrace();
                } catch (InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    System.err.println("Constructor for EntityStack class '" + c.getName() + "' must be public.");
                    e.printStackTrace();
                }

                if(entityStack != null)
                    entities.add(entityStack);
            }
        }
    }
}
