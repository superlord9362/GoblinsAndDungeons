package superlord.goblinsanddungeons.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.magic.PlayerSpells;

public class StaffItem extends Item {

	public StaffItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		PlayerSpells knownSpells = new PlayerSpells();
		if (player.getAbilities().instabuild && player.isShiftKeyDown()) {
			ItemStack newStack = new ItemStack(ItemInit.STAFF_BULLET.get());
			int damage = this.getDamage(player.getItemInHand(hand));
			player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_bullet"), true);
			player.setItemInHand(hand, newStack);
			newStack.setDamageValue(damage);
		} else if (player.isShiftKeyDown() && knownSpells.doesKnowSoulBullet()) {
			ItemStack newStack = new ItemStack(ItemInit.STAFF_BULLET.get());
			int damage = this.getDamage(player.getItemInHand(hand));
			player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_bullet"), true);
			player.setItemInHand(hand, newStack);
			newStack.setDamageValue(damage);
		} else if (player.isShiftKeyDown() && knownSpells.doesKnowSoulJump() && !knownSpells.doesKnowSoulBullet()) {
			ItemStack newStack = new ItemStack(ItemInit.STAFF_JUMP.get());
			int damage = this.getDamage(player.getItemInHand(hand));
			player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_soul_jump"), true);
			player.setItemInHand(hand, newStack);
			newStack.setDamageValue(damage);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (GoblinsDungeonsConfig.magicalWorld) {
			p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
			p_77624_3_.add(new TranslatableComponent("no_spell").withStyle(ChatFormatting.GRAY));
		}
	}

}
