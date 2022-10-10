package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import superlord.goblinsanddungeons.common.entity.ai.GoomSmokeGoal;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class GoomEntity extends GoblinEntity {
	private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(GoomEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(GoomEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(GoomEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BLOWN = SynchedEntityData.defineId(GoomEntity.class, EntityDataSerializers.BOOLEAN);
	@SuppressWarnings("unused")
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int timeTillBomb = 0;

	public GoomEntity(EntityType<? extends GoomEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new GoomSmokeGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}


	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, (double)0.275F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 25.0D);
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.GOOM_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.GOOM_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.GOOM_DEATH;
	}

	public boolean doHurtTarget(Entity entityIn) {
		boolean flag = super.doHurtTarget(entityIn);
		if (flag) {
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
				entityIn.setSecondsOnFire(2 * (int)f);
			}
		}

		return flag;
	}

	public boolean isBlownUp() {
		return this.entityData.get(BLOWN);
	}

	public void setBlownUp(boolean isBlownUp) {
		this.entityData.set(BLOWN, isBlownUp);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CLIMBING, (byte)0);
		this.entityData.define(STATE, -1);
		this.entityData.define(IGNITED, false);
		this.entityData.define(BLOWN, false);
	}

	public void tick() {
		super.tick();
		if (this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if (this.hasIgnited()) {
				this.setGoomState(1);
			}

			int i = this.getGoomState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundInit.GOOM_WARNING, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				if (!this.isBlownUp()) {
					this.explode();
				}
			}
			if (this.isBlownUp()) {
				this.timeTillBomb++;
				if (this.timeTillBomb >= 6000) {
					this.ignite(false);
					this.setBlownUp(false);
					BlockPos pos = new BlockPos (this.getX(), this.getY(), this.getZ());
					level.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.HOSTILE, 0.3F, 0.5F);
				}
			}
		}

	}
	
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setBlownUp(compound.getBoolean("IsBlownUp"));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compound) {
		spawnData = super.finalizeSpawn(worldIn, difficulty, reason, spawnData, compound);
		return spawnData;
	}
	
	public int getGoomState() {
		return this.entityData.get(STATE);
	}

	/**
	 * Sets the state of goom, -1 to idle and 1 to be 'in fuse'
	 */
	public void setGoomState(int state) {
		this.entityData.set(STATE, state);
	}

	protected InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			this.level.playSound(p_230254_1_, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
			if (!this.level.isClientSide) {
				this.ignite(true);
				itemstack.hurtAndBreak(1, p_230254_1_, (player) -> {
					player.broadcastBreakEvent(p_230254_2_);
				});
			}

			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			return super.mobInteract(p_230254_1_, p_230254_2_);
		}
	}

	private void explode() {
		if (!this.level.isClientSide) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
			areaeffectcloudentity.setParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE);
			this.setBlownUp(true);
			BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
			level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 0.3F, 0.5F);
			this.level.addFreshEntity(areaeffectcloudentity);
		}

	}

	public boolean hasIgnited() {
		return this.entityData.get(IGNITED);
	}

	public void ignite(boolean hasIgnited) {
		this.entityData.set(IGNITED, hasIgnited);
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.GOOM_SPAWN_EGG.get());
	}
	
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getMobType() == CreatureAttributeInit.GOBLIN) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}
	
	public class PostExplosionAttackGoal extends MeleeAttackGoal {

		public PostExplosionAttackGoal(PathfinderMob creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		public boolean canUse() {
			if (GoomEntity.this.isBlownUp() && super.canUse()) return true;
			else return false;
		}
		
		public boolean canContinueToUse() {
			if (!GoomEntity.this.isBlownUp() || !super.canContinueToUse()) return false;
			else return true;
		}
		
	}

}
