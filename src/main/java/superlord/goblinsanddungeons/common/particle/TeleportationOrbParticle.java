package superlord.goblinsanddungeons.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeleportationOrbParticle extends TextureSheetParticle {
	
	protected TeleportationOrbParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale, SpriteSet spriteWithAge) {
		super(world, x, y, z, 0.1F, 0.1F, 0.1F);
	}


	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			TeleportationOrbParticle teleportationorbpartcile = new TeleportationOrbParticle(worldIn, x, y, z, xSpeed, 0.01, zSpeed, 1.5F, this.spriteSet);
			teleportationorbpartcile.pickSprite(spriteSet);
			return teleportationorbpartcile;
		}
	}


	@Override
	public ParticleRenderType getRenderType() {
	      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

}
