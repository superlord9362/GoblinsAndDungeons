package superlord.goblinsanddungeons.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class OgreEntity extends GoblinEntity {

	private static final RangedInteger field_234196_bu_ = TickRangeConverter.convertRange(20, 39);
	private int field_234197_bv_;
	private UUID field_234198_bw_;
	private int attackTimer;
	private static final DataParameter<Boolean> ROARING = EntityDataManager.createKey(OgreEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> BUTT_SMASH = EntityDataManager.createKey(OgreEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CAN_BUTT_SMASH = EntityDataManager.createKey(OgreEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CAN_ROAR = EntityDataManager.createKey(OgreEntity.class, DataSerializers.BOOLEAN);
	private int roarTicks = 1800;
	private int buttSmashTicks = 900;

	public OgreEntity(EntityType<? extends OgreEntity> type, World worldIn) {
		super(type, worldIn);
		this.stepHeight = 1.0F;
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new OgreEntity.AttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, new OgreEntity.RoarGoal());
		this.targetSelector.addGoal(1, new OgreEntity.ButtSmashGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.15F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 25.0D);
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.OGRE_IDLE;
	}
	
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.OGRE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.OGRE_DEATH;
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean isRoaring() {
		return this.dataManager.get(ROARING);
	}

	private void setRoaring(boolean isRoaring) {
		this.dataManager.set(ROARING, isRoaring);
	}

	public boolean isFallingOnButt() {
		return this.dataManager.get(BUTT_SMASH);
	}

	private void setFallingOnButt(boolean buttSmash) {
		this.dataManager.set(BUTT_SMASH, buttSmash);
	}
	
	public boolean canRoar() {
		return this.dataManager.get(CAN_ROAR);
	}
	
	private void setCanRoar(boolean canRoar) {
		this.dataManager.set(CAN_ROAR, canRoar);
	}
	
	public boolean canButtSmash() {
		return this.dataManager.get(CAN_BUTT_SMASH);
	}
	
	private void setCanButtSmash(boolean canButtSmash) {
		this.dataManager.set(CAN_BUTT_SMASH, canButtSmash);
	}

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(ROARING, false);
		this.dataManager.register(BUTT_SMASH, false);
		this.dataManager.register(CAN_ROAR, false);
		this.dataManager.register(CAN_BUTT_SMASH, false);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsRoaring", this.isRoaring());
		compound.putBoolean("ButtSmash", this.isFallingOnButt());
		compound.putBoolean("CanRoar", this.canRoar());
		compound.putBoolean("CanButtSmash", this.canButtSmash());
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setRoaring(compound.getBoolean("IsRoaring"));
		this.setFallingOnButt(compound.getBoolean("ButtSmash"));
		this.setCanButtSmash(compound.getBoolean("CanButtSmash"));
		this.setCanRoar(compound.getBoolean("CanRoar"));
	}

	public void livingTick() {
		super.livingTick();
		if (this.getAttackTarget() != null) {
			++this.attackTimer;
		}
		if (this.attackTimer > 200 || this.getAttackTarget() == null) {
			this.attackTimer = 0;
		}
		if (buttSmashTicks > 0) {
			buttSmashTicks--;
		}
		if(roarTicks > 0) {
			roarTicks--;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}


	public void func_230258_H__() {
		this.setAngerTime(field_234196_bu_.getRandomWithinRange(this.rand));
	}

	public void setAngerTime(int time) {
		this.field_234197_bv_ = time;
	}

	public int getAngerTime() {
		return this.field_234197_bv_;
	}

	public void setAngerTarget(@Nullable UUID target) {
		this.field_234198_bw_ = target;
	}

	public UUID getAngerTarget() {
		return this.field_234198_bw_;
	}

	private float func_226511_et_() {
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		this.world.setEntityState(this, (byte)4);
		float f = this.func_226511_et_();
		float f1 = (int)f > 0 ? f / 2.0F + (float)this.rand.nextInt((int)f) : f;
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
		if (flag) {
			entityIn.setMotion(entityIn.getMotion().add(0.0D, (double)0.4F, 0.0D));
			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		boolean flag = super.attackEntityFrom(source, amount);
		return flag;
	}

	public IronGolemEntity.Cracks func_226512_l_() {
		return IronGolemEntity.Cracks.func_226515_a_(this.getHealth() / this.getMaxHealth());
	}

	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}

	@OnlyIn(Dist.CLIENT)
	public Vector3d func_241205_ce_() {
		return new Vector3d(0.0D, (double)(0.875F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
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
	
	public void tick() {
		super.tick();
		if (roarTicks == 0) {
			this.setCanRoar(true);
		}
		if (buttSmashTicks == 0) {
			this.setCanButtSmash(true);
		}
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.OGRE_SPAWN_EGG.get());
	}
	
	class AttackGoal extends MeleeAttackGoal {

		public AttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		@Override
		public boolean shouldExecute() {
			if (OgreEntity.this.getAttackTarget() != null) return true;
			else return false;
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			if (OgreEntity.this.getAttackTarget() == null || OgreEntity.this.canButtSmash() || OgreEntity.this.canRoar()) return false;
			else return true;
		}
		
	}

	class RoarGoal extends Goal {
		int timer = 0;
		
		@Override
		public boolean shouldExecute() {
			if (OgreEntity.this.getAttackTarget() != null && OgreEntity.this.canRoar()) {
				return true;
			} else {
				return false;
			}
		}

		public void tick() {
			timer++;
			if (timer == 50) {
				this.resetTask();
			}
		}

		public void startExecuting() {
			OgreEntity.this.setRoaring(true);
			LivingEntity entity = OgreEntity.this.getAttackTarget();
			world.playSound((PlayerEntity) null, OgreEntity.this.getPosition(), SoundInit.OGRE_ROAR, SoundCategory.HOSTILE, 2, 1);
			OgreEntity.this.playSound(SoundInit.OGRE_ROAR, 1, 1);
			entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 300, 1));
		}

		@Override
		public boolean shouldContinueExecuting() {
			if (OgreEntity.this.getAttackTarget() == null || !OgreEntity.this.canRoar()) return false;
			else return true;
		}

		@Override
		public void resetTask() {
			super.resetTask();
			OgreEntity.this.setRoaring(false);
			OgreEntity.this.roarTicks = 1800;
			timer = 0;
		}

	}

	class ButtSmashGoal extends Goal {
		Boolean hasJumped = false;
		OgreEntity ogre;
		int timer;

		public ButtSmashGoal(OgreEntity ogre) {
			this.ogre = ogre;
		}

		@Override
		public boolean shouldExecute() {
			if (ogre.getAttackTarget() != null && ogre.canButtSmash()) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			if (ogre.getAttackTarget() == null || !ogre.canButtSmash()) return false;
			else return true;
		}

		@Override
		public void tick() {
			timer++;
			if (ogre.isOnGround() && !this.hasJumped) {
				ogre.setMotion(0, 0.5, 0);
				
				this.hasJumped = true;
			}
			double perpFacing = ogre.renderYawOffset * (Math.PI / 180);
			double facingAngle = perpFacing + Math.PI / 2;
			int hitY = MathHelper.floor(ogre.getBoundingBox().minY - 0.5);
			int tick = timer;
			final int maxDistance = 6;
			ServerWorld world = (ServerWorld) ogre.world;
			if (tick > 0) {
				int distance = tick / 2 - 2;
				double spread = Math.PI * 2;
				int arcLen = MathHelper.ceil(distance * spread);
				double minY = ogre.getBoundingBox().minY;
				double maxY = ogre.getBoundingBox().maxY;
				for (int i = 0; i < arcLen; i++) {
					double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
					double vx = Math.cos(theta);
					double vz = Math.sin(theta);
					double px = ogre.getPosX() + vx * distance;
					double pz = ogre.getPosZ() + vz * distance;
					float factor = 1 - distance / (float) maxDistance;
					AxisAlignedBB selection = new AxisAlignedBB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);
					List<Entity> hit = world.getEntitiesWithinAABB(Entity.class, selection);
					for (Entity entity : hit) {
						if (entity == this.ogre || entity instanceof GDFallingBlockEntity) {
							continue;
						}
						float applyKnockbackResistance = 0;
						if (entity instanceof LivingEntity) {
							entity.attackEntityFrom(DamageSource.causeMobDamage(this.ogre),(float) ((factor * 5 + 1) * ogre.getAttributeValue(Attributes.ATTACK_DAMAGE)));
							applyKnockbackResistance = (float) ((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
						}
						double magnitude = world.rand.nextDouble() * 0.15 + 0.1;
						float x = 0, y = 0, z = 0;
						x += vx * factor * magnitude * (1 - applyKnockbackResistance);
						if (entity.isOnGround()) {
							y += 0.1 * (1 - applyKnockbackResistance) + factor * 0.15 * (1 - applyKnockbackResistance);
						}
						z += vz * factor * magnitude * (1 - applyKnockbackResistance);
						entity.setMotion(entity.getMotion().add(x, y, z));
						if (entity instanceof ServerPlayerEntity) {
							((ServerPlayerEntity)entity).connection.sendPacket(new SEntityVelocityPacket(entity));
						}
						if (world.rand.nextBoolean()) {
							int hitX = MathHelper.floor(px);
							int hitZ = MathHelper.floor(pz);
							BlockPos pos = new BlockPos(hitX, hitY, hitZ);
							BlockPos abovePos = new BlockPos(pos).up();
							BlockState block = world.getBlockState(pos);
							BlockState blockAbove = world.getBlockState(abovePos);
							if (block.getMaterial() != Material.AIR && block.isNormalCube(world, pos) && !block.getBlock().hasTileEntity(block) && !blockAbove.getMaterial().blocksMovement()) {
								GDFallingBlockEntity fallingBlock = new GDFallingBlockEntity(EntityInit.FALLING_BLOCK.get(), world, block, (float) (0.4 + factor * 0.2));
								fallingBlock.setPosition(hitX + 0.5, hitY + 1, hitZ + 0.5);
								world.addEntity(fallingBlock);
							}
						}
					}
				}
			}
			if (ogre.isAirBorne) {
				ogre.setFallingOnButt(true);
			} else {
				ogre.setFallingOnButt(false);
				this.resetTask();
			}
		}

		@Override
		public void resetTask() {
			super.resetTask();
			ogre.buttSmashTicks = 900;
			ogre.setCanButtSmash(false);
		}

	}

}
