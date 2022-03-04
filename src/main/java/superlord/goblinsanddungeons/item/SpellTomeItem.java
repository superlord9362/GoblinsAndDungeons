package superlord.goblinsanddungeons.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.common.util.ManaEntityStats;
import superlord.goblinsanddungeons.init.ItemInit;

public class SpellTomeItem extends Item {

	public SpellTomeItem(Properties p_41383_) {
		super(p_41383_);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		if (item == ItemInit.SOUL_BULLET_SPELL_TOME.get() && !(ManaEntityStats.getKnownSpells(player).knowsSoulBullet())) {
			ManaEntityStats.getKnownSpells(player).setKnowsSoulBullet(true, player);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.isCreative()) {
				stack.setCount(0);;
			}
		}
		if (item == ItemInit.SOUL_JUMP_SPELL_TOME.get() && !(ManaEntityStats.getKnownSpells(player).knowsSoulJump())) {
			ManaEntityStats.getKnownSpells(player).setKnowsSoulJump(true, player);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.isCreative()) {
				stack.setCount(0);;
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

}
