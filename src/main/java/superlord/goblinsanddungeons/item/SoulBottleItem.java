package superlord.goblinsanddungeons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SoulBottleItem extends Item {

	public SoulBottleItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		super.finishUsingItem(stack, world, user);
		if (!stack.isEdible() && user instanceof Player) {
			if (!((Player) user).isCreative()) {
				stack.shrink(1);
			}
		}
		return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_42931_) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
		return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
	}

}
