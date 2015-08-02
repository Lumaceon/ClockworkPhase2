package lumaceon.mods.clockworkphase2.client.render.elements.world;

import net.minecraft.world.World;

public abstract class WorldRenderElement
{
    private static int nextRenderID = 0;
    public static int getNextRenderID() {
        int nextID = nextRenderID;
        ++nextRenderID;
        return nextID;
    }

    public World world;
    public int xPos, yPos, zPos;

    public WorldRenderElement(World world, int x, int y, int z) {
        this.world = world;
        xPos = x;
        yPos = y;
        zPos = z;
    }

    public abstract void render(double x, double y, double z);

    public boolean alreadyExists(WorldRenderElement wre) {
        return wre.xPos == xPos && wre.yPos == yPos && wre.zPos == zPos;
    }

    public boolean isSameWorld(World world) {
        return world.equals(this.world);
    }
}