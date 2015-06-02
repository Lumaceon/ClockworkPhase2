package lumaceon.mods.clockworkphase2.api.util;

/**
 * The times used here follow this article - http://en.wikipedia.org/wiki/Unit_of_time
 * Yottaseconds? Ain't nobody got time for that.
 */
public class TimeConverter
{
    public static final long SECOND = 1;
    public static final long MINUTE = SECOND * 60; //60
    public static final long HOUR = MINUTE * 60; //3,600
    public static final long DAY = HOUR * 24; //86,400
    public static final long WEEK = DAY * 7; //604,800 - Skipped in most cases.
    public static final long MONTH = DAY * 30; //2,592,000
    public static final long YEAR = MONTH * 12; //31,104,000
    public static final long DECADE = YEAR * 10; //311,040,000
    public static final long CENTURY = YEAR * 100; //3,110,400,000
    public static final long MILLENNIUM = YEAR * 1000; //31,104,000,000
    public static final long TERASECOND = (long) 1000000 * 1000000; //1,000,000,000,000 | one trillion seconds.
    public static final long AGE = YEAR * 1000000; //31,104,000,000,000 | 1 million years.
    public static final long EPOCH = AGE * 10; //311,040,000,000,000 | 10 million years.
    public static final long ERA = EPOCH * 10; //3,110,400,000,000,000 | 100 million years.
    public static final long GALACTIC_YEAR = YEAR * 230000000; //7,153,920,000,000,000 | 230 million years.
    public static final long EON = ERA * 10; //31,104,000,000,000,000 | 1 billion years.
    public static final long EXASECOND = TERASECOND * 1000000; //1,000,000,000,000,000,000 | 1 quintillion seconds
    public static final long INFINITE = 9223372036854775807L; //Never shown: if this is acquired, do something cool.

    /**
     * Takes a number of time sand and parses it into a readable time, much like a clock. The field typesToParse allows
     * the method caller to specify a maximum amount of types, making lower 'typesToParse' values less accurate.
     *
     * For example: calling parseNumber(3740, 2) would return "1 Hour, 2 Minutes" as a string.
     * Calling parseNumber(3740, 3) would, instead, return "1 Hour, 2 Minutes, 20 Seconds" as a string.
     * @param timeSand The time sand number to parse.
     * @param typesToParse The maximum amount of times names to parse, after which, others will be ignored. (max 15)
     * @return A string representing the time sand in time format.
     */
    public static String parseNumber(long timeSand, int typesToParse)
    {
        String parsedString = "";
        int pass = 0;
        long numberOf;
        if(timeSand >= EXASECOND)
        {
            numberOf = timeSand / EXASECOND;
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Exasecond");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= EXASECOND * numberOf;
        }

        if(timeSand >= EON)
        {
            numberOf = timeSand / EON;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Eon");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= EON * numberOf;
        }

        if(timeSand >= ERA)
        {
            numberOf = timeSand / ERA;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Era");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= ERA * numberOf;
        }

        if(timeSand >= EPOCH)
        {
            numberOf = timeSand / EPOCH;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Epoch");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= EPOCH * numberOf;
        }

        if(timeSand >= AGE)
        {
            numberOf = timeSand / AGE;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Age");
            if(numberOf > 1)
                parsedString = parsedString.concat("s");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= AGE * numberOf;
        }

        if(timeSand >= TERASECOND)
        {
            numberOf = timeSand / TERASECOND;
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
                timeSand -= TERASECOND * numberOf;
        }

        if(timeSand >= MILLENNIUM)
        {
            numberOf = timeSand / MILLENNIUM;
            if(pass > 0)
                parsedString = parsedString.concat(", ");
            parsedString = parsedString.concat(Long.toString(numberOf));
            parsedString = parsedString.concat(" Millenni");
            if(numberOf > 1)
                parsedString = parsedString.concat("um");
            else
                parsedString = parsedString.concat("a");
            pass++;
            if(pass == typesToParse)
                return parsedString;
            else
                timeSand -= MILLENNIUM * numberOf;
        }

        if(timeSand >= CENTURY)
        {
            numberOf = timeSand / CENTURY;
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
                timeSand -= CENTURY * numberOf;
        }

        if(timeSand >= DECADE)
        {
            numberOf = timeSand / DECADE;
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
                timeSand -= DECADE * numberOf;
        }

        if(timeSand >= YEAR)
        {
            numberOf = timeSand / YEAR;
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
                timeSand -= YEAR * numberOf;
        }

        if(timeSand >= MONTH)
        {
            numberOf = timeSand / MONTH;
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
                timeSand -= MONTH * numberOf;
        }

        if(timeSand >= DAY)
        {
            numberOf = timeSand / DAY;
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
                timeSand -= DAY * numberOf;
        }

        if(timeSand >= HOUR)
        {
            numberOf = timeSand / HOUR;
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
                timeSand -= HOUR * numberOf;
        }

        if(timeSand >= MINUTE)
        {
            numberOf = timeSand / MINUTE;
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
                timeSand -= MINUTE * numberOf;
        }

        if(timeSand > 0)
        {
            numberOf = timeSand;
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
