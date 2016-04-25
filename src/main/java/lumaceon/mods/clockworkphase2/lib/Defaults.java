package lumaceon.mods.clockworkphase2.lib;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;

public class Defaults
{
    public static class TENSION {
        public static int perBlock = 50;
        public static int perWhack = 250;
        public static int maxMainspringTension = 1000000;
    }

    public static class TIME {
        public static int perXP = TimeConverter.SECOND;
        public static int maxTimezoneTime = TimeConverter.YEAR;
    }

    public static class DIM_ID {
        public static int ZEROTH_AGE;
        public static int FIRST_AGE;
        public static int SECOND_AGE;
        public static int THIRD_AGE;
    }
}
