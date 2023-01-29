package superlord.goblinsanddungeons.common.entity;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import superlord.goblinsanddungeons.init.EffectInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class Beholder extends Monster implements FlyingAnimal {
	private final BeholderPart[] subEntities;
	private final BeholderPart body;
	public final BeholderPart stalk1;
	private final BeholderPart stalk2;
	private final BeholderPart stalk3;
	private final BeholderPart stalk4;
	private final BeholderPart stalk5;
	public final double[][] positions = new double[64][3];
	public int posPointer = -1;
	public float stalk1Health = 20;
	public float stalk2Health = 20;
	public float stalk3Health = 20;
	public float stalk4Health = 20;
	public float stalk5Health = 20;
	public int stunTick = 0;
	public int attackTimer = 75;
	public int levitationAttackCooldown = 0;
	public int paralysisAttackCooldown = 0;
	public int teleportationAttackCooldown = 0;
	public int fireAttackCooldown = 0;
	public int blindnessAttackCooldown = 0;
	public int slownessAttackCooldown = 0;
	public int weaknessAttackCooldown = 0;
	public int witherAttackCooldown = 0;
	public int explosiveAttackCooldown = 0;
	public int energyAttackCooldown = 0;
	@Nullable
	private LivingEntity clientSideCachedAttackTarget;
	private static final EntityDataAccessor<Boolean> STALK_1_SHUT = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STALK_2_SHUT = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STALK_3_SHUT = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STALK_4_SHUT = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STALK_5_SHUT = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PHASE_1 = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PHASE_2 = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PHASE_3 = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.INT);


	private static final EntityDataAccessor<Boolean> LEVITATION_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PARALYSIS_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> TELEPORTATION_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FIRE_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BLINDNESS_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SLOWNESS_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> WEAKNESS_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> WITHER_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> EXPLOSIVE_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ENERGY_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SLAM_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CHARGE_ATTACK = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(Beholder.class, EntityDataSerializers.BOOLEAN);

	private final ServerBossEvent bossInfo = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);

	public Beholder(EntityType<? extends Beholder> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.moveControl = new Beholder.FlyingMoveControl(this, 32, true);
		this.body = new BeholderPart(this, "body", 1.8F, 1.8F);
		this.stalk1 = new BeholderPart(this, "stalk1", 0.5F, 0.5F);
		this.stalk2 = new BeholderPart(this, "stalk2", 0.5F, 0.5F);
		this.stalk3 = new BeholderPart(this, "stalk3", 0.5F, 0.5F);
		this.stalk4 = new BeholderPart(this, "stalk4", 0.5F, 0.5F);
		this.stalk5 = new BeholderPart(this, "stalk4", 0.5F, 0.5F);
		this.subEntities = new BeholderPart[]{this.body, this.stalk1, this.stalk2, this.stalk3, this.stalk4, this.stalk5};
	}

	public float getStalk1Health() {
		return this.stalk1Health;
	}

	private void setStalk1Health(float newStalk1Health) {
		this.stalk1Health = newStalk1Health;
	}

	public float getStalk2Health() {
		return this.stalk2Health;
	}

	private void setStalk2Health(float newStalk2Health) {
		this.stalk2Health = newStalk2Health;
	}

	public float getStalk3Health() {
		return this.stalk3Health;
	}

	private void setStalk3Health(float newStalk3Health) {
		this.stalk3Health = newStalk3Health;
	}

	public float getStalk4Health() {
		return this.stalk4Health;
	}

	private void setStalk4Health(float newStalk4Health) {
		this.stalk4Health = newStalk4Health;
	}

	public float getStalk5Health() {
		return this.stalk5Health;
	}

	private void setStalk5Health(float newStalk5Health) {
		this.stalk5Health = newStalk5Health;
	}

	public boolean isStalk1Shut() {
		return this.entityData.get(STALK_1_SHUT);
	}

	private void setStalk1Shut(boolean isStalk1Shut) {
		this.entityData.set(STALK_1_SHUT, isStalk1Shut);
	}

	public boolean isStalk2Shut() {
		return this.entityData.get(STALK_2_SHUT);
	}

	private void setStalk2Shut(boolean isStalk2Shut) {
		this.entityData.set(STALK_2_SHUT, isStalk2Shut);
	}

	public boolean isStalk3Shut() {
		return this.entityData.get(STALK_3_SHUT);
	}

	private void setStalk3Shut(boolean isStalk3Shut) {
		this.entityData.set(STALK_3_SHUT, isStalk3Shut);
	}

	public boolean isStalk4Shut() {
		return this.entityData.get(STALK_4_SHUT);
	}

	private void setStalk4Shut(boolean isStalk4Shut) {
		this.entityData.set(STALK_4_SHUT, isStalk4Shut);
	}

	public boolean isStalk5Shut() {
		return this.entityData.get(STALK_5_SHUT);
	}

	private void setStalk5Shut(boolean isStalk5Shut) {
		this.entityData.set(STALK_5_SHUT, isStalk5Shut);
	}

	public boolean isPhase1() {
		return this.entityData.get(PHASE_1);
	}

	private void setPhase1(boolean isPhase1) {
		this.entityData.set(PHASE_1, isPhase1);
	}

	public boolean isPhase2() {
		return this.entityData.get(PHASE_2);
	}

	private void setPhase2(boolean isPhase2) {
		this.entityData.set(PHASE_2, isPhase2);
	}

	public boolean isPhase3() {
		return this.entityData.get(PHASE_3);
	}

	private void setPhase3(boolean isPhase3) {
		this.entityData.set(PHASE_3, isPhase3);
	}

	public boolean startUsingLevitationAttack() {
		return this.entityData.get(LEVITATION_ATTACK);
	}

	private void setUsingLevitationAttack(boolean startUsingLevitationAttack) {
		this.entityData.set(LEVITATION_ATTACK, startUsingLevitationAttack);
	}

	public boolean startUsingParalysisAttack() {
		return this.entityData.get(PARALYSIS_ATTACK);
	}

	private void setUsingParalysisAttack(boolean startUsingParalysisAttack) {
		this.entityData.set(PARALYSIS_ATTACK, startUsingParalysisAttack);
	}

	public boolean startUsingTeleportationAttack() {
		return this.entityData.get(TELEPORTATION_ATTACK);
	}

	public int getAttackDuration() {
		return 80;
	}

	private void setUsingTeleportationAttack(boolean startUsingTeleportationAttack) {
		this.entityData.set(TELEPORTATION_ATTACK, startUsingTeleportationAttack);
	}

	public boolean startUsingFireAttack() {
		return this.entityData.get(FIRE_ATTACK);
	}

	private void setUsingFireAttack(boolean startUsingFireAttack) {
		this.entityData.set(FIRE_ATTACK, startUsingFireAttack);
	}

	public boolean startUsingBlindnessAttack() {
		return this.entityData.get(BLINDNESS_ATTACK);
	}

	private void setUsingBlindnessAttack(boolean startUsingBlindnessAttack) {
		this.entityData.set(BLINDNESS_ATTACK, startUsingBlindnessAttack);
	}

	public boolean startUsingSlownessAttack() {
		return this.entityData.get(SLOWNESS_ATTACK);
	}

	private void setUsingSlownessAttack(boolean startUsingSlownessAttack) {
		this.entityData.set(SLOWNESS_ATTACK, startUsingSlownessAttack);
	}

	public boolean startUsingWitherAttack() {
		return this.entityData.get(WITHER_ATTACK);
	}

	private void setUsingWitherAttack(boolean startUsingWitherAttack) {
		this.entityData.set(WITHER_ATTACK, startUsingWitherAttack);
	}

	public boolean startUsingWeaknessAttack() {
		return this.entityData.get(WEAKNESS_ATTACK);
	}

	private void setUsingWeaknessAttack(boolean startUsingWeaknessAttack) {
		this.entityData.set(WEAKNESS_ATTACK, startUsingWeaknessAttack);
	}

	public boolean startUsingExplosiveAttack() {
		return this.entityData.get(EXPLOSIVE_ATTACK);
	}

	private void setUsingExplosiveAttack(boolean startUsingExplosiveAttack) {
		this.entityData.set(EXPLOSIVE_ATTACK, startUsingExplosiveAttack);
	}

	public boolean startUsingEnergyAttack() {
		return this.entityData.get(ENERGY_ATTACK);
	}

	private void setUsingEnergyAttack(boolean startUsingEnergyAttack) {
		this.entityData.set(ENERGY_ATTACK, startUsingEnergyAttack);
	}

	public boolean startUsingSlamAttack() {
		return this.entityData.get(SLAM_ATTACK);
	}

	private void setUsingSlamAttack(boolean startUsingSlamAttack) {
		this.entityData.set(SLAM_ATTACK, startUsingSlamAttack);
	}

	public boolean startUsingChargeAttack() {
		return this.entityData.get(CHARGE_ATTACK);
	}

	private void setUsingChargeAttack(boolean startUsingChargeAttack) {
		this.entityData.set(CHARGE_ATTACK, startUsingChargeAttack);
	}

	public boolean isStunned() {
		return this.entityData.get(STUNNED);
	}

	private void setStunned(boolean isStunned) {
		this.entityData.set(STUNNED, isStunned);
	}

	public boolean areEyesClosed() {
		return this.isStalk1Shut() && this.isStalk2Shut() && this.isStalk3Shut() && this.isStalk4Shut() && this.isStalk5Shut();
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STALK_1_SHUT, false);
		this.entityData.define(STALK_2_SHUT, false);
		this.entityData.define(STALK_3_SHUT, false);
		this.entityData.define(STALK_4_SHUT, false);
		this.entityData.define(STALK_5_SHUT, false);
		this.entityData.define(LEVITATION_ATTACK, false);
		this.entityData.define(PARALYSIS_ATTACK, false);
		this.entityData.define(TELEPORTATION_ATTACK, false);
		this.entityData.define(FIRE_ATTACK, false);
		this.entityData.define(BLINDNESS_ATTACK, false);
		this.entityData.define(SLOWNESS_ATTACK, false);
		this.entityData.define(WEAKNESS_ATTACK, false);
		this.entityData.define(WITHER_ATTACK, false);
		this.entityData.define(EXPLOSIVE_ATTACK, false);
		this.entityData.define(ENERGY_ATTACK, false);
		this.entityData.define(PHASE_1, false);
		this.entityData.define(PHASE_2, false);
		this.entityData.define(PHASE_3, false);
		this.entityData.define(SLAM_ATTACK, false);
		this.entityData.define(CHARGE_ATTACK, false);
		this.entityData.define(STUNNED, false);
		this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_32834_) {
		super.onSyncedDataUpdated(p_32834_);
		if (DATA_ID_ATTACK_TARGET.equals(p_32834_)) {
			this.clientSideCachedAttackTarget = null;
		}

	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Stalk1Shut", this.isStalk1Shut());
		compound.putBoolean("Stalk2Shut", this.isStalk2Shut());
		compound.putBoolean("Stalk3Shut", this.isStalk3Shut());
		compound.putBoolean("Stalk4Shut", this.isStalk4Shut());
		compound.putBoolean("Stalk5Shut", this.isStalk5Shut());
		compound.putBoolean("LevitationAttack", this.startUsingLevitationAttack());
		compound.putBoolean("ParalysisAttack", this.startUsingParalysisAttack());
		compound.putBoolean("TeleportationAttack", this.startUsingTeleportationAttack());
		compound.putBoolean("FireAttack", this.startUsingFireAttack());
		compound.putBoolean("BlindnessAttack", this.startUsingBlindnessAttack());
		compound.putBoolean("SlownessAttack", this.startUsingSlownessAttack());
		compound.putBoolean("WeaknessAttack", this.startUsingWeaknessAttack());
		compound.putBoolean("WitherAttack", this.startUsingWitherAttack());
		compound.putBoolean("ExplosiveAttack", this.startUsingExplosiveAttack());
		compound.putBoolean("EnergyAttack", this.startUsingEnergyAttack());
		compound.putBoolean("Phase1", this.isPhase1());
		compound.putBoolean("Phase2", this.isPhase2());
		compound.putBoolean("Phase3", this.isPhase3());
		compound.putBoolean("SlamAttack", this.startUsingSlamAttack());
		compound.putBoolean("ChargeAttack", this.startUsingChargeAttack());
		compound.putBoolean("Stunned", this.isStunned());
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setStalk1Shut(compound.getBoolean("Stalk1Shut"));
		this.setStalk2Shut(compound.getBoolean("Stalk2Shut"));
		this.setStalk3Shut(compound.getBoolean("Stalk3Shut"));
		this.setStalk4Shut(compound.getBoolean("Stalk4Shut"));
		this.setStalk5Shut(compound.getBoolean("Stalk5Shut"));
		this.setUsingLevitationAttack(compound.getBoolean("LevitationAttack"));
		this.setUsingParalysisAttack(compound.getBoolean("ParalysisAttack"));
		this.setUsingTeleportationAttack(compound.getBoolean("TeleportationAttack"));
		this.setUsingFireAttack(compound.getBoolean("FireAttack"));
		this.setUsingBlindnessAttack(compound.getBoolean("BlindnessAttack"));
		this.setUsingSlownessAttack(compound.getBoolean("SlownessAttack"));
		this.setUsingWeaknessAttack(compound.getBoolean("WeaknessAttack"));
		this.setUsingWitherAttack(compound.getBoolean("WitherAttack"));
		this.setUsingExplosiveAttack(compound.getBoolean("ExplosiveAttack"));
		this.setUsingEnergyAttack(compound.getBoolean("EnergyAttack"));
		this.setPhase1(compound.getBoolean("Phase1"));
		this.setPhase2(compound.getBoolean("Phase2"));
		this.setPhase3(compound.getBoolean("Phase3"));
		this.setUsingSlamAttack(compound.getBoolean("SlamAttack"));
		this.setUsingChargeAttack(compound.getBoolean("ChargeAttack"));
		this.setStunned(compound.getBoolean("Stunned"));
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));
		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, true));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new Beholder.BeholderWanderGoal());
		this.goalSelector.addGoal(0, new Beholder.BlindnessAttackGoal());
		this.goalSelector.addGoal(0, new Beholder.LevitationAttackGoal());
		this.goalSelector.addGoal(0, new Beholder.ExplosiveAttackGoal());
		this.goalSelector.addGoal(0, new Beholder.WeaknessAttackGoal());
		this.goalSelector.addGoal(0, new Beholder.TeleportAttackGoal());
		this.goalSelector.addGoal(0, new Beholder.FireAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.ParalysisAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.SlownessAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.WitherAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.EnergyAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.SlamAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.ChargeAttackGoal(this));
		this.goalSelector.addGoal(0, new Beholder.AttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(0, new PhaseThreeMoveGoal(this, 1D, 32.0F));
		this.goalSelector.addGoal(0, new Beholder.BeholderAvoidEntityGoal(this, Player.class, 5.0F, 1.2F, 1.2F));
		this.goalSelector.addGoal(0, new Beholder.BeholderWanderGoal());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.FLYING_SPEED, (double)0.3F).add(Attributes.FOLLOW_RANGE, 90.0D);
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_, @Nullable CompoundTag p_29537_) {
		this.setPhase1(true);
		return super.finalizeSpawn(p_29533_, p_29534_, p_29535_, p_29536_, p_29537_);
	}

	public class FlyingMoveControl extends MoveControl {
		private final int maxTurn;
		private final boolean hoversInPlace;

		public FlyingMoveControl(Mob p_24893_, int p_24894_, boolean p_24895_) {
			super(p_24893_);
			this.maxTurn = p_24894_;
			this.hoversInPlace = p_24895_;
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				this.operation = MoveControl.Operation.WAIT;
				this.mob.setNoGravity(false);
				double d0 = this.wantedX - this.mob.getX();
				double d1 = this.wantedY - this.mob.getY();
				double d2 = this.wantedZ - this.mob.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				if (d3 < (double)2.5000003E-7F) {
					this.mob.setYya(0.0F);
					this.mob.setZza(0.0F);
					return;
				}

				float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
				this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 90.0F));
				float f1;
				f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));

				this.mob.setSpeed(f1);
				double d4 = Math.sqrt(d0 * d0 + d2 * d2);
				if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
					float f2 = (float)(-(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
					this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, (float)this.maxTurn));
					this.mob.setYya(d1 > 0.0D ? f1 : -f1);
				}
			} else {
				if (!this.hoversInPlace) {
					this.mob.setNoGravity(false);
				}

				this.mob.setYya(0.0F);
				this.mob.setZza(0.0F);
			}

		}
	}

	public void tick() {
		super.tick();
		if (this.getTarget() != null) {
			LivingEntity entity = this.getTarget();
			if (this.hasLineOfSight(entity) && !this.isStunned()) {
				if (entity instanceof Player) {
					if (!entity.hasEffect(EffectInit.MAGIC_NULLIFICATION.get())) {
						entity.addEffect(new MobEffectInstance(EffectInit.MAGIC_NULLIFICATION.get(), 150));
					}
				}
			}
		}
	}

	void setActiveAttackTarget(int p_32818_) {
		this.entityData.set(DATA_ID_ATTACK_TARGET, p_32818_);
	}

	public boolean hasActiveAttackTarget() {
		return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getActiveAttackTarget() {
		if (!this.hasActiveAttackTarget()) {
			return null;
		} else if (this.level.isClientSide) {
			if (this.clientSideCachedAttackTarget != null) {
				return this.clientSideCachedAttackTarget;
			} else {
				Entity entity = this.level.getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
				if (entity instanceof LivingEntity) {
					this.clientSideCachedAttackTarget = (LivingEntity)entity;
					return this.clientSideCachedAttackTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public boolean damagePart(BeholderPart part, DamageSource source, float damage) {
		double f1 = this.yBodyRot * (Math.PI / 180F);
		double f2 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
		double f3 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
		double f4 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F);
		double f5 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F);
		if (this.isPhase1() || this.isPhase2()) {
			if (part == this.stalk1) {
				if (this.getStalk1Health() - damage <= 0) {
					this.setStalk1Health(0);
					this.setStalk1Shut(true);
				} else {
					this.setStalk1Health(this.getStalk1Health() - damage);
				}
				level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX() - f2, this.getY() + 1.3F, this.getZ() + f3, 0, 0, 0);
				return true;
			} else if (part == this.stalk2) {
				if (this.getStalk2Health() - damage <= 0) {
					this.setStalk2Health(0);
					this.setStalk2Shut(true);
				} else {
					this.setStalk2Health(this.getStalk2Health() - damage);
				}
				level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX() - f4, this.getY() + 2.5F, this.getZ() + f5, 0, 0, 0);
				return true;
			} else if (part == this.stalk3) {
				if (this.getStalk3Health() - damage <= 0) {
					this.setStalk3Health(0);
					this.setStalk3Shut(true);
				} else {
					this.setStalk3Health(this.getStalk3Health() - damage);
				}
				level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY() + 2.8F, this.getZ(), 0, 0, 0);
				return true;
			} else if (part == this.stalk4) {
				if (this.getStalk4Health() - damage <= 0) {
					this.setStalk4Health(0);
					this.setStalk4Shut(true);
				} else {
					this.setStalk4Health(this.getStalk4Health() - damage);
				}
				level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX() + f4, this.getY() + 2.5F, this.getZ() - f5, 0, 0, 0);
				return true;
			} else if (part == this.stalk5) {
				if (this.getStalk5Health() - damage <= 0) {
					this.setStalk5Health(0);
					this.setStalk5Shut(true);
				} else {
					this.setStalk5Health(this.getStalk5Health() - damage);
				}
				level.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX() + f2, this.getY() + 1.3F, this.getZ() - f3, 0, 0, 0);
				return true;
			} else if (part == this.body) {
				return false;
			}
		} else if (this.isPhase3()) {
			this.hurt(source, damage);
			return true;
		}
		return false;
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}

	@Override
	public net.minecraftforge.entity.PartEntity<?>[] getParts() {
		return this.subEntities;
	}

	public void recreateFromPacket(ClientboundAddMobPacket p_149572_) {
		super.recreateFromPacket(p_149572_);
		BeholderPart[] beholderPart = this.getSubEntities();

		for(int i = 0; i < beholderPart.length; ++i) {
			beholderPart[i].setId(i + p_149572_.getId());
		}

	}

	@Override
	public boolean hurt(DamageSource source, float damage) {
		if (this.isPhase3()) {
			return super.hurt(source, damage);
		} else {
			return false;
		}
	}

	class BeholderWanderGoal extends Goal {
		@SuppressWarnings("unused")
		private static final int WANDER_THRESHOLD = 22;

		BeholderWanderGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return Beholder.this.navigation.isDone() && Beholder.this.random.nextInt(10) == 0;
		}

		public boolean canContinueToUse() {
			return Beholder.this.navigation.isInProgress();
		}

		public void start() {
			Vec3 vec3 = this.findPos();
			if (vec3 != null) {
				Beholder.this.navigation.moveTo(Beholder.this.navigation.createPath(new BlockPos(vec3), 1), 1.0D);
			}

		}

		@Nullable
		private Vec3 findPos() {
			Vec3 vec3;
			vec3 = Beholder.this.getViewVector(0.0F);

			Vec3 vec32 = HoverRandomPos.getPos(Beholder.this, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
			return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Beholder.this, 8, 4, -2, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
		}
	}

	public BeholderPart[] getSubEntities() {
		return this.subEntities;
	}

	protected void updateParts() {
		double f1 = this.yBodyRot * (Math.PI / 180F);
		double f2 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
		double f3 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
		double f4 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F);
		double f5 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F);
		movePart(body, 0, 0.5f, 0);
		movePart(stalk1, -f2, 1.3, f3);
		movePart(stalk5, f2, 1.3, -f3);
		movePart(stalk2, -f4, 2.5, f5);
		movePart(stalk4, f4, 2.5, -f5);
		movePart(stalk3, 0, 2.8, 0);
	}

	protected void movePart(BeholderPart part, double dX, double dY, double dZ)
	{
		Vec3 lastPos = new Vec3(part.getX(), part.getY(), part.getZ());

		part.setPos(this.getX() + dX, this.getY() + dY, this.getZ() + dZ);

		part.xo = lastPos.x;
		part.yo = lastPos.y;
		part.zo = lastPos.z;
		part.xOld = lastPos.x;
		part.yOld = lastPos.y;
		part.zOld = lastPos.z;
	}

	public void aiStep() {
		super.aiStep();
		updateParts();
		if (this.isStunned()) {
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0);
		} else {
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue((double) 0.3F);
		}
		if (this.getTarget() != null) {
			attackTimer--;
			if (attackTimer == 0 && this.isPhase1()) {
				int attack = random.nextInt(5);
				System.out.println(attack);
				if (attack == 0) {
					if (this.levitationAttackCooldown == 0 && !this.isStalk1Shut()) {
						this.setUsingLevitationAttack(true);
						attackTimer = 75;
					} else {
						attack = 1;
					}
				} else if (attack == 1) {
					if (this.paralysisAttackCooldown == 0 && !this.isStalk2Shut()) {
						this.setUsingParalysisAttack(true);
						attackTimer = 75;
					} else {
						attack = 2;
					}
				} else if (attack == 2) {
					if (this.teleportationAttackCooldown == 0 && !this.isStalk3Shut()) {
						this.setUsingTeleportationAttack(true);
						attackTimer = 75;
					} else {
						attack = 3;
					}
				} else if (attack == 3) {
					if (this.fireAttackCooldown == 0 && !this.isStalk4Shut()) {
						this.setUsingFireAttack(true);
						attackTimer = 75;
					} else {
						attack = 4;
					}
				} else if (attack == 4) {
					if (this.blindnessAttackCooldown == 0 && !this.isStalk5Shut()) {
						this.setUsingBlindnessAttack(true);
						attackTimer = 75;
					} else {
						attack = 0;
					}
				}
			}
			if (attackTimer == 0 && this.isPhase2()) {
				int attack = random.nextInt(5);
				System.out.println(attack);
				if (attack == 0) {
					if (this.slownessAttackCooldown == 0 && !this.isStalk1Shut()) {
						this.setUsingSlownessAttack(true);
						attackTimer = 75;
					} else {
						attack = 1;
					}
				} else if (attack == 1) {
					if (this.weaknessAttackCooldown == 0 && !this.isStalk2Shut()) {
						this.setUsingWeaknessAttack(true);
						attackTimer = 75;
					} else {
						attack = 2;
					}
				} else if (attack == 2) {
					if (this.witherAttackCooldown == 0 && !this.isStalk3Shut()) {
						this.setUsingWitherAttack(true);
						attackTimer = 75;
					} else {
						attack = 3;
					}
				} else if (attack == 3) {
					if (this.explosiveAttackCooldown == 0 && !this.isStalk4Shut()) {
						this.setUsingExplosiveAttack(true);
						attackTimer = 75;
					} else {
						attack = 4;
					}
				} else if (attack == 4) {
					if (this.energyAttackCooldown == 0 && !this.isStalk5Shut()) {
						this.setUsingEnergyAttack(true);
						attackTimer = 75;
					} else {
						attack = 0;
					}
				}
			}
			if (attackTimer == 0 && this.isPhase3() && !this.isStunned()) {
				int attack = random.nextInt(5);
				if (attack == 0) {
					this.setUsingSlamAttack(true);
					attackTimer = 75;
					System.out.println("Slam");
				} else if (attack == 1 || attack == 2) {
					this.setUsingChargeAttack(true);
					System.out.println("Charge!");
					attackTimer = 75;
				} else {
					attackTimer = 75;
				}
			}
		}
		if (this.isStunned()) {
			stunTick++;
			if (stunTick == 100) {
				this.setStunned(false);
				stunTick = 0;
			}
		}
		if (stunTick > 100) {
			stunTick = 0;
		}
		if (this.getTarget() == null || attackTimer < -10) {
			attackTimer = 75;
		}
		if (this.levitationAttackCooldown > 0) {
			levitationAttackCooldown--;
		}
		if (this.paralysisAttackCooldown > 0) {
			paralysisAttackCooldown--;
		}
		if (this.teleportationAttackCooldown > 0) {
			teleportationAttackCooldown--;
		}
		if (this.fireAttackCooldown > 0) {
			fireAttackCooldown--;
		}
		if (this.blindnessAttackCooldown > 0) {
			blindnessAttackCooldown--;
		}
		if (this.slownessAttackCooldown > 0) {
			slownessAttackCooldown--;
		}
		if (this.weaknessAttackCooldown > 0) {
			weaknessAttackCooldown--;
		}
		if (this.witherAttackCooldown > 0) {
			witherAttackCooldown--;
		}
		if (this.explosiveAttackCooldown > 0) {
			explosiveAttackCooldown--;
		}
		if (this.energyAttackCooldown > 0) {
			energyAttackCooldown--;
		}
		if (this.areEyesClosed() && this.isPhase1()) {
			this.setStalk1Health(20);
			this.setStalk2Health(20);
			this.setStalk3Health(20);
			this.setStalk4Health(20);
			this.setStalk5Health(20);
			this.setStalk1Shut(false);
			this.setStalk2Shut(false);
			this.setStalk3Shut(false);
			this.setStalk4Shut(false);
			this.setStalk5Shut(false);
			this.setPhase2(true);
			this.setPhase1(false);
		}
		if (this.areEyesClosed() && this.isPhase2()) {
			this.setPhase3(true);
			this.setPhase2(false);
		}
	}

	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	protected void customServerAiStep() {
		if (this.isPhase1()) {
			this.bossInfo.setProgress((this.getStalk1Health() + this.getStalk2Health() + this.getStalk3Health() + this.getStalk4Health() + this.getStalk5Health()) / 100F);
		} else if (this.isPhase2()) {
			this.bossInfo.setProgress((this.getStalk1Health() + this.getStalk2Health() + this.getStalk3Health() + this.getStalk4Health() + this.getStalk5Health()) / 100F);
		} else {
			this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}

	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.BEHOLDER_SPAWN_EGG.get());
	}

	class LevitationAttackGoal extends Goal {

		public LevitationAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		}

		public boolean canUse() {
			if (Beholder.this.startUsingLevitationAttack() && Beholder.this.getTarget() != null) {
				return true;
			} else return false;
		}

		public void start() {
			LivingEntity livingentity = Beholder.this.getTarget();
			if (livingentity != null) {
				Beholder.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}
		}

		public void tick() {
			LevitationOrb levitationOrb = new LevitationOrb(Beholder.this.level, Beholder.this);
			double f1 = Beholder.this.yBodyRot * (Math.PI / 180F);
			double f2 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
			double f3 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
			levitationOrb.setPos(Beholder.this.getX() -f2, Beholder.this.getY() + 0.5F, Beholder.this.getZ() + f3);
			double d0 = Beholder.this.getTarget().getY() - (double)1.1F;
			double d1 = Beholder.this.getTarget().getX() - levitationOrb.getX();
			double d2 = d0 - levitationOrb.getY();
			double d3 = Beholder.this.getTarget().getZ() - levitationOrb.getZ();
			float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			levitationOrb.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
			level.playSound((Player) null, Beholder.this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
			Beholder.this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
			Beholder.this.level.addFreshEntity(levitationOrb);
			Beholder.this.levitationAttackCooldown = 150;
			Beholder.this.setUsingLevitationAttack(false);
		}

	}

	class BlindnessAttackGoal extends Goal {

		public BlindnessAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		}

		public boolean canUse() {
			if (Beholder.this.startUsingBlindnessAttack() && Beholder.this.getTarget() != null) {
				return true;
			} else return false;
		}

		public void start() {
			LivingEntity livingentity = Beholder.this.getTarget();
			if (livingentity != null) {
				Beholder.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}
		}

		public void tick() {
			BlindnessOrb blindnessOrb = new BlindnessOrb(Beholder.this.level, Beholder.this);
			double f1 = Beholder.this.yBodyRot * (Math.PI / 180F);
			double f2 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
			double f3 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F * 1.25F);
			blindnessOrb.setPos(Beholder.this.getX() + f2, Beholder.this.getY() + 0.5F, Beholder.this.getZ() - f3);
			double d0 = Beholder.this.getTarget().getY() - (double)1.1F;
			double d1 = Beholder.this.getTarget().getX() - blindnessOrb.getX();
			double d2 = d0 - blindnessOrb.getY();
			double d3 = Beholder.this.getTarget().getZ() - blindnessOrb.getZ();
			float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			blindnessOrb.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
			level.playSound((Player) null, Beholder.this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
			Beholder.this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
			Beholder.this.level.addFreshEntity(blindnessOrb);
			Beholder.this.blindnessAttackCooldown = 150;
			Beholder.this.setUsingBlindnessAttack(false);
		}

	}

	class TeleportAttackGoal extends Goal {

		public TeleportAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		}

		public boolean canUse() {
			if (Beholder.this.startUsingTeleportationAttack() && Beholder.this.getTarget() != null) {
				return true;
			} else return false;
		}

		public void start() {
			LivingEntity livingentity = Beholder.this.getTarget();
			if (livingentity != null) {
				Beholder.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}
		}

		public void tick() {
			TeleportationOrb teleportationOrb = new TeleportationOrb(Beholder.this.level, Beholder.this);
			teleportationOrb.setPos(Beholder.this.getX(), Beholder.this.getY() + 2.8F, Beholder.this.getZ());
			double d0 = Beholder.this.getTarget().getY() - (double)1.1F;
			double d1 = Beholder.this.getTarget().getX() - Beholder.this.getX();
			double d2 = d0 - teleportationOrb.getY();
			double d3 = Beholder.this.getTarget().getZ() - Beholder.this.getZ();
			float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			teleportationOrb.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
			level.playSound((Player) null, Beholder.this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
			Beholder.this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
			Beholder.this.level.addFreshEntity(teleportationOrb);
			Beholder.this.teleportationAttackCooldown = 150;
			Beholder.this.setUsingTeleportationAttack(false);
		}

	}

	class WeaknessAttackGoal extends Goal {

		public WeaknessAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		}

		public boolean canUse() {
			if (Beholder.this.startUsingWeaknessAttack() && Beholder.this.getTarget() != null) {
				return true;
			} else return false;
		}

		public void start() {
			LivingEntity livingentity = Beholder.this.getTarget();
			if (livingentity != null) {
				Beholder.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}
		}

		public void tick() {
			WeaknessOrb blindnessOrb = new WeaknessOrb(Beholder.this.level, Beholder.this);
			double f1 = Beholder.this.yBodyRot * (Math.PI / 180F);
			double f4 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F);
			double f5 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F);
			blindnessOrb.setPos(Beholder.this.getX() - f4, Beholder.this.getY() + 0.5F, Beholder.this.getZ() + f5);
			double d0 = Beholder.this.getTarget().getY() - (double)1.1F;
			double d1 = Beholder.this.getTarget().getX() - blindnessOrb.getX();
			double d2 = d0 - blindnessOrb.getY();
			double d3 = Beholder.this.getTarget().getZ() - blindnessOrb.getZ();
			float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			blindnessOrb.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
			level.playSound((Player) null, Beholder.this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
			Beholder.this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
			Beholder.this.level.addFreshEntity(blindnessOrb);
			Beholder.this.weaknessAttackCooldown = 150;
			Beholder.this.setUsingWeaknessAttack(false);
		}

	}

	class ExplosiveAttackGoal extends Goal {

		public ExplosiveAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		}

		public boolean canUse() {
			if (Beholder.this.startUsingExplosiveAttack() && Beholder.this.getTarget() != null) {
				return true;
			} else return false;
		}

		public void start() {
			LivingEntity livingentity = Beholder.this.getTarget();
			if (livingentity != null) {
				Beholder.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}
		}

		public void tick() {
			ExplosiveOrb blindnessOrb = new ExplosiveOrb(Beholder.this.level, Beholder.this);
			double f1 = Beholder.this.yBodyRot * (Math.PI / 180F);
			double f4 = (Math.sin(f1 - 6.5F * -1.2F) * 1.25F);
			double f5 = (Math.cos(f1 - 6.5F * -1.2F) * 1.25F);
			blindnessOrb.setPos(Beholder.this.getX() + f4, Beholder.this.getY() + 0.5F, Beholder.this.getZ() - f5);
			double d0 = Beholder.this.getTarget().getY() - (double)1.1F;
			double d1 = Beholder.this.getTarget().getX() - blindnessOrb.getX();
			double d2 = d0 - blindnessOrb.getY();
			double d3 = Beholder.this.getTarget().getZ() - blindnessOrb.getZ();
			float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			blindnessOrb.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
			level.playSound((Player) null, Beholder.this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
			Beholder.this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
			Beholder.this.level.addFreshEntity(blindnessOrb);
			Beholder.this.explosiveAttackCooldown = 150;
			Beholder.this.setUsingExplosiveAttack(false);
		}

	}

	static class FireAttackGoal extends Goal {
		private final Beholder beholder;
		private int attackTime;

		public FireAttackGoal(Beholder beholder) {
			this.beholder = beholder;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.beholder.getTarget();
			return livingentity != null && livingentity.isAlive() && beholder.startUsingFireAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.beholder.getTarget() != null && this.beholder.distanceToSqr(this.beholder.getTarget()) > 9.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.beholder.getNavigation().stop();
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}

			this.beholder.hasImpulse = true;
		}

		public void stop() {
			this.beholder.setActiveAttackTarget(0);
			this.beholder.setTarget((LivingEntity)null);
			this.beholder.fireAttackCooldown = 150;
			this.beholder.setUsingFireAttack(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getNavigation().stop();
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
				if (!this.beholder.hasLineOfSight(livingentity)) {
					this.beholder.setTarget((LivingEntity)null);
				} else {
					++this.attackTime;
					if (this.attackTime == 0) {
						this.beholder.setActiveAttackTarget(livingentity.getId());
					} else if (this.attackTime >= this.beholder.getAttackDuration()) {
						this.beholder.setTarget((LivingEntity)null);
					}
					if (this.attackTime > 0) {
						livingentity.setSecondsOnFire(15);
					}
					super.tick();
				}
			}
		}
	}

	static class ParalysisAttackGoal extends Goal {
		private final Beholder beholder;
		private int attackTime;

		public ParalysisAttackGoal(Beholder beholder) {
			this.beholder = beholder;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.beholder.getTarget();
			return livingentity != null && livingentity.isAlive() && beholder.startUsingParalysisAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.beholder.getTarget() != null && this.beholder.distanceToSqr(this.beholder.getTarget()) > 9.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.beholder.getNavigation().stop();
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}

			this.beholder.hasImpulse = true;
		}

		public void stop() {
			this.beholder.setActiveAttackTarget(0);
			this.beholder.setTarget((LivingEntity)null);
			this.beholder.paralysisAttackCooldown = 150;
			this.beholder.setUsingParalysisAttack(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getNavigation().stop();
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
				if (!this.beholder.hasLineOfSight(livingentity)) {
					this.beholder.setTarget((LivingEntity)null);
				} else {
					++this.attackTime;
					if (this.attackTime == 0) {
						this.beholder.setActiveAttackTarget(livingentity.getId());
					} else if (this.attackTime >= this.beholder.getAttackDuration()) {
						this.beholder.setTarget((LivingEntity)null);
					}
					if (this.attackTime > 0) {
						livingentity.addEffect(new MobEffectInstance(EffectInit.PARALYSIS.get(), 150));
					}
					super.tick();
				}
			}
		}
	}

	static class SlownessAttackGoal extends Goal {
		private final Beholder beholder;
		private int attackTime;

		public SlownessAttackGoal(Beholder beholder) {
			this.beholder = beholder;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.beholder.getTarget();
			return livingentity != null && livingentity.isAlive() && beholder.startUsingSlownessAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.beholder.getTarget() != null && this.beholder.distanceToSqr(this.beholder.getTarget()) > 9.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.beholder.getNavigation().stop();
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}

			this.beholder.hasImpulse = true;
		}

		public void stop() {
			this.beholder.setActiveAttackTarget(0);
			this.beholder.setTarget((LivingEntity)null);
			this.beholder.slownessAttackCooldown = 150;
			this.beholder.setUsingSlownessAttack(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getNavigation().stop();
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
				if (!this.beholder.hasLineOfSight(livingentity)) {
					this.beholder.setTarget((LivingEntity)null);
				} else {
					++this.attackTime;
					if (this.attackTime == 0) {
						this.beholder.setActiveAttackTarget(livingentity.getId());
					} else if (this.attackTime >= this.beholder.getAttackDuration()) {
						this.beholder.setTarget((LivingEntity)null);
					}
					if (this.attackTime > 0) {
						livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150));
					}
					super.tick();
				}
			}
		}
	}

	static class WitherAttackGoal extends Goal {
		private final Beholder beholder;
		private int attackTime;

		public WitherAttackGoal(Beholder beholder) {
			this.beholder = beholder;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.beholder.getTarget();
			return livingentity != null && livingentity.isAlive() && beholder.startUsingWitherAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.beholder.getTarget() != null && this.beholder.distanceToSqr(this.beholder.getTarget()) > 9.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.beholder.getNavigation().stop();
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}

			this.beholder.hasImpulse = true;
		}

		public void stop() {
			this.beholder.setActiveAttackTarget(0);
			this.beholder.setTarget((LivingEntity)null);
			this.beholder.witherAttackCooldown = 150;
			this.beholder.setUsingWitherAttack(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getNavigation().stop();
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
				if (!this.beholder.hasLineOfSight(livingentity)) {
					this.beholder.setTarget((LivingEntity)null);
				} else {
					++this.attackTime;
					if (this.attackTime == 0) {
						this.beholder.setActiveAttackTarget(livingentity.getId());
					} else if (this.attackTime >= this.beholder.getAttackDuration()) {
						this.beholder.setTarget((LivingEntity)null);
					}
					if (this.attackTime > 0) {
						livingentity.addEffect(new MobEffectInstance(MobEffects.WITHER, 150));
					}
					super.tick();
				}
			}
		}
	}

	static class EnergyAttackGoal extends Goal {
		private final Beholder beholder;
		private int attackTime;

		public EnergyAttackGoal(Beholder beholder) {
			this.beholder = beholder;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.beholder.getTarget();
			return livingentity != null && livingentity.isAlive() && beholder.startUsingEnergyAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && (this.beholder.getTarget() != null && this.beholder.distanceToSqr(this.beholder.getTarget()) > 9.0D);
		}

		public void start() {
			this.attackTime = -10;
			this.beholder.getNavigation().stop();
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
			}

			this.beholder.hasImpulse = true;
		}

		public void stop() {
			this.beholder.setActiveAttackTarget(0);
			this.beholder.setTarget((LivingEntity)null);
			this.beholder.energyAttackCooldown = 150;
			this.beholder.setUsingEnergyAttack(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = this.beholder.getTarget();
			if (livingentity != null) {
				this.beholder.getNavigation().stop();
				this.beholder.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
				if (!this.beholder.hasLineOfSight(livingentity)) {
					this.beholder.setTarget((LivingEntity)null);
				} else {
					++this.attackTime;
					if (this.attackTime == 0) {
						this.beholder.setActiveAttackTarget(livingentity.getId());
					} else if (this.attackTime >= this.beholder.getAttackDuration()) {
						this.beholder.setTarget((LivingEntity)null);
					}
					if (this.attackTime > 0) {
						livingentity.hurt(DamageSource.MAGIC, 4);
					}
					super.tick();
				}
			}
		}
	}

	@Override
	public boolean isFlying() {
		return true;
	}

	private float func_226511_et_() {
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public boolean doHurtTarget(Entity entityIn) {
		this.level.broadcastEntityEvent(this, (byte)4);
		float f = this.func_226511_et_();
		float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
		boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);
		if (flag) {
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, (double)0.4F, 0.0D));
			this.doEnchantDamageEffects(this, entityIn);
		}

		return flag;
	}

	class SlamAttackGoal extends Goal {
		Beholder beholder;
		int timer = 0;
		boolean hasJumped = false;

		public SlamAttackGoal(Beholder beholder) {
			this.beholder = beholder;
		}

		@Override
		public boolean canUse() {
			if (beholder.getTarget() != null && beholder.startUsingSlamAttack()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean canContinueToUse() {
			if (beholder.getTarget() == null || !beholder.startUsingSlamAttack()) return false;
			else return true;
		}

		@Override
		public void tick() {
			timer++;
			if (beholder.isOnGround() && !this.hasJumped) {
				beholder.setDeltaMovement(0, 0.5, 0);

				this.hasJumped = true;
			}
			double perpFacing = beholder.yo * (Math.PI / 180);
			double facingAngle = perpFacing + Math.PI / 2;
			int hitY = Mth.floor(beholder.getBoundingBox().minY - 0.5);
			int tick = timer;
			final int maxDistance = 6;
			ServerLevel world = (ServerLevel) beholder.level;
			if (tick > 0) {
				int distance = tick / 2 - 2;
				double spread = Math.PI * 2;
				int arcLen = Mth.ceil(distance * spread);
				double minY = beholder.getBoundingBox().minY;
				double maxY = beholder.getBoundingBox().maxY;
				for (int i = 0; i < arcLen; i++) {
					double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
					double vx = Math.cos(theta);
					double vz = Math.sin(theta);
					double px = beholder.getX() + vx * distance;
					double pz = beholder.getZ() + vz * distance;
					float factor = 1 - distance / (float) maxDistance;
					AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);
					List<Entity> hit = world.getEntitiesOfClass(Entity.class, selection);
					for (Entity entity : hit) {
						if (entity == this.beholder || entity instanceof GDFallingBlock) {
							continue;
						}
						float applyKnockbackResistance = 0;
						if (entity instanceof LivingEntity) {
							entity.hurt(DamageSource.mobAttack(this.beholder),(float) ((factor * 5 + 1) * beholder.getAttributeValue(Attributes.ATTACK_DAMAGE)));
							applyKnockbackResistance = (float) ((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
						}
						double magnitude = world.random.nextDouble() * 0.15 + 0.1;
						float x = 0, y = 0, z = 0;
						x += vx * factor * magnitude * (1 - applyKnockbackResistance);
						if (entity.isOnGround()) {
							y += 0.1 * (1 - applyKnockbackResistance) + factor * 0.15 * (1 - applyKnockbackResistance);
						}
						z += vz * factor * magnitude * (1 - applyKnockbackResistance);
						entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
						if (entity instanceof ServerPlayer) {
							((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
						}
						if (world.random.nextBoolean()) {
							int hitX = Mth.floor(px);
							int hitZ = Mth.floor(pz);
							BlockPos pos = new BlockPos(hitX, hitY, hitZ);
							BlockPos abovePos = new BlockPos(pos).above();
							BlockState block = world.getBlockState(pos);
							BlockState blockAbove = world.getBlockState(abovePos);
							if (block.getMaterial() != Material.AIR && block.isRedstoneConductor(world, pos) && !block.hasBlockEntity() && !blockAbove.getMaterial().blocksMotion()) {
								GDFallingBlock fallingBlock = new GDFallingBlock(EntityInit.FALLING_BLOCK.get(), world, block, (float) (0.4 + factor * 0.2));
								fallingBlock.setPos(hitX + 0.5, hitY + 1, hitZ + 0.5);
								world.addFreshEntity(fallingBlock);
							}
						}
					}
				}
			}
			this.stop();
			beholder.setUsingSlamAttack(false);
		}

		@Override
		public void stop() {
			super.stop();
			beholder.setUsingSlamAttack(false);
		}

	}

	class AttackGoal extends MeleeAttackGoal {

		public AttackGoal(PathfinderMob creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}

		@Override
		public boolean canUse() {
			if (Beholder.this.getTarget() != null && Beholder.this.isPhase3() && !Beholder.this.startUsingSlamAttack() && !Beholder.this.startUsingChargeAttack()) return true;
			else return false;
		}

		@Override
		public boolean canContinueToUse() {
			if (Beholder.this.getTarget() == null || Beholder.this.startUsingSlamAttack() || Beholder.this.startUsingChargeAttack()) return false;
			else return true;
		}

	}

	class ChargeAttackGoal extends Goal {

		Beholder beholder;
		int timer = 0;

		public ChargeAttackGoal(Beholder beholder) {
			this.beholder = beholder;
		}

		@Override
		public boolean canUse() {
			return beholder.getTarget() != null && beholder.startUsingChargeAttack();
		}

		public void tick() {
			super.tick();
			timer++;
			LivingEntity target = beholder.getTarget();
			this.beholder.getLookControl().setLookAt(target, 90.0F, 90.0F);
			if (timer < 100) {
				beholder.getNavigation().isDone();
			}
			if (timer >= 100) {
				beholder.moveControl.setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.2F);
				if (beholder.verticalCollision) {
					beholder.setStunned(true);
				}
				if (beholder.closerThan(target, 0.2D)) {
					target.hurt(DamageSource.mobAttack(beholder), (float) beholder.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
				}
			}
			if (timer == 200) {
				this.stop();
				beholder.setUsingChargeAttack(false);
			}
		}

		public void stop() {
			beholder.setUsingChargeAttack(false);
		}

	}

	class PhaseThreeMoveGoal extends MoveTowardsTargetGoal {

		public PhaseThreeMoveGoal(PathfinderMob p_25646_, double p_25647_, float p_25648_) {
			super(p_25646_, p_25647_, p_25648_);
		}

		public boolean canUse() {
			return super.canUse() && Beholder.this.isPhase3() && !Beholder.this.startUsingSlamAttack() && !Beholder.this.startUsingChargeAttack();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && !Beholder.this.startUsingSlamAttack() && !Beholder.this.startUsingChargeAttack();
		}

	}

	@SuppressWarnings("rawtypes")
	class BeholderAvoidEntityGoal extends AvoidEntityGoal {

		@SuppressWarnings("unchecked")
		public BeholderAvoidEntityGoal(PathfinderMob p_25027_, Class p_25028_, float p_25029_, double p_25030_, double p_25031_) {
			super(p_25027_, p_25028_, p_25029_, p_25030_, p_25031_);
		}

		public boolean canUse() {
			return super.canUse() && !Beholder.this.isPhase3();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && !Beholder.this.isPhase3();
		}

	}	

}
