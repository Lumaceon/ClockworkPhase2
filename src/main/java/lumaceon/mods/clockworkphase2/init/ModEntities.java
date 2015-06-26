package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class ModEntities
{
    public static void init()
    {
        registerEntity(EntityPAC.class, Names.ENTITY.PERSONAL_ASSISTANT_CUBE, 1, 1);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name, int mainColor, int secondColor)
    {
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, name, id);
        EntityRegistry.registerModEntity(entityClass, name, id, ClockworkPhase2.instance, 64, 1, true);
        EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, mainColor, secondColor));
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name)
    {
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, name, id);
        EntityRegistry.registerModEntity(entityClass, name, id, ClockworkPhase2.instance, 64, 1, true);
    }
}