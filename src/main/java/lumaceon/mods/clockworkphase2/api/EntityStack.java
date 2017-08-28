package lumaceon.mods.clockworkphase2.api;

import lumaceon.mods.clockworkphase2.capabilities.custombehavior.CapabilityCustomBehavior;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * In a similar fashion to ItemStack for items and FluidStack for fluids, this class is meant to contain the 'inventory'
 * representation of an entity. Generally, an EntityStack instance is used to load only information that is likely to be
 * accessed frequently.
 *
 * Check Entity#writeToNBT for potential information to add to a child EntityStack class. Child classes should ALWAYS
 * override getEntityStackClassID. Additionally, one should register new classes with registerCustomEntityStackClass, so
 * they can be saved and loaded properly.
 */
@SuppressWarnings("deprecation")
public class EntityStack
{
    private static final HashMap<String, Class<? extends EntityStack>> ENTITY_STACK_CLASSES = new HashMap<String, Class<? extends EntityStack>>();

    public NBTTagCompound nbt = null;
    protected net.minecraftforge.common.capabilities.CapabilityDispatcher capabilities;
    protected Class<? extends Entity> entityClass = null;
    protected String entityID = "";
    protected String customName = "";

    /**
     * Used to load the data you need from nbt. Call serializeNBT from the entity and pass this constructor the NBT.
     *
     * @param nbt The tag representing this entity.
     */
    public EntityStack(NBTTagCompound nbt)
    {
        this.nbt = nbt;
        this.entityID = nbt.getString("id");
        if(nbt.hasKey("CustomName"))
            this.customName = nbt.getString("CustomName");
        this.entityClass = EntityList.getClass(new ResourceLocation(getEntityTypeID()));
        if(nbt.hasKey("ForgeCaps"))
        {
            if(this.capabilities == null)
            {
                Map<ResourceLocation, ICapabilityProvider> list = new HashMap<>();
                list.put(new ResourceLocation(Reference.MOD_ID + ":custom_behavior"), new CapabilityCustomBehavior.CustomBehaviorGenericProvider());
                capabilities = new CapabilityDispatcher(list);
            }
            this.capabilities.deserializeNBT(nbt.getCompoundTag("ForgeCaps"));
        }
    }

    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return getCapability(capability, facing) != null || capabilities != null && capabilities.hasCapability(capability, facing);
    }

    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        return capabilities == null ? null : capabilities.getCapability(capability, facing);
    }

    /**
     * ### OVERRIDE THIS IN YOUR CHILD CLASS ###
     * This should always return a unique string ID to be used for this class.
     * @return The unique string ID for this class, must be the same as registered in registerCustomEntityStackClass.
     */
    public String getEntityStackClassID() {
        return "";
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    /**
     * @return The string ID for this type of entity. This can be used to get the entity's class via the map
     * EntityList#NAME_TO_CLASS by using this as the key.
     */
    public String getEntityTypeID() {
        return entityID;
    }

    /**
     * @return The localized name for this type of entity.
     */
    public String getEntityTypeDisplayName() {
        return I18n.translateToLocal(entityID);
    }

    /**
     * @return Ths custom name for this entity, or null if the entity has none.
     */
    public String getEntityCustomName() {
        return customName;
    }

    /**
     * Creates an Entity class for this stack, with the passed in world as the parameter for it's constructor. This does
     * not spawn said entity, merely creates the class. This uses the tag returned from writeToNBT to spawn the entity.
     * @return An entity loaded from this stack, but not spawned in-world yet.
     */
    public Entity createEntityForWorld(World world) {
        return EntityList.createEntityFromNBT(writeToNBT(), world);
    }

    /**
     * Saves the custom data back to nbt for both spawning the entity and saving the changes made via EntityStack.
     * @return The NBTTagCompound with the EntityStack data saved to it.
     */
    public NBTTagCompound writeToNBT()
    {
        nbt.setString("id", entityID);
        if(!customName.equals(""))
            nbt.setString("CustomName", customName);
        if (this.capabilities != null) nbt.setTag("ForgeCaps", this.capabilities.serializeNBT());
        return nbt;
    }

    /**
     * @param id The string ID for the desired EntityStack class.
     * @return The custom class extending EntityStack, or the EntityStack class if there is no key registered to the id.
     */
    public static Class<? extends EntityStack> getEntityStackClass(String id)
    {
        Class<? extends EntityStack> c = ENTITY_STACK_CLASSES.get(id);
        if(c != null)
            return c;
        return EntityStack.class;
    }

    public static void registerCustomEntityStackClass(Class<? extends EntityStack> c, String ID) {
        ENTITY_STACK_CLASSES.put(ID, c);
    }
}
