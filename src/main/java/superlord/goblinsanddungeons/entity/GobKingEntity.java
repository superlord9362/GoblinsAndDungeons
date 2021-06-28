package superlord.goblinsanddungeons.entity;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.EntityInit;
import superlord.goblinsanddungeons.init.ItemInit;

public class GobKingEntity extends GoblinEntity implements IRangedAttackMob {

	private final ServerBossInfo bossInfo = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);

	public GobKingEntity(EntityType<? extends GobKingEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected PathNavigator createNavigator(World worldIn) {
		return new ClimberPathNavigator(this, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractRaiderEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, AbstractVillagerEntity.class, 5.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolemEntity.class, 5.0F, 2.2D, 2.2D));
	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	public boolean preventDespawn() {
		return super.preventDespawn();
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}

	}

	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	public void tick() {
		super.tick();
		int i = rand.nextInt(999);
		if (this.getAttackTarget() != null && i == 0) {
			System.out.println("Hoo hoo");
			int goblin = rand.nextInt(5);
			if (goblin == 0) {
				GobEntity gob = new GobEntity(EntityInit.GOB.get(), world);
				gob.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				gob.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(gob.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				world.addEntity(gob);
			}
			if (goblin == 1) {
				GarchEntity garch = new GarchEntity(EntityInit.GARCH.get(), world);
				garch.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				garch.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(garch.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				world.addEntity(garch);
			}
			if (goblin == 2) {
				GobloEntity goblo = new GobloEntity(EntityInit.GOBLO.get(), world);
				goblo.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				goblo.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(goblo.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				world.addEntity(goblo);
			}
			if (goblin == 3) {
				GoomEntity goom = new GoomEntity(EntityInit.GOOM.get(), world);
				goom.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				goom.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(goom.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				world.addEntity(goom);
			}
			if (goblin == 4) {
				HobGobEntity hobgob = new HobGobEntity(EntityInit.HOBGOB.get(), world);
				hobgob.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				world.addEntity(hobgob);
			}
			if (goblin == 5) {
				GobberEntity gobber = new GobberEntity(EntityInit.GOBBER.get(), world);
				GobberEntity gobber2 = new GobberEntity(EntityInit.GOBBER.get(), world);
				GobberEntity gobber3 = new GobberEntity(EntityInit.GOBBER.get(), world);
				GobberEntity gobber4 = new GobberEntity(EntityInit.GOBBER.get(), world);
				gobber.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				gobber2.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				gobber3.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				gobber4.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
				gobber.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(gobber.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				gobber2.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(gobber2.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				gobber3.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(gobber3.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				gobber4.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(gobber4.getPosition()), SpawnReason.REINFORCEMENT, (ILivingEntityData)null, (CompoundNBT)null);
				world.addEntity(gobber);
				world.addEntity(gobber2);
				world.addEntity(gobber3);
				world.addEntity(gobber4);
			}
		}		
	}

	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	protected void updateAITasks() {
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		Effect effect = Effects.WEAKNESS;
		List<ServerPlayerEntity> list = ((ServerWorld)this.world).getPlayers((p_210138_1_) -> {
			return this.getDistanceSq(p_210138_1_) < 2500.0D && p_210138_1_.interactionManager.survivalOrAdventure();
		});

		for(ServerPlayerEntity serverplayerentity : list) {
			if (!serverplayerentity.isPotionActive(effect) || serverplayerentity.getActivePotionEffect(effect).getAmplifier() < 2 || serverplayerentity.getActivePotionEffect(effect).getDuration() < 1200) {
				serverplayerentity.addPotionEffect(new EffectInstance(effect, 6000, 2));
			}
		}
	}

	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 150.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.25F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		return spawnDataIn;
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

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemInit.GOB_SPAWN_EGG.get());
	}

	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		GoblinSoulBulletEntity soulBullet = new GoblinSoulBulletEntity(this.world, this);
		double d0 = target.getPosYEye() - (double)1.1F;
		double d1 = target.getPosX() - this.getPosX();
		double d2 = d0 - soulBullet.getPosY();
		double d3 = target.getPosZ() - this.getPosZ();
		float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
		soulBullet.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
		this.world.addEntity(soulBullet);
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

	protected boolean teleportRandomly() {
		if (!this.world.isRemote() && this.isAlive()) {
			double d0 = this.getPosX() + (this.rand.nextDouble() - 0.5D) * 16.0D;
			double d1 = this.getPosY() + (double)(this.rand.nextInt(16) - 8);
			double d2 = this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 16.0D;
			return this.teleportTo(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleportTo(double x, double y, double z) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z);

		while(blockpos$mutable.getY() > 0 && !this.world.getBlockState(blockpos$mutable).getMaterial().blocksMovement()) {
			blockpos$mutable.move(Direction.DOWN);
		}

		BlockState blockstate = this.world.getBlockState(blockpos$mutable);
		boolean flag = blockstate.getMaterial().blocksMovement();
		boolean flag1 = blockstate.getFluidState().isTagged(FluidTags.WATER);
		if (flag && !flag1) {
			net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
			boolean flag2 = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag2 && !this.isSilent()) {
				this.world.playSound((PlayerEntity)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
				this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}

			return flag2;
		} else {
			return false;
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		boolean flag = super.attackEntityFrom(source, amount);
		if (source.getTrueSource() instanceof LivingEntity && this.getHealth() <= (this.getMaxHealth() / 2)) {
			this.teleportRandomly();
		}

		return flag;
	}

}

