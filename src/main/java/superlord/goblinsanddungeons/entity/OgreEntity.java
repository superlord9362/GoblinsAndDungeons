package superlord.goblinsanddungeons.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
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
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.init.ItemInit;

public class OgreEntity extends MonsterEntity {

	private int sheepTimer;
	private OgreEntity.SlamAttack slamAttackGoal;


	public OgreEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(0, new OgreEntity.SlamAttack(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	protected void updateAITasks() {
		this.sheepTimer = this.slamAttackGoal.getSlammingTimer();
		super.updateAITasks();
	}

	public void livingTick() {
		if (this.world.isRemote) {
			this.sheepTimer = Math.max(0, this.sheepTimer - 1);
		}

		super.livingTick();
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.15F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			this.sheepTimer = 0;
		} else {
			super.handleStatusUpdate(id);
		}

	}


	public class SlamAttack extends Goal {
		OgreEntity entity;

		private int tickCounter;
		public SlamAttack(OgreEntity entity) {
			super();
			this.entity = entity;
		}


		public int getSlammingTimer() {
			return this.tickCounter;
		}

		@Override
		public void	startExecuting() {
			this.tickCounter = 40;
		}

		@Override
		public void tick() {
			if(this.tickCounter >= 40) {
				slam();
			}
			++this.tickCounter;
			super.tick();
		}

		@Override
		public void resetTask() {
			this.tickCounter = 0;
		}

		public boolean shouldContinueExecuting() {
			return this.tickCounter > 0;
		}

		@SuppressWarnings("unused")
		public void slam() {
			entity.setMotion(0, entity.getMotion().y, 0);
			double perpFacing = entity.renderYawOffset * (Math.PI / 180);
			double facingAngle = perpFacing + Math.PI / 2;
			int hitY = MathHelper.floor(entity.getBoundingBox().minY - 0.5);
			int tick = tickCounter;
			final int maxDistance = 6;
			ServerWorld world = (ServerWorld) entity.world;
			if (tick > 9 && tick < 17) {
				if (tick == 10) {
					final double infront = 1.47, side = -0.21;
					double vx = Math.cos(facingAngle) * infront;
					double vz = Math.sin(facingAngle) * infront;
					double perpX = Math.cos(perpFacing);
					double perpZ = Math.sin(perpFacing);
					double fx = entity.getPosX() + vx + perpX * side;
					double fy = entity.getBoundingBox().minY + 0.1;
					double fz = entity.getPosZ() + vz + perpZ * side;
					int bx = MathHelper.floor(fx);
					int bz = MathHelper.floor(fz);
					int amount = 16 + world.rand.nextInt(8);
					while (amount-- > 0) {
						double theta = world.rand.nextDouble() * Math.PI * 2;
						double dist = world.rand.nextDouble() * 0.1 + 0.25;
						double sx = Math.cos(theta);
						double sz = Math.sin(theta);
						double px = fx + sx * dist;
						double py = fy + world.rand.nextDouble() * 0.1;
						double pz = fz + sz * dist;
						world.addParticle(ParticleTypes.SMOKE, px, py, pz, sx * 0.065, 0, sz * 0.065);
					}
				} else if (tick == 12) {
					entity.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2, 1F + entity.getRNG().nextFloat() * 0.1F);
				}
				if (tick % 2 == 0) {
					int distance = tick / 2 - 2;
					double spread = Math.PI * 2;
					int arcLen = MathHelper.ceil(distance * spread);
					double minY = entity.getBoundingBox().minY;
					double maxY = entity.getBoundingBox().maxY;
					for (int i = 0; i < arcLen; i++) {
						double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
						double vx = Math.cos(theta);
						double vz = Math.sin(theta);
						double px = entity.getPosX() + vx * distance;
						double pz = entity.getPosZ() + vz * distance;
						float factor = 1 - distance / (float) maxDistance;
						AxisAlignedBB selection = new AxisAlignedBB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);
						List<Entity> hit = world.getEntitiesWithinAABB(Entity.class, selection);
						for (Entity entity : hit) {
							if (entity == this.entity || entity instanceof FallingBlockEntity) {
								continue;
							}
							float knockbackResistance = 0;
							if (entity instanceof LivingEntity) {
								entity.attackEntityFrom(DamageSource.causeMobDamage(this.entity), (factor * 5 + 1) * (float) ((LivingEntity)entity).getAttribute(Attributes.ATTACK_DAMAGE).getValue());
								knockbackResistance = (float) ((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
							}
							double magnitude = world.rand.nextDouble() * 0.15 + 0.1;
							float x = 0, y = 0, z = 0;
							x += vx * factor * magnitude * (1 - knockbackResistance);
							if (entity.isOnGround()) {
								y += 0.1 * (1 - knockbackResistance) + factor * 0.15 * (1 - knockbackResistance);
							}
							z += vz * factor * magnitude * (1 - knockbackResistance);
							entity.setMotion(entity.getMotion().add(x, y, z));
							if (entity instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) entity).connection.sendPacket(new SEntityVelocityPacket(entity));
							}
						}
						if (world.rand.nextBoolean()) {
							int hitX = MathHelper.floor(px);
							int hitZ = MathHelper.floor(pz);
							BlockPos pos = new BlockPos(hitX, hitY, hitZ);
							BlockPos abovePos = new BlockPos(pos).up();
							BlockPos belowPos = new BlockPos(pos).down();
							if (world.isAirBlock(abovePos) && !world.isAirBlock(belowPos)) {
								BlockState block = world.getBlockState(pos);
								if (block.getMaterial() != Material.AIR && block.isNormalCube(world, pos) && block.getBlock() != Blocks.BEDROCK && !block.getBlock().hasTileEntity(block)) {
									FallingBlockEntity fallingBlock = new FallingBlockEntity(world, hitX + 0.5, hitY + 0.5, hitZ + 0.5, block);
									fallingBlock.setMotion(0, 0.4 + factor * 0.2, 0);
									fallingBlock.fallTime = 2;
									world.addEntity(fallingBlock);
									world.removeBlock(pos, false);
									int amount = 6 + world.rand.nextInt(10);
									int stateId = Block.getStateId(block);
									while (amount --> 0) {
										double cx = px + world.rand.nextFloat() * 2 - 1;
										double cy = entity.getBoundingBox().minY + 0.1 + world.rand.nextFloat() * 0.3;
										double cz = pz + world.rand.nextFloat() * 2 - 1;
										world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, block), cx, cy, cz, vx, 0.4 + world.rand.nextFloat() * 0.2F, vz);
									}
								}
							}
						}
						if (world.rand.nextBoolean()) {
							int amount = world.rand.nextInt(5);
							while (amount-- > 0) {
								double velX = vx * 0.075;
								double velY = factor * 0.3 + 0.025;
								double velZ = vz * 0.075;
								world.addParticle(ParticleTypes.CLOUD, px + world.rand.nextFloat() * 2 - 1, entity.getBoundingBox().minY + 0.1 + world.rand.nextFloat() * 1.5, pz + world.rand.nextFloat() * 2 - 1, velX, velY, velZ);
							}
						}
					}
				}
			}
		}

		@Override
		public boolean shouldExecute() {
			return entity.getAttackTarget() != null && this.tickCounter >= 40;
		}
	}
	
	@Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ItemInit.OGRE_SPAWN_EGG.get());
    }

}
