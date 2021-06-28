package superlord.goblinsanddungeons.entity;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;

public class GobberEntity extends GoblinEntity implements ICrossbowUser {

	private static final DataParameter<Boolean> HAS_CROSSBOW = EntityDataManager.createKey(GobberEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(GobberEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(GobberEntity.class, DataSerializers.BOOLEAN);

	public GobberEntity(EntityType<? extends GobberEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
	}

	public boolean hasCrossbow() {
		return this.dataManager.get(HAS_CROSSBOW);
	}

	@OnlyIn(Dist.CLIENT)
	public AbstractIllagerEntity.ArmPose getArmPose() {
		if (this.isCharging()) {
			return AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE;
		} else if (this.canEquip(Items.CROSSBOW)) {
			return AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD;
		} else {
			return this.isAggressive() ? AbstractIllagerEntity.ArmPose.ATTACKING : AbstractIllagerEntity.ArmPose.NEUTRAL;
		}
	}

	private void setHasCrossbow(boolean hasCrossbow) {
		this.dataManager.set(HAS_CROSSBOW, hasCrossbow);
	}

	public boolean isSleeping() {
		return this.dataManager.get(SLEEPING);
	}

	private void setSleeping(boolean isSleeping) {
		this.dataManager.set(SLEEPING, isSleeping);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new GobberEntity.SleepGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(DATA_CHARGING_STATE, false);
		this.dataManager.register(HAS_CROSSBOW, false);
		this.dataManager.register(SLEEPING, false);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.15F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D).createMutableAttribute(Attributes.ARMOR, 5.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 7.5D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D);
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	protected void updateAITasks() {
		if (this.isSleeping()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) 0.15F);
		}
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		this.setCombatTask();
		return spawnDataIn;
	}

	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		int i = this.rand.nextInt(3);
		if (i == 0) {
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
			this.setHasCrossbow(true);
		} else {
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
		}
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

	public void setCombatTask() {
		if (this.world != null && !this.world.isRemote) {
			ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			if (itemstack.getItem() == Items.CROSSBOW) {
				this.goalSelector.addGoal(0, new RangedCrossbowAttackGoal<>(this, 1.0D, 8.0F));
			}
			if (itemstack.getItem() != Items.CROSSBOW) {
				this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
			}
		}
	}

	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		this.func_234281_b_(this, 1.6F);
	}

	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		return ProjectileHelper.fireArrow(this, arrowStack, distanceFactor);
	}

	public boolean func_230280_a_(ShootableItem p_230280_1_) {
		return p_230280_1_ == Items.CROSSBOW;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.GOBBER_SPAWN_EGG.get());
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isCharging() {
		return this.dataManager.get(DATA_CHARGING_STATE);
	}

	public void setCharging(boolean isCharging) {
		this.dataManager.set(DATA_CHARGING_STATE, isCharging);
	}

	public void func_230283_U__() {
		this.idleTime = 0;
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsSleeping", this.isSleeping());
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setCombatTask();
		this.setSleeping(compound.getBoolean("IsSleeping"));
	}

	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
		super.setItemStackToSlot(slotIn, stack);
		if (!this.world.isRemote) {
			this.setCombatTask();
		}

	}


	@Override
	public void func_230284_a_(LivingEntity p_230284_1_, ItemStack p_230284_2_, ProjectileEntity p_230284_3_, float p_230284_4_) {
		this.func_234279_a_(this, p_230284_1_, p_230284_3_, p_230284_4_, 1.6F);
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

	class SleepGoal extends Goal {
		GobberEntity gobber;

		public SleepGoal(GobberEntity gobber) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
			this.gobber = gobber;
		}

		public boolean shouldExecute() {
			List<PlayerEntity> players = GobberEntity.this.world.getEntitiesWithinAABB(PlayerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<AbstractIllagerEntity> illagers = GobberEntity.this.world.getEntitiesWithinAABB(AbstractIllagerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<AbstractVillagerEntity> villagers = GobberEntity.this.world.getEntitiesWithinAABB(AbstractVillagerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<GolemEntity> golems = GobberEntity.this.world.getEntitiesWithinAABB(GolemEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (players.isEmpty() && illagers.isEmpty() && villagers.isEmpty() && golems.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}

		public boolean shouldContinueExecuting() {
			List<PlayerEntity> players = GobberEntity.this.world.getEntitiesWithinAABB(PlayerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<AbstractIllagerEntity> illagers = GobberEntity.this.world.getEntitiesWithinAABB(AbstractIllagerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<AbstractVillagerEntity> villagers = GobberEntity.this.world.getEntitiesWithinAABB(AbstractVillagerEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			List<GolemEntity> golems = GobberEntity.this.world.getEntitiesWithinAABB(GolemEntity.class, GobberEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!players.isEmpty() || !illagers.isEmpty() || !villagers.isEmpty() || !golems.isEmpty()) {
				return false;
			} else {
				return true;
			}
		}

		public void startExecuting() {
			gobber.setAttackTarget(null);
			gobber.setRevengeTarget(null);
			gobber.setSleeping(true);
			gobber.getNavigator().clearPath();
			gobber.setMotion(0.0D, 0.0D, 0.0D);
			gobber.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		}

		public void resetTask() {
			super.resetTask();
			gobber.setSleeping(false);
			gobber.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
		}

	}

}
