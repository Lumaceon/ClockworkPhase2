package lumaceon.mods.clockworkphase2.api.time;

import net.minecraft.util.ResourceLocation;

/**
 * A type of time (usually an action) that can be stored and/or used. Each time must have a unique name, which is used
 * as a key in a HashMap (see TimeRegistry). A higher magnitude makes it take up more space in storage, for example 10
 * "magnitude 2" seconds will actually take up 20 seconds worth of temporal storage.
 */
public class Time
{
    private String name;
    private int magnitude;
    private ResourceLocation texture;

    public Time(String name, int magnitude, ResourceLocation texture) {
        this.name = name;
        this.magnitude = magnitude;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
