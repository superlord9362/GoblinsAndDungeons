package superlord.goblinsanddungeons.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.GDArmorMaterial;
import superlord.goblinsanddungeons.init.ItemInit;

public class StealthRingItem extends ArmorItem {

	public static final ArmorMaterial MATERIAL = new GDArmorMaterial(GoblinsAndDungeons.MOD_ID + ":stealth_ring", 7, new int[] {0, 0, 0, 0}, 0, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, null);

	public StealthRingItem(Properties builderIn) {
		super(MATERIAL, EquipmentSlot.OFFHAND, builderIn);
	}

	@Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)) {
			if (p_77624_1_.getItem() == ItemInit.RING_OF_STEALTH.get()) {
				p_77624_3_.add(new TranslatableComponent("stealth_ring").withStyle(ChatFormatting.GRAY));
			}	
			if (p_77624_1_.getItem() == ItemInit.RING_OF_EXPERIENCE.get()) {
				p_77624_3_.add(new TranslatableComponent("experience_ring").withStyle(ChatFormatting.GRAY));
			}
			if (p_77624_1_.getItem() == ItemInit.RING_OF_GLORY.get()) {
				p_77624_3_.add(new TranslatableComponent("glory_ring").withStyle(ChatFormatting.GRAY));
			}
		} else {
			p_77624_3_.add(new TranslatableComponent("shift").withStyle(ChatFormatting.GRAY));
		}
	}

}
