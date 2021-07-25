package superlord.goblinsanddungeons.common;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.entity.GoblinEntity;
import superlord.goblinsanddungeons.entity.ai.FollowOgreGoal;
import superlord.goblinsanddungeons.init.GDMapInit;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.StructureInit;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Bus.FORGE)
public class CommonEvents {

	@SubscribeEvent
	public void onVillagerTrades(WandererTradesEvent  event) {
		List<VillagerTrades.ITrade> list = event.getGenericTrades();
		list.add(new EmeraldForMapTrade(new ItemStack(Items.EMERALD, 12), new ItemStack(Items.COMPASS, 1), 14, 10, 0.2F, StructureInit.RUINED_KEEP.get(), GDMapInit.Type.RUINED_KEEP));
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof AbstractIllagerEntity || event.getEntity() instanceof GolemEntity) {
			((MobEntity) event.getEntity()).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((MobEntity) event.getEntity(), GoblinEntity.class, true));
		}
		if (event.getEntity() instanceof AbstractVillagerEntity) {
			((MobEntity) event.getEntity()).goalSelector.addGoal(0, new AvoidEntityGoal<>((CreatureEntity)event.getEntity(), GoblinEntity.class, 10.0F, 1.0D, 1.0D));
		}
		if (event.getEntity() instanceof DonkeyEntity) {
			((MobEntity) event.getEntity()).goalSelector.addGoal(4, new FollowOgreGoal((AnimalEntity)event.getEntity(), 1.0F));
		}
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {		
		DamageSource source = event.getSource();
		if (source.getTrueSource() instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) source.getTrueSource();
			ItemStack stack = playerEntity.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
			Item item = stack.getItem();
			if (item == ItemInit.RING_OF_EXPERIENCE.get() && event.getEntity() instanceof LivingEntity) {
				MobEntity entity = (MobEntity) event.getEntity();
				int xp = entity.experienceValue;
				entity.experienceValue = xp + 1;
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
					player.sendBreakAnimation(EquipmentSlotType.OFFHAND);
				});
			}
		}
	}

	static class EmeraldForMapTrade implements VillagerTrades.ITrade {
		private final ItemStack buying1, buying2;
		private final int maxUses, xp;
		private final float priceMultiplier;
		private final Structure<?> structureName;
		private final GDMapInit.Type type;

		public EmeraldForMapTrade(ItemStack buying1, ItemStack buying2, int maxUses, int xp, float priceMultiplier, Structure<?> structureName, GDMapInit.Type type) {
			this.buying1 = buying1;
			this.buying2 = buying2;
			this.maxUses = maxUses;
			this.xp = xp;
			this.priceMultiplier = priceMultiplier;
			this.structureName = structureName;
			this.type = type;
		}

		@Nullable
		@Override
		public MerchantOffer getOffer(Entity trader, Random rand) {
			if (!(trader.world instanceof ServerWorld)) {
				return null;
			} else {
				ServerWorld serverworld = (ServerWorld)trader.world;
				BlockPos blockpos = serverworld.func_241117_a_(this.structureName, trader.getPosition(), 100, true);
				if (blockpos != null) {
					ItemStack itemstack = FilledMapItem.setupNewMap(serverworld, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
					FilledMapItem.func_226642_a_(serverworld, itemstack);
					GDMapInit.addTargetDecoration(itemstack, blockpos, "+", this.type);
					itemstack.setDisplayName(new TranslationTextComponent("filled_map." + this.structureName.getStructureName().toLowerCase(Locale.ROOT)));
					return new MerchantOffer(buying1, buying2, itemstack, this.maxUses, this.xp, this.priceMultiplier);
				} else {
					return null;
				}
			}
		}

	}

}
