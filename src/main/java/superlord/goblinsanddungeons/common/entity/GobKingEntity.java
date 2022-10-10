package superlord.goblinsanddungeons.common.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import superlord.goblinsanddungeons.common.entity.event.GobKingTeleportEvent;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class GobKingEntity extends GoblinEntity implements RangedAttackMob {

	private final ServerBossEvent bossInfo = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);

	public GobKingEntity(EntityType<? extends GobKingEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	protected PathNavigation createNavigation(Level worldIn) {
		return new WallClimberNavigation(this, worldIn);
	}

	protected SoundEvent getAmbientSound() {
		int laugh = random.nextInt(5);
		if (laugh == 0) {
			return SoundInit.GOBLIN_KING_LAUGH;
		} else {
			return SoundInit.GOBLIN_KING_IDLE;
		}
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.GOBLIN_KING_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.GOBLIN_KING_DEATH;
	}
	
	public void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemInit.STAFF_AMETHYST.get()));
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Raider.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractVillager.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 5.0F, 2.2D, 2.2D));
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence();
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}

	}

	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	public void tick() {
		super.tick();
		int i = random.nextInt(999);
		if (this.getTarget() != null && i == 0) {
			int goblin = random.nextInt(5);
			if (goblin == 0) {
				GobEntity gob = new GobEntity(EntityInit.GOB.get(), level);
				gob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				gob.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(gob.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(gob);
			}
			if (goblin == 1) {
				GarchEntity garch = new GarchEntity(EntityInit.GARCH.get(), level);
				garch.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				garch.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(garch.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(garch);
			}
			if (goblin == 2) {
				GobloEntity goblo = new GobloEntity(EntityInit.GOBLO.get(), level);
				goblo.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				goblo.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(goblo.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(goblo);
			}
			if (goblin == 3) {
				GoomEntity goom = new GoomEntity(EntityInit.GOOM.get(), level);
				goom.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				goom.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(goom.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(goom);
			}
			if (goblin == 4) {
				HobGobEntity hobgob = new HobGobEntity(EntityInit.HOBGOB.get(), level);
				hobgob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				hobgob.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(hobgob.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(hobgob);
			}
			if (goblin == 5) {
				GobberEntity gobber = new GobberEntity(EntityInit.GOBBER.get(), level);
				GobberEntity gobber2 = new GobberEntity(EntityInit.GOBBER.get(), level);
				GobberEntity gobber3 = new GobberEntity(EntityInit.GOBBER.get(), level);
				GobberEntity gobber4 = new GobberEntity(EntityInit.GOBBER.get(), level);
				gobber.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				gobber2.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				gobber3.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				gobber4.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
				gobber.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(gobber.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				gobber2.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(gobber2.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				gobber3.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(gobber3.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				gobber4.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(gobber4.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
				level.addFreshEntity(gobber);
				level.addFreshEntity(gobber2);
				level.addFreshEntity(gobber3);
				level.addFreshEntity(gobber4);
			}
		}		
	}

	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	protected void customServerAiStep() {
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 150.0D).add(Attributes.MOVEMENT_SPEED, (double)0.25F).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.FOLLOW_RANGE, 35.0D);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		return spawnDataIn;
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

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(ItemInit.GOB_SPAWN_EGG.get());
	}

	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		GoblinSoulBulletEntity soulBullet = new GoblinSoulBulletEntity(this.level, this);
		double d0 = target.getY() - (double)1.1F;
		double d1 = target.getX() - this.getX();
		double d2 = d0 - soulBullet.getY();
		double d3 = target.getZ() - this.getZ();
		float f = (float) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
		soulBullet.shoot(d1, d2 + (double)f, d3, 1F, 12.0F);
		level.playSound((Player) null, this.blockPosition(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.HOSTILE, 1, 1);
		this.playSound(SoundInit.SOUL_BULLET_LAUNCH, 1, 1);
		this.level.addFreshEntity(soulBullet);
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

	protected boolean teleport() {
		if (!this.level.isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 16.0D;
			double d1 = this.getY() + (double)(this.random.nextInt(16) - 8);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 16.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleport(double x, double y, double z) {
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(x, y, z);

		while(blockpos$mutable.getY() > 0 && !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
			blockpos$mutable.move(Direction.DOWN);
		}

		BlockState blockstate = this.level.getBlockState(blockpos$mutable);
		boolean flag = blockstate.getMaterial().blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			GobKingTeleportEvent event = new GobKingTeleportEvent(this, x, y, z, 0);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
			boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag2 && !this.isSilent()) {
				this.level.playSound((Player)null, this.xo, this.yo, this.zo, SoundInit.SPELL_CASTING, this.getSoundSource(), 1.0F, 1.0F);
				this.playSound(SoundInit.SPELL_CASTING, 1.0F, 1.0F);
			}
			return flag2;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean randomTeleport(double x, double y, double z, boolean p_213373_7_) {
	      double d0 = this.getX();
	      double d1 = this.getY();
	      double d2 = this.getZ();
	      double d3 = y;
	      boolean flag = false;
	      BlockPos blockpos = new BlockPos(x, y, z);
	      Level world = this.level;
	      if (world.hasChunkAt(blockpos)) {
	         boolean flag1 = false;

	         while(!flag1 && blockpos.getY() > 0) {
	            BlockPos blockpos1 = blockpos.below();
	            BlockState blockstate = world.getBlockState(blockpos1);
	            if (blockstate.getMaterial().blocksMotion()) {
	               flag1 = true;
	            } else {
	               --d3;
	               blockpos = blockpos1;
	            }
	         }

	         if (flag1) {
	            this.teleportTo(x, d3, z);
	            if (world.noCollision(this) && !world.containsAnyLiquid(this.getBoundingBox())) {
	               flag = true;
	            }
	         }
	      }

	      if (!flag) {
	         this.teleportTo(d0, d1, d2);
	         return false;
	      } else {
	    	  for(int j = 0; j < 128; ++j) {
					double d4 = (double)j / 127.0D;
					float f = (this.random.nextFloat() - 0.5F) * 0.2F;
					float f1 = (this.random.nextFloat() - 0.5F) * 0.2F;
					float f2 = (this.random.nextFloat() - 0.5F) * 0.2F;
					double d5 = Mth.lerp(d4, this.xo, this.getX()) + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth() * 2.0D;
					double d6 = Mth.lerp(d4, this.yo, this.getY()) + this.random.nextDouble() * (double)this.getBbHeight();
					double d7 = Mth.lerp(d4, this.zo, this.getZ()) + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth() * 2.0D;
		            this.level.addParticle(ParticleInit.GOB_SOUL_BULLET, d5, d6, d7, (double)f, (double)f1, (double)f2);
				}

	         if (this instanceof PathfinderMob) {
	            ((PathfinderMob)this).getNavigation().stop();
	         }

	         return true;
	      }
	   }

	public boolean hurt(DamageSource source, float amount) {
		boolean flag = super.hurt(source, amount);
		if (source.getEntity() instanceof LivingEntity && this.getHealth() <= (this.getMaxHealth() / 2)) {
			this.teleport();
		}

		return flag;
	}
}

