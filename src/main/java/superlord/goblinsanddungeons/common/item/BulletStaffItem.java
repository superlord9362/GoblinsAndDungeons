package superlord.goblinsanddungeons.common.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.common.entity.SoulBulletEntity;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.SoundInit;
import superlord.goblinsanddungeons.magic.PlayerMana;
import superlord.goblinsanddungeons.magic.PlayerSpells;
import superlord.goblinsanddungeons.networking.ModMessages;
import superlord.goblinsanddungeons.networking.packet.CastSoulBulletC2SPacket;

public class BulletStaffItem extends Item {

	public BulletStaffItem(Properties p_41383_) {
		super(p_41383_);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Random random = new Random();
		PlayerSpells knownSpells = new PlayerSpells();
		PlayerMana mana = new PlayerMana();
		if ((mana.getMana() > 0 || player.isCreative()) && !player.isShiftKeyDown()) {
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
				ModMessages.sendToServer(new CastSoulBulletC2SPacket());
			}
		} else if (player.isShiftKeyDown()) {
			if (player.isCreative() || knownSpells.doesKnowSoulJump()) {
				ItemStack newStack = new ItemStack(ItemInit.STAFF_JUMP.get());
				int damage = this.getDamage(player.getItemInHand(hand));
				player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_jump"), true);
				player.setItemInHand(hand, newStack);
				newStack.setDamageValue(damage);
			} else {
				ItemStack newStack = new ItemStack(ItemInit.STAFF_AMETHYST.get());
				int damage = this.getDamage(player.getItemInHand(hand));
				player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_no_spell"), true);
				player.setItemInHand(hand, newStack);
				newStack.setDamageValue(damage);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (GoblinsDungeonsConfig.magicalWorld) {
			p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
			p_77624_3_.add(new TranslatableComponent("soul_bullet").withStyle(ChatFormatting.BLUE));
		}
	}

}
