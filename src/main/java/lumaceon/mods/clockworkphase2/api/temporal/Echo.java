package lumaceon.mods.clockworkphase2.api.temporal;

import net.minecraft.util.text.translation.I18n;

public class Echo
{
    private String registryName;

    public Echo(String registryName)
    {
        this.registryName = registryName;
        EchoRegistry.registerEcho(this);
    }

    public String getRegistryName() {
        return registryName;
    }

    @SuppressWarnings("deprecation")
    public String getDisplayName() {
        return I18n.translateToLocal(registryName);
    }


}
