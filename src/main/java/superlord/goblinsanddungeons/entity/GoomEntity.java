package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.entity.ai.GoomSmokeGoal;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class GoomEntity extends GoblinEntity {
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> STATE = EntityDataManager.createKey(GoomEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> BLOWN = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BOOLEAN);
	@SuppressWarnings("unused")
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int timeTillBomb = 0;

	public GoomEntity(EntityType<? extends GoomEntity> type, World worldIn) {
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
		this.goalSelector.addGoal(1, new GoomSmokeGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}


	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.275F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D);
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.GOOM_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.GOOM_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.GOOM_DEATH;
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

	public boolean isBlownUp() {
		return this.dataManager.get(BLOWN);
	}

	public void setBlownUp(boolean isBlownUp) {
		this.dataManager.set(BLOWN, isBlownUp);
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(CLIMBING, (byte)0);
		this.dataManager.register(STATE, -1);
		this.dataManager.register(IGNITED, false);
		this.dataManager.register(BLOWN, false);
	}

	public void tick() {
		super.tick();
		if (this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if (this.hasIgnited()) {
				this.setGoomState(1);
			}

			int i = this.getGoomState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundInit.GOOM_WARNING, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				if (!this.isBlownUp()) {
					this.explode();
				}
			}
			if (this.isBlownUp()) {
				this.timeTillBomb++;
				if (this.timeTillBomb >= 6000) {
					this.ignite(false);
					this.setBlownUp(false);
					BlockPos pos = new BlockPos (this.getPosX(), this.getPosY(), this.getPosZ());
					world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.HOSTILE, 0.3F, 0.5F);
				}
			}
		}

	}
	
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT compound) {
		spawnData = super.onInitialSpawn(worldIn, difficulty, reason, spawnData, compound);
		return spawnData;
	}
	
	public int getGoomState() {
		return this.dataManager.get(STATE);
	}

	/**
	 * Sets the state of goom, -1 to idle and 1 to be 'in fuse'
	 */
	public void setGoomState(int state) {
		this.dataManager.set(STATE, state);
	}

	protected ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			this.world.playSound(p_230254_1_, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			if (!this.world.isRemote) {
				this.ignite(true);
				itemstack.damageItem(1, p_230254_1_, (player) -> {
					player.sendBreakAnimation(p_230254_2_);
				});
			}

			return ActionResultType.func_233537_a_(this.world.isRemote);
		} else {
			return super.func_230254_b_(p_230254_1_, p_230254_2_);
		}
	}

	private void explode() {
		if (!this.world.isRemote) {
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
			areaeffectcloudentity.setParticleData(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE);
			this.setBlownUp(true);
			BlockPos pos = new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ());
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.3F, 0.5F);
			this.world.addEntity(areaeffectcloudentity);
		}

	}

	public boolean hasIgnited() {
		return this.dataManager.get(IGNITED);
	}

	public void ignite(boolean hasIgnited) {
		this.dataManager.set(IGNITED, hasIgnited);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.GOOM_SPAWN_EGG.get());
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
	
	class PostExplosionAttackGoal extends MeleeAttackGoal {

		public PostExplosionAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		public boolean shouldExecute() {
			if (GoomEntity.this.isBlownUp() && super.shouldExecute()) return true;
			else return false;
		}
		
		public boolean shouldContinueExecuting() {
			if (!GoomEntity.this.isBlownUp() || !super.shouldContinueExecuting()) return false;
			else return true;
		}
		
	}

}
