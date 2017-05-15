package lumaceon.mods.clockworkphase2.api.temporal;

import java.util.HashMap;

public class EchoRegistry
{
    private static HashMap<String, Echo> ECHOS = new HashMap<String, Echo>();

    public static void registerEcho(Echo echo) {
        ECHOS.put(echo.getRegistryName(), echo);
    }

    public static Echo getEchoFromRegistryName(String registryName) {
        return ECHOS.get(registryName);
    }
}
