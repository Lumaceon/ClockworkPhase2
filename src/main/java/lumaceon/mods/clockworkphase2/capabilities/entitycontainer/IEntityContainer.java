package lumaceon.mods.clockworkphase2.capabilities.entitycontainer;

import lumaceon.mods.clockworkphase2.api.EntityStack;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public interface IEntityContainer
{
    /**
     * Can be used to set the capacity of this container (how many entities it can hold). Warning, this will delete
     * stored entities if enough downsizing occurs.
     * @param capacity The new capacity.
     */
    public void setCapacity(int capacity);

    public int getCapacity();

    public int getCurrentEntityIndex();
    public void setCurrentEntityIndex(int index);

    public int getEntityCount();

    /**
     * Adds the entity to this container.
     * @param entity The EntityStack representation of an entity to add to this container.
     * @return True if added successfully, false if it couldn't be.
     */
    public boolean addEntity(EntityStack entity);

    public EntityStack getCurrentEntity();

    /**
     * Gets the entity at the appropriate index, but does not remove it. Alternatively, use extractEntity to both
     * get the entity and remove it from this container.
     * @param index The index to get.
     * @return The stack representation of the entity at index, or null if non-existent.
     */
    public EntityStack getEntity(int index);

    /**
     * Used to get the first entity, either of, or extending the given class.
     * @param entityClass The entity type to check for.
     * @return The first occurrence of a valid entity.
     */
    public EntityStack getFirstEntityOfType(Class<? extends Entity> entityClass);

    /**
     * @return An array containing all of the entities in this container.
     */
    public EntityStack[] getEntities();

    /**
     * @return An array containing all the entities of the given type, or an empty array if none were found.
     */
    public EntityStack[] getEntitiesOfType(Class<? extends Entity> entityClass);

    /**
     * An alternative getter method used to get the entity and then remove it from the container. This is best used
     * when something is actively taking the entity and doing something with it (such as spawning it).
     * @param index The index which will be removed from this container.
     * @return The entity which was removed from this container, or null if nothing was removed.
     */
    public EntityStack extractEntity(int index);

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound base);
}
