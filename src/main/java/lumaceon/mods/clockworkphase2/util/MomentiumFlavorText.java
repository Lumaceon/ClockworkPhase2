package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class MomentiumFlavorText
{
    public static boolean shouldProgress = false;
    private static int dialogueIndex = 0;
    private static int mainStoryPath = 0;
    private static int subStoryPath = 0;

    private static int nextDialogueIndex = 0;
    private static int nextMainStoryPath = 0;
    private static int nextSubStoryPath = 0;

    public static void speakToMoni(ItemStack item, EntityPlayer player, List list)
    {
        if(shouldProgress)
        {
            dialogueIndex = nextDialogueIndex;
            mainStoryPath = nextMainStoryPath;
            subStoryPath = nextSubStoryPath;
        }

        switch(mainStoryPath)
        {
            case 0:
                introduction(item, player, list, INGOT.MONI);
                break;
            case 1:
                break;
        }
    }

    public static void speakToEtna(ItemStack item, EntityPlayer player, List list)
    {
        if(shouldProgress)
        {
            dialogueIndex = nextDialogueIndex;
            mainStoryPath = nextMainStoryPath;
            subStoryPath = nextSubStoryPath;
        }

        switch(mainStoryPath)
        {
            case 0:
                introduction(item, player, list, INGOT.ETNA);
                break;
        }
    }

    private static void biomeTrekking(ItemStack item, EntityPlayer player, List list)
    {
        switch(dialogueIndex)
        {
            case 0:
                list.add("So...where to start...");
                progress();
                break;
            case 1:
                list.add("I've always wanted to see a desert.");
                progress();
                break;
            case 2:
                list.add("I've heard they're delicious.");
                progress();
                break;
            case 3:
                list.add("Why do you meat bodies eat sand?");
                list.add("That doesn't seem delicious to me.");
                progress();
                break;
            case 4:
                list.add("Maybe I'll understand when I see it.");
                progress();
                break;
            case 5:
                list.add("So I guess that'll be my first official request");
                progress();
                break;
            case 6:
                list.add("Find a desert!");
                progress();
                break;
            case 7:
                list.add("Oh, and I'll obviously want to see it.");
                progress();
                break;
            case 8:
                list.add("So I guess you'll have to....ya know...");
                list.add(Colors.LIGHT_PURPLE + "....pick me up....");
                progress();
                break;
            case 9:
                list.add("Don't get any funny ideas.");
                progress();
                break;
            case 10:
                list.add("It's JUST so I can see the desert.");
                list.add("Then you'll be putting me right back down.");
                progress();
                break;
            case 11:
                list.add("Well, talk to me again when you're ready.");
                progress();
                break;
            default:
                break;
        }
    }

    private static void introduction(ItemStack item, EntityPlayer player, List list, INGOT ingot)
    {
        if(ingot.equals(INGOT.MONI))
        {
            if(player.getHeldItem() != null && player.getHeldItem().equals(item)) {
                list.add(Colors.RED + "HEY! Hands off!");
                list.add(Colors.RED + "I barely even know you yet...");
                return;
            }

            switch(dialogueIndex)
            {
                case 0:
                    list.add("Wow, you actually managed to craft me.");
                    progress();
                    break;
                case 1:
                    list.add("Well isn't that clever of you?");
                    progress();
                    break;
                case 2:
                    list.add("I guess I should introduce myself...");
                    list.add("I'm Moni the Momentium Ingot!");
                    progress();
                    break;
                case 3:
                    list.add("Oh, you're still there?");
                    progress();
                    break;
                case 4:
                    list.add("What do you want from me?!");
                    progress();
                    break;
                case 5:
                    list.add("Have you just never seen a momentium ingot before?");
                    progress();
                    break;
                case 6:
                    list.add("I AM pretty fabulous compared to other ingots.");
                    progress();
                    break;
                case 7:
                    list.add("Not that I'm trying to brag or anything.");
                    progress();
                    break;
                case 8:
                    list.add("Do you really have nothing better to do...?");
                    progress();
                    break;
                case 9:
                    list.add("Wait! I...umm...you can't go just yet...");
                    progress();
                    break;
                case 10:
                    list.add("Why?! Because...um...");
                    progress();
                    break;
                case 11:
                    list.add("Err....");
                    progress();
                    break;
                case 12:
                    list.add("...");
                    progress();
                    break;
                case 13:
                    list.add("Okay FINE, it's because I need your help.");
                    progress();
                    break;
                case 14:
                    list.add("THERE, I admitted it. Are you happy now?");
                    progress();
                    break;
                case 15:
                    list.add("The thing is, being an ingot, I can't move.");
                    progress();
                    break;
                case 16:
                    list.add("...but I've always loved seeing new places.");
                    progress();
                    break;
                case 17:
                    list.add("Well not seeing, but I can still...");
                    list.add("Actually never mind, that's not important.");
                    list.add("Gosh, this feels so awkward...");
                    progress();
                    break;
                case 18:
                    list.add("The important thing is, we'd make a good team.");
                    progress();
                    break;
                case 19:
                    list.add("I have brains and you have your mobile meat body.");
                    progress();
                    break;
                case 20:
                    list.add("It's like a match made in meaty heaven.");
                    progress();
                    break;
                case 21:
                    list.add("I'll tell you what, if you help me travel the world.");
                    list.add("I might be able to make it worth your while.");
                    progress();
                    break;
                case 22:
                    list.add("Or maybe not, but I know you're type.");
                    list.add("Just the potential is enough to get you going.");
                    progress();
                    break;
                case 23:
                    list.add("If you're interested, talk to me again.");
                    list.add("If not, well...");
                    list.add("I'll still be here...");
                    list.add("I can't move on my own after all.");
                    progress();
                    break;
                case 24:
                    list.add("Alright, fantastic! Thank you so much!");
                    progress();
                    break;
                case 25:
                    list.add("Are you as excited as I am?");
                    list.add("I doubt it!");
                    list.add("I haven't been this excited since I was first crafted!");
                    progress();
                    break;
                case 26:
                    list.add("Okay, just so I know you're up to the task...");
                    list.add("...I prepared a little test for you.");
                    progress();
                    break;
                case 27:
                    list.add("I have a sister ingot: Etna.");
                    list.add("Though you'd probably know her as the Eternium Ingot.");
                    progress();
                    break;
                case 28:
                    list.add("She doesn't say much, but she's really pretty nice.");
                    list.add("If you get her to speak to you that is.");
                    progress();
                    break;
                case 29:
                    list.add("Ready? Here's your test...");
                    progress();
                    break;
                case 30:
                    switch(subStoryPath)
                    {
                        case 0: //First time
                            if(inventoryHasItem(ModItems.ingotEternium, player.inventory))
                            {
                                list.add("You have to fi-- wait a minute...");
                                list.add("Etna?! How long have you been laying there?");
                                progress();
                                break;
                            }
                            else
                            {
                                list.add("Find Etna and place her in your inventory with me.");
                                list.add("You already found me, so this should be easy enough.");
                                list.add("Once you have her, talk to me again.");
                                progressSubStory();
                                break;
                            }
                        case 1: //Second time.
                            if(inventoryHasItem(ModItems.ingotEternium, player.inventory))
                            {
                                list.add("Nice work, meat body; that was actually pretty fast.");
                                list.add("Etna! It's me, Moni!");
                                progress();
                                break;
                            }
                            else
                            {
                                list.add("Didja forget what you were doing?");
                                list.add("Find Etna the Eternium Ingot.");
                                list.add("Put her in your inventory with me.");
                                list.add("Then talk to me again.");
                                progressSubStory();
                                break;
                            }
                        case 2: //3rd time and up.
                            if(inventoryHasItem(ModItems.ingotEternium, player.inventory))
                            {
                                list.add("Well that took you long enough...");
                                list.add("Etna! It's me, Moni!");
                                progress();
                                break;
                            }
                            else
                            {
                                list.add("I'm really starting to question your intelligence...");
                                list.add(Colors.RED + "Pay attention!");
                                list.add("Find an ETERNIUM INGOT.");
                                list.add("Put the ingot in YOUR INVENTORY.");
                                list.add("Put ME in YOUR INVENTORY.");
                                list.add("THEN talk to me.");
                                break;
                            }
                    }
                    break;
                case 32:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                    {
                        list.add("I know, right? It's been forever...");
                        progress();
                    }
                    else
                        list.add(Colors.DARK_GREY + "*Moni is looking around for Etna*");
                    break;
                case 33:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                    {
                        list.add("Listen to this though:");
                        list.add("Meat body here agreed to take me exploring!");
                        progress();
                    }
                    else
                        list.add(Colors.DARK_GREY + "*Moni is looking around for Etna*");
                    break;
                case 35:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                    {
                        list.add("Okay, okay, I'll be careful.");
                        list.add("Thanks Etna!");
                        progress();
                    }
                    else
                        list.add(Colors.DARK_GREY + "*Moni is looking around for Etna*");
                    break;
                case 36:
                    list.add("Meat body, now I can officially say...");
                    progress();
                    break;
                case 37:
                    list.add("Test passed!");
                    progress();
                    break;
                case 38:
                    list.add("I 'see' that confusion on your face.");
                    progress();
                    break;
                case 39:
                    list.add("Etna has her own way of talking...");
                    progress();
                    break;
                case 40:
                    list.add("Maybe it's too subtle for meat bodies?");
                    progress();
                    break;
                case 41:
                    list.add("Anyways, we're partners now!");
                    progress();
                    break;
                case 42:
                    list.add("I'd do a happy dance, but, ya know; ingot.");
                    nextDialogueIndex = 0;
                    nextMainStoryPath = 1;
                    nextSubStoryPath = 0;
                    break;
                default:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                        list.add(Colors.DARK_GREY + "*Moni is listening to Etna*");
                    else
                        list.add(Colors.DARK_GREY + "*Moni is looking around for Etna*");
                    break;
            }
        }
        else if(ingot.equals(INGOT.ETNA))
        {
            switch(dialogueIndex)
            {
                case 31:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                    {
                        list.add("\".....!\"");
                        progress();
                    }
                    else
                        list.add(Colors.DARK_GREY + "*Etna is looking around for Moni*");
                    break;
                case 34:
                    if(inventoryHasItem(ModItems.ingotEternium, player.inventory) && inventoryHasItem(ModItems.ingotMomentium, player.inventory))
                    {
                        list.add("\"..,....;.....'...!\"");
                        progress();
                    }
                    else
                        list.add(Colors.DARK_GREY + "*Etna is looking around for Moni*");
                    break;
                default:
                    list.add("\"...\"");
                    break;
            }
        }
    }

    private static boolean inventoryHasItem(Item item, IInventory inventory)
    {
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            ItemStack i = inventory.getStackInSlot(n);
            if(i != null && i.getItem() != null && item != null && i.getItem().equals(item))
                return true;
        }
        return false;
    }

    private static void progress() {
        if(shouldProgress)
        {
            ++nextDialogueIndex;
            nextSubStoryPath = 0;
            shouldProgress = false;
        }
    }

    private static void progressSubStory() {
        if(shouldProgress)
        {
            ++nextSubStoryPath;
            shouldProgress = false;
        }
    }

    public static void reset() {
        mainStoryPath = 0;
        subStoryPath = 0;
        dialogueIndex = 0;
        nextMainStoryPath = 0;
        nextDialogueIndex = 0;
        nextSubStoryPath = 0;
    }

    public enum INGOT
    {
        MONI, ETNA, PARA
    }
}
