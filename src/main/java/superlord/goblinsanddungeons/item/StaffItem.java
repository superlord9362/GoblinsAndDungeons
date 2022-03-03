package superlord.goblinsanddungeons.item;

import java.util.Random;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.common.util.ManaEntityStats;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;
import superlord.goblinsanddungeons.init.SoundInit;

public class StaffItem extends Item {

	public StaffItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Random random = new Random();
		if (ManaEntityStats.getManaStats(player).getManaLevel() > 0) {
			world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				GoblinSoulBulletEntity soulBullet = new GoblinSoulBulletEntity(world, player);
				soulBullet.getItem();
				soulBullet.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, 1.5F, 1.0F);
				world.addFreshEntity(soulBullet);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.isCreative()) {
				stack.hurtAndBreak(1, player, (p_220009_1_) -> {
					p_220009_1_.broadcastBreakEvent(hand);
				});
				int manaLevel = ManaEntityStats.getManaStats(player).getManaLevel();
				ManaEntityStats.getManaStats(player).setManaLevel(manaLevel - 1);
				System.out.println(ManaEntityStats.getManaStats(player).getManaLevel());
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

}
