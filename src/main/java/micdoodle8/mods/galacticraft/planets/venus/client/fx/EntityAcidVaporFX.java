package micdoodle8.mods.galacticraft.planets.venus.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityAcidVaporFX extends EntityFX
{
    float smokeParticleScale;

    public EntityAcidVaporFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double motionX, double motionY, double motionZ, float p_i46348_14_)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += motionX / 10.0F;
        this.motionY += motionY;
        this.motionZ += motionZ / 10.0F;
        this.particleRed = (float)(Math.random() * 0.10000001192092896D + 0.8);
        this.particleGreen = particleRed;
        this.particleBlue = (float)(Math.random() * 0.10000001192092896D);
        this.particleAlpha = 0.5F;
        this.particleScale *= 0.75F;
        this.particleScale *= p_i46348_14_;
        this.smokeParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int)((float)this.particleMaxAge * p_i46348_14_);
        this.noClip = false;
    }

    @Override
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
    {
        GlStateManager.disableLighting();
        float f = (float) Math.pow(this.particleAge / 11.0, 2.0F);
        f = Math.max(f, 0.1F);
        this.particleAlpha = this.particleAlpha * 0.99F;
        this.particleScale = this.smokeParticleScale * f;
        super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.motionY += 0.004D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        this.motionY *= 0.9899999785423279D;
        this.motionX *= 1.001D;
        this.motionZ *= 1.001D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}