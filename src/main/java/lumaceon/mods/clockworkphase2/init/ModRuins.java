package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.structure.StructureRegistry;
import lumaceon.mods.clockworkphase2.structure.StructureTemplate;

public class ModRuins
{
    public static void init()
    {
        initForthAge();
        initThirdAge();
        initSecondAge();
        initFirstAge();
        initZerothAge();
    }

    public static void initZerothAge()
    {

    }

    public static void initFirstAge()
    {

    }

    public static void initSecondAge()
    {

    }

    public static StructureTemplate testRuins;
    public static void initThirdAge()
    {
        testRuins = new StructureTemplate("testRuins", "NewSchematic", true);

        StructureRegistry.registerStructure(testRuins, "testRuins");
    }

    //public static RuinTemplate smallerRuins;
    public static void initForthAge()
    {
        //smallerRuins = new RuinTemplateTest(RuinTemplates.FIFTH.TEST, "A Small Test");
        //RuinRegistry.registerStructure(smallerRuins, "A Small Test");
    }
}
