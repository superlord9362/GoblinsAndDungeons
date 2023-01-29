package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class ExplosiveOrb extends ThrowableItemProjectile {
	public ExplosiveOrb(EntityType<? extends ExplosiveOrb> p_i50159_1_, Level p_i50159_2_) {
		super(p_i50159_1_, p_i50159_2_);
	}

	public ExplosiveOrb(Level worldIn, LivingEntity throwerIn) {
		super(EntityInit.EXPLOSIVE_ORB.get(), throwerIn, worldIn);
	}

	public ExplosiveOrb(Level worldIn, double x, double y, double z) {
		super(EntityInit.EXPLOSIVE_ORB.get(), x, y, z, worldIn);
	}

	protected Item getDefaultItem() {
		return ItemInit.EXPLOSIVE_ORB.get();
	}

	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			this.spawnParticles(8);
			if (this.tickCount >= 300) {
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	private void spawnParticles(int particleCount) {
		this.level.addParticle(ParticleInit.BLINDNESS_ORB, true, this.getX() - 0.1, this.getY() - 1, this.getZ() - 0.1 , 0, 0, 0);
	}

	@Override
	protected float getGravity() {
		return 0.0F;
	}

	@Nonnull
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity hitEntity = result.getEntity();
		if (hitEntity instanceof LivingEntity) {
			hitEntity.hurt(DamageSource.indirectMagic(this, this.getOwner()), 4);
			this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.BlockInteraction.NONE);
		}
		level.playSound((Player) null, new BlockPos(this.getX(), this.getY(), this.getZ()), SoundInit.SOUL_BULLET_COLLISION, SoundSource.HOSTILE, 1, 1);
		this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			level.playSound((Player) null, new BlockPos(this.getX(), this.getY(), this.getZ()), SoundInit.SOUL_BULLET_COLLISION, SoundSource.HOSTILE, 1, 1);
			this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
			this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.BlockInteraction.NONE);
			this.remove(RemovalReason.DISCARDED);
		}

	}
}
