package superlord.goblinsanddungeons.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.common.util.KnownSpellsData;
import superlord.goblinsanddungeons.common.util.ManaEntityStats;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.entity.SoulBulletEntity;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;

public class StaffItem extends Item {

	int activeSpell;
	CompoundTag currentSpell;

	public StaffItem(Properties properties) {
		super(properties);
	}
	
	public SpellType getActiveSpell() {
		return SpellType.byId(activeSpell);
	}

	public SpellType setActiveSpell(int newSpell) {
		activeSpell = newSpell;
		return SpellType.byId(newSpell);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
		if (GoblinsDungeonsConfig.magicalWorld) {
			Random random = new Random();
			if (player.isShiftKeyDown()) {
				KnownSpellsData knownSpells = ManaEntityStats.getKnownSpells(player);
				if (player.isCreative()) {
					if (getActiveSpell() == SpellType.NO_SPELL) {
						setActiveSpell(SpellType.SOUL_BULLET.getId());
						player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_bullet"), true);
                        tag.putInt("ActiveSpell", 1);
					} else if (getActiveSpell() == SpellType.SOUL_BULLET) {
						setActiveSpell(SpellType.SOUL_JUMP.getId());
                        tag.putInt("ActiveSpell", 2);
						player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_jump"), true);
					} else if (getActiveSpell() == SpellType.SOUL_JUMP) {
						setActiveSpell(SpellType.NO_SPELL.getId());
						player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_no_spell"), true);
                        tag.putInt("ActiveSpell", 0);
					}
				} else {
					if (!(knownSpells.knowsSoulBullet()) && knownSpells.knowsSoulJump() && getActiveSpell() == SpellType.NO_SPELL) {
						setActiveSpell(SpellType.SOUL_JUMP.getId());
						player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_jump"), true);
                        tag.putInt("ActiveSpell", 2);
					} else {
						if (knownSpells.knowsSoulBullet() && getActiveSpell() == SpellType.NO_SPELL) {
							setActiveSpell(SpellType.SOUL_BULLET.getId());
							player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_bullet"), true);
	                        tag.putInt("ActiveSpell", 1);
						} else if (knownSpells.knowsSoulJump() && getActiveSpell() == SpellType.SOUL_BULLET) {
							setActiveSpell(SpellType.SOUL_JUMP.getId());
							player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_jump"), true);
	                        tag.putInt("ActiveSpell", 2);
						}  else if (getActiveSpell() == SpellType.SOUL_JUMP && knownSpells.knowsSoulBullet()) {
							setActiveSpell(SpellType.NO_SPELL.getId());
							player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_no_spell"), true);
	                        tag.putInt("ActiveSpell", 0);
						}
					}
				}

			} else {
				if (tag.getInt("ActiveSpell") == 1) {
					if (ManaEntityStats.getManaStats(player).getManaLevel() > 0 || player.isCreative()) {
						world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
						if (!world.isClientSide) {
							SoulBulletEntity soulBullet = new SoulBulletEntity(world, player);
							soulBullet.getItem();
							soulBullet.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, 1.5F, 1.0F);
							world.addFreshEntity(soulBullet);
						}
						player.awardStat(Stats.ITEM_USED.get(this));
						if (!player.isCreative()) {
							stack.hurtAndBreak(1, player, (p_220009_1_) -> {
								p_220009_1_.broadcastBreakEvent(hand);
							});
							ManaEntityStats.getManaStats(player).shrink(1, player);
						}
					}
				}
				if (tag.getInt("ActiveSpell") == 2) {
					if (ManaEntityStats.getManaStats(player).getManaLevel() > 1 || player.isCreative()) {
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
						if (!player.isCreative()) {
							stack.hurtAndBreak(1, player, (p_220009_1_) -> {
								p_220009_1_.broadcastBreakEvent(hand);
							});
							ManaEntityStats.getManaStats(player).shrink(2, player);
						}
					}
				}
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (GoblinsDungeonsConfig.magicalWorld) {
			if (activeSpell == 0) {
				p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
				p_77624_3_.add(new TranslatableComponent("no_spell").withStyle(ChatFormatting.GRAY));
			} else if (activeSpell == 1) {
				p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
				p_77624_3_.add(new TranslatableComponent("soul_bullet").withStyle(ChatFormatting.BLUE));
			} else if (activeSpell == 2) {
				p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
				p_77624_3_.add(new TranslatableComponent("soul_jump").withStyle(ChatFormatting.BLUE));
			}
		}
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
