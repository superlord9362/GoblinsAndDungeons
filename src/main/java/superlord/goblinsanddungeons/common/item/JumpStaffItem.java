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
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.init.ItemInit;
import superlord.goblinsanddungeons.init.ParticleInit;
import superlord.goblinsanddungeons.init.SoundInit;
import superlord.goblinsanddungeons.magic.PlayerManaProvider;
import superlord.goblinsanddungeons.magic.PlayerSpellsProvider;
import superlord.goblinsanddungeons.networking.ModMessages;
import superlord.goblinsanddungeons.networking.packet.CastSoulJumpC2SPacket;

public class JumpStaffItem extends Item {

	public JumpStaffItem(Properties p_41383_) {
		super(p_41383_);
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Random random = new Random();
		player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
			if ((mana.getMana() > 1 || player.isCreative()) && !player.isShiftKeyDown()) {
				world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundInit.SOUL_BULLET_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y + 0.5F, player.getDeltaMovement().z);
				AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(world, player.getX(), player.getY(), player.getZ());
				areaeffectcloudentity.setRadius(1F);
				areaeffectcloudentity.setRadiusOnUse(-0.5F);
				areaeffectcloudentity.setWaitTime(1);
				areaeffectcloudentity.setDuration(4);
				areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
				areaeffectcloudentity.setParticle(ParticleInit.SOUL_BULLET);
				world.addFreshEntity(areaeffectcloudentity);
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.isCreative()) {
					stack.hurtAndBreak(1, player, (p_220009_1_) -> {
						p_220009_1_.broadcastBreakEvent(hand);
					});
					ModMessages.sendToServer(new CastSoulJumpC2SPacket());
				}
			}
		});
		player.getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(spells -> {
			if (player.isShiftKeyDown()) {
				ItemStack newStack = new ItemStack(ItemInit.STAFF_AMETHYST.get());
				int damage = this.getDamage(player.getItemInHand(hand));
				player.displayClientMessage(new TranslatableComponent("item.goblinsanddungeons.current_spell_no_spell"), true);
				player.setItemInHand(hand, newStack);
				newStack.setDamageValue(damage);
			}	
		});
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (GoblinsDungeonsConfig.magicalWorld) {
			p_77624_3_.add(new TranslatableComponent("active_spell").withStyle(ChatFormatting.GRAY));
			p_77624_3_.add(new TranslatableComponent("soul_jump").withStyle(ChatFormatting.BLUE));
		}
	}

}
