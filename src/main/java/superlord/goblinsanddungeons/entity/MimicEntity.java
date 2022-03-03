package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class MimicEntity extends GoblinEntity {
	private static final EntityDataAccessor<Boolean> HIDING = SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.BOOLEAN);
	private BlockPos currentAttachmentPosition = null;
	private int clientSideTeleportInterpolation;

	public MimicEntity(EntityType<? extends MimicEntity> p_i50196_1_, Level p_i50196_2_) {
		super(p_i50196_1_, p_i50196_2_);
	}
	public boolean isHiding() {
		return this.entityData.get(HIDING);
	}

	private void setHiding(boolean isHiding) {
		this.entityData.set(HIDING, isHiding);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MimicEntity.HidingLookGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new MimicEntity.AttackNearestGoal(this));
		this.goalSelector.addGoal(0, new MimicEntity.HideGoal());
		this.targetSelector.addGoal(1, new MimicEntity.DefendGoal(this));
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

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compound) {
		spawnData = super.finalizeSpawn(worldIn, difficulty, reason, spawnData, compound);
		this.setHiding(true);
		return spawnData;
	}

	public void aiStep() {
		super.aiStep();
		if (this.isHiding()) {
			this.setDeltaMovement(Vec3.ZERO);
			if (!this.isNoAi()) {
				this.yOld = 0.0F;
				this.yo = 0.0F;
			}
		}
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


	public boolean func_241845_aY() {
		return this.isAlive();
	}

	@OnlyIn(Dist.CLIENT)
	public int getClientTeleportInterp() {
		return this.clientSideTeleportInterpolation;
	}

	@OnlyIn(Dist.CLIENT)
	public BlockPos getOldAttachPos() {
		return this.currentAttachmentPosition;
	}

	public void push(Entity entityIn) {
		if (!this.isHiding()) {
	         super.push(entityIn);
		}
	}

	public float getCollisionBorderSize() {
		return 0.0F;
	}

	class DefendGoal extends HurtByTargetGoal {

		public DefendGoal(PathfinderMob creatureIn) {
			super(creatureIn);
		}

		public void start() {
			super.start();
			MimicEntity.this.setHiding(false);
		}


	}

	class AttackNearestGoal extends NearestAttackableTargetGoal<Player> {
		public AttackNearestGoal(MimicEntity mimic) {
			super(mimic, Player.class, true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			if (!MimicEntity.this.isHiding() && super.canUse()) {
				return true;
			} else {
				return false;
			}
		}

		public void start() {
			super.start();
			MimicEntity.this.setHiding(false);
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
			if (super.canUse() && !MimicEntity.this.isHiding()) {
				return true;
			} else {
				return false;
			}
		}

	}

	class HideGoal extends Goal {

		@Override
		public boolean canUse() {
			if (MimicEntity.this.getTarget() == null) {
				return true;
			} else {
				return false;
			}
		}
		
		public void startExecuting() {
			MimicEntity.this.setHiding(true);
		}

	}


	@OnlyIn(Dist.CLIENT)
	public Vec3 getLightProbePosition(float p_241842_1_) {
		return super.getLightProbePosition(p_241842_1_);
	}

}
