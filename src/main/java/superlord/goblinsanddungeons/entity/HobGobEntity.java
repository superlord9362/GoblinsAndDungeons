package superlord.goblinsanddungeons.entity;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class HobGobEntity extends GoblinEntity {
	
	public HobGobEntity(EntityType<? extends HobGobEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.22F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D);
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
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

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.HOBGOB_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.HOBGOB_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.HOBGOB_DEATH;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.HOBGOB_SPAWN_EGG.get());
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
	
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new HobGobEntity.ThrowGoblinGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SheepEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, HorseEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DonkeyEntity.class, true));
	}
	
	class ThrowGoblinGoal extends Goal {

		HobGobEntity hobgob;

		public ThrowGoblinGoal(HobGobEntity hobgob) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
			this.hobgob = hobgob;
		}

		@Override
		public boolean shouldExecute() {
			List<PlayerEntity> player = hobgob.world.getEntitiesWithinAABB(PlayerEntity.class, hobgob.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!player.isEmpty() && hobgob.getRevengeTarget() == null) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean shouldContinueExecuting() {
			if (hobgob.getRevengeTarget() != null) {
				return false;
			} else {
				return true;
			}

		}

		public void tick() {
			List<GobEntity> gob1 = hobgob.world.getEntitiesWithinAABB(GobEntity.class, hobgob.getBoundingBox().grow(1.0D, 1.0D, 1.0D));
			if (!gob1.isEmpty()){
				if(hobgob.getHorizontalFacing() == Direction.NORTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(0.0D, 1.0D, -2.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.WEST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(-2.0D, 1.0D, 0.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.SOUTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(0.0D, 1.0D, 2.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.EAST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(2.0D, 1.0D, 0.0D);
				}
					
			}
		}
	}
	
	/**

	public HobGobEntity(EntityType<? extends HobGobEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
		//this.goalSelector.addGoal(4, new HobGobEntity.ThrowGoblinGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SheepEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, HorseEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DonkeyEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.22F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D);
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
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

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.HOBGOB_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.HOBGOB_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.HOBGOB_DEATH;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.HOBGOB_SPAWN_EGG.get());
	}

	class ThrowGoblinGoal extends Goal {

		HobGobEntity hobgob;

		public ThrowGoblinGoal(HobGobEntity hobgob) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
			this.hobgob = hobgob;
		}

		@Override
		public boolean shouldExecute() {
			List<PlayerEntity> player = hobgob.world.getEntitiesWithinAABB(PlayerEntity.class, hobgob.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!player.isEmpty() && hobgob.getRevengeTarget() == null) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean shouldContinueExecuting() {
			if (hobgob.getRevengeTarget() != null) {
				return false;
			} else {
				return true;
			}

		}

		public void tick() {
			List<GobEntity> gob1 = hobgob.world.getEntitiesWithinAABB(GobEntity.class, hobgob.getBoundingBox().grow(1.0D, 1.0D, 1.0D));
			if (!gob1.isEmpty()){
				if(hobgob.getHorizontalFacing() == Direction.NORTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(0.0D, 1.0D, 2.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.WEST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(2.0D, 1.0D, 0.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.SOUTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(0.0D, 1.0D, -2.0D);
				}
				if (hobgob.getHorizontalFacing() == Direction.EAST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setMotion(-2.0D, 1.0D, 0.0D);
				}
					
			}
		}
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
	
	*/

}
