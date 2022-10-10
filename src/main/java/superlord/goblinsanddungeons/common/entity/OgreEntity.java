package superlord.goblinsanddungeons.common.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class OgreEntity extends GoblinEntity {

	private static final UniformInt field_234196_bu_ = TimeUtil.rangeOfSeconds(20, 39);
	private int field_234197_bv_;
	private UUID field_234198_bw_;
	private int attackTimer;
	private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BUTT_SMASH = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_BUTT_SMASH = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_ROAR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
	private int roarTicks = 1800;
	private int buttSmashTicks = 900;

	public OgreEntity(EntityType<? extends OgreEntity> type, Level worldIn) {
		super(type, worldIn);
		this.maxUpStep = 1.0F;
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new OgreEntity.AttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(1, new OgreEntity.RoarGoal());
		this.targetSelector.addGoal(1, new OgreEntity.ButtSmashGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, (double)0.15F).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.KNOCKBACK_RESISTANCE, 25.0D);
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
		return this.entityData.get(ROARING);
	}

	private void setRoaring(boolean isRoaring) {
		this.entityData.set(ROARING, isRoaring);
	}

	public boolean isFallingOnButt() {
		return this.entityData.get(BUTT_SMASH);
	}

	private void setFallingOnButt(boolean buttSmash) {
		this.entityData.set(BUTT_SMASH, buttSmash);
	}
	
	public boolean canRoar() {
		return this.entityData.get(CAN_ROAR);
	}
	
	private void setCanRoar(boolean canRoar) {
		this.entityData.set(CAN_ROAR, canRoar);
	}
	
	public boolean canButtSmash() {
		return this.entityData.get(CAN_BUTT_SMASH);
	}
	
	private void setCanButtSmash(boolean canButtSmash) {
		this.entityData.set(CAN_BUTT_SMASH, canButtSmash);
	}

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ROARING, false);
		this.entityData.define(BUTT_SMASH, false);
		this.entityData.define(CAN_ROAR, false);
		this.entityData.define(CAN_BUTT_SMASH, false);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsRoaring", this.isRoaring());
		compound.putBoolean("ButtSmash", this.isFallingOnButt());
		compound.putBoolean("CanRoar", this.canRoar());
		compound.putBoolean("CanButtSmash", this.canButtSmash());
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setRoaring(compound.getBoolean("IsRoaring"));
		this.setFallingOnButt(compound.getBoolean("ButtSmash"));
		this.setCanButtSmash(compound.getBoolean("CanButtSmash"));
		this.setCanRoar(compound.getBoolean("CanRoar"));
	}

	public void aiStep() {
		super.aiStep();
		if (this.getTarget() != null) {
			++this.attackTimer;
		}
		if (this.attackTimer > 200 || this.getTarget() == null) {
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
		this.setAngerTime(field_234196_bu_.sample(this.random));
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

	public boolean doHurtTarget(Entity entityIn) {
		this.level.broadcastEntityEvent(this, (byte)4);
		float f = this.func_226511_et_();
		float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
		boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);
		if (flag) {
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, (double)0.4F, 0.0D));
			this.doEnchantDamageEffects(this, entityIn);
		}

		return flag;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean hurt(DamageSource source, float amount) {
		boolean flag = super.hurt(source, amount);
		return flag;
	}
	public void die(DamageSource cause) {
		super.die(cause);
	}

	@OnlyIn(Dist.CLIENT)
	public Vec3 func_241205_ce_() {
		return new Vec3(0.0D, (double)(0.875F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
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
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.OGRE_SPAWN_EGG.get());
	}
	
	class AttackGoal extends MeleeAttackGoal {

		public AttackGoal(PathfinderMob creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		@Override
		public boolean canUse() {
			if (OgreEntity.this.getTarget() != null) return true;
			else return false;
		}
		
		@Override
		public boolean canContinueToUse() {
			if (OgreEntity.this.getTarget() == null || OgreEntity.this.canButtSmash() || OgreEntity.this.canRoar()) return false;
			else return true;
		}
		
	}

	class RoarGoal extends Goal {
		int timer = 0;
		
		@Override
		public boolean canUse() {
			if (OgreEntity.this.getTarget() != null && OgreEntity.this.canRoar()) {
				return true;
			} else {
				return false;
			}
		}

		public void tick() {
			timer++;
			if (timer == 50) {
				this.stop();
			}
		}

		public void start() {
			OgreEntity.this.setRoaring(true);
			LivingEntity entity = OgreEntity.this.getTarget();
			level.playSound((Player) null, OgreEntity.this.blockPosition(), SoundInit.OGRE_ROAR, SoundSource.HOSTILE, 2, 1);
			OgreEntity.this.playSound(SoundInit.OGRE_ROAR, 1, 1);
			entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 1));
		}

		@Override
		public boolean canContinueToUse() {
			if (OgreEntity.this.getTarget() == null || !OgreEntity.this.canRoar()) return false;
			else return true;
		}

		@Override
		public void stop() {
			super.stop();
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
		public boolean canUse() {
			if (ogre.getTarget() != null && ogre.canButtSmash()) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public boolean canContinueToUse() {
			if (ogre.getTarget() == null || !ogre.canButtSmash()) return false;
			else return true;
		}

		@Override
		public void tick() {
			timer++;
			if (ogre.isOnGround() && !this.hasJumped) {
				ogre.setDeltaMovement(0, 0.5, 0);
				
				this.hasJumped = true;
			}
			double perpFacing = ogre.yo * (Math.PI / 180);
			double facingAngle = perpFacing + Math.PI / 2;
			int hitY = Mth.floor(ogre.getBoundingBox().minY - 0.5);
			int tick = timer;
			final int maxDistance = 6;
			ServerLevel world = (ServerLevel) ogre.level;
			if (tick > 0) {
				int distance = tick / 2 - 2;
				double spread = Math.PI * 2;
				int arcLen = Mth.ceil(distance * spread);
				double minY = ogre.getBoundingBox().minY;
				double maxY = ogre.getBoundingBox().maxY;
				for (int i = 0; i < arcLen; i++) {
					double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
					double vx = Math.cos(theta);
					double vz = Math.sin(theta);
					double px = ogre.getX() + vx * distance;
					double pz = ogre.getZ() + vz * distance;
					float factor = 1 - distance / (float) maxDistance;
					AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);
					List<Entity> hit = world.getEntitiesOfClass(Entity.class, selection);
					for (Entity entity : hit) {
						if (entity == this.ogre || entity instanceof GDFallingBlockEntity) {
							continue;
						}
						float applyKnockbackResistance = 0;
						if (entity instanceof LivingEntity) {
							entity.hurt(DamageSource.mobAttack(this.ogre),(float) ((factor * 5 + 1) * ogre.getAttributeValue(Attributes.ATTACK_DAMAGE)));
							applyKnockbackResistance = (float) ((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
						}
						double magnitude = world.random.nextDouble() * 0.15 + 0.1;
						float x = 0, y = 0, z = 0;
						x += vx * factor * magnitude * (1 - applyKnockbackResistance);
						if (entity.isOnGround()) {
							y += 0.1 * (1 - applyKnockbackResistance) + factor * 0.15 * (1 - applyKnockbackResistance);
						}
						z += vz * factor * magnitude * (1 - applyKnockbackResistance);
						entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
						if (entity instanceof ServerPlayer) {
							((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
						}
						if (world.random.nextBoolean()) {
							int hitX = Mth.floor(px);
							int hitZ = Mth.floor(pz);
							BlockPos pos = new BlockPos(hitX, hitY, hitZ);
							BlockPos abovePos = new BlockPos(pos).above();
							BlockState block = world.getBlockState(pos);
							BlockState blockAbove = world.getBlockState(abovePos);
							if (block.getMaterial() != Material.AIR && block.isRedstoneConductor(world, pos) && !block.hasBlockEntity() && !blockAbove.getMaterial().blocksMotion()) {
								GDFallingBlockEntity fallingBlock = new GDFallingBlockEntity(EntityInit.FALLING_BLOCK.get(), world, block, (float) (0.4 + factor * 0.2));
								fallingBlock.setPos(hitX + 0.5, hitY + 1, hitZ + 0.5);
								world.addFreshEntity(fallingBlock);
							}
						}
					}
				}
			}
			if (!ogre.isOnGround()) {
				ogre.setFallingOnButt(true);
			} else {
				ogre.setFallingOnButt(false);
				this.stop();
			}
		}

		@Override
		public void stop() {
			super.stop();
			ogre.buttSmashTicks = 900;
			ogre.setCanButtSmash(false);
		}

	}

}
