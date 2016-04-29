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
    public static final long DECADE = (long) YEAR * 10; //6,220,800,000 - now too large to store as an integer.
    public static final long CENTURY = DECADE * 10; //62,208,000,000
    public static final long MILLENNIUM = CENTURY * 10; //622,080,000,000
    public static final long TERASECOND = (long) SECOND * 1000000000000L; //20,000,000,000,000 (20 trillion)
    public static final long PETASECOND = (long) SECOND * 1000000000000000L; //20,000,000,000,000,000 (20 quadrillion)
    public static final long EON = (long) YEAR * 500000000L; //311,040,000,000,000,000 (311 quadrillion)
    public static final long ETERNITY = Long.MAX_VALUE; //9,223,372,036,854,775,807 (9 quintillion) - not recommended.

    /**
     * Takes an integer representing time and parses it into a readable time, much like a clock. The field typesToParse
     * allows the method caller to specify a maximum amount of types, making lower 'typesToParse' values less accurate.
     *
     * For example: calling parseNumber(3740, 2) would return "1 Hour, 2 Minutes" as a string.
     * Calling parseNumber(3740, 3) would, instead, return "1 Hour, 2 Minutes, 20 Seconds" as a string.
     * @param time The amount of time to parse to a string.
     * @param typesToParse The maximum amount of times names to parse, after which, others will be ignored. (max 15)
     * @return A string representing the time value in time format.
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

    /**
     * Similar to the above method, except this takes in a long and parses time farther than a year. Used for potential
     * extreme-end-game stuff, as time is typically stored as an integer.
     */
    public static String parseNumber(long time, int typesToParse)
    {
        if(time >= ETERNITY)
            return "Eternity";

        String parsedString = "";
        int pass = 0;
        long numberOf;

        if(time >= EON)
        {
            numberOf = time / EON;
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Eon");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= EON * numberOf;
        }

        if(time >= PETASECOND)
        {
            numberOf = time / PETASECOND;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Petasecond");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= PETASECOND * numberOf;
        }

        if(time >= TERASECOND)
        {
            numberOf = time / TERASECOND;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Terasecond");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= TERASECOND * numberOf;
        }

        if(time >= MILLENNIUM)
        {
            numberOf = time / MILLENNIUM;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Millenn");
            if(numberOf > 1)
                parsedString = parsedString.concat("ia");
            else
                parsedString = parsedString.concat("ium");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= MILLENNIUM * numberOf;
        }

        if(time >= CENTURY)
        {
            numberOf = time / CENTURY;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Centur");
            if(numberOf > 1)
                parsedString = parsedString.concat("ies");
            else
                parsedString = parsedString.concat("y");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= CENTURY * numberOf;
        }

        if(time >= DECADE)
        {
            numberOf = time / DECADE;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Decade");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                time -= DECADE * numberOf;
        }

        if(time >= YEAR)
        {
            numberOf = time / YEAR;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
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
