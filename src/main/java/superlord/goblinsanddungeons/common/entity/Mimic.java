package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class Mimic extends Goblin {
	private static final EntityDataAccessor<Boolean> HIDING = SynchedEntityData.defineId(Mimic.class, EntityDataSerializers.BOOLEAN);

	public Mimic(EntityType<? extends Mimic> p_i50196_1_, Level p_i50196_2_) {
		super(p_i50196_1_, p_i50196_2_);
	}
	public boolean isHiding() {
		return this.entityData.get(HIDING);
	}

	private void setHiding(boolean isHiding) {
		this.entityData.set(HIDING, isHiding);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new Mimic.HidingLookGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new Mimic.AttackNearestGoal(this));
		this.goalSelector.addGoal(0, new Mimic.HideGoal());
		this.targetSelector.addGoal(1, new Mimic.DefendGoal(this));
	}


	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HIDING, false);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHiding(compound.getBoolean("IsHiding"));
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsHiding", this.isHiding());
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		if (!level.isClientSide) {
			this.setHiding(false);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(p_230254_1_, p_230254_2_);
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

	public void push(Entity p_33474_) {
		if (this.isHiding()) {
			super.push(p_33474_);
		}
	}

	public boolean canBeCollidedWith() {
		return this.isAlive();
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compound) {
		spawnData = super.finalizeSpawn(worldIn, difficulty, reason, spawnData, compound);
		this.setHiding(true);
		return spawnData;
	}

	protected SoundEvent getAmbientSound() {
		if (!this.isHiding()) return SoundInit.MIMIC_IDLE;
		else return null;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.MIMIC_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.MIMIC_DEATH;
	}

	public boolean hurt(DamageSource source, float amount) {

		if (super.hurt(source, amount)) {
			this.setHiding(false);
			return true;
		} else {
			return false;
		}
	}

	class DefendGoal extends HurtByTargetGoal {

		public DefendGoal(PathfinderMob creatureIn) {
			super(creatureIn);
		}

		public void start() {
			super.start();
			Mimic.this.setHiding(false);
		}


	}

	public void recreateFromPacket(ClientboundAddMobPacket p_149798_) {
		super.recreateFromPacket(p_149798_);
		this.yBodyRot = 0.0F;
		this.yBodyRotO = 0.0F;
	}

	class AttackNearestGoal extends NearestAttackableTargetGoal<Player> {
		public AttackNearestGoal(Mimic mimic) {
			super(mimic, Player.class, true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			if (!Mimic.this.isHiding() && super.canUse()) {
				return true;
			} else {
				return false;
			}
		}

		public void start() {
			super.start();
			Mimic.this.setHiding(false);
		}

		public void stop() {
			super.stop();
		}

	}

	class HidingLookGoal extends LookAtPlayerGoal {

		public HidingLookGoal(Mob entityIn, Class<? extends Player> watchTargetClass, float maxDistance) {
			super(entityIn, watchTargetClass, maxDistance);
		}

		public boolean canUse() {
			if (super.canUse() && !Mimic.this.isHiding()) {
				return true;
			} else {
				return false;
			}
		}

	}

	class HideGoal extends Goal {

		@Override
		public boolean canUse() {
			if (Mimic.this.getTarget() == null) {
				return true;
			} else {
				return false;
			}
		}

		public void startExecuting() {
			Mimic.this.setHiding(true);
		}

	}

}
