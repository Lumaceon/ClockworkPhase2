package lumaceon.mods.clockworkphase2.client.render;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.World;
import java.util.List;

public class SchematicWorldRenderer extends WorldRenderer
{
    public SchematicWorldRenderer(World world, List tileEntities, int x, int y, int z, int glRenderListID) {
        super(world, tileEntities, x, y, z, glRenderListID);
    }
}
