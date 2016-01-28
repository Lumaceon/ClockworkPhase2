package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Random;

public class ParadoxiumIngotFlavorText
{
    private static Random random = new Random();
    public static int index = -1;

    public static String howDoIFeelYouAsk(ItemStack item, EntityPlayer player)
    {
        if(player == null || item == null)
            return "Something feels off...I'd rather not talk at the moment...";

        ArrayList<String> potentialFeelings = new ArrayList<String>(100);
        ItemStack heldItem = player.getHeldItem();

        if(heldItem == null)
        {
            potentialFeelings.add("Are you here to abandon me in another one of your dusty boxes?");
            potentialFeelings.add("I need a hug... <[ 0.0 ]>");
            potentialFeelings.add("Hold me, I want to see this world you keep talking about.");
            potentialFeelings.add("Hey. Have you got any watermelons?");
        }
        else if(heldItem.equals(item))
        {
            potentialFeelings.add("You've been holding me for a while, do you have..." + Colors.LIGHT_PURPLE + "feelings for me?");
            potentialFeelings.add("I'm both happy and disturbed you've been holding me so closely...");

            if(player.dimension == 0)
                potentialFeelings.add("Wowie! The world is huge. I like the comfort of your inventory though.");
            else if(player.dimension == -1)
                potentialFeelings.add("This is just plain creepy. Put me back, I want nothing to do with this world.");
            else
                potentialFeelings.add("I love exploring, but you know what they say: there's no place like home.");

            potentialFeelings.add("I wish I could hug you back, but I don't have arms.");
            potentialFeelings.add("I see you looking at that 'Q' button. " + Colors.RED + "Don't even think about it.");
        }
        else if(heldItem.getItem().equals(Items.bone))
        {
            potentialFeelings.add("Nyeh heh heh!");
            potentialFeelings.add("If you keep holding that item...you're gonna have a bad time.");
            potentialFeelings.add("You're working me down to the BONE here!");
        }
        else if(heldItem.getItem().equals(Items.bucket))
        {
            potentialFeelings.add("That thing would make a great helmet if used right.");
            potentialFeelings.add("What's that thing for? Plant protection?");
            potentialFeelings.add("Melt me into the bucket and I will haunt you in your sleep.");
        }
        else if(heldItem.getItem().equals(Items.apple))
        {
            potentialFeelings.add("I'm more of a PC ingot myself.");
            potentialFeelings.add("More nutritious when surrounded with gold. That's almost as bad mixing gold/iron to get brass.");
            potentialFeelings.add("Greetings from apple world!");
        }
        else if(heldItem.getItem().equals(Items.arrow))
        {
            potentialFeelings.add("I used to be an adventurer like you until...");
            potentialFeelings.add("Make 900,000 more and you could get a cape.");
            potentialFeelings.add("It won't work as a cursor, no matter how much you wave it around.");
        }
        else if(heldItem.getItem().equals(Items.baked_potato))
        {
            if(player.getFoodStats().getFoodLevel() < 10)
                potentialFeelings.add("Po-ta-toh no, this could be bad.");
            else if(player.getFoodStats().needFood())
                potentialFeelings.add("This is spudder nonsense. Just eat it.");
            else
                potentialFeelings.add("Me and a potato: there's a starch contrast.");
        }
        else if(heldItem.getItem().equals(Items.bed))
        {
            potentialFeelings.add("A sleepover? Maybe if you have an extra bed somewhere.");
            potentialFeelings.add("No thanks, I'm too excited to rest.");
            if(player.dimension == -1 || player.dimension == 1)
                potentialFeelings.add("How stupid do you think I am?");
        }
        else if(heldItem.getItem().equals(Items.beef) || heldItem.getItem().equals(Items.chicken) || heldItem.getItem().equals(Items.porkchop))
        {
            potentialFeelings.add("I like rare meat, but that's way too extreme.");
            potentialFeelings.add("I'm not eating that. Well I couldn't either way, but you get the idea.");
            if(player.getFoodStats().needFood())
                potentialFeelings.add("I know you're hungry, but that's just disgusting.");
        }
        else if(heldItem.getItem().equals(Items.blaze_powder))
        {
            potentialFeelings.add("Blaze powder eh? Probably one of my top 5 favorite powders.");
            potentialFeelings.add("Do you just grind random substances for entertainment?");
            potentialFeelings.add("That stuff would make the hottest bowl of curry ever.");
        }
        else if(heldItem.getItem().equals(Items.boat))
        {
            potentialFeelings.add("Would this be what they call a 'shipfic' nowadays?");
            potentialFeelings.add("You and that boat? I guess I'd ship it.");
            potentialFeelings.add("I'm getting a sinking feeling.");
        }
        else if(heldItem.getItem().equals(Items.book))
        {
            potentialFeelings.add("I'll wait for the movie.");
            potentialFeelings.add("Ya know, just carrying that thing doesn't make you look any smarter.");
            potentialFeelings.add("Didja know book spelled backwards is koob? The things books can teach you...");
        }
        else if(heldItem.getItem().equals(Items.blaze_rod))
        {
            potentialFeelings.add("Somewhere a piece of coal is wallowing in it's inferiority.");
            potentialFeelings.add("How you brew potions with this is beyond me.");
            potentialFeelings.add("The bane of equivalent exchange.");
        }
        else if(heldItem.getItem().equals(Items.bow))
        {
            potentialFeelings.add("No present is fully wrapped without one.");
            potentialFeelings.add("Too bad it's not soggy, or you could call it a rainbow.");
            potentialFeelings.add("It's saying it's lost it's sheep.");
        }
        else if(heldItem.getItem().equals(Items.bowl))
        {
            potentialFeelings.add("How could you possibly find that item more interesting than me?");
            potentialFeelings.add("Hobbies include: swimming, fishing and bowling");
            potentialFeelings.add("You could turn it upside-down and call it a bowler hat.");
        }
        else
        {
            potentialFeelings.add("You spend so much time with your " + Colors.RED + "OTHER" + Colors.GREY + " items...");
            potentialFeelings.add("Do you EVER clean your inventory!?");
            potentialFeelings.add("It's not like I LIKE you or anything...");
            potentialFeelings.add("Did you know we ingots love watermelons?");
        }


        if(player.getHealth() <= 5.0F && player.getHealth() > 1.0F)
        {
            potentialFeelings.add("You're not lookin' too hot.");
            potentialFeelings.add("I'm glad I don't have HP. That looks painful.");

            if(player.getFoodStats().getFoodLevel() < 18)
            {
                potentialFeelings.add("You're looking a bit peckish there. I can't help though.");
                potentialFeelings.add("You might wanna eat something rather than staring at me.");
            }
            else
            {
                potentialFeelings.add("Not that I'm telling you how to live your life, but you should really rest.");
                potentialFeelings.add("Survival is pretty tough for you biological types, isn't it?");
            }
        }

        if(player.getHealth() <= 1.0F)
        {
            potentialFeelings.add("Stay away from lava, I don't wanna fall in when you die...");
            potentialFeelings.add("Woah! Get help! Seriously. MEDIC!");
            potentialFeelings.add("OH MY GOSH! What do I do?! Um...okay, just breathe and um... try not to move around too much.");
            potentialFeelings.add("Don't die. That's an order.");
            potentialFeelings.add("Look, I'm you:" + Colors.AQUA + "'Hi ingot! I need intensive care, but I'm chatting with you instead!'");

            if(player.getFoodStats().getFoodLevel() < 18)
            {
                potentialFeelings.add("You should probably know that ingots aren't edible.");
                potentialFeelings.add("Don't eat me! We ingots taste terrible; like rotten fish but worse.");
            }
            else
            {
                potentialFeelings.add("Come on, you can pull through this!");
                potentialFeelings.add("Oww, that's painful just to look at.");
                potentialFeelings.add("You should probably be looking for more food, that meal won't last forever.");
            }
        }

        if(potentialFeelings.size() <= 0)
            return "";
        if(index < 0 || index >= potentialFeelings.size())
            index = random.nextInt(potentialFeelings.size());
        return potentialFeelings.get(index);
    }
}
