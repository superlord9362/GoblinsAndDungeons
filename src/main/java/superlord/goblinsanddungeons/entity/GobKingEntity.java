package superlord.goblinsanddungeons.entity;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.init.ItemInit;

public class GobKingEntity extends MonsterEntity {
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BYTE);

	public GobKingEntity(EntityType<? extends GobKingEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected PathNavigator createNavigator(World worldIn) {
		return new ClimberPathNavigator(this, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(1, new GobKingEntity.AttackGoal());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractRaiderEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractVillagerEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolemEntity.class, 5.0F, 2.2D, 2.2D));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.25F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		return spawnDataIn;
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);
		if (flag) {
			float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();
			if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
				entityIn.setFire(2 * (int)f);
			}
		}

		return flag;
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(CLIMBING, (byte)0);
	}

	public void tick() {
		super.tick();
		if (!this.world.isRemote) {
			this.setBesideClimbableBlock(this.collidedHorizontally);
		}

	}

	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataManager.get(CLIMBING) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbing) {
		byte b0 = this.dataManager.get(CLIMBING);
		if (climbing) {
			b0 = (byte)(b0 | 1);
		} else {
			b0 = (byte)(b0 & -2);
		}

		this.dataManager.set(CLIMBING, b0);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.GOB_SPAWN_EGG.get());
	}

	class AttackGoal extends Goal {
		private int attackTime;

		public AttackGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			LivingEntity livingentity = GobKingEntity.this.getAttackTarget();
			if (livingentity != null && livingentity.isAlive()) {
				return GobKingEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
			} else {
				return false;
			}
		}

		public void startExecuting() {
			this.attackTime = 20;
		}

		public void tick() {
			if (GobKingEntity.this.world.getDifficulty() != Difficulty.PEACEFUL) {
				--this.attackTime;
				LivingEntity livingentity = GobKingEntity.this.getAttackTarget();
				GobKingEntity.this.getLookController().setLookPositionWithEntity(livingentity, 180.0F, 180.0F);
				double d0 = GobKingEntity.this.getDistanceSq(livingentity);
				if (d0 < 400.0D) {
					if (this.attackTime <= 0) {
						this.attackTime = 20 + GobKingEntity.this.rand.nextInt(10) * 20 / 2;
						GobKingEntity.this.world.addEntity(new GoblinSoulBulletEntity(GobKingEntity.this.world, GobKingEntity.this, livingentity));
						GobKingEntity.this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (GobKingEntity.this.rand.nextFloat() - GobKingEntity.this.rand.nextFloat()) * 0.2F + 1.0F);
					}
				} else {
					GobKingEntity.this.setAttackTarget((LivingEntity)null);
				}

				super.tick();
			}
		}
	}

}
