package superlord.goblinsanddungeons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.entity.GoblinSoulBulletEntity;
import superlord.goblinsanddungeons.init.SoundInit;

public class GoblinSoulBulletItem extends Item {
	
	public GoblinSoulBulletItem(Properties properties) {
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote) {
			GoblinSoulBulletEntity soulBullet = new GoblinSoulBulletEntity(world, player);
			soulBullet.getItem();
			soulBullet.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.addEntity(soulBullet);
		}
		player.addStat(Stats.ITEM_USED.get(this));
		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}
	      return ActionResult.func_233538_a_(stack, world.isRemote());
	}
	
	public GoblinSoulBulletEntity createSoulBullet(World world, ItemStack stack, LivingEntity shooter) {
		GoblinSoulBulletEntity goblinSoulBullet = new GoblinSoulBulletEntity(world, shooter);
		return goblinSoulBullet;
	}

}
