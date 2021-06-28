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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;

public class GobloEntity extends GoblinEntity {

	private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(GobloEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> HAS_CHICKEN = EntityDataManager.createKey(GobloEntity.class, DataSerializers.BOOLEAN);
	public int eatTicks;

	public GobloEntity(EntityType<? extends GobloEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCanPickUpLoot(true);
	}

	public boolean isSleeping() {
		return this.dataManager.get(SLEEPING);
	}

	private void setSleeping(boolean isSleeping) {
		this.dataManager.set(SLEEPING, isSleeping);
	}

	public boolean hasChicken() {
		return this.dataManager.get(HAS_CHICKEN);
	}

	private void setHasChicken(boolean hasChicken) {
		this.dataManager.set(HAS_CHICKEN, hasChicken);
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean preventDespawn() {
		return super.preventDespawn();
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
		this.goalSelector.addGoal(1, new GobloEntity.SleepGoal(this));
		this.goalSelector.addGoal(2, new GobloEntity.PickUpChickenGoal(this));
		this.goalSelector.addGoal(2, new GobloEntity.FindItemsGoal());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D);
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
		this.dataManager.register(SLEEPING, false);
		this.dataManager.register(HAS_CHICKEN, false);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsSleeping", this.isSleeping());
		compound.putBoolean("HasChicken", this.hasChicken());
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSleeping(compound.getBoolean("IsSleeping"));
		this.setHasChicken(compound.getBoolean("HasChicken"));
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 45) {
			ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			if (!itemstack.isEmpty()) {
				for(int i = 0; i < 8; ++i) {
					Vector3d vector3d = (new Vector3d(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).rotatePitch(-this.rotationPitch * ((float)Math.PI / 180F)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F));
					this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), this.getPosX() + this.getLookVec().x / 2.0D, this.getPosY(), this.getPosZ() + this.getLookVec().z / 2.0D, vector3d.x, vector3d.y + 0.05D, vector3d.z);
				}
			}
		} else {
			super.handleStatusUpdate(id);
		}

	}

	public SoundEvent getEatSound(ItemStack itemStackIn) {
		return SoundEvents.ENTITY_FOX_EAT;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void livingTick() {
		if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
			++this.eatTicks;
			ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			if (this.canEatItem(itemstack)) {
				
				if (this.eatTicks > 100 && itemstack.getItem() == Items.BEETROOT) {
					ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
					if (!itemstack1.isEmpty()) {
						this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
					}
					this.setSleeping(true);
					itemstack.shrink(1);
					this.eatTicks = 0;
				} else if (this.eatTicks > 100) {
					ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
					if (!itemstack1.isEmpty()) {
						this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
					}
					itemstack.shrink(1);
					this.eatTicks = 0;
				} else if (this.eatTicks > 60 && this.rand.nextFloat() < 0.1F) {
					this.playSound(this.getEatSound(itemstack), 1.0F, 1.0F);
					this.world.setEntityState(this, (byte)45);
				}
			}

		}
		if (!this.isBeingRidden()) {
			this.setHasChicken(false);
		}
	      Vector3d vector3d = this.getMotion();

		if (!this.onGround && vector3d.y < 0.0D && this.hasChicken()) {
	         this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
	      }

		if (this.isSleeping()) {
			this.setMotion(vector3d.mul(0.0D, 0.0D, 0.0D));
		}
		super.livingTick();
	}

	public boolean onLivingFall(float distance, float damageMultiplier) {
		if(this.hasChicken()) {
			return false;
		} else {
			return super.onLivingFall(distance, damageMultiplier);
		}
	}

	private boolean canEatItem(ItemStack itemStackIn) {
		return itemStackIn.getItem() != Items.DIAMOND && itemStackIn.getItem() != Items.DIAMOND_AXE && itemStackIn.getItem() != Items.DIAMOND_BLOCK && itemStackIn.getItem() != Items.DIAMOND_BOOTS && itemStackIn.getItem() != Items.DIAMOND_CHESTPLATE && itemStackIn.getItem() != Items.DIAMOND_HELMET && itemStackIn.getItem() != Items.DIAMOND_HORSE_ARMOR && itemStackIn.getItem() != Items.DIAMOND_LEGGINGS && itemStackIn.getItem() != Items.DIAMOND_ORE && itemStackIn.getItem() != Items.DIAMOND_PICKAXE && itemStackIn.getItem() != Items.DIAMOND_SHOVEL && itemStackIn.getItem() != Items.DIAMOND_SWORD && itemStackIn.getItem() != Items.WITHER_ROSE && itemStackIn.getItem() != Items.WITHER_SKELETON_SKULL && itemStackIn.getItem() != Items.NETHER_STAR && itemStackIn.getItem() != Items.BEACON && itemStackIn.getItem() != Items.DRAGON_HEAD && itemStackIn.getItem() != Items.DRAGON_EGG && itemStackIn.getItem() != Items.CREEPER_HEAD && itemStackIn.getItem() != Items.PLAYER_HEAD && itemStackIn.getItem() != Items.ZOMBIE_HEAD && itemStackIn.getItem() != Items.SKELETON_SKULL && itemStackIn.getItem() != Items.EMERALD && itemStackIn.getItem() != Items.EMERALD_BLOCK && itemStackIn.getItem() != Items.EMERALD_ORE && itemStackIn.getItem() != Items.ANCIENT_DEBRIS && itemStackIn.getItem() != Items.NETHERITE_AXE && itemStackIn.getItem() != Items.NETHERITE_BOOTS && itemStackIn.getItem() != Items.NETHERITE_BRICKS && itemStackIn.getItem() != Items.NETHERITE_CHESTPLATE && itemStackIn.getItem() != Items.NETHERITE_HELMET && itemStackIn.getItem() != Items.NETHERITE_HOE && itemStackIn.getItem() != Items.NETHERITE_INGOT && itemStackIn.getItem() != Items.NETHERITE_LEGGINGS && itemStackIn.getItem() != Items.NETHERITE_PICKAXE && itemStackIn.getItem() != Items.NETHERITE_SCRAP && itemStackIn.getItem() != Items.NETHERITE_SHOVEL && itemStackIn.getItem() != Items.NETHERITE_SWORD && itemStackIn.getItem() != Items.END_CRYSTAL && itemStackIn.getItem() != Items.END_PORTAL_FRAME && itemStackIn.getItem() != Items.BLACK_SHULKER_BOX && itemStackIn.getItem() != Items.BLUE_SHULKER_BOX && itemStackIn.getItem() != Items.BROWN_SHULKER_BOX && itemStackIn.getItem() != Items.CYAN_SHULKER_BOX && itemStackIn.getItem() != Items.GRAY_SHULKER_BOX && itemStackIn.getItem() != Items.GREEN_SHULKER_BOX && itemStackIn.getItem() != Items.LIGHT_BLUE_SHULKER_BOX && itemStackIn.getItem() != Items.LIGHT_GRAY_SHULKER_BOX && itemStackIn.getItem() != Items.LIME_SHULKER_BOX && itemStackIn.getItem() != Items.MAGENTA_SHULKER_BOX && itemStackIn.getItem() != Items.ORANGE_SHULKER_BOX && itemStackIn.getItem() != Items.PINK_SHULKER_BOX && itemStackIn.getItem() != Items.PURPLE_SHULKER_BOX && itemStackIn.getItem() != Items.RED_SHULKER_BOX && itemStackIn.getItem() != Items.SHULKER_BOX && itemStackIn.getItem() != Items.WHITE_SHULKER_BOX && itemStackIn.getItem() != Items.YELLOW_SHULKER_BOX && itemStackIn.getItem() != Items.ELYTRA && itemStackIn.getItem() != Items.HEART_OF_THE_SEA && itemStackIn.getItem() != Items.TOTEM_OF_UNDYING && this.onGround && !this.isSleeping();
	}

	public boolean canEquipItem(ItemStack stack) {
		Item item = stack.getItem();
		ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
		return itemstack.isEmpty() && !this.hasChicken() && !this.isSleeping() || this.eatTicks > 0 && item.isFood() && !itemstack.getItem().isFood();
	}

	protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
		ItemStack itemstack = itemEntity.getItem();
		if (this.canEquipItem(itemstack)) {
			this.triggerItemPickupTrigger(itemEntity);
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.split(1));
			this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
			this.onItemPickup(itemEntity, itemstack.getCount());
			itemEntity.remove();
			this.eatTicks = 0;
		}

	}

	class PickUpChickenGoal extends Goal {

		GobloEntity goblo;
		ChickenEntity chicken;
		int ticks = 0;

		public PickUpChickenGoal(GobloEntity goblo) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
			this.goblo = goblo;
		}

		public boolean shouldExecute() {
			if (goblo.getRevengeTarget() == null && goblo.getAttackTarget() == null && !goblo.hasChicken()) {
				return true;
			} else {
				return false;
			}
		}

		public void tick() {
			List<ChickenEntity> list = goblo.world.getEntitiesWithinAABB(ChickenEntity.class, goblo.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!list.isEmpty() && !goblo.hasChicken()) goblo.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
		}

		public void startExecuting() {
			List<ChickenEntity> list = goblo.world.getEntitiesWithinAABB(ChickenEntity.class, goblo.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!list.isEmpty() && !goblo.hasChicken()) {
				goblo.getNavigator().tryMoveToEntityLiving(list.get(0), (double) 1.2F);
			}
			List<ChickenEntity> list2 = goblo.world.getEntitiesWithinAABB(ChickenEntity.class, goblo.getBoundingBox().grow(1.0D, 1.0D, 1.0D)); 
			if (!list2.isEmpty() && !goblo.hasChicken()) {
				chicken = list2.get(0);
				chicken.startRiding(goblo);
				this.goblo.setHasChicken(true);
			}
			if (goblo.getRevengeTarget() != null) {
				chicken.dismount();
				this.goblo.setHasChicken(false);
			}
		}

		public boolean shouldContinueExecuting() {
			if (goblo.getRevengeTarget() != null) {
				return false;
			} else {
				return true;
			}
		}

	}

	class FindItemsGoal extends Goal {
		public FindItemsGoal() {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean shouldExecute() {
			if (!GobloEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() || GobloEntity.this.hasChicken()) {
				return false;
			} else if (GobloEntity.this.getAttackTarget() == null && GobloEntity.this.getRevengeTarget() == null) {
				if (GobloEntity.this.getRNG().nextInt(10) != 0) {
					return false;
				} else {
					List<ItemEntity> list = GobloEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, GobloEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
					return !list.isEmpty() && GobloEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
				}
			} else {
				return false;
			}
		}

		public void tick() {
			List<ItemEntity> list = GobloEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, GobloEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			ItemStack itemstack = GobloEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			if (itemstack.isEmpty() && !list.isEmpty()) {
				GobloEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
			}

		}
		
		public void startExecuting() {
			List<ItemEntity> list = GobloEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, GobloEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (!list.isEmpty()) {
				GobloEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
			}

		}
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.GOBLO_SPAWN_EGG.get());
	}

	class SleepGoal extends Goal {
		GobloEntity goblo;
		int sleepTicks = 0;

		public SleepGoal(GobloEntity goblo) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
			this.goblo = goblo;
		}

		public boolean shouldExecute() {
			if (goblo.isSleeping()) {
				return true;
			} else {
				return false;
			}
		}

		public boolean shouldContinueExecuting() {
			if (sleepTicks >= 6000 || goblo.getRevengeTarget() != null) {
				return false;
			} else {
				return true;
			}
		}

		public void tick() {
			++sleepTicks;
		}

		public void startExecuting() {
			goblo.setAttackTarget(null);
			goblo.setRevengeTarget(null);
			GobloEntity.this.getNavigator().clearPath();
			GobloEntity.this.setMotion(0.0D, 0.0D, 0.0D);
			GobloEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		}

		public void resetTask() {
			super.resetTask();
			sleepTicks = 0;
			goblo.setSleeping(false);
			goblo.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
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

}
