package superlord.goblinsanddungeons.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.entity.ai.GoomSmokeGoal;
import superlord.goblinsanddungeons.init.ItemInit;

public class GoomEntity extends MonsterEntity {
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> STATE = EntityDataManager.createKey(GoomEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(GoomEntity.class, DataSerializers.BOOLEAN);
	@SuppressWarnings("unused")
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;

	public GoomEntity(EntityType<? extends GoomEntity> type, World worldIn) {
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
		this.goalSelector.addGoal(1, new GoomSmokeGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.275F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.DAGGER.get()));

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
		this.dataManager.register(STATE, -1);
		this.dataManager.register(IGNITED, false);
	}

	public void tick() {
		super.tick();
		if (!this.world.isRemote) {
			this.setBesideClimbableBlock(this.collidedHorizontally);
		}
		if (this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if (this.hasIgnited()) {
				this.setGoomState(1);
			}

			int i = this.getGoomState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

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
				this.ignite();
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
			this.world.addEntity(areaeffectcloudentity);
		}

	}

	public boolean hasIgnited() {
		return this.dataManager.get(IGNITED);
	}

	public void ignite() {
		this.dataManager.set(IGNITED, true);
	}

	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataManager.get(CLIMBING) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
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
        return new ItemStack(ItemInit.GOOM_SPAWN_EGG.get());
    }

}
