package lumaceon.mods.clockworkphase2.api.timezone.function;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * An abstract class for singletons used in registering a type of timezone function. Each timezone can map up to one
 * TimezoneFunction instance for each type.
 *
 * The type is responsible for general information shared by all functions: display name, display icon, creation
 * conditions, etc.
 *
 * Registry is automatically done in the constructor.
 */
public abstract class TimezoneFunctionType<K extends TimezoneFunctionConstructor>
{
    String uniqueID;
    ResourceLocation iconLocation;

    public TimezoneFunctionType(String uniqueID, ResourceLocation iconLocation) {
        this.uniqueID = uniqueID;
        this.iconLocation = iconLocation;
        TimezoneFunctionRegistry.registerFunctionType(this);
    }

    /**
     * Used in saving/loading. Each type must return a unique and consistent string ID.
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * @return The (localized) name for display.
     */
    public abstract String getDisplayName();

    /**
     * @param detailed False for a few-word summery, true to toggle a longer and more complete description.
     * @return A description of what this timezone function actually does.
     */
    public abstract String getDescription(boolean detailed);

    /**
     * @return A ResourceLocation to be bound to the renderer. The texture is rendered stretched with blending enabled.
     */
    @SideOnly(Side.CLIENT)
    public ResourceLocation getIconLocation() {
        return iconLocation;
    }

    /**
     * Creates an instance of a TimezoneFunctionConstructor subclass, which handles the construction of this function.
     * @return A new instance of the appropriate timezone function constructor subclass.
     */
    public abstract K createTimezoneFunctionConstructorInstance();
}
