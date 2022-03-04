package superlord.goblinsanddungeons.entity;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class MimicEntity extends GoblinEntity {
	private static final EntityDataAccessor<Boolean> HIDING = SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.BOOLEAN);
	private BlockPos currentAttachmentPosition = null;
	private int clientSideTeleportInterpolation;
	@Nullable
	private BlockPos clientOldAttachPosition;

	public MimicEntity(EntityType<? extends MimicEntity> p_i50196_1_, Level p_i50196_2_) {
		super(p_i50196_1_, p_i50196_2_);
	}
	public boolean isHiding() {
		return this.entityData.get(HIDING);
	}

	private void setHiding(boolean isHiding) {
		this.entityData.set(HIDING, isHiding);
	}

	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			if (this.clientSideTeleportInterpolation > 0) {
				--this.clientSideTeleportInterpolation;
			} else {
				this.clientOldAttachPosition = null;
			}
		}

	}

	public boolean startRiding(Entity p_149773_, boolean p_149774_) {
		if (this.level.isClientSide()) {
			this.clientOldAttachPosition = null;
			this.clientSideTeleportInterpolation = 0;
		}
		return super.startRiding(p_149773_, p_149774_);
	}

	public void stopRiding() {
		super.stopRiding();
		if (this.level.isClientSide) {
			this.clientOldAttachPosition = this.blockPosition();
		}

		this.yBodyRotO = 0.0F;
		this.yBodyRot = 0.0F;
	}

	public void setPos(double p_33449_, double p_33450_, double p_33451_) {
		BlockPos blockpos = this.blockPosition();
		if (this.isPassenger()) {
			super.setPos(p_33449_, p_33450_, p_33451_);
		} else {
			super.setPos((double)Mth.floor(p_33449_) + 0.5D, (double)Mth.floor(p_33450_ + 0.5D), (double)Mth.floor(p_33451_) + 0.5D);
		}

		if (this.tickCount != 0) {
			BlockPos blockpos1 = this.blockPosition();
			if (!blockpos1.equals(blockpos)) {
				this.hasImpulse = true;
				if (this.level.isClientSide && !this.isPassenger() && !blockpos1.equals(this.clientOldAttachPosition)) {
					this.clientOldAttachPosition = blockpos;
					this.clientSideTeleportInterpolation = 6;
					this.xOld = this.getX();
					this.yOld = this.getY();
					this.zOld = this.getZ();
				}
			}

		}
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

	public static AABB getProgressAabb(Direction p_149791_, float p_149792_) {
		return getProgressDeltaAabb(p_149791_, -1.0F, p_149792_);
	}

	public static AABB getProgressDeltaAabb(Direction p_149794_, float p_149795_, float p_149796_) {
		double d0 = (double)Math.max(p_149795_, p_149796_);
		double d1 = (double)Math.min(p_149795_, p_149796_);
		return (new AABB(BlockPos.ZERO)).expandTowards((double)p_149794_.getStepX() * d0, (double)p_149794_.getStepY() * d0, (double)p_149794_.getStepZ() * d0).contract((double)(-p_149794_.getStepX()) * (1.0D + d1), (double)(-p_149794_.getStepY()) * (1.0D + d1), (double)(-p_149794_.getStepZ()) * (1.0D + d1));
	}

	boolean canStayAt(BlockPos p_149786_, Direction p_149787_) {
		if (this.isPositionBlocked(p_149786_)) {
			return false;
		} else {
			Direction direction = p_149787_.getOpposite();
			if (!this.level.loadedAndEntityCanStandOnFace(p_149786_.relative(p_149787_), this, direction)) {
				return false;
			} else {
				AABB aabb = getProgressAabb(direction, 1.0F).move(p_149786_).deflate(1.0E-6D);
				return this.level.noCollision(this, aabb);
			}
		}
	}

	private boolean isPositionBlocked(BlockPos p_149813_) {
		BlockState blockstate = this.level.getBlockState(p_149813_);
		if (blockstate.isAir()) {
			return false;
		} else {
			boolean flag = blockstate.is(Blocks.MOVING_PISTON) && p_149813_.equals(this.blockPosition());
			return !flag;
		}
	}

	public boolean canBeCollidedWith() {
		return this.isAlive();
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

	public Optional<Vec3> getRenderPosition(float p_149767_) {
		if (this.clientOldAttachPosition != null && this.clientSideTeleportInterpolation > 0) {
			double d0 = (double)((float)this.clientSideTeleportInterpolation - p_149767_) / 6.0D;
			d0 *= d0;
			BlockPos blockpos = this.blockPosition();
			double d1 = (double)(blockpos.getX() - this.clientOldAttachPosition.getX()) * d0;
			double d2 = (double)(blockpos.getY() - this.clientOldAttachPosition.getY()) * d0;
			double d3 = (double)(blockpos.getZ() - this.clientOldAttachPosition.getZ()) * d0;
			return Optional.of(new Vec3(-d1, -d2, -d3));
		} else {
			return Optional.empty();
		}
	}

	public void recreateFromPacket(ClientboundAddMobPacket p_149798_) {
		super.recreateFromPacket(p_149798_);
		this.yBodyRot = 0.0F;
		this.yBodyRotO = 0.0F;
	}


}
