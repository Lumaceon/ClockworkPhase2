package lumaceon.mods.clockworkphase2.api.util;

import java.util.Random;

/**
 * Used to apply weighted chances. Higher weight has more chance of occurring, and is calculated as the weight divided
 * by the total weight (so a weight of 5 vs a weight of 1 is like throwing your name in a hat 5 times vs 1).
 */
public class WeightedChance<E>
{
    public static Random random = new Random();
    public E object = null;
    public int weight = 1;

    public WeightedChance(E object, int weight) {
        this.object = object;
        this.weight = weight;
    }

    public E getObjectFromWeightedChance(WeightedChance<E>[] chances)
    {
        int totalWeight = 0;
        int randomPick;

        for(WeightedChance dc : chances)
            totalWeight += dc.weight;

        if(totalWeight <= 0)
            return null;

        randomPick = random.nextInt(totalWeight);
        int iterations = 0;
        int temp = -1;
        while(temp < randomPick && iterations < chances.length)
        {
            temp += chances[iterations].weight;
            ++iterations;
        }

        return chances[iterations-1].object;
    }
}
