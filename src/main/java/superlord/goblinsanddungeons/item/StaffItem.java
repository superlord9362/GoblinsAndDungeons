package superlord.goblinsanddungeons.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.common.util.ManaStats;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;
import superlord.goblinsanddungeons.init.SoundInit;

public class StaffItem extends Item {
	
	public StaffItem(Properties properties) {
		super(properties);
	}

	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		System.out.println("Right Clicking");
		if (ManaStats.getManaStats(player).getManaLevel() > 0) {
			System.out.println("Has Mana");
			world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				GoblinSoulBulletEntity soulBullet = new GoblinSoulBulletEntity(world, player);
				soulBullet.getItem();
				soulBullet.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.addEntity(soulBullet);
			}
			player.addStat(Stats.ITEM_USED.get(this));
			if (!player.abilities.isCreativeMode) {
				stack.damageItem(1, player, (p_220009_1_) -> {
	                p_220009_1_.sendBreakAnimation(player.getActiveHand());
	             });
				ManaStats.getManaStats(player).addStats(-1);;;
				System.out.println(ManaStats.getManaStats(player).getManaLevel());
			}
		}
		return ActionResult.func_233538_a_(stack, world.isRemote());
	}

}
