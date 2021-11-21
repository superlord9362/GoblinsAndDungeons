package superlord.goblinsanddungeons.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class GoblinSoulBulletEntity extends ProjectileItemEntity {
	public GoblinSoulBulletEntity(EntityType<? extends GoblinSoulBulletEntity> p_i50159_1_, World p_i50159_2_) {
		super(p_i50159_1_, p_i50159_2_);
	}

	public GoblinSoulBulletEntity(World worldIn, LivingEntity throwerIn) {
		super(EntityInit.GOBLIN_SOUL_BULLET.get(), throwerIn, worldIn);
	}

	public GoblinSoulBulletEntity(World worldIn, double x, double y, double z) {
		super(EntityInit.GOBLIN_SOUL_BULLET.get(), x, y, z, worldIn);
	}

	protected Item getDefaultItem() {
		return ItemInit.GOBLIN_SOUL_BULLET.get();
	}

	public void tick() {
		super.tick();
		if (this.world.isRemote) {
			this.spawnParticles(8);
			if (this.ticksExisted >= 300) {
				this.remove();
			}
		}
	}

	private void spawnParticles(int particleCount) {
		this.world.addParticle(ParticleInit.gobSoulBullet, true, this.getPosX() - 0.1, this.getPosY() - 1, this.getPosZ() - 0.1 , 0, 0, 0);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
		super.onEntityHit(p_213868_1_);
		Entity entity = p_213868_1_.getEntity();
		entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 4);
		((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 1));
		world.playSound((PlayerEntity) null, this.getPosition(), SoundInit.SOUL_BULLET_COLLISION, SoundCategory.HOSTILE, 1, 1);
		this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
	}

	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {
			world.playSound((PlayerEntity) null, this.getPosition(), SoundInit.SOUL_BULLET_COLLISION, SoundCategory.HOSTILE, 1, 1);
			this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
			this.world.setEntityState(this, (byte)3);
			this.remove();
		}

	}
}
