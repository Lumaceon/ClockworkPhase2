package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.RuinRegistry;
import lumaceon.mods.clockworkphase2.api.RuinTemplate;
import lumaceon.mods.clockworkphase2.lib.RuinTemplates;
import lumaceon.mods.clockworkphase2.ruins.templates.RuinTemplateTest;

public class ModRuins
{
    public static void init()
    {
        initFifthAge();
        initForthAge();
        initThirdAge();
        initSecondAge();
        initFirstAge();
    }

    public static void initFirstAge()
    {

    }

    public static void initSecondAge()
    {

    }

    public static void initThirdAge()
    {

    }

    //public static RuinTemplate testRuins;
    public static void initForthAge()
    {
        //testRuins = new RuinTemplateTest(RuinTemplates.FORTH.TEST, "TEEEEESSSSSST");

        //RuinRegistry.registerRuins(testRuins, "TEEEEESSSSSST");
    }

    //public static RuinTemplate smallerRuins;
    public static void initFifthAge()
    {
        //smallerRuins = new RuinTemplateTest(RuinTemplates.FIFTH.TEST, "A Small Test");
        //RuinRegistry.registerRuins(smallerRuins, "A Small Test");
    }
}
