package superlord.goblinsanddungeons.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.ManaEntityStats;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.entity.GoblinEntity;
import superlord.goblinsanddungeons.entity.ai.FollowOgreGoal;
import superlord.goblinsanddungeons.init.BlockInit;
import superlord.goblinsanddungeons.init.ItemInit;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.FORGE)
public class CommonEvents {

	public static Map<Block, Block> BLOCK_SHOVEL_MAP = new HashMap<>();

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

	@SubscribeEvent
	public static void registerMana(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player) event.getEntityLiving();
			ManaEntityStats.addStatsOnSpawn(player);
			if (!player.level.isClientSide) {
				ManaEntityStats.getManaStats(player).baseTick(player);
			}
		}
	}

	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event) throws IllegalAccessException {
		if (GoblinsDungeonsConfig.magicalWorld) {
			ResourceLocation name = event.getName();
			if (name.equals(BuiltInLootTables.SIMPLE_DUNGEON)) {
				LootPool pool = event.getTable().getPool("main");
				addEntry(pool, getInjectEntry(new ResourceLocation("goblinsanddungeons:inject/simple_dungeon"), 20, 1));
			}
			if (name.equals(BuiltInLootTables.ABANDONED_MINESHAFT)) {
				LootPool pool = event.getTable().getPool("main");
				addEntry(pool, getInjectEntry(new ResourceLocation("goblinsanddungeons:inject/abandoned_mineshaft"), 20, 1));
			}
		}
	}

	private static LootPoolEntryContainer getInjectEntry(ResourceLocation location, int weight, int quality) {
		return LootTableReference.lootTableReference(location).setWeight(weight).setQuality(quality).build();
	}

	private static void addEntry(LootPool pool, LootPoolEntryContainer entry) throws IllegalAccessException {
		LootPoolEntryContainer[] newEntries = new LootPoolEntryContainer[pool.entries.length + 1];
		System.arraycopy(pool.entries, 0, newEntries, 0, pool.entries.length);
		newEntries[pool.entries.length] = entry;
		pool.entries = newEntries;
	}


	@SubscribeEvent
	public static void convertCampfire(BlockEvent.EntityPlaceEvent event) {
		if (GoblinsDungeonsConfig.magicalWorld) {
			BlockPos pos = event.getPos();
			LevelAccessor world = event.getWorld();
			Block block = event.getPlacedBlock().getBlock();
			if (block == Blocks.SOUL_SAND && world.getBlockState(pos.below()).getBlock() == Blocks.CAMPFIRE) {
				world.setBlock(pos.below(), BlockInit.SOUL_ASH_CAMPFIRE.get().defaultBlockState(), 0);
			}
			if (block == Blocks.SOUL_SAND && world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_CAMPFIRE) {
				world.setBlock(pos.below(), BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get().defaultBlockState(), 0);
			}
			if (block == Blocks.CAMPFIRE && world.getBlockState(pos.above()).getBlock() == Blocks.SOUL_SAND) {
				world.setBlock(pos, BlockInit.SOUL_ASH_CAMPFIRE.get().defaultBlockState(), 0);
			}

			if (block == Blocks.SOUL_CAMPFIRE && world.getBlockState(pos.above()).getBlock() == Blocks.SOUL_SAND) {
				world.setBlock(pos, BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get().defaultBlockState(), 0);
			}
		}
	}

	@SubscribeEvent
	public static void convertCampfireBack(BlockEvent.BreakEvent event) {
		BlockPos pos = event.getPos();
		LevelAccessor world = event.getWorld();
		Block block = world.getBlockState(pos).getBlock();
		if ((block == Blocks.SOUL_SAND || block == BlockInit.ASHED_SOUL_SAND) && world.getBlockState(pos.below()).getBlock() == BlockInit.SOUL_ASH_CAMPFIRE.get()) {
			world.setBlock(pos.below(), Blocks.CAMPFIRE.defaultBlockState(), 0);
		}
		if ((block == Blocks.SOUL_SAND || block == BlockInit.ASHED_SOUL_SAND) && world.getBlockState(pos.below()).getBlock() == BlockInit.SOUL_ASH_SOUL_CAMPFIRE.get()) {
			world.setBlock(pos.below(), Blocks.SOUL_CAMPFIRE.defaultBlockState(), 0);
		}
	}

	static {
		BLOCK_SHOVEL_MAP.put(BlockInit.ASHED_SOUL_SAND, Blocks.SOUL_SAND);
	}

	@SubscribeEvent
	public static void onBlockClicked(PlayerInteractEvent.RightClickBlock event) {
		if(event.getItemStack().getItem() instanceof ShovelItem) {
			Level world = event.getWorld();
			BlockPos pos = event.getPos();
			Player player = event.getPlayer();
			BlockState state = world.getBlockState(pos);
			Block block = BLOCK_SHOVEL_MAP.get(state.getBlock());
			if(block != null) {
				Player entity = event.getPlayer();
				world.playSound(entity, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
				if(!world.isClientSide) {
					world.setBlock(pos, block.defaultBlockState(), 11);
					player.addItem(new ItemStack(ItemInit.SOUL_ASH.get()));
					if(entity != null) {
						event.getItemStack().hurtAndBreak(1, entity, (p_220040_1_) -> {
							p_220040_1_.broadcastBreakEvent(event.getHand());
						});
					}
				}
			}
		}
	}

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
