package lumaceon.mods.clockworkphase2.ai;

import java.util.HashMap;

public abstract class CustomAIs
{
    public static final HashMap<String, AIFactory> CUSTOM_AI_FACTORIES = new HashMap<>(50);

    public static AIFactory getCustomAIFromID(String id) {
        return CUSTOM_AI_FACTORIES.get(id);
    }
}
