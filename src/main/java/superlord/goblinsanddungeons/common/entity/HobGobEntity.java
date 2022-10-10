package superlord.goblinsanddungeons.common.entity;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class HobGobEntity extends GoblinEntity {
	
	public HobGobEntity(EntityType<? extends HobGobEntity> type, Level worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, (double)0.22F).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.FOLLOW_RANGE, 25.0D);
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
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

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
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
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.HOBGOB_SPAWN_EGG.get());
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
	
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new HobGobEntity.ThrowGoblinGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Sheep.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Cow.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Pig.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Horse.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Donkey.class, true));
	}
	
	class ThrowGoblinGoal extends Goal {

		HobGobEntity hobgob;

		public ThrowGoblinGoal(HobGobEntity hobgob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			this.hobgob = hobgob;
		}

		@Override
		public boolean canUse() {
			List<Player> player = hobgob.level.getEntitiesOfClass(Player.class, hobgob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
			if (!player.isEmpty() && hobgob.getTarget() == null) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean shouldContinueExecuting() {
			if (hobgob.getTarget() != null) {
				return false;
			} else {
				return true;
			}

		}

		public void tick() {
			List<GobEntity> gob1 = hobgob.level.getEntitiesOfClass(GobEntity.class, hobgob.getBoundingBox().inflate(1.0D, 1.0D, 1.0D));
			if (!gob1.isEmpty()){
				if(hobgob.getDirection() == Direction.NORTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setDeltaMovement(0.0D, 1.0D, -2.0D);
				}
				if (hobgob.getDirection() == Direction.WEST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setDeltaMovement(-2.0D, 1.0D, 0.0D);
				}
				if (hobgob.getDirection() == Direction.SOUTH) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setDeltaMovement(0.0D, 1.0D, 2.0D);
				}
				if (hobgob.getDirection() == Direction.EAST) {
					GobEntity gobEntity = gob1.get(0);
					gobEntity.setDeltaMovement(2.0D, 1.0D, 0.0D);
				}
					
			}
		}
	}


}
