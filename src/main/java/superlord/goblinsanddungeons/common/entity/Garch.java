package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class Garch extends Goblin implements RangedAttackMob {

	private final RangedBowAttackGoal<Garch> aiArrowAttack = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);

	private static final EntityDataAccessor<Boolean> HAS_RIDER = SynchedEntityData.defineId(Garch.class, EntityDataSerializers.BOOLEAN);

	public boolean hasRider() {
		return this.entityData.get(HAS_RIDER);
	}

	private void setHasRider(boolean hasRider) {
		this.entityData.set(HAS_RIDER, hasRider);
	}

	public Garch(EntityType<? extends Garch> type, Level worldIn) {
		super(type, worldIn);
		this.reassessWeaponGoal();
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Raider.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractVillager.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 5.0F, 2.2D, 2.2D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.GARCH_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.GARCH_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.GARCH_DEATH;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.25F).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.FOLLOW_RANGE, 35.0D);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		this.reassessWeaponGoal();
		return spawnDataIn;
	}

	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.aiArrowAttack);
			ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
			if (itemstack.is(Items.BOW)) {
				int i = 20;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}
				this.aiArrowAttack.setMinAttackInterval(i);
				this.goalSelector.addGoal(4, this.aiArrowAttack);
			}

		}
	}

	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
		AbstractArrow abstractarrowentity = this.getArrow(itemstack, distanceFactor);
	      if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
	    	  abstractarrowentity = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrowentity);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.3333333333333333D) - abstractarrowentity.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = (double)Math.sqrt(d0 * d0 + d2 * d2);
		abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(abstractarrowentity);
	}

	protected AbstractArrow getArrow(ItemStack arrowStack, float distanceFactor) {
		return ProjectileUtil.getMobArrow(this, arrowStack, distanceFactor);
	}

	public boolean canFireProjectileWeapon(ProjectileWeaponItem p_230280_1_) {
		return p_230280_1_ == Items.BOW;
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.reassessWeaponGoal();
		this.setHasRider(compound.getBoolean("HasRider"));
	}

	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!this.level.isClientSide) {
			this.reassessWeaponGoal();
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_RIDER, false);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsSleeping", this.isSleeping());
		compound.putBoolean("HasRider", this.hasRider());
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.GARCH_SPAWN_EGG.get());
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

}
