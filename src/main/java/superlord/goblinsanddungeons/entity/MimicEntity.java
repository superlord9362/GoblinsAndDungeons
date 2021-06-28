package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class MimicEntity extends GoblinEntity {
	private static final DataParameter<Boolean> HIDING = EntityDataManager.createKey(MimicEntity.class, DataSerializers.BOOLEAN);
	private BlockPos currentAttachmentPosition = null;
	private int clientSideTeleportInterpolation;

	public MimicEntity(EntityType<? extends MimicEntity> p_i50196_1_, World p_i50196_2_) {
		super(p_i50196_1_, p_i50196_2_);
	}
	public boolean isHiding() {
		return this.dataManager.get(HIDING);
	}

	private void setHiding(boolean isHiding) {
		this.dataManager.set(HIDING, isHiding);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MimicEntity.HidingLookGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new MimicEntity.AttackNearestGoal(this));
		this.goalSelector.addGoal(0, new MimicEntity.HideGoal());
		this.targetSelector.addGoal(1, new MimicEntity.DefendGoal(this));
	}


	protected void registerData() {
		super.registerData();
		this.dataManager.register(HIDING, false);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.3F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setHiding(compound.getBoolean("IsHiding"));
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsHiding", this.isHiding());
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        if (!world.isRemote) {
        	this.setHiding(false);
            return ActionResultType.SUCCESS;
        }
		return super.func_230254_b_(p_230254_1_, p_230254_2_);
	}

	public boolean isOnSameTeam(Entity entityIn) {
		if (super.isOnSameTeam(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttributeInit.GOBLIN) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		this.setHiding(true);
		return spawnDataIn;
	}

	public void livingTick() {
		super.livingTick();
		if (this.isHiding()) {
			this.setMotion(Vector3d.ZERO);
			if (!this.isAIDisabled()) {
				this.prevRenderYawOffset = 0.0F;
				this.renderYawOffset = 0.0F;
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

	public boolean attackEntityFrom(DamageSource source, float amount) {

		if (super.attackEntityFrom(source, amount)) {
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

	public void applyEntityCollision(Entity entityIn) {
		if (!this.isHiding()) {
	         super.applyEntityCollision(entityIn);
		}
	}

	public float getCollisionBorderSize() {
		return 0.0F;
	}

	class DefendGoal extends HurtByTargetGoal {

		public DefendGoal(CreatureEntity creatureIn) {
			super(creatureIn);
		}

		public void startExecutign() {
			super.startExecuting();
			MimicEntity.this.setHiding(false);
		}


	}

	class AttackNearestGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		public AttackNearestGoal(MimicEntity shulker) {
			super(shulker, PlayerEntity.class, true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			if (!MimicEntity.this.isHiding() && super.shouldExecute()) {
				return true;
			} else {
				return false;
			}
		}

		public void startExecuting() {
			super.startExecuting();
			MimicEntity.this.setHiding(false);
		}

		public void resetTask() {
			super.resetTask();
		}

	}

	class HidingLookGoal extends LookAtGoal {

		public HidingLookGoal(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
			super(entityIn, watchTargetClass, maxDistance);
		}

		public boolean shouldExecute() {
			if (super.shouldExecute() && !MimicEntity.this.isHiding()) {
				return true;
			} else {
				return false;
			}
		}

	}

	class HideGoal extends Goal {

		@Override
		public boolean shouldExecute() {
			if (MimicEntity.this.getAttackTarget() == null && MimicEntity.this.getRevengeTarget() == null) {
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
	public Vector3d func_241842_k(float p_241842_1_) {
		return super.func_241842_k(p_241842_1_);
	}

}
