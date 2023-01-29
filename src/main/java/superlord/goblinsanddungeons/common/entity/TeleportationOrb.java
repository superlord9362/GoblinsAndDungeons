package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class TeleportationOrb extends ThrowableItemProjectile {
	public TeleportationOrb(EntityType<? extends TeleportationOrb> p_i50159_1_, Level p_i50159_2_) {
		super(p_i50159_1_, p_i50159_2_);
	}

	public TeleportationOrb(Level worldIn, LivingEntity throwerIn) {
		super(EntityInit.TELEPORTATION_ORB.get(), throwerIn, worldIn);
	}

	public TeleportationOrb(Level worldIn, double x, double y, double z) {
		super(EntityInit.TELEPORTATION_ORB.get(), x, y, z, worldIn);
	}

	protected Item getDefaultItem() {
		return ItemInit.TELEPORTATION_ORB.get();
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
		this.level.addParticle(ParticleInit.TELEPORTATION_ORB, true, this.getX() - 0.1, this.getY() - 1, this.getZ() - 0.1 , 0, 0, 0);
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
			LivingEntity entity = (LivingEntity) hitEntity;
			Level level = entity.level;
			if (!level.isClientSide) {
				double d0 = entity.getX();
				double d1 = entity.getY();
				double d2 = entity.getZ();
				for(int i = 0; i < 16; ++i) {
					double d3 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
					double d4 = Mth.clamp(hitEntity.getY() + (double)(entity.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
					double d5 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
					net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(entity, d3, d4, d5);
					if (entity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
			               SoundEvent soundevent = entity instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
			               level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
			               entity.playSound(soundevent, 1.0F, 1.0F);
			               break;
			            }
				}
			}
		}
		level.playSound((Player) null, new BlockPos(this.getX(), this.getY(), this.getZ()), SoundInit.SOUL_BULLET_COLLISION, SoundSource.HOSTILE, 1, 1);
		this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			level.playSound((Player) null, new BlockPos(this.getX(), this.getY(), this.getZ()), SoundInit.SOUL_BULLET_COLLISION, SoundSource.HOSTILE, 1, 1);
			this.playSound(SoundInit.SOUL_BULLET_COLLISION, 1, 1);
			this.remove(RemovalReason.DISCARDED);
		}

	}
}
