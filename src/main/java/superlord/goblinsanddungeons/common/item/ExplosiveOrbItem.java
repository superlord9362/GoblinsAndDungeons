package superlord.goblinsanddungeons.common.item;

import java.util.Random;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.common.entity.ExplosiveOrb;
import superlord.goblinsanddungeons.init.SoundInit;

public class ExplosiveOrbItem extends Item {
	
	public ExplosiveOrbItem(Properties properties) {
		super(properties);
	}
	
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Random random = new Random();
		world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			ExplosiveOrb explosiveOrb = new ExplosiveOrb(world, player);
			explosiveOrb.setItem(stack);
			explosiveOrb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(explosiveOrb);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.isCreative()) {
			stack.shrink(1);
		}
	      return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
	
	public ExplosiveOrb createExplosiveOrb(Level world, ItemStack stack, LivingEntity shooter) {
		ExplosiveOrb explosiveOrb = new ExplosiveOrb(world, shooter);
		return explosiveOrb;
	}

}
