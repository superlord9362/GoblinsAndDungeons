package superlord.goblinsanddungeons.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.entity.SoulBulletEntity;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class ScrollItem extends Item {
	
	SpellType type;
	
	public ScrollItem(SpellType type, Properties p_41383_) {
		super(p_41383_);
		this.type = type;
	}
	
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Random random = new Random();
		if (GoblinsDungeonsConfig.magicalWorld) {
			if (this.type == SpellType.SOUL_BULLET) {
				world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				if (!world.isClientSide) {
					SoulBulletEntity soulBullet = new SoulBulletEntity(world, player);
					soulBullet.getItem();
					soulBullet.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, 1.5F, 1.0F);
					world.addFreshEntity(soulBullet);
				}
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.isCreative()) {
					stack.setCount(0);;
				}
			}
			if (this.type == SpellType.SOUL_JUMP) {
				world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				player.setDeltaMovement(player.getDeltaMovement().x, 0.7, player.getDeltaMovement().z);
				AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(world, player.getX(), player.getY(), player.getZ());
				areaeffectcloudentity.setRadius(1F);
				areaeffectcloudentity.setRadiusOnUse(-0.5F);
				areaeffectcloudentity.setWaitTime(1);
				areaeffectcloudentity.setDuration(4);
				areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
				areaeffectcloudentity.setParticle(ParticleInit.SOUL_BULLET);
				world.addFreshEntity(areaeffectcloudentity);
				player.awardStat(Stats.ITEM_USED.get(this));
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.isCreative()) {
					stack.setCount(0);;
				}
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
	
	public enum SpellType {
		NO_SPELL(0, "no_spell"),
		SOUL_BULLET(1, "soul_bullet"),
		SOUL_JUMP(2, "soul_jump");

		private static final SpellType[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SpellType::getId)).toArray((id) -> {
			return new SpellType[id];
		});
		private final int id;
		private final String name;

		private SpellType(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return this.name;
		}

		public static SpellType byId(int id) {
			if (id < 0 || id >= BY_ID.length) {
				id = 0;
			}
			return BY_ID[id];
		}

		public static SpellType byName(String name, SpellType type) {
			for (SpellType spellType : values()) {
				if (spellType.name.equals(name)) {
					return spellType;
				}
			}
			return type;
		}

		public String toString() {
			return this.name;
		}

		public String getSerializedName() {
			return this.name;
		}

	}

}
