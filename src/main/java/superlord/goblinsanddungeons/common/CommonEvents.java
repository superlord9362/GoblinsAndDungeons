package superlord.goblinsanddungeons.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GoblinEntity;
import superlord.goblinsanddungeons.entity.ai.FollowOgreGoal;
import superlord.goblinsanddungeons.init.ItemInit;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.FORGE)
public class CommonEvents {

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof AbstractIllager || event.getEntity() instanceof AbstractGolem) {
			((Mob) event.getEntity()).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((Mob) event.getEntity(), GoblinEntity.class, true));
		}
		if (event.getEntity() instanceof AbstractVillager) {
			((Mob) event.getEntity()).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob)event.getEntity(), GoblinEntity.class, 10.0F, 1.0D, 1.0D));
		}
		if (event.getEntity() instanceof Donkey) {
			((Mob) event.getEntity()).goalSelector.addGoal(4, new FollowOgreGoal((Animal)event.getEntity(), 1.0F));
		}
	}
	/*
	@SubscribeEvent
	public static void convertCampfire(BlockEvent.EntityPlaceEvent event) {
		BlockPos pos = event.getPos();
		IWorld world = event.getWorld();
		Block block = event.getPlacedBlock().getBlock();
		if (block == Blocks.SOUL_SAND && world.getBlockState(pos.down()).getBlock() == Blocks.CAMPFIRE) {
			world.setBlockState(pos.down(), BlockInit.SOUL_ASH_CAMPFIRE.get().getDefaultState(), 0);
		}
		if (block == Blocks.SOUL_SAND && world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_CAMPFIRE) {
			world.setBlockState(pos.down(), BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get().getDefaultState(), 0);
		}
		if (block == Blocks.CAMPFIRE && world.getBlockState(pos.up()).getBlock() == Blocks.SOUL_SAND) {
			world.setBlockState(pos, BlockInit.SOUL_ASH_CAMPFIRE.get().getDefaultState(), 0);
		}

		if (block == Blocks.SOUL_CAMPFIRE && world.getBlockState(pos.up()).getBlock() == Blocks.SOUL_SAND) {
			world.setBlockState(pos, BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get().getDefaultState(), 0);
		}
	}
	
	@SubscribeEvent
	public static void convertCampfireBack(BlockEvent.BreakEvent event) {
		BlockPos pos = event.getPos();
		IWorld world = event.getWorld();
		Block block = world.getBlockState(pos).getBlock();
		if ((block == Blocks.SOUL_SAND || block == BlockInit.ASHED_SOUL_SAND.get()) && world.getBlockState(pos.down()).getBlock() == BlockInit.SOUL_ASH_CAMPFIRE.get()) {
			world.setBlockState(pos.down(), Blocks.CAMPFIRE.getDefaultState(), 0);
		}
		if ((block == Blocks.SOUL_SAND || block == BlockInit.ASHED_SOUL_SAND.get()) && world.getBlockState(pos.down()).getBlock() == BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get()) {
			world.setBlockState(pos.down(), Blocks.SOUL_CAMPFIRE.getDefaultState(), 0);
		}
	}
*/
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {		
		DamageSource source = event.getSource();
		if (source.getDirectEntity() instanceof Player) {
			Player playerEntity = (Player) source.getDirectEntity();
			ItemStack stack = playerEntity.getItemBySlot(EquipmentSlot.OFFHAND);
			Item item = stack.getItem();
			if (item == ItemInit.RING_OF_EXPERIENCE.get() && event.getEntity() instanceof LivingEntity) {
				Mob entity = (Mob) event.getEntity();
				int xp = entity.xpReward;
				entity.xpReward = xp + 1;
			}
			if (item == ItemInit.RING_OF_GLORY.get() && !playerEntity.isCreative() && event.getEntity() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) event.getEntity();
				if (entity.getMaxHealth() <= 15.0D) {
					playerEntity.heal(1);
				} else if (entity.getMaxHealth() > 15.0D && entity.getMaxHealth() <= 30.0D) {
					playerEntity.heal(2);
				} else if (entity.getMaxHealth() > 30.0D && entity.getMaxHealth() <= 45.0D) {
					playerEntity.heal(3);
				} else if (entity.getMaxHealth() > 45.0D && entity.getMaxHealth() <= 60.0D) {
					playerEntity.heal(4);
				} else if (entity.getMaxHealth() > 75.0D && entity.getMaxHealth() <= 90.0D) {
					playerEntity.heal(5);
				} else if (entity.getMaxHealth() > 90.0D && entity.getMaxHealth() <= 105.0D) {
					playerEntity.heal(6);
				} else if (entity.getMaxHealth() > 105.0D && entity.getMaxHealth() <= 120.0D) {
					playerEntity.heal(7);
				} else if (entity.getMaxHealth() > 120.0D && entity.getMaxHealth() <= 135.0D) {
					playerEntity.heal(8);
				} else if (entity.getMaxHealth() > 135.0D && entity.getMaxHealth() <= 150.0D) {
					playerEntity.heal(9);
				} else if (entity.getMaxHealth() > 150.0D && entity.getMaxHealth() <= 165.0D) {
					playerEntity.heal(10);
				} else if (entity.getMaxHealth() > 165.0D && entity.getMaxHealth() <= 180.0D) {
					playerEntity.heal(11);
				} else if (entity.getMaxHealth() > 180.0D && entity.getMaxHealth() <= 195.0D) {
					playerEntity.heal(12);
				} else if (entity.getMaxHealth() > 195.0D && entity.getMaxHealth() <= 210.0D) {
					playerEntity.heal(13);
				} else if (entity.getMaxHealth() > 210.0D && entity.getMaxHealth() <= 225.0D) {
					playerEntity.heal(14);
				} else if (entity.getMaxHealth() > 225.0D && entity.getMaxHealth() <= 240.0D) {
					playerEntity.heal(15);
				} else if (entity.getMaxHealth() > 240.0D && entity.getMaxHealth() <= 255.0D) {
					playerEntity.heal(16);
				} else if (entity.getMaxHealth() > 255.0D && entity.getMaxHealth() <= 270.0D) {
					playerEntity.heal(17);
				} else if (entity.getMaxHealth() > 270.0D && entity.getMaxHealth() <= 285.0D) {
					playerEntity.heal(18);
				} else if (entity.getMaxHealth() > 285.0D && entity.getMaxHealth() <= 300.0D) {
					playerEntity.heal(19);
				} else if (entity.getMaxHealth() > 300.0D) {
					playerEntity.heal(20);
				}
				item.damageItem(stack, 1, playerEntity, (player) -> {
					player.broadcastBreakEvent(EquipmentSlot.OFFHAND);
				});
			}
		}
	}
	
}
