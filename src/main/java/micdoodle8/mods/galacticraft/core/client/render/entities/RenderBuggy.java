package micdoodle8.mods.galacticraft.core.client.render.entities;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityBuggy;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBuggy extends Render<EntityBuggy>
{
    private OBJModel.OBJBakedModel mainModel;
    private OBJModel.OBJBakedModel radarDish;
    private OBJModel.OBJBakedModel wheelBackLeft;
    private OBJModel.OBJBakedModel wheelBackRight;
    private OBJModel.OBJBakedModel wheelFrontLeft;
    private OBJModel.OBJBakedModel wheelFrontRight;
    private OBJModel.OBJBakedModel cargoLeft;
    private OBJModel.OBJBakedModel cargoMid;
    private OBJModel.OBJBakedModel cargoRight;

    private void updateModels()
    {
        if (mainModel == null)
        {
            try
            {
                OBJModel model = (OBJModel) ModelLoaderRegistry.getModel(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "buggy.obj"));
                model = (OBJModel) model.process(ImmutableMap.of("flip-v", "true"));

                Function<ResourceLocation, TextureAtlasSprite> spriteFunction = new Function<ResourceLocation, TextureAtlasSprite>() {
                    @Override
                    public TextureAtlasSprite apply(ResourceLocation location) {
                        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
                    }
                };
                mainModel = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("MainBody"), false), DefaultVertexFormats.ITEM, spriteFunction);
                radarDish = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("RadarDish_Dish"), false), DefaultVertexFormats.ITEM, spriteFunction);
                wheelBackLeft = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("Wheel_Back_Left"), false), DefaultVertexFormats.ITEM, spriteFunction);
                wheelBackRight = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("Wheel_Back_Right"), false), DefaultVertexFormats.ITEM, spriteFunction);
                wheelFrontLeft = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("Wheel_Front_Left"), false), DefaultVertexFormats.ITEM, spriteFunction);
                wheelFrontRight = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("Wheel_Front_Right"), false), DefaultVertexFormats.ITEM, spriteFunction);
                cargoLeft = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("CargoLeft"), false), DefaultVertexFormats.ITEM, spriteFunction);
                cargoMid = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("CargoMid"), false), DefaultVertexFormats.ITEM, spriteFunction);
                cargoRight = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("CargoRight"), false), DefaultVertexFormats.ITEM, spriteFunction);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public RenderBuggy()
    {
        super(FMLClientHandler.instance().getClient().getRenderManager());
        this.shadowSize = 1.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBuggy par1Entity)
    {
        return new ResourceLocation("missing");
    }

    @Override
    public void doRender(EntityBuggy entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        final float var24 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * par9;
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-var24, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.41F, 0.41F, 0.41F);
//        this.bindTexture(RenderBuggy.buggyTextureWheel);

        this.updateModels();

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.locationBlocksTexture);

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        float rotation = entity.wheelRotationX;

        // Front wheels
        GL11.glPushMatrix();
//        GL11.glTranslatef(0.0F, 1.0F, -2.7F);
        GL11.glRotatef(entity.wheelRotationZ, 0, 1, 0);
        GL11.glRotatef(rotation, 1, 0, 0);
        this.drawBakedModel(wheelFrontRight);
        this.drawBakedModel(wheelFrontLeft);
//        GL11.glTranslatef(1.4F, 0.0F, 0.0F);
//        this.modelBuggyWheelRight.renderPart("WheelRight_Wheel");
//        GL11.glTranslatef(-2.8F, 0.0F, 0.0F);
//        this.modelBuggyWheelLeft.renderPart("WheelLeft_Wheel");
        GL11.glPopMatrix();

        // Back wheels
        GL11.glPushMatrix();
//        GL11.glTranslatef(0.0F, 1.0F, 3.6F);
        GL11.glRotatef(-entity.wheelRotationZ, 0, 1, 0);
        GL11.glRotatef(rotation, 1, 0, 0);
        this.drawBakedModel(wheelBackRight);
        this.drawBakedModel(wheelBackLeft);
//        GL11.glTranslatef(2.0F, 0.0F, 0.0F);
//        this.modelBuggyWheelRight.renderPart("WheelRight_Wheel");
//        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
//        this.modelBuggyWheelLeft.renderPart("WheelLeft_Wheel");
        GL11.glPopMatrix();

//        this.bindTexture(RenderBuggy.buggyTextureBody);
//        this.modelBuggy.renderPart("MainBody");
        this.drawBakedModel(mainModel);

        // Radar Dish
        GL11.glPushMatrix();
        GL11.glTranslatef(-1.178F, 4.1F, -2.397F);
        GL11.glRotatef((float)Math.sin(entity.ticksExisted * 0.05) * 50.0F, 1, 0, 0);
        GL11.glRotatef((float)Math.cos(entity.ticksExisted * 0.1) * 50.0F, 0, 0, 1);
//        this.modelBuggy.renderPart("RadarDish_Dish");
        this.drawBakedModel(radarDish);
        GL11.glPopMatrix();

//        this.bindTexture(RenderBuggy.buggyTextureStorage);

        if (entity.buggyType > 0)
        {
            this.drawBakedModel(cargoLeft);
//            this.modelBuggy.renderPart("CargoLeft");

            if (entity.buggyType > 1)
            {
                this.drawBakedModel(cargoMid);
//                this.modelBuggy.renderPart("CargoMid");

                if (entity.buggyType > 2)
                {
                    this.drawBakedModel(cargoRight);
//                    this.modelBuggy.renderPart("CargoRight");
                }
            }
        }

        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    private void drawBakedModel(IFlexibleBakedModel model)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUADS, model.getFormat());

        for(BakedQuad bakedquad : model.getGeneralQuads())
            LightUtil.renderQuadColor(worldrenderer, bakedquad, -1);

        tessellator.draw();
    }
}
