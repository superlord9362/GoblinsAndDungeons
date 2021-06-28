package superlord.goblinsanddungeons.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GoblinSoulBulletParticle extends SpriteTexturedParticle {
	
	private final double bulletPosX;
	private final double bulletPosY;
	private final double bulletPosZ;
	
	public GoblinSoulBulletParticle(ClientWorld world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
		this.motionX = xSpeed;
		this.motionY = ySpeed;
		this.motionZ = zSpeed;
		this.posX = xCoord;
		this.posY = yCoord;
		this.posZ = zCoord;
		this.bulletPosX = this.posX;
		this.bulletPosY = this.posY;
		this.bulletPosZ = this.posZ;
		this.maxAge = (int)(Math.random() * 10.0D) + 40;
	}
	
	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	@Override
	public void move(double x, double y, double z) {
		this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
		this.resetPositionToBB();
	}
	
	@Override
	public float getScale(float partialTick) {
		float f = ((float)this.age + partialTick) / (float)this.maxAge;
		f = 1.0F - f;
		f = f* f;
		f = 1.0F - f;
		return this.particleScale * f;
	}
	
	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			float f = (float)this.age / (float)this.maxAge;
			float f1 = -f + f * f * 2.0F;
			float f2 = 1.0F - f1;
			this.posX = this.bulletPosX + this.motionX * (double)f2;
			this.posY = this.bulletPosY + this.motionY * (double)f2 + (double) (1.0F - f);
			this.posZ = this.bulletPosZ + this.motionZ * (double)f2;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;
		
		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}
		
		@Override
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			GoblinSoulBulletParticle bulletParticle = new GoblinSoulBulletParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			bulletParticle.selectSpriteRandomly(this.spriteSet);
			return bulletParticle;		}
	}

}
