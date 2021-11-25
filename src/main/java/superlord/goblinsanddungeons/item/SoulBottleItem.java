package superlord.goblinsanddungeons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SoulBottleItem extends Item {

	public SoulBottleItem(Properties properties) {
		super(properties);
	}

	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity user) {
        super.onItemUseFinish(stack, world, user);
        if (!stack.isFood() && user instanceof PlayerEntity) {
            if (!((PlayerEntity) user).isCreative()) {
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
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }

}
