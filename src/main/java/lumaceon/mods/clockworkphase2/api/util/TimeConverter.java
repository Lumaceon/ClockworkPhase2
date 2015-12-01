package lumaceon.mods.clockworkphase2.api.util;

public class TimeConverter
{
    public static final int SECOND = 20;
    public static final int MINUTE = SECOND * 60; //1200
    public static final int HOUR = MINUTE * 60; //72,000
    public static final int DAY = HOUR * 24; //1,728,000
    public static final int WEEK = DAY * 7; //12,096,000 - Skipped in most cases.
    public static final int MONTH = DAY * 30; //51,840,000
    public static final int YEAR = MONTH * 12; //622,080,000
    public static final long INFINITE = Integer.MAX_VALUE; //Never shown: if this is acquired, do something cool.

    /**
     * Takes a number of time sand and parses it into a readable time, much like a clock. The field typesToParse allows
     * the method caller to specify a maximum amount of types, making lower 'typesToParse' values less accurate.
     *
     * For example: calling parseNumber(3740, 2) would return "1 Hour, 2 Minutes" as a string.
     * Calling parseNumber(3740, 3) would, instead, return "1 Hour, 2 Minutes, 20 Seconds" as a string.
     * @param time The time sand number to parse.
     * @param typesToParse The maximum amount of times names to parse, after which, others will be ignored. (max 15)
     * @return A string representing the time sand in time format.
     */
    public static String parseNumber(int time, int typesToParse)
    {
        String parsedString = "";
        int pass = 0;
        long numberOf;

        if(time >= YEAR)
        {
            numberOf = time / YEAR;
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Year");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= YEAR * numberOf;
        }

        if(time >= MONTH)
        {
            numberOf = time / MONTH;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Month");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= MONTH * numberOf;
        }

        if(time >= DAY)
        {
            numberOf = time / DAY;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Day");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= DAY * numberOf;
        }

        if(time >= HOUR)
        {
            numberOf = time / HOUR;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Hour");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= HOUR * numberOf;
        }

        if(time >= MINUTE)
        {
            numberOf = time / MINUTE;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Minute");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= MINUTE * numberOf;
        }

        if(time >= SECOND)
        {
            numberOf = time / SECOND;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Second");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
        }
        if(parsedString.isEmpty())
            parsedString = "0 Seconds";
        return parsedString;
    }
}
