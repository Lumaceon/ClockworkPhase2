package lumaceon.mods.clockworkphase2.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTHelper
{
    public static boolean hasTag(ItemStack is, String keyName)
    {
        return is != null && is.stackTagCompound != null && is.stackTagCompound.hasKey(keyName);
    }

    public static void removeTag(ItemStack is, String keyName)
    {
        if(is.stackTagCompound != null)
            is.stackTagCompound.removeTag(keyName);
    }

    private static void initNBTTagCompound(ItemStack is)
    {
        if(is.stackTagCompound == null)
            is.setTagCompound(new NBTTagCompound());
    }

    public static void setTag(ItemStack is, String keyName, NBTBase nbt)
    {
        initNBTTagCompound(is);

        is.stackTagCompound.setTag(keyName, nbt);
    }

    public static NBTBase getTag(ItemStack is, String keyName)
    {
        initNBTTagCompound(is);

        if(!is.stackTagCompound.hasKey(keyName))
        {
            LIST.set(is, keyName, new NBTTagList());
        }

        return is.stackTagCompound.getTag(keyName);
    }

    public static class INVENTORY
    {
        public static void set(ItemStack is, String keyName, ItemStack[] inventory)
        {
            initNBTTagCompound(is);

            NBTTagList nbtList = new NBTTagList();
            for(int index = 0; index < inventory.length; index++)
            {
                if(inventory[index] != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    inventory[index].writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
                else
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    nbtList.appendTag(tag);
                }
            }
            LIST.set(is, keyName, nbtList);
        }

        public static ItemStack[] get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
            {
                return null;
            }

            NBTTagList nbt = LIST.get(is, keyName);
            ItemStack[] inventory;
            inventory = new ItemStack[nbt.tagCount()];

            for(int i = 0; i < nbt.tagCount(); ++i)
            {
                NBTTagCompound tagCompound = nbt.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("slot_index");
                if(slotIndex >= 0 && slotIndex < inventory.length)
                {
                    inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                }
            }
            return inventory;
        }
    }

    public static class LIST
    {
        public static void set(ItemStack is, String keyName, NBTTagList nbt)
        {
            initNBTTagCompound(is);

            is.stackTagCompound.setTag(keyName, nbt);
        }

        public static NBTTagList get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
            {
                set(is, keyName, new NBTTagList());
            }

            NBTBase returnValue = is.stackTagCompound.getTag(keyName);

            if(!(returnValue instanceof NBTTagList))
            {
                Logger.error("Method getTagList in NBTHelper attempted to load an invalid tag.");
                return new NBTTagList();
            }
            return (NBTTagList)returnValue;
        }
    }

    public static class STRING
    {
        public static void set(ItemStack is, String keyName, String keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setString(keyName, keyValue);
        }

        public static String get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, "");

            return is.stackTagCompound.getString(keyName);
        }
    }

    public static class BOOLEAN
    {
        public static void set(ItemStack is, String keyName, boolean keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setBoolean(keyName, keyValue);
        }

        public static boolean get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, false);

            return is.stackTagCompound.getBoolean(keyName);
        }
    }

    public static class BYTE
    {
        public static void set(ItemStack is, String keyName, byte keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setByte(keyName, keyValue);
        }

        public static byte get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, (byte) 0);

            return is.stackTagCompound.getByte(keyName);
        }
    }

    public static class SHORT
    {
        public static void set(ItemStack is, String keyName, short keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setShort(keyName, keyValue);
        }

        public static short get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, (short) 0);

            return is.stackTagCompound.getShort(keyName);
        }
    }

    public static class INT
    {
        public static void set(ItemStack is, String keyName, int keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setInteger(keyName, keyValue);
        }

        public static int get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, 0);

            return is.stackTagCompound.getInteger(keyName);
        }
    }

    public static class LONG
    {
        public static void set(ItemStack is, String keyName, long keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setLong(keyName, keyValue);
        }

        public static long get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, 0);

            return is.stackTagCompound.getLong(keyName);
        }
    }

    public static class FLOAT
    {
        public static void set(ItemStack is, String keyName, float keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setFloat(keyName, keyValue);
        }

        public static float get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, 0);

            return is.stackTagCompound.getFloat(keyName);
        }
    }

    public static class DOUBLE
    {
        public static void set(ItemStack is, String keyName, double keyValue)
        {
            initNBTTagCompound(is);
            is.stackTagCompound.setDouble(keyName, keyValue);
        }

        public static double get(ItemStack is, String keyName)
        {
            initNBTTagCompound(is);

            if(!is.stackTagCompound.hasKey(keyName))
                set(is, keyName, 0);

            return is.stackTagCompound.getDouble(keyName);
        }
    }
}
