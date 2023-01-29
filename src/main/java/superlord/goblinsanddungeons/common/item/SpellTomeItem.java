package superlord.goblinsanddungeons.common.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.magic.PlayerSpellsProvider;
import superlord.goblinsanddungeons.networking.ModMessages;
import superlord.goblinsanddungeons.networking.packet.LearnSoulBulletC2SPacket;
import superlord.goblinsanddungeons.networking.packet.LearnSoulJumpC2SPacket;

public class SpellTomeItem extends Item {

	public SpellTomeItem(Properties p_41383_) {
		super(p_41383_);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		player.getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(spells -> {
			if (item == ItemInit.SOUL_BULLET_SPELL_TOME.get() && !(spells.doesKnowSoulBullet())) {
				ModMessages.sendToServer(new LearnSoulBulletC2SPacket());
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.isCreative()) {
					stack.setCount(0);;
				}
			}
			if (item == ItemInit.SOUL_JUMP_SPELL_TOME.get() && !(spells.doesKnowSoulJump())) {
				ModMessages.sendToServer(new LearnSoulJumpC2SPacket());
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.isCreative()) {
					stack.setCount(0);;
				}
			}
		});
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

}
