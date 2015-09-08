package lumaceon.mods.clockworkphase2.client.render.elements.world;

import lumaceon.mods.clockworkphase2.client.FakeSchematicWorld;
import lumaceon.mods.clockworkphase2.client.render.SchematicWorldRenderer;
import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class WorldRenderElementSchematic extends WorldRenderElement
{
    public SchematicUtility.Schematic schematic;
    public Minecraft mc;
    private RenderBlocks blockRenderer;

    public WorldRenderElementSchematic(World world, int x, int y, int z) {
        super(world, x, y, z);
        this.mc = Minecraft.getMinecraft();
        blockRenderer = new RenderBlocks();
    }

    @Override
    public void render(double x, double y, double z)
    {
        if(mc == null)
            mc = Minecraft.getMinecraft();
        if(schematic != null && world != null)
        {
            GL11.glPushMatrix();
            //GL11.glTranslated(x, y, z);
            Tessellator tessy = Tessellator.instance;
            tessy.startDrawingQuads();
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            int index = 0;
            /*for(short schematicY = 0; schematicY < schematic.height; schematicY++)
                for(short schematicZ = 0; schematicZ < schematic.length; schematicZ++)
                    for(short schematicX = 0; schematicX < schematic.width; schematicX++)
                    {
                        Block block = Block.getBlockById(schematic.blocks[index]);
                        if(block.getMaterial() != Material.air)
                        {
                            tessy.addVertexWithUV(schematicX, schematicY, schematicZ, 0, 0);
                            tessy.addVertexWithUV(schematicX, schematicY, schematicZ + 1, 0, 1);
                            tessy.addVertexWithUV(schematicX + 1, schematicY, schematicZ + 1, 1, 1);
                            tessy.addVertexWithUV(schematicX + 1, schematicY, schematicZ, 1, 0);

                            tessy.addVertexWithUV(schematicX + 1, schematicY, schematicZ, 0, 0);
                            tessy.addVertexWithUV(schematicX + 1, schematicY, schematicZ + 1, 0, 1);
                            tessy.addVertexWithUV(schematicX, schematicY, schematicZ + 1, 1, 1);
                            tessy.addVertexWithUV(schematicX, schematicY, schematicZ, 1, 0);
                            blockRenderer.renderBlockByRenderType(block, schematicX, schematicY, schematicZ);
                        }
                        index++;
                    }*/
            tessy.draw();
            GL11.glPopMatrix();
        }
    }

    public void setSchematic(SchematicUtility.Schematic schematic, World world, int x, int y, int z)
    {
        if(schematic != null)
        {
            this.schematic = schematic;
        }
        if(world != null)
            this.world = world;
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
}
